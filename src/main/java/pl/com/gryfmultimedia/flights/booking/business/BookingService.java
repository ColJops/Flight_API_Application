package pl.com.gryfmultimedia.flights.booking.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gryfmultimedia.flights.booking.data.Booking;
import pl.com.gryfmultimedia.flights.booking.data.BookingRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking save(Booking booking) {
        return null;
    }

    public Booking get(UUID bookingId) {
        return null;
    }

    public Collection<Booking> geByPassengerEmail(String passengerEmail) {
        return List.of();
    }

    public Booking changeSeat(UUID bookingId, String newSeat) { return null; }

    public void cancel(UUID bookingId) {}
}
