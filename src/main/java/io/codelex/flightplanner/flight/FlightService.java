package io.codelex.flightplanner.flight;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id);
    }

    public void clearFlights() {
        flightRepository.clearFlights();
    }
}
