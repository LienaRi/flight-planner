package io.codelex.flightplanner.airport;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport[] getAirports(String search) {
        if (search == null || search.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Airport not found");
        }
        return airportRepository.getAirports(search.toLowerCase().trim());
    }

    public void addAirportFromFlight(Airport ofFlight) {
        airportRepository.addAirport(new Airport(ofFlight.getCountry(), ofFlight.getCity(), ofFlight.getAirport()));
    }

    public void clearAirports() {
        airportRepository.clearAirports();
    }
}
