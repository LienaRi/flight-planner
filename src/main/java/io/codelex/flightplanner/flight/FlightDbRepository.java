package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.airport.Airport;
import io.codelex.flightplanner.api.PageResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightDbRepository extends JpaRepository<Flight, Long> {

    Boolean findFlightsByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime);

    Optional<Flight> findFlightById(Long id);

    void deleteFlightById(Long id);
    void deleteAllByIdNotNull();

    List<Flight> findFlightsByFromIsLikeIgnoreCaseAndToLikeIgnoreCaseAndDepartureTime_Date(String from, String to, LocalDate departureDate);


//
//    void clearFlights();
//

//
//
//
//    CharSequence getFlights();
}
