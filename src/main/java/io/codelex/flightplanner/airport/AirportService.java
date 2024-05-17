package io.codelex.flightplanner.airport;

import io.codelex.flightplanner.flight.Flight;
import org.springframework.stereotype.Service;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport[] getAirports() {
        return  airportRepository.getAirports();
    }

    public void addAirport(Airport airport) {
        airportRepository.addAirport(airport);
    }

    public void addAirportFromFlight(Airport ofFlight) {
        airportRepository.addAirport(new Airport(ofFlight.getCountry(), ofFlight.getCity(), ofFlight.getAirport()));
    }
}
