package pl.edu.agh.student.zyngier.database;

import com.mysql.cj.util.StringUtils;
import pl.edu.agh.student.zyngier.Flights;
import pl.edu.agh.student.zyngier.Main;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

import java.time.DayOfWeek;
import java.util.*;
import java.util.Date;

import static java.time.DayOfWeek.*;

public class DB{
    private static final String DB_HOST = "mysql.agh.edu.pl";
    private static final String DB_NAME = "zyngier";
    private static final String DB_USER = "zyngier";
    private static final String DB_PASSWORD = "Qzmj6z3CcUg1tb6Z";

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;


    public void openConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://"+ DB_HOST +"/"+ DB_NAME +"", DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }catch(Exception e){e.printStackTrace();}
    }

    public void closeConnection(){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) { } // ignore
            rs = null;
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore

            stmt = null;
        }
    }

    public boolean checkIfEmailAndPasswordIsCorrect(String email, String password){
        boolean ifUserExist = false;
        try {
            openConnection();

            Sha1 hash = new Sha1();

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*), u.userID, p.firstName, p.lastName, p.pesel FROM users u LEFT JOIN passengers p ON u.userID = p.passengerID WHERE u.email='"+ email +"' AND u.password='"+ hash.sha1(password) +"'");

            while(rs.next()){
                if(rs.getInt(1) > 0) ifUserExist = true;

                if(ifUserExist){
                    System.setProperty("id", rs.getString(2));
                    System.setProperty("firstName", rs.getString(3));
                    System.setProperty("lastName", rs.getString(4));
                    System.setProperty("pesel", rs.getString(5));
                }
            }
        }catch (SQLException | NoSuchAlgorithmException ex){
            // handle any error
        }finally {
            closeConnection();
        }

        return ifUserExist;
    }

    public boolean checkIfEmailExist(String email){
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM users WHERE email='"+ email +"'");

            while(rs.next()){
                if(rs.getInt(1) > 0) return true;
            }
        }catch (SQLException ex){
            // handle any error
        }

        return false;
    }

    public boolean registerNewUser(String email, String password, String fisrtName, String lastName, String pesel){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO passengers (firstName, lastName, pesel) VALUES ('"+ fisrtName +"','"+ lastName +"','"+ pesel +"')");

            rs = stmt.executeQuery("SELECT * FROM passengers WHERE pesel='"+ pesel +"';");

            String id = null;
            while(rs.next()){
                id = rs.getString(1);
            }

            Sha1 hash = new Sha1();

            stmt.executeUpdate("INSERT INTO users (userID, email, password) VALUES ('"+ id +"','"+ email +"','"+ hash.sha1(password) +"')");
        }catch (SQLException | NoSuchAlgorithmException e){
            // handle any error
        }

        return false;
    }

    public ArrayList<String> getDepartureAirports(){
        ArrayList<String> departureAirports = new ArrayList<String>();

        try {
            Date date = new Date();

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT DISTINCT airportDeparture FROM flights WHERE lastFlightDate>NOW()");

            while(rs.next()){
                departureAirports.add(rs.getString(1));
            }
        }catch (SQLException e){
            // handle any error
        }

        return departureAirports;
    }

    public ArrayList<String> getArrivalAirports(String departureAirport){
        ArrayList<String> arrivalAirports = new ArrayList<String>();

        try {
            Date date = new Date();

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT DISTINCT airportArrival FROM flights WHERE airportDeparture='"+ departureAirport +"' AND lastFlightDate>NOW()");

            while(rs.next()){
                arrivalAirports.add(rs.getString(1));
            }
        }catch (SQLException e){
            // handle any error
        }

        return arrivalAirports;
    }

    public ArrayList<DayOfWeek> getFlightDays(String departureAirport, String arrivalAirport){
        ArrayList<DayOfWeek> flightDays = new ArrayList<>();
        List<DayOfWeek> tmp_days = Arrays.asList(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY);

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT flightDay FROM flights WHERE airportDeparture='"+ departureAirport +"' and airportArrival='"+ arrivalAirport +"'");

            String getDays = "";

            while(rs.next()){
                getDays = rs.getString(1);
                List<String> tmp_values = Arrays.asList(getDays.split(";"));

                for(int i=0; i<tmp_values.size(); i++){
                    if(tmp_values.get(i).equals("1")){
                        flightDays.add(tmp_days.get(i));
                    }
                }
            }

        }catch (SQLException e){
            // handle any error
        }

        return flightDays;
    }

    public ArrayList<Date> getDateOfFirstAndLastFlight(String departureAirport, String arrivalAirport){
        ArrayList<Date> dates = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT firstFlightDate, lastFlightDate FROM flights WHERE airportDeparture='"+ departureAirport +"' and airportArrival='"+ arrivalAirport +"'");

            rs.next();
            //dates.add(rs.getString(1));

        }catch (SQLException e){
            // handle any error
        }

        return dates;
    }

    public ArrayList<Flights> getFlights(String departureAirport, String arrivalAirport, String flightDate){
        ArrayList<Flights> flights = new ArrayList<Flights>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT f.flightNumber, f.timeDeparture, f.timeArrival, (j.numberofRows*j.numberOfColumns-COUNT(t.ticketNumber)) as 'numberOfAvailableSeats', f.basePrice FROM flights f LEFT JOIN tickets t ON f.flightNumber=t.flightNumber AND t.flightDate='"+ flightDate +"' LEFT JOIN jets j ON f.jetNumber=j.jetNumber WHERE f.airportDeparture='"+ departureAirport +"' AND f.airportArrival='"+ arrivalAirport +"' AND '"+ flightDate +"' BETWEEN f.firstFlightDate AND f.lastFlightDate GROUP BY t.flightNumber, t.flightDate;");
            while(rs.next()){
                System.out.println(rs);
                if(rs.getInt(4)>0){
                    String price = Double.toString(rs.getInt(4));

                    if(rs.getInt(4)<10){
                        price = Double.toString(rs.getInt(5) * 3);
                    }
                    else if(rs.getInt(4)<20){
                        price = Double.toString(rs.getInt(5) * 2.5);
                    }
                    else if(rs.getInt(4)<30){
                        price = Double.toString(rs.getInt(5) * 2.25);
                    }
                    else if(rs.getInt(4)<50){
                        price = Double.toString(rs.getInt(5) * 2);
                    }
                    else if(rs.getInt(4)<100){
                        price = Double.toString(rs.getInt(5) * 1.5);
                    }
                    else if(rs.getInt(4)<150){
                        price = Double.toString(rs.getInt(5) * 1);
                    }
                    else if(rs.getInt(4)<160){
                        price = Double.toString(rs.getInt(5) * 0.8);
                    }
                    else{
                        price = Double.toString(rs.getInt(5) * 0.6);
                    }
                    
                    price += "\u20AC";

                    flights.add(new Flights(departureAirport, arrivalAirport, rs.getString(2), rs.getString(3), price));
                }
            }
        }catch (SQLException e){
            // handle any error
        }

        return flights;
    }
}
