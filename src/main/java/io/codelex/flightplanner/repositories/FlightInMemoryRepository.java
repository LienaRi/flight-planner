package io.codelex.flightplanner.repositories;

import io.codelex.flightplanner.api.PageResult;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightInMemoryRepository {
    private List<Flight> flights;

    public FlightInMemoryRepository() {
        flights = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(long id) {
        for (Flight flight : flights) {
            if (flight.getId() == id) {
                return flight;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public synchronized void clearFlights() {
        flights.clear();
    }

    @ResponseStatus(HttpStatus.CREATED)
    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void deleteFlight(long id) {
        flights.removeIf(flight -> flight.getId() == id);
    }

    public PageResult<Flight> searchFlights(String from, String to, LocalDate departureDate) {
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
        return new PageResult<>(0, flightSearchResults.length, flightSearchResults);
    }
}