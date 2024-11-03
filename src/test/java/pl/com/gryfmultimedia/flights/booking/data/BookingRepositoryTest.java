package pl.com.gryfmultimedia.flights.booking.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.com.gryfmultimedia.flights.flight.data.Flight;
import pl.com.gryfmultimedia.flights.passenger.data.Passenger;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void shouldFindBookingFetchingFlightAndPassenger() {
        //given
        var passenger = new Passenger("Wolfgang Amadeus", "Mozart", "einklas@mozart.at");
        var flight = new Flight("LO1234", "KRK", "VIE", LocalDateTime.now().plusHours(24), LocalDateTime.now().plusHours(25));

        var bookingId = bookingRepository
                .save(Booking.newBooking(flight, passenger, "17F", BigDecimal.valueOf(99.99), "EUR"))
                .getId();
        //when
        var booking = bookingRepository.findById(bookingId);
        //then
        assertEquals("Wolfgang Amadeus",
                booking.map(Booking::getPassenger)
                        .map(Passenger::getFirstName)
                        .orElseThrow()
        );
        assertEquals("LO1234",
                 booking.map(Booking::getFlight)
                         .map(Flight::getNumber)
                         .orElseThrow()
        );
    }

}