package io.codelex.flightplanner.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.codelex.flightplanner.airport.Airport;

import java.time.LocalDateTime;
import java.util.Objects;


public class Flight {

    private int id;
    private Airport from;
    private Airport to;
    private String carrier;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    public Flight() {

    }

    public Flight(Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight(int id, Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("from")
    public Airport getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(Airport from) {
        this.from = from;
    }

    @JsonProperty("to")
    public Airport getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(Airport to) {
        this.to = to;
    }

    @JsonProperty("carrier")
    public String getCarrier() {
        return carrier;
    }

    @JsonProperty("carrier")
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @JsonProperty("departureTime")
    public LocalDateTime  getDepartureTime() {
        return departureTime;
    }

    @JsonProperty("departureTime")
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    @JsonProperty("arrivalTime")
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }


    @JsonProperty("arrivalTime")
public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(from, flight.from) && Objects.equals(to, flight.to) && Objects.equals(carrier, flight.carrier) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, carrier, departureTime, arrivalTime);
    }
}
