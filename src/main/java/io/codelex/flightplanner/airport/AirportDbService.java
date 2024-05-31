package io.codelex.flightplanner.airport;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AirportDbService {
    private final AirportDbRepository airportDbRepository;

    public AirportDbService(AirportDbRepository airportDbRepository) {
        this.airportDbRepository = airportDbRepository;
    }

    public Airport[] getAirports(String search) {
        if (search == null || search.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Airport not found");
        }
        return airportDbRepository.getAirports(search.toLowerCase().trim());
    }

    public void addAirportFromFlight(Airport ofFlight) {
        airportDbRepository.addAirport(new Airport(ofFlight.getCountry(), ofFlight.getCity(), ofFlight.getAirport()));
    }

    public void clearAirports() {
        airportDbRepository.clearAirports();
    }
}
