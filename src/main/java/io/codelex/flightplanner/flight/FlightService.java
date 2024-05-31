package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.PageResult;
import io.codelex.flightplanner.api.SearchFlightRequest;
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
        return flight.getFrom() == null
                || flight.getTo() == null
                || flight.getFrom().getCountry() == null
                || flight.getFrom().getCountry().isBlank()
                || flight.getFrom().getCity() == null
                || flight.getFrom().getCity().isBlank()
                || flight.getFrom().getAirport() == null
                || flight.getFrom().getAirport().isBlank()
                || flight.getTo().getCountry() == null
                || flight.getTo().getCountry().isBlank()
                || flight.getTo().getCity() == null
                || flight.getTo().getCity().isBlank()
                || flight.getTo().getAirport() == null
                || flight.getTo().getAirport().isBlank()
                || flight.getCarrier() == null
                || flight.getCarrier().isBlank()
                || flight.getDepartureTime() == null
                || flight.getArrivalTime() == null;
    }

    protected boolean areFlightDatesStrange(Flight flight) {
        Duration duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
        return duration.toDays() > 3;
    }



}

