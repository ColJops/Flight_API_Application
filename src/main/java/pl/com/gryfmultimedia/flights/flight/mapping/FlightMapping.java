package pl.com.gryfmultimedia.flights.flight.mapping;

import org.springframework.stereotype.Component;
import pl.com.gryfmultimedia.flights.flight.business.discovery.FlightDiscoveryQuery;
import pl.com.gryfmultimedia.flights.flight.business.discovery.TicketPriseAwareFlight;
import pl.com.gryfmultimedia.flights.flight.data.Flight;
import pl.com.gryfmultimedia.flights.flight.web.FlightCreationRequest;
import pl.com.gryfmultimedia.flights.flight.web.FlightDetails;

import java.util.Date;

public interface FlightMapping {

    @Component
    class ApiMapping {

        public Flight map(FlightCreationRequest request) {
            return new Flight(
                    null,
                    request.origin(),
                    request.destination(),
                    request.departure(),
                    request.arrival()
            );
        }

        public FlightDiscoveryQuery map(String origin, String destination, Date departure) {
            if (origin == null || origin.isEmpty()) {
                throw new IllegalArgumentException("Origin is required");
            }
            if (destination != null && departure != null) {
                return new FlightDiscoveryQuery
                        .forOriginAndDestinationDepartureDate(origin, destination, departure);
            } else if (departure != null) {
                return new FlightDiscoveryQuery.forOriginAndDepartureDate(origin, departure);
            } else if (destination != null) {
                return new FlightDiscoveryQuery
                        .forOriginDestination(origin, destination);
            }
            throw new IllegalArgumentException("Either destination or departure date is required");
        }
    }

    @Component
    class ModelMapping {
        public FlightDetails map(Flight flight) {
            return new FlightDetails(
                    flight.getNumber(),
                    flight.getOrigin(),
                    flight.getDestination(),
                    null,
                    flight.getDeparture(),
                    flight.getArrival()
            );
        }

        public FlightDetails map(TicketPriseAwareFlight ticketPriseAwareFlight) {
            var flight = ticketPriseAwareFlight.flight();
            var ticketPrice = ticketPriseAwareFlight.ticketPrice();
            return new FlightDetails(
              flight.getNumber(),
              flight.getOrigin(),
                    flight.getDestination(),
                    ticketPrice.toString(),
                    flight.getDeparture(),
                    flight.getArrival()
            );
        }
    }
}

