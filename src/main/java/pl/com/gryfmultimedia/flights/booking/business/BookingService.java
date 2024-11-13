package pl.com.gryfmultimedia.flights.booking.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gryfmultimedia.flights.booking.data.Booking;
import pl.com.gryfmultimedia.flights.booking.data.BookingRepository;

import java.util.Collection;
import java.util.UUID;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private boolean seatIsAlreadyBookedForFlight(UUID flightId, String seat) {
        return bookingRepository.existsBookingByFlightIdAndSeat(flightId, seat);
    }

    public Booking save(Booking booking) throws BookingException {
        if(Objects.isNull(booking.getPassenger())) {
            throw new BookingException("Passenger not found");
        }
        if(Objects.isNull(booking.getFlight())) {
            throw new BookingException("Flight not found");
        }
        if(seatIsAlreadyBookedForFlight(booking.getFlight().getId(), booking.getSeat())) {
            throw new BookingException("Seat not available");
        }
        return bookingRepository.save(booking);
    }

    public Booking get(UUID bookingId) throws BookingException {
        return bookingRepository.findById(bookingId).orElseThrow(()-> new BookingException("Booking not found"));
    }

    public Collection<Booking> getByPassengerEmail(String passengerEmail) {
        return bookingRepository.findBookingByPassengerEmail(passengerEmail);
    }

    public Booking changeSeat(UUID bookingId, String newSeat) throws BookingException {
        var booking = get(bookingId);
        if (seatIsAlreadyBookedForFlight(booking.getFlight().getId(), newSeat)) {
            throw new BookingException("Seat not avaible");
        }
        booking.changeSeat(newSeat);
        return booking;
    }

    public void cancel(UUID bookingId) throws BookingException {
        var booking = get(bookingId);
        bookingRepository.delete(booking);
    }

}
