package pl.com.gryfmultimedia.flights.booking.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.com.gryfmultimedia.flights.booking.business.BookingException;
import pl.com.gryfmultimedia.flights.booking.business.BookingService;
import pl.com.gryfmultimedia.flights.flight.business.management.FlightManagmentService;
import pl.com.gryfmultimedia.flights.flight.data.Flight;
import pl.com.gryfmultimedia.flights.passenger.business.PassengerService;
import pl.com.gryfmultimedia.flights.passenger.data.Passenger;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.com.gryfmultimedia.flights.booking.data.Booking.newBooking;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private FlightManagmentService flightService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookingService bookingService;

    @Test
    void shouldCreateBooking() throws Exception {
        //given
        passengerService.createPassenger(new Passenger("Ed", "Sheeran", "ed@perfect.com"));
        flightService.saveFlight(new Flight("LO1234", "WAW", "LHR", now().plusHours(1), now().plusHours(3)));
        String bookingRequestJson = """
                {
                "flightNumber": "LO1234",
                "email": "ed@perfect.com",
                "seat": "17C",
                "price": "99.99",
                "currency": "GBP"
                }
                """;
        //when
        var result = mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingRequestJson));
        //then
        result.andExpect(status().isOk());
        String jsonResponse = result.andReturn()
                .getResponse()
                .getContentAsString();
        BookingDetails createdBooking = objectMapper.readValue(jsonResponse, BookingDetails.class);
        assertNotNull(createdBooking);
        assertNotNull(createdBooking.bookingId());
        assertEquals("Sheeran", createdBooking.lastName());
        assertEquals("LO1234", createdBooking.flightNumber());
    }

    @Test
    void shouldDeleteBooking() throws Exception {
        //given
        var passenger = passengerService.createPassenger(new Passenger("Antonio", "Vivaldi", "antonio@classic.at"));
        var flight = flightService.saveFlight(new Flight("IT1234", "VIE", "VCE", now().plusHours(24), now().plusHours(25)));
        var booking = bookingService.save(newBooking(flight, passenger, "17C" ,valueOf(119), "EUR"));
        //then
        var result = mockMvc.perform(delete("/bookings/%s".formatted(booking.getId())));
        //then
        result.andExpect(status().isNoContent());
        var exception = assertThrows(BookingException.class, () -> bookingService.get(booking.getId()));
        assertEquals("Booking not found", exception.getMessage());
    }

}