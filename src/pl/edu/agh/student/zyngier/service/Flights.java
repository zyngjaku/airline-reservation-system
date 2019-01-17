package pl.edu.agh.student.zyngier.service;

public class Flights {
    private String flightNumber;
    private String from;
    private String to;
    private String flightDate;
    private String departureTime;
    private String arrivalTime;
    private String price;


    public Flights(String from, String to, String flightDate, String departureTime, String arrivalTime, String price){
        this.from = from;
        this.to = to;
        this.flightDate = flightDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    public String getFrom(){
        return from;
    }

    public String getTo(){
        return to;
    }

    public String getDepartureTime(){
        return departureTime;
    }

    public String getArrivalTime(){
        return arrivalTime;
    }

    public String getPrice(){
        return price;
    }

    public String getFlightDate() { return flightDate; }
}
