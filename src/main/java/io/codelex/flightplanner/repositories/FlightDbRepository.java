package io.codelex.flightplanner.repositories;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightDbRepository extends JpaRepository<Flight, Long> {
    Flight findFirstByFromAndToAndCarrierAndDepartureTimeAndArrivalTime
            (Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime);

    @Query("SELECT f FROM Flight f WHERE f.from = :from AND f.to = :to AND CAST(f.departureTime AS DATE) = :departureDate")
    List<Flight> findAllByFromAndToAndDepartureDate(@Param("from") Airport from,
                                                    @Param("to") Airport to,
                                                    @Param("departureDate") LocalDate departureDate);
}
