package io.codelex.flightplanner.airport;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public interface AirportService {

    public Airport[] getAirports(String search);

    public void addAirportFromFlight(Airport ofFlight);

    public void clearAirports();
}
