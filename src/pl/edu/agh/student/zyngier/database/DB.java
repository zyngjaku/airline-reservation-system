package pl.edu.agh.student.zyngier.database;

import com.mysql.cj.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DB{
    private static final String DB_HOST = "mysql.agh.edu.pl";
    private static final String DB_NAME = "zyngier";
    private static final String DB_USER = "zyngier";
    private static final String DB_PASSWORD = "Qzmj6z3CcUg1tb6Z";

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    private void openConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://"+ DB_HOST +"/"+ DB_NAME +"", DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }catch(Exception e){e.printStackTrace();}
    }

    private void closeConnection(){
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
            openConnection();

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM users WHERE email='"+ email +"'");

            while(rs.next()){
                if(rs.getInt(1) > 0) return true;
            }
        }catch (SQLException ex){
            // handle any error
        }finally {
            closeConnection();
        }

        return false;
    }

    public boolean registerNewUser(String email, String password, String fisrtName, String lastName, String pesel){
        try {
            openConnection();

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
        } finally {
            closeConnection();
        }

        return false;
    }
}
