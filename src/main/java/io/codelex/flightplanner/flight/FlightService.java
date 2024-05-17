package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.api.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class FlightService{

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AirportService airportService;


    public PageResult searchFlights(String from, String to, LocalDate departureDate) {
        return flightRepository.searchFlights(from, to, departureDate);
    }


    public Flight findById(int id) {
        return flightRepository.findById(id);
    }

    public void clearFlights() {
        flightRepository.clearFlights();
    }

    public void deleteFlight(int id) {
        flightRepository.deleteFlight(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(Flight flight) {
        if (isFlightValid(flight)) {
            airportService.addAirportFromFlight(flight.getFrom());
            airportService.addAirportFromFlight(flight.getTo());
            return flightRepository.addFlight(flight);
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Invalid flight data provided.");
        }
    }

    private boolean isFlightValid(Flight flight) {
        return isFlightDataComplete(flight) && isUniqueFlight(flight) && isDepartureBeforeArrival(flight);
    }

    private boolean isFlightDataComplete(Flight flight) {
        return flight.getFrom() != null && flight.getTo() != null &&
                flight.getCarrier() != null && flight.getDepartureTime() != null &&
                flight.getArrivalTime() != null && !flight.getFrom().equals(flight.getTo());
    }

    private boolean isUniqueFlight(Flight flight) {
        boolean unique = true;
        if (!flightRepository.getFlights().isEmpty()) {
            for (int i = 0; i < flightRepository.getFlights().size(); i++) {
                if (flightRepository.getFlights().get(i).equals(flight)) {
                    unique = false;
                    break;
                }
            }
    }
        if(flightRepository.getFlights().isEmpty()) {
            unique = true;
        }
    return unique;
    }

    private boolean isDepartureBeforeArrival(Flight flight) {
        return flight.getDepartureTime().isBefore(flight.getArrivalTime());
    }

}
