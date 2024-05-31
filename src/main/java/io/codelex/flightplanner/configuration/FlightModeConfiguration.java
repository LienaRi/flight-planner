package io.codelex.flightplanner.configuration;

import io.codelex.flightplanner.airport.AirportService;
import io.codelex.flightplanner.flight.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightModeConfiguration {

    @Value("${flightplanner.flight-storage-mode}")
    private String flightStorageMode;

    @Bean
    public FlightService createFlightServiceBean(FlightDbRepository flightDbRepository,
                                                 FlightInMemoryRepository flightInMemoryRepository,
                                                 AirportService airportService) {
        if (flightStorageMode.equalsIgnoreCase("database")) {
            return new FlightDbService(flightDbRepository, airportService);
        } else {
            return new FlightInMemoryService(flightInMemoryRepository, airportService);
        }
    }
}
