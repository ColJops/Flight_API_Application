package pl.com.gryfmultimedia.flights.booking.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.com.gryfmultimedia.flights.booking.data.Booking;
import pl.com.gryfmultimedia.flights.flight.business.management.FlightManagmentService;
import pl.com.gryfmultimedia.flights.flight.data.Flight;
import pl.com.gryfmultimedia.flights.passenger.business.PassengerService;
import pl.com.gryfmultimedia.flights.passenger.data.Passenger;
import java.math.BigDecimal;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static pl.com.gryfmultimedia.flights.booking.data.Booking.newBooking;

@SpringBootTest
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private FlightManagmentService flightService;

    private Flight flight;
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        var flight = new Flight(randomAlphanumeric(6), randomAlphabetic(3), randomAlphanumeric(3), now(), now().plusHours(5));
        var passenger = new Passenger(randomAlphabetic(10), randomAlphabetic(10), randomAlphanumeric(15));
        this.flight = flightService.saveFlight(flight);
        this.passenger = passengerService.createPassenger(passenger);
    }

    @Test
    void shouldBookTicket() throws BookingException {
        //given
        Booking request = newBooking(flight, passenger,"17B", BigDecimal.valueOf(99.99), "USD");
        //when
        Booking result = bookingService.save(request);
        //then
        assertNotNull(result);
    }

    @Test
    void shouldNotBookTicketWhenFlightDoesNotExist() {
        //given
        Booking request = newBooking(null, passenger,"17B", BigDecimal.valueOf(99.99), "USD");
        //when
        Throwable exception = assertThrows(BookingException.class, () -> bookingService.save(request));
        //then
        assertEquals("Flight not found", exception.getMessage());
    }

    @Test
    void shouldNotBookTicketWhenPassengerDoesNotExist() {
        //given
        Booking request = newBooking(flight, null,"17B", BigDecimal.valueOf(99.99), "USD");
        //when
        Throwable exception = assertThrows(BookingException.class, () -> bookingService.save(request));
        //then
        assertEquals("Passenger not found", exception.getMessage());
    }

    @Test
    void shouldNotBookTicketWhenSeatIsNotAvailable() throws BookingException {
        //given
        bookingService.save(newBooking(flight, passenger, "17B", BigDecimal.valueOf(99.99), "USD"));
        Booking request = newBooking(flight, passenger, "17B", BigDecimal.valueOf(99.99), "USD");
        //when
        Throwable exception = assertThrows(BookingException.class, () -> bookingService.save(request));
        //then
        assertEquals("Seat not available", exception.getMessage());
    }

    @Test
    void shouldChangeTicket() throws BookingException {
        //given
        var booking = bookingService
                .save(newBooking(flight, passenger, "17B", BigDecimal.valueOf(99.99), "USD"));
        //when
        var result = bookingService.changeSeat(booking.getId(), "18F");
        //then
        assertEquals("18F", result.getSeat());
    }

    @Test
    void shouldNotChangeTicketWhenSeatIsNotAvailable() throws BookingException {
        //given

        var bookingForSeat17F = bookingService
                .save(newBooking(flight, passenger, "17B", BigDecimal.valueOf(69.99), "USD"));
        var bookingForSeat18F = bookingService
                .save(newBooking(flight, passenger, "18F", BigDecimal.valueOf(69.99), "USD"));
        //when
        Throwable exception = assertThrows(BookingException.class, () -> bookingService.changeSeat(bookingForSeat18F.getId(), "17F"));
        //then
        assertEquals("Seat not available", exception.getMessage());
    }
}