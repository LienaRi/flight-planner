package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.api.PageResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Repository
public class FlightRepository {
    private List<Flight> flights;

    public FlightRepository() {
        flights = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Flight findById(int id) {
        Flight searchedFlight = flights.stream()
                .filter(flight -> flight.getId() == (id))
                .findAny()
                .orElse(null);
        if (searchedFlight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return searchedFlight;
        }
    }


    public void clearFlights() {
        flights.clear();
    }

    @ResponseStatus(HttpStatus.CREATED)
    public synchronized Flight addFlight(@Valid Flight flight) {
        if (flights.isEmpty()) {
            flight.setId(0);
        } else {
            flight.setId(flights.size());
        }
        flights.add(flight);
        return flight;
    }


    public void deleteFlight(int id) {
        flights.removeIf(flight -> flight.getId() == (id));
    }

    public PageResult searchFlights(String from, String to, LocalDate departureDate) {
        List<Flight> searchedFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getFrom().getAirport().equals(from)
                    && flight.getTo().getAirport().equals(to)
                    && flight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .equals(departureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                searchedFlights.add(flight);
            }
        }
        Flight[] flightSearchResults = searchedFlights.toArray(new Flight[0]);
        return new PageResult(1, searchedFlights.size(), flightSearchResults);
    }
}

