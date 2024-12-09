package pl.com.gryfmultimedia.flights.flight.web;

import java.time.LocalDateTime;

public record FlightDetails(String number, String origin, String destination, String price, LocalDateTime departure, LocalDateTime arrival) {
}
