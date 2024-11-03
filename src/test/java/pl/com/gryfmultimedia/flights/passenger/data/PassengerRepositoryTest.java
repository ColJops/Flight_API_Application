package pl.com.gryfmultimedia.flights.passenger.data;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository passengerRepository;


    @Test
    void shouldPersistPassenger() {
        //given
        UUID id = UUID.randomUUID();
        Passenger unsavePassenger = new Passenger(id, "Elton", "John", "elton@john.uk");
        //when
        Passenger savedPassenger = passengerRepository.save(unsavePassenger);
        //when
        assertNotNull(savedPassenger);
    }

    @Test
    void shouldFindPassengerById() {
        //given
        UUID id = UUID.randomUUID();
        Passenger passengerId = new Passenger(id, "Michael", "Jackson", "michael@jackson.uk");
        passengerRepository.save(passengerId);
        //when
        Optional<Passenger> foundPassenger = passengerRepository.findById(passengerId.getId());
        //then
        assertTrue(foundPassenger.isPresent());
        assertEquals("Michael", foundPassenger.get().getFirstName());
        assertEquals("Jackson", foundPassenger.get().getLastName());
    }

    @Test
    void shouldFindPassengerByEmail() {
        UUID id = UUID.randomUUID();
        Passenger passenger = new Passenger(id, "Brian", "Adams", "brian@adams.com");
        passengerRepository.save(passenger);
        //when
        Optional<Passenger> foundPassenger = passengerRepository.findPassengerByEmail(passenger.getEmail());
        //then
        assertTrue(foundPassenger.isPresent());
        assertEquals("Brian", foundPassenger.get().getFirstName());
        assertEquals("Adams", foundPassenger.get().getLastName());
    }

    @Test
    void shouldNotFindPassengerByUnknowEmail() {
        //given
        //when
        Optional<Passenger> maybePassenger = passengerRepository.findPassengerByEmail("unknow@email.com");
        //then
        assertTrue(maybePassenger.isEmpty());
    }
}