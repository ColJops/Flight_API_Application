package pl.com.gryfmultimedia.flights.flight.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    @Column(name = "number", length = 6, unique = true)
    private String number;
    @Column(name = "origin", length = 3)
    private String origin;
    @Column(name = "destination", length = 3)
    private String destination;
    @Column(name = "departure")
    private LocalDateTime departure;
    @Column(name = "arrival")
    private LocalDateTime arrival;

    public Flight(String number, String origin, String destination, LocalDateTime departure, LocalDateTime arrival) {
        this.number = number;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
    }
}
