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
        return switch (request) {
            case FlightDiscoveryQuery.forOriginAndDestinationDepartureDate rq->
                flightRepository.findFlight(rq.origin(), rq.destination(), rq.departureDate());
            case FlightDiscoveryQuery.forOriginAndDepartureDate rq->
                flightRepository.findFlight(rq.origin(), rq.departureDate());
            case FlightDiscoveryQuery.forOriginDestination rq ->
                flightRepository.findFlight(rq.origin(), rq.destination());
        };
    }

    public Optional<Flight> getByFlightNumber(String flightNumber) {
        return flightRepository.findFlightByNumber(flightNumber);
    }
}
