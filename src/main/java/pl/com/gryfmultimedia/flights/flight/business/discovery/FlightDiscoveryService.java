package pl.com.gryfmultimedia.flights.flight.business.discovery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gryfmultimedia.flights.flight.data.Flight;
import pl.com.gryfmultimedia.flights.flight.data.FlightRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlightDiscoveryService {

    private final FlightRepository flightRepository;

    public List<Flight> search(FlightDiscoveryQuery request) {
        return null;
    }

    public Optional<Flight> getByFlightNumber(String flightNumber) {
        return Optional.empty();
    }
}
