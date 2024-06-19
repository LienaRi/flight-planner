package io.codelex.flightplanner.services;

import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.PageResult;
import io.codelex.flightplanner.api.SearchFlightRequest;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

public abstract class FlightService {
    public abstract PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest);

    public abstract Flight addFlight(AddFlightRequest flightRequest);

    public abstract Flight findFlightById(long id);

    public abstract void clearFlights();

    public abstract void deleteFlight(long id);

    protected boolean checkIfSearchValid(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getFrom() == null
                || searchFlightRequest.getTo() == null
                || searchFlightRequest.getDepartureDate() == null) {
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Incomplete search request");
        }
        if (searchFlightRequest.getTo().equals(searchFlightRequest.getFrom())) {
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Matching departure and arrival airports");
        }
        return true;
    }

    protected boolean areFromAndToSame(Flight flight) {
        return flight.getFrom().getCountry().trim().equalsIgnoreCase(flight.getTo().getCountry().trim())
                && flight.getFrom().getCity().trim().equalsIgnoreCase(flight.getTo().getCity().trim())
                && flight.getFrom().getAirport().trim().equalsIgnoreCase(flight.getTo().getAirport().trim());
    }

    protected boolean isDepartureBeforeArrival(Flight flight) {
        return flight.getDepartureTime().isBefore(flight.getArrivalTime());
    }

    protected boolean isFlightIncomplete(Flight flight) {
        return isAirportIncomplete(flight.getFrom())
                || isAirportIncomplete(flight.getTo())
                || flight.getCarrier() == null
                || flight.getCarrier().isBlank()
                || flight.getDepartureTime() == null
                || flight.getArrivalTime() == null;
    }

    private boolean isAirportIncomplete(Airport airport) {
        return airport == null
                || airport.getCountry() == null
                || airport.getCountry().isBlank()
                || airport.getCity() == null
                || airport.getCity().isBlank()
                || airport.getAirport() == null
                || airport.getAirport().isBlank();
    }

    protected boolean areFlightDatesStrange(Flight flight) {
        Duration duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
        return duration.toDays() > 3;
    }
}

