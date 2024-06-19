package io.codelex.flightplanner.services;

import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.PageResult;
import io.codelex.flightplanner.api.SearchFlightRequest;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repositories.FlightInMemoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.atomic.AtomicInteger;

public class FlightInMemoryService extends FlightService {

    private final FlightInMemoryRepository flightInMemoryRepository;
    private final AirportService airportService;
    private final AtomicInteger idGenerator = new AtomicInteger();

    public FlightInMemoryService(FlightInMemoryRepository flightInMemoryRepository, AirportService airportService) {
        this.flightInMemoryRepository = flightInMemoryRepository;
        this.airportService = airportService;
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        if (checkIfSearchValid(searchFlightRequest)) {
            return flightInMemoryRepository.searchFlights(searchFlightRequest.getFrom(), searchFlightRequest.getTo(), searchFlightRequest.getDepartureDate());
        } else {
            return null;
        }
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(long id) {
        return flightInMemoryRepository.findFlightById(id);
    }

    @Override
    public void clearFlights() {
        flightInMemoryRepository.clearFlights();
    }

    @Override
    public void deleteFlight(long id) {
        flightInMemoryRepository.deleteFlight(id);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(AddFlightRequest flightRequest) {
        Flight flight = new Flight(flightRequest.getFrom(),
                flightRequest.getTo(),
                flightRequest.getCarrier(),
                flightRequest.getDepartureTime(),
                flightRequest.getArrivalTime());

        if (isFlightIncomplete(flight)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Incomplete flight details");
        } else if (!isUniqueFlight(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate flight");
        } else if (!isDepartureBeforeArrival(flight) || areFlightDatesStrange(flight)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid flight dates");
        } else if (areFromAndToSame(flight)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Matching departure and arrival airports");
        } else {
            airportService.addAirportFromFlight(flight.getFrom());
            airportService.addAirportFromFlight(flight.getTo());
            flight.setId(idGenerator.incrementAndGet());
            flightInMemoryRepository.addFlight(flight);
        }
        return flight;
    }

    private synchronized boolean isUniqueFlight(Flight flight) {
        boolean unique = true;
        if (!flightInMemoryRepository.getFlights().isEmpty() && flightInMemoryRepository.getFlights() != null && flight != null) {
            for (int i = 0; i < flightInMemoryRepository.getFlights().size(); i++) {
                if (flightInMemoryRepository.getFlights().get(i).equals(flight)) {
                    unique = false;
                    break;
                }
            }
        }
        if (flightInMemoryRepository.getFlights().isEmpty()) {
            unique = true;
        }
        return unique;
    }
}