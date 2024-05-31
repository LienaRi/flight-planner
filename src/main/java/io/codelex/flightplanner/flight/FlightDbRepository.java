package io.codelex.flightplanner.flight;

import io.codelex.flightplanner.airport.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightDbRepository extends JpaRepository<Flight, Long> {

//    Boolean findFlightsByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime);
//
//    List<Flight> findFlightsByFromIsLikeIgnoreCaseAndToLikeIgnoreCaseAndDepartureTime_Date(String from, String to, LocalDate departureDate);


//
//    void clearFlights();
//

//
//
//
//    CharSequence getFlights();
}
