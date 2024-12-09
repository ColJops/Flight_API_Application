package pl.com.gryfmultimedia.flights.flight.business.discovery;

import java.math.BigDecimal;

public record TicketPrice(BigDecimal price, String currency) {

    public String toString() { return "%.2f %s".formatted(price, currency); }
}
