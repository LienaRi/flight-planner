package io.codelex.flightplanner.airport;

import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AirportRepository {
    private static List<Airport> airports = new ArrayList<>();


    public Airport[] getAirports() {
        if (!airports.isEmpty()) {
            return airports.toArray(new Airport[airports.size()]);
        } else {
            return new Airport[0];
        }

    }

    public void addAirport(Airport airport) {
        if (airports.isEmpty() || !airports.contains(airport)) {
            airports.add(airport);
        }
    }
}
