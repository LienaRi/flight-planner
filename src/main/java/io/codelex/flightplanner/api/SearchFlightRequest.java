package io.codelex.flightplanner.api;

import java.time.LocalDate;

public class SearchFlightRequest {
    private String from;
    private String to;
    private LocalDate departureDate;

    public SearchFlightRequest(String from, String to, LocalDate departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public SearchFlightRequest() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }
}
