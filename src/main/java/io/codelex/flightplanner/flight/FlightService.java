package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.api.AddFlightRequest;
import io.codelex.flightplanner.api.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class FlightService {

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
    public synchronized Flight addFlight(AddFlightRequest flightRequest) {
        Flight flight = new Flight(flightRequest.getFrom(),
                flightRequest.getTo(),
                flightRequest.getCarrier(),
                dateTimeFormatter(flightRequest.getDepartureTime()),
                dateTimeFormatter(flightRequest.getArrivalTime()));
        addId(flight);

        if (isFlightComplete(flight)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Incomplete flight details");
        } else if (!isUniqueFlight(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate flight");
        } else if(!isDepartureBeforeArrival(flight) || areFromAndToSame(flight)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Invalid flight details");
        } else {
            airportService.addAirportFromFlight(flight.getFrom());
            airportService.addAirportFromFlight(flight.getTo());
            flightRepository.addFlight(flight);
        }
        return flight;
    }

    public LocalDateTime dateTimeFormatter(LocalDateTime dateTimeToFormat) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (dateTimeToFormat != null) {
            return LocalDateTime.parse(dateTimeToFormat.toString(), formatter);
        } else {
            return null;
        }
    }

    private boolean isFlightComplete(Flight flight) {
        return flight.getFrom() == null
                || flight.getTo() == null
                || flight.getFrom().getCountry() == null
                || flight.getFrom().getCity() == null
                || flight.getFrom().getAirport() == null
                || flight.getTo().getCountry() == null
                || flight.getTo().getCity() == null
                || flight.getTo().getAirport() == null
                || flight.getCarrier() == null
                || flight.getDepartureTime() == null
                || flight.getArrivalTime() == null;
    }

    private synchronized void addId(Flight flight) {
        if (flightRepository.getFlights().isEmpty()) {
            flight.setId(0);
        } else {
            flight.setId(flightRepository.getFlights().size());
        }
    }

    private synchronized boolean isUniqueFlight(Flight flight) {
        boolean unique = true;
        if (flightRepository.getFlights().size() > 1 && flightRepository.getFlights() != null && flight != null) {
            for (int i = 0; i < flightRepository.getFlights().size(); i++) {
                if (flightRepository.getFlights().get(i).equals(flight)) {
                    unique = false;
                }
            }
        }
        if (flightRepository.getFlights().isEmpty()) {
            unique = true;
        }
        return unique;
    }

    private boolean areFromAndToSame(Flight flight) {
        return flight.getFrom().equals(flight.getTo());
    }

    private boolean isDepartureBeforeArrival(Flight flight) {
        return flight.getDepartureTime().isBefore(flight.getArrivalTime());
    }

}
