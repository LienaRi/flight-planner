package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.airport.Airport;
import java.time.LocalDateTime;


public class AddFlightRequest {
    private Airport from;
    private Airport to;
    private String carrier;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    public AddFlightRequest(Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public AddFlightRequest() {
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departure) {
        this.departureTime = departure;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrival) {
        this.arrivalTime = arrival;
    }
}
