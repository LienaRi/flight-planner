package io.codelex.flightplanner.airport;


import java.util.Objects;

public class Airport {

    protected String country;

    protected String city;

    protected String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public Airport() {

    }

    public Airport(String airport) {
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", airport='" + airport + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return Objects.equals(country, airport1.country) && Objects.equals(city, airport1.city) && Objects.equals(airport, airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}
