package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.stereotype.Service;

@Service
public interface AirportService {

   Airport[] getAirports(String search);

    String cleanSearchPhrase(String search);

    void addAirportFromFlight(Airport ofFlight);

    void clearAirports();
}
