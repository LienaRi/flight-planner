package io.codelex.flightplanner.services;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.repositories.AirportInMemoryRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AirportInMemoryService implements AirportService {
    private final AirportInMemoryRepository airportInMemoryRepository;

    public AirportInMemoryService(AirportInMemoryRepository airportInMemoryRepository) {
        this.airportInMemoryRepository = airportInMemoryRepository;
    }

    @Override
    public Airport[] getAirports(String search) {
        if (search == null || search.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Airport not found");
        }
        return airportInMemoryRepository.getAirports(cleanSearchPhrase(search));
    }

    @Override
    public String cleanSearchPhrase(String search) {
        return search.replaceAll("[^a-zA-Z0-9]", "").toLowerCase().trim();
    }

    @Override
    public void addAirportFromFlight(Airport ofFlight) {
        airportInMemoryRepository.addAirport(new Airport(ofFlight.getCountry(), ofFlight.getCity(), ofFlight.getAirport()));
    }

    @Override
    public void clearAirports() {
        airportInMemoryRepository.clearAirports();
    }
}
