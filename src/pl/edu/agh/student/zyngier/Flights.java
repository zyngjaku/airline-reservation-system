package pl.edu.agh.student.zyngier;

public class Flights {
    private String flightNumber;
    private String from;
    private String to;
    private String departureTime;
    private String arrivalTime;
    private String price;

    public Flights(String from, String to, String departureTime, String arrivalTime, String price){
        this.from = from;
        this.to = to;
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
}
