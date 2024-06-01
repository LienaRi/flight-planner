package io.codelex.flightplanner.repositories;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AirportInMemoryRepository {
    private final List<Airport> airports;

    public AirportInMemoryRepository(List<Airport> airports) {
        this.airports = airports;
    }

    public Airport[] getAirports(String search) {
        List<Airport> airportList = new ArrayList<>();
        if (airports.isEmpty()) {
            return new Airport[0];
        }
        for (Airport airport : airports) {
            if (airport.getAirport().toLowerCase().contains(search)
                    || airport.getCountry().toLowerCase().contains(search)
                    || airport.getCity().toLowerCase().contains(search)) {
                airportList.add(airport);
            }
        }
        return airportList.toArray(new Airport[0]);
    }

    public void addAirport(Airport airport) {
        if (airports.isEmpty() || !airports.contains(airport)) {
            airports.add(airport);
        }
    }

    public void clearAirports() {
        airports.clear();
    }
}
