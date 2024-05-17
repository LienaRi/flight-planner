package io.codelex.flightplanner.flight;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.api.PageResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FlightService{
    private final FlightRepository flightRepository;
    private final AirportService airportService;

    public FlightService(FlightRepository flightRepository, AirportService airportService) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
    }

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
    public Flight addFlight(@Valid Flight flight){

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
        if (!FlightRepository.flights.isEmpty() && flight != null) {
            for (int i = 0; i < FlightRepository.flights.size(); i++) {
                if (FlightRepository.flights.get(i).equals(flight)) {
                    unique = false;
                    break;
                }
            }
        }
        if (FlightRepository.flights.isEmpty()) {
            unique = true;
    }
    return unique;
    }

    private boolean isDepartureBeforeArrival(Flight flight) {
        return flight.getDepartureTime().isBefore(flight.getArrivalTime());
    }

//    private LocalDateTime dateTimeParser(String flightDateTime) {
//        return LocalDateTime.parse(flightDateTime.replace(" ", "T"));
//    }

}
