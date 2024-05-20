package io.codelex.flightplanner.dataRemover;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.flight.FlightService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataRemoverController {
    private final FlightService flightService;
    private final AirportService airportService;

    public DataRemoverController(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    @PostMapping("/testing-api/clear")
    public void clearFlights() {
        flightService.clearFlights();
        airportService.clearAirports();
    }

}
