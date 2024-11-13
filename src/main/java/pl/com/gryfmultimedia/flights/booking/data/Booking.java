package pl.com.gryfmultimedia.flights.booking.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.gryfmultimedia.flights.flight.data.Flight;
import pl.com.gryfmultimedia.flights.passenger.data.Passenger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight", referencedColumnName = "id")
    private Flight flight;
    @ManyToOne
    @JoinColumn(name = "passenger", referencedColumnName = "id")
    private Passenger passenger;
    @Column(name = "seat", length = 3)
    private String seat;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "currency", length = 3)
    private String currency;
    @Column(name = "created")
    private LocalDateTime created;

    public Booking(Flight flight, Passenger passenger, String seat, BigDecimal price, String currency, LocalDateTime created) {
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;
        this.price = price;
        this.currency = currency;
        this.created = created;
    }

    public static Booking newBooking(Flight flight, Passenger passenger, String seat, BigDecimal price, String currency) {
      return new Booking(flight, passenger, seat, price, currency, now());
    }

    public void changeSeat(String newSeat) {
        this.seat = newSeat;
    }
}
