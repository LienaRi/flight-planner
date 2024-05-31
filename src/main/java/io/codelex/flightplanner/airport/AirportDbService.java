package io.codelex.flightplanner.airport;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

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
        return null;
//        return airportDbRepository.findBy(search.toLowerCase().trim());
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
