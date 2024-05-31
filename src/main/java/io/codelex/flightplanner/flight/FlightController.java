package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.PageResult;
import io.codelex.flightplanner.api.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin-api/flights/{id}")
    public Flight findFlightById(@PathVariable long id) {
        return flightService.findFlightById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/flights/{id}")
    public Flight findFlightByIdCustomer(@PathVariable long id) {
        return flightService.findFlightById(id);
    }

    @DeleteMapping("/admin-api/flights/{id}")
    public void deleteFlight(@PathVariable long id) {
        flightService.deleteFlight(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/flights/search")
    public PageResult<Flight> searchFlights(@RequestBody SearchFlightRequest flightRequest) {
        return flightService.searchFlights(flightRequest);
    }
}
