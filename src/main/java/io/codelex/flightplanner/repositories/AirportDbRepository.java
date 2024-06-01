package io.codelex.flightplanner.repositories;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDbRepository  extends JpaRepository<Airport, Long>{

    List<Airport> findAllByCountryContainingIgnoreCaseOrCityContainingIgnoreCaseOrAirportContainingIgnoreCase
    (String search, String search1, String search2);
}
