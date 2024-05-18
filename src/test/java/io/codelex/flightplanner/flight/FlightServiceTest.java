package io.codelex.flightplanner.flight;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FlightServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void dateTimeFormatter() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Flight flight = new Flight(
                null, null, "Airbaltic",
                LocalDateTime.of(2022, 2, 2, 14, 44),
                LocalDateTime.of(2022, 2, 2, 15, 55)
        );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateString1 = formatter.format(flight.getDepartureTime());
        LocalDateTime date1 = LocalDateTime.parse(dateString1, formatter);
        flight.setDepartureTime(date1);
        String dateString2 = formatter.format(flight.getArrivalTime());
        LocalDateTime date2 = LocalDateTime.parse(dateString2, formatter);
        flight.setArrivalTime(date2);

        MockHttpServletRequestBuilder requestBuilder = put("/admin-api/flights", flight)
                .with(user("codelex-admin").password("Password123"))
                .content(objectMapper.writeValueAsString(flight))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is(201)).andReturn();
        String content = result.getResponse().getContentAsString();
        Flight response = objectMapper.readValue(content, Flight.class);

        assertEquals(flight.getDepartureTime(), response.getDepartureTime());
        assertEquals(flight.getArrivalTime(), response.getArrivalTime());

    }


}