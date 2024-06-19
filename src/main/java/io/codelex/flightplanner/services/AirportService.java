package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AirportService {

    Optional<Airport> findAirport(String search);

    String cleanSearchPhrase(String search);

    void addAirportFromFlight(Airport ofFlight);

    void clearAirports();
}
