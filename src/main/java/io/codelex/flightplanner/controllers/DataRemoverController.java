package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.services.AirportService;
import io.codelex.flightplanner.services.FlightService;
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
