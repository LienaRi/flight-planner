package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RequestMapping
@RestController
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/admin-api/flights")
    public synchronized Flight addFlight(@RequestBody AddFlightRequest flightRequest) {
        return flightService.addFlight(flightRequest);
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight searchFlight(@PathVariable int id) {
        return flightService.findById(id);
    }

    @DeleteMapping("/admin-api/flights/{id}")
    public void deleteFlight(@PathVariable int id) {
        flightService.deleteFlight(id);
    }

    @PostMapping("/clear")
    public void clearFlights() {
        flightService.clearFlights();
    }

    @PostMapping("/flights/search")
    public PageResult searchFlights(@RequestParam String from, String to, LocalDate departureDate){
        return flightService.searchFlights(from, to, departureDate);
    }
}
