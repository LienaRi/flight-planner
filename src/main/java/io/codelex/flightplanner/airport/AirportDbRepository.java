package io.codelex.flightplanner.airport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportDbRepository  extends JpaRepository<Airport, Long>{
}
