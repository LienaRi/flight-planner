package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repositories.AirportDbRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

public class AirportDbService implements AirportService {
    private final AirportDbRepository airportDbRepository;

    public AirportDbService(AirportDbRepository airportDbRepository) {
        this.airportDbRepository = airportDbRepository;
    }

    @Override
    public Airport[] getAirports(String search) {
        if (search == null || search.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Airport not found");
        }
        String cleanedSearch = cleanSearchPhrase(search);
        return airportDbRepository.findAllByCountryContainingIgnoreCaseOrCityContainingIgnoreCaseOrAirportContainingIgnoreCase
                (cleanedSearch, cleanedSearch, cleanedSearch).toArray(new Airport[0]);
    }

    @Override
    public String cleanSearchPhrase(String search) {
        return search.replaceAll("[^a-zA-Z0-9]", "").toLowerCase().trim();
    }

    @Override
    public void addAirportFromFlight(Airport ofFlight) {
        airportDbRepository.save(ofFlight);
    }

    @Override
    public void clearAirports() {
        airportDbRepository.deleteAll();
    }
}
