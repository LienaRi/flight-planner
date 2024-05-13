package io.codelex.flightplanner.flight;

import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    public void addFlight(@RequestBody Flight flight) {
        Flight flight1 = flight;
    }

    @GetMapping("/flights/{id}")
    public Flight searchFlight(@PathVariable Long id) {
        return flightService.findById(id);
    }

    @PostMapping("/clear")
    public void clearFlights() {
        flightService.clearFlights();
    }
}
