package pl.com.gryfmultimedia.flights.flight.web;

import java.time.LocalDateTime;

public record FlightCreationRequest(String origin, String destination, LocalDateTime departure, LocalDateTime arrival) {
}
