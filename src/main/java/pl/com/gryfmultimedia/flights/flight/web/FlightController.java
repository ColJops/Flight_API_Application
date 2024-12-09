package pl.com.gryfmultimedia.flights.flight.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.gryfmultimedia.flights.flight.business.discovery.FlightDiscoveryService;
import pl.com.gryfmultimedia.flights.flight.business.management.FlightManagmentService;
import pl.com.gryfmultimedia.flights.flight.mapping.FlightMapping;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/flights")
@AllArgsConstructor
class FlightController {

    private final FlightDiscoveryService discoveryService;
    private final FlightManagmentService managementService;
    private final FlightMapping.ApiMapping apiMapping;
    private final FlightMapping.ModelMapping modelMapping;

    @PostMapping("/management")
    @ResponseStatus(HttpStatus.CREATED)
    FlightDetails createFlight(@RequestBody FlightCreationRequest request) {
        var flight = apiMapping.map(request);
        var savedFlight = managementService.saveFlight(flight);
        return modelMapping.map(savedFlight);
    }

    @GetMapping("/discovery")
    @ResponseStatus(HttpStatus.OK)
    List<FlightDetails> discoverFlights(
            @RequestParam("origin") String origin,
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "departure", required = false) Date departure
    ) {
        var query = apiMapping.map(origin, destination, departure);
        return discoveryService.search(query)
                .stream()
                .map(modelMapping::map)
                .toList();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .body(ex.getMessage());
    }

}
