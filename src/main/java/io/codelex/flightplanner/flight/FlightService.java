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
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportService airportService;
    private final AtomicInteger idGenerator = new AtomicInteger();

    public FlightService(FlightRepository flightRepository, AirportService airportService) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
    }

    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getFrom() == null
                || searchFlightRequest.getTo() == null
                || searchFlightRequest.getDepartureDate() == null) {
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Incomplete search request");
        }
        if (searchFlightRequest.getTo().equals(searchFlightRequest.getFrom())) {
            throw new ResponseStatusException(HttpStatus.valueOf(400), "Matching departure and arrival airports");
        }

        return flightRepository.searchFlights(searchFlightRequest.getFrom(), searchFlightRequest.getTo(), searchFlightRequest.getDepartureDate());
    }

    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(int id) {
        return flightRepository.findFlightById(id);
    }

    public void clearFlights() {
        flightRepository.clearFlights();
    }

    public void deleteFlight(int id) {
        flightRepository.deleteFlight(id);
    }

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
            flightRepository.addFlight(flight);
        }
        return flight;
    }

    private boolean areFlightDatesStrange(Flight flight) {
        Duration duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
        return duration.toDays() > 3;
    }

    private boolean isFlightIncomplete(Flight flight) {
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

    private synchronized boolean isUniqueFlight(Flight flight) {
        boolean unique = true;
        if (!flightRepository.getFlights().isEmpty() && flightRepository.getFlights() != null && flight != null) {
            for (int i = 0; i < flightRepository.getFlights().size(); i++) {
                if (flightRepository.getFlights().get(i).equals(flight)) {
                    unique = false;
                    break;
                }
            }
        }
        if (flightRepository.getFlights().isEmpty()) {
            unique = true;
        }
        return unique;
    }

    private boolean areFromAndToSame(Flight flight) {
        return flight.getFrom().getCountry().trim().equalsIgnoreCase(flight.getTo().getCountry().trim())
                && flight.getFrom().getCity().trim().equalsIgnoreCase(flight.getTo().getCity().trim())
                && flight.getFrom().getAirport().trim().equalsIgnoreCase(flight.getTo().getAirport().trim());
    }

    private boolean isDepartureBeforeArrival(Flight flight) {
        return flight.getDepartureTime().isBefore(flight.getArrivalTime());
    }

}