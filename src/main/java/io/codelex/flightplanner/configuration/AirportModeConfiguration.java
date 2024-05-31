package io.codelex.flightplanner.configuration;

import io.codelex.flightplanner.airport.AirportDbRepository;
import io.codelex.flightplanner.airport.AirportDbService;
import io.codelex.flightplanner.airport.AirportInMemoryRepository;
import io.codelex.flightplanner.airport.AirportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class AirportModeConfiguration {

    @Value("${flightplanner.flight-storage-mode}")
    private String airportStorageMode;

    @Bean
    public AirportService createAirportServiceBean(AirportDbRepository airportDbRepository, AirportInMemoryRepository airportInMemoryRepository) {
        if (airportStorageMode.equalsIgnoreCase("database")) {
            return new AirportDbService(airportDbRepository, airportInMemoryRepository);
        } else {
            return new AirportInMemoryRepository(new ArrayList<>());
        }
    }
}
