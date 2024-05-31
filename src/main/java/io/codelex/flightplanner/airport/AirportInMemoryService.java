package io.codelex.flightplanner.airport;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
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
        return airportInMemoryRepository.getAirports(search.toLowerCase().trim());
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
