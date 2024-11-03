package pl.com.gryfmultimedia.flights.flight.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightRepository extends CrudRepository<Flight, UUID> {

    @Query("""
    SELECT f FROM Flight f
    WHERE f.origin = :origin
    AND f.destination = :destination
    AND f.departure > CURRENT_TIMESTAMP
    """)
    List<Flight> findFlight (
            @Param("origin") String origin,
            @Param("destination") String destination);

    Optional<Flight> findFlightByNumber(String flightNumber);
}
