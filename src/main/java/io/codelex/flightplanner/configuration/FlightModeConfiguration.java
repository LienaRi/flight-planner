package io.codelex.flightplanner.configuration;

import io.codelex.flightplanner.repositories.FlightDbRepository;
import io.codelex.flightplanner.repositories.FlightInMemoryRepository;
import io.codelex.flightplanner.services.AirportService;
import io.codelex.flightplanner.services.FlightDbService;
import io.codelex.flightplanner.services.FlightInMemoryService;
import io.codelex.flightplanner.services.FlightService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightModeConfiguration {

    @Value("${flight-planner.store-type}")
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
