package pl.com.gryfmultimedia.flights.booking.mapping;


import org.springframework.stereotype.Component;
import pl.com.gryfmultimedia.flights.booking.data.Booking;
import pl.com.gryfmultimedia.flights.booking.web.BookingDetails;
import pl.com.gryfmultimedia.flights.booking.web.BookingRequest;
import pl.com.gryfmultimedia.flights.flight.business.discovery.FlightDiscoveryService;
import pl.com.gryfmultimedia.flights.passenger.business.PassengerService;


public interface BookingMapping {

    @Component
    class ApiMapping {

        private final FlightDiscoveryService flightDiscoveryService;
        private final PassengerService passengerService;


        public ApiMapping(FlightDiscoveryService flightDiscoveryService, PassengerService passengerService) {
            this.flightDiscoveryService = flightDiscoveryService;
            this.passengerService = passengerService;
        }

        public Booking map(BookingRequest request) {
            var maybeFlight = flightDiscoveryService.getByFlightNumber(request.flightNumber());
            var maybePassenger = passengerService.findPassengerByEmail(request.email());
            return Booking.newBooking(
                    maybeFlight.orElse(null),
                    maybePassenger.orElse(null),
                    request.seat(),
                    request.price(),
                    request.currency()
            );
        }

    }

    @Component
    class ModelMapping {
        public BookingDetails map(Booking booking) {
            return new BookingDetails(
                    booking.getId(),
                    booking.getFlight().getNumber(),
                    booking.getSeat(),
                    booking.getPassenger().getFirstName(),
                    booking.getPassenger().getLastName(),
                    booking.getFlight().getOrigin(),
                    booking.getFlight().getDestination(),
                    booking.getFlight().getDeparture(),
                    booking.getFlight().getArrival()
            );
        }
    }
}
