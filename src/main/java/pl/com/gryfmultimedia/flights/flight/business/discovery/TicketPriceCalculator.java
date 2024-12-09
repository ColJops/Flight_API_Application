package pl.com.gryfmultimedia.flights.flight.business.discovery;

import pl.com.gryfmultimedia.flights.flight.data.Flight;

@FunctionalInterface
public interface TicketPriceCalculator {

    TicketPrice calculate(Flight flight);
}
