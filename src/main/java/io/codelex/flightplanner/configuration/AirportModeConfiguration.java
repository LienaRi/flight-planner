package io.codelex.flightplanner.configuration;

import io.codelex.flightplanner.repositories.AirportDbRepository;
import io.codelex.flightplanner.repositories.AirportInMemoryRepository;
import io.codelex.flightplanner.services.AirportDbService;
import io.codelex.flightplanner.services.AirportInMemoryService;
import io.codelex.flightplanner.services.AirportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AirportModeConfiguration {

    @Value("${flight-planner.store-type}")
    private String airportStorageMode;

    @Bean
    public AirportService createAirportServiceBean(AirportDbRepository airportDbRepository, AirportInMemoryRepository airportInMemoryRepository) {
        if (airportStorageMode.equalsIgnoreCase("database")) {
            return new AirportDbService(airportDbRepository);
        } else {
            return new AirportInMemoryService(airportInMemoryRepository);
        }
    }
}
