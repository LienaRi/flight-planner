package io.codelex.flightplanner.flight;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelex.flightplanner.airport.Airport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    FlightController flightController;

    static ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void addFlightTest() throws Exception {

        Airport from = new Airport("LV", "Riga", "RIX");
        Airport to = new Airport("IT", "Milan", "BGY");
        String carrier = "AirBaltic";
        LocalDateTime departureTime = LocalDateTime.of(2022, 04, 22,  07, 00);
        LocalDateTime arrivalTime = LocalDateTime.of(2022, 04, 22,  10, 00);;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Flight request = new Flight(from, to, carrier, departureTime, arrivalTime);


        MockHttpServletRequestBuilder requestBuilder = put("/admin-api/flights", request)
                .with(user("codelex-admin").password("Password123"))
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is(201)).andReturn();
        String content = result.getResponse().getContentAsString();
        Flight response = objectMapper.readValue(content, Flight.class);

        assertNotNull(response.getId());
        assertEquals(response.getFrom(), from);
        assertEquals(response.getTo(), to);
        assertEquals(response.getCarrier(), carrier);
        assertEquals(response.getDepartureTime(), formatter.format(departureTime));
        assertEquals(response.getArrivalTime(), formatter.format(arrivalTime));




    }

    @Test
    void searchFlightsTest() {
    }

    @Test
    void parseJsonTest() throws JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Airport from = new Airport("LV", "Riga", "RIX");
        Airport to = new Airport("IT", "Milan", "BGY");
        String carrier = "AirBaltic";
        LocalDateTime departureTime = LocalDateTime.parse("2022-04-22 07:00", formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse("2022-04-22 10:00", formatter);

//
//        Flight flight = new Flight(from, to, carrier, formatter.format(departureTime), formatter.format(arrivalTime));
    }
}