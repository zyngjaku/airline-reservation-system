package pl.edu.agh.student.zyngier.services;

public class Flights {
    private String flightNumber;
    private String from;
    private String to;
    private String flightDate;
    private String departureTime;
    private String arrivalTime;
    private String price;
    private double priceDouble;


    public Flights(String flightNumber, String from, String to, String flightDate, String departureTime, String arrivalTime, String price) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        this.flightDate = flightDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.priceDouble = Float.valueOf(price);
        this.price = price + "\u20AC";

    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getPrice() {
        return price;
    }

    public double getPriceDouble() { return priceDouble; }
}