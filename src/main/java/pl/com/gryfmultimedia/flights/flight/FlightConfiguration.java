package pl.com.gryfmultimedia.flights.flight;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.gryfmultimedia.flights.flight.business.discovery.TicketPrice;
import pl.com.gryfmultimedia.flights.flight.business.discovery.TicketPriceCalculator;
import pl.com.gryfmultimedia.flights.flight.business.management.FlightNumberGenerator;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
class FlightConfiguration {

    @Bean
    TicketPriceCalculator ticketPriceCalculator() {
        return flight -> new TicketPrice(BigDecimal.valueOf(109.99), "USD");
    }

    @Bean
    FlightNumberGenerator flightNumberGenerator() {
        var counter = new AtomicInteger(1000);
        return () -> "SQ" + counter.getAndIncrement();
    }
}
