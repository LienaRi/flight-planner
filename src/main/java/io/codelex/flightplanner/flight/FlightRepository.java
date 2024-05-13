package io.codelex.flightplanner.flight;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {
    protected List<Flight> flights;

    public FlightRepository() {
        flights = new ArrayList<>();
    }

    public Flight findById(Long id) {
        return flights.stream()
                .filter(flight -> flight.getId().equals(id))
                .findAny()
                .orElse(null);
    }


    public void clearFlights() {
        flights.clear();
    }
}
