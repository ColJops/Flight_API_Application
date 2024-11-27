package pl.com.gryfmultimedia.flights.booking.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.gryfmultimedia.flights.booking.business.BookingException;
import pl.com.gryfmultimedia.flights.booking.business.BookingService;
import pl.com.gryfmultimedia.flights.booking.mapping.BookingMapping;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
class BookingController {

    private final BookingService bookingService;
    private final BookingMapping.ApiMapping apiMapping;
    private final BookingMapping.ModelMapping modelMapping;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    BookingDetails createBooking(@RequestBody  BookingRequest request) throws BookingException {
        var booking = apiMapping.map(request);
        var savedBooking = bookingService.save(booking);
        return modelMapping.map(savedBooking);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookingDetails getBooking(@PathVariable("id") UUID bookingId) throws BookingException {
        var booking = bookingService.get(bookingId);
        return modelMapping.map(booking);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Collection<BookingDetails> getBookingsForPassenger(@RequestParam("email") String email) throws BookingException {
        var matchedBooking = bookingService.getByPassengerEmail(email);
        return matchedBooking.stream()
                .map(modelMapping::map)
                .toList();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookingDetails updateBooking(@PathVariable("id") UUID bookingId, @RequestBody String newSeat) throws BookingException {
        var booking = bookingService.changeSeat(bookingId, newSeat);
        return  modelMapping.map(booking);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelBooking(@PathVariable("id") UUID bookingId) throws BookingException {
        bookingService.cancel(bookingId);
    }

    @ExceptionHandler(BookingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleBookingException(BookingException exception) {
        return exception.getMessage();
    }
}
