package io.codelex.flightplanner.airport;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirportRepositoryTest {

    @Test
    void getAirports() {
        List<Airport> airports = new ArrayList<>();
        airports.add(new Airport("Latvia", "Riga", "RIX"));
        String searchParam = "Latv";

        List<Airport> airportList = new ArrayList<>();
        for (Airport airport : airports) {
            if(airport.getAirport().toLowerCase().contains(searchParam.toLowerCase().trim())
                    || airport.getCountry().toLowerCase().contains(searchParam.toLowerCase().trim())
                    || airport.getCity().toLowerCase().contains(searchParam.toLowerCase().trim())) {
                airportList.add(airport);
            }
        }
        Airport[] result = airportList.toArray(new Airport[0]);

        assertEquals("RIX", result[0].toString());
    }
}