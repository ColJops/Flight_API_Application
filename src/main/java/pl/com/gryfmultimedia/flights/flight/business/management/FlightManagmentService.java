package pl.com.gryfmultimedia.flights.flight.business.management;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gryfmultimedia.flights.flight.data.Flight;
import pl.com.gryfmultimedia.flights.flight.data.FlightRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class FlightManagmentService {
    
    private final FlightRepository flightRepository;
    
    public Flight saveFlight(Flight flight) {
        return flight;
    }
    
}
