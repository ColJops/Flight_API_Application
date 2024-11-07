package pl.com.gryfmultimedia.flights.flight.business.discovery;

import java.util.Date;

public sealed interface FlightDiscoveryQuery {

    record forOriginAndDestinationDepartureDate(String origin, String destination, Date departureDate)
    implements FlightDiscoveryQuery {}

    record forOriginAndDepartureDate(String origin, Date departureDate)
    implements FlightDiscoveryQuery {}

    record forOriginDestination(String origin, String destination)
    implements FlightDiscoveryQuery {}
}
