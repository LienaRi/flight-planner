package io.codelex.flightplanner.dataremover;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.flight.FlightInMemoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataRemoverController {
    private final FlightInMemoryService flightInMemoryService;
    private final AirportService airportService;

    public DataRemoverController(FlightInMemoryService flightInMemoryService, AirportService airportService) {
        this.flightInMemoryService = flightInMemoryService;
        this.airportService = airportService;
    }

    @PostMapping("/testing-api/clear")
    public void clearFlights() {
        flightInMemoryService.clearFlights();
        airportService.clearAirports();
    }

}
