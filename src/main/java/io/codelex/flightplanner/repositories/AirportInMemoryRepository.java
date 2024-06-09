package io.codelex.flightplanner.repositories;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AirportInMemoryRepository {
    private final List<Airport> airports;

    public AirportInMemoryRepository(List<Airport> airports) {
        this.airports = airports;
    }

    public Optional<Airport> getAirports(String search) {
        return airports.stream().filter(airport -> checkIfAirportContainsSearchValue(airport, search)).findFirst();
    }

    public void addAirport(Airport airport) {
        if (airports.isEmpty() || !airports.contains(airport)) {
            airports.add(airport);
        }
    }

    public void clearAirports() {
        airports.clear();
    }

    private boolean checkIfAirportContainsSearchValue(Airport airport, String search) {
        return airport.getAirport().toLowerCase().contains(search)
                || airport.getCountry().toLowerCase().contains(search)
                || airport.getCity().toLowerCase().contains(search);
    }
}
