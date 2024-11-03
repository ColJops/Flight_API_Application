package pl.com.gryfmultimedia.flights.booking.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, UUID> {

    @Query  ("""
            SELECT b FROM Booking b
            WHERE b.passenger.email = :email
            ORDER BY b.flight.departure ASC
            """)
    Collection<Booking> findBookingByPassengerEmail(@Param("email") String email);
}
