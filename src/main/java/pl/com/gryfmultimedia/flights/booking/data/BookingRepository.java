package pl.com.gryfmultimedia.flights.booking.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface BookingRepository extends CrudRepository<Booking, UUID> {

    @Query  ("""
            SELECT b FROM Booking b
            WHERE b.passenger.email = :email
            ORDER BY b.flight.departure ASC
            """)
    Collection<Booking> findBookingByPassengerEmail(@Param("email") String email);

    @Query("""
            SELECT (COUNT (b) > 0)
            FROM Booking b
            WHERE b.flight.id = :flight
            AND b.seat = :seat
    """)
    boolean existsBookingByFlightIdAndSeat(
            @Param("flight") UUID flightId,
            @Param("seat") String seat);
}
