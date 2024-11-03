package pl.com.gryfmultimedia.flights.passenger.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gryfmultimedia.flights.passenger.data.Passenger;
import pl.com.gryfmultimedia.flights.passenger.data.PassengerRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class PassengerService {


    private final PassengerRepository passengerRepository;

    public Passenger createPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public Optional<Passenger> findPassengerByEmail(String email) {
        return passengerRepository.findPassengerByEmail(email);
    }
}
