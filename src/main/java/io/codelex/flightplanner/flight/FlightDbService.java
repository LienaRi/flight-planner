package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.PageResult;
import io.codelex.flightplanner.api.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FlightDbService extends FlightService {

    private final FlightDbRepository flightDbRepository;
    private final AirportService airportService;

    public FlightDbService(FlightDbRepository flightDbRepository, AirportService airportService) {
        this.flightDbRepository = flightDbRepository;
        this.airportService = airportService;
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        List<Flight> searchedFlights;
        if (checkIfSearchValid(searchFlightRequest)) {
            searchedFlights = flightDbRepository.findFlightsByFromIsLikeIgnoreCaseAndToLikeIgnoreCaseAndDepartureTime_Date
                    (searchFlightRequest.getFrom(),searchFlightRequest.getTo(), searchFlightRequest.getDepartureDate());
            Flight[] flightSearchResults = searchedFlights.toArray(new Flight[0]);
            return new PageResult<>(0, flightSearchResults.length, flightSearchResults);
        } else {
            return null;
        }
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(long id) {
        return this.flightDbRepository.findFlightById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void clearFlights() {
        this.flightDbRepository.deleteAll();
    }

    @Override
    public void deleteFlight(long id) {
        this.flightDbRepository.deleteFlightById(id);
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
            this.flightDbRepository.save(flight);
        }
        return flight;
    }

    private synchronized boolean isUniqueFlight(Flight flight) {
        return !flightDbRepository.findFlightsByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(flight.getFrom(), flight.getTo(), flight.getCarrier(), flight.getDepartureTime(), flight.getArrivalTime());
    }

}