package pl.edu.agh.student.zyngier.services;

public class Flights {
    private String flightNumber;
    private String from;
    private String to;
    private String flightDate;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private int seatRow;
    private char seatColumn;


    public Flights(String flightNumber, String from, String to, String flightDate, String departureTime, String arrivalTime, int seatRow, char seatColumn, String price) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        this.flightDate = flightDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = Float.valueOf(price);
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }

    public Flights(String flightNumber, String from, String to, String flightDate, String departureTime, String arrivalTime, String price) {
        this(flightNumber, from, to, flightDate, departureTime, arrivalTime, -1, 'X', price);
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
        return Double.toString(price)+"\u20AC";
    }

    public double getPriceDouble() {
        return price;
    }

    public void setSeat(int seatRow, char seatColumn){
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }

    public int getSeatRow(){
        return seatRow;
    }

    public char getSeatColumn(){
        return seatColumn;
    }

    public String getDate() { return flightDate+" "+departureTime; }
}