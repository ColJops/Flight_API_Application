package pl.com.gryfmultimedia.flights.passenger.data;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PassengerRepository  extends CrudRepository<Passenger, UUID> {

    Optional<Passenger> findPassengerByEmail(String email);
}
