package pl.com.gryfmultimedia.flights.flight.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateFlight() {
        //given
        var request = new FlightCreationRequest("WAW", "DXB", now().plusHours(1), now().plusHours(9));
        //when
        var response = restTemplate
                .postForEntity(
                        "http://localhost:"
                                + port +
                                "/flights",
                        request,
                        FlightDetails.class);
        //when
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request, response.getBody());
        assertEquals("WAW", response.getBody().origin());
        assertEquals("DXB", response.getBody().destination());
        assertNotNull(response.getBody().price());
    }

    @Test
    void shouldDiscoverFlight() {
        //given
        var request = new FlightCreationRequest("WAW", "DXB", now().plusHours(1), now().plusHours(9));
        restTemplate.postForEntity("http://localhost:"
                + port +
                "/flights/management", request, FlightDetails.class);
        //when
        var response = restTemplate.getForEntity(
                "http://localhost"
                + port
                +"/flights/discovery?origin=WAW&destination=CXB", FlightDetails.class);
        //when
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<FlightDetails> matchedFlights = List.of(response.getBody());
    }

}