package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.api.AddFlightResponse;
import io.codelex.flightplanner.api.PageResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RequestMapping
@RestController
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/admin-api/flights")
    public synchronized Flight addFlight(@Valid @RequestBody AddFlightResponse addFlightResponse) {
        Flight flight = new Flight(addFlightResponse.getFrom(),
                addFlightResponse.getTo(),
                addFlightResponse.getCarrier(),
                addFlightResponse.getDepartureTime(),
                addFlightResponse.getArrivalTime());
        return flightService.addFlight(flight);
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
