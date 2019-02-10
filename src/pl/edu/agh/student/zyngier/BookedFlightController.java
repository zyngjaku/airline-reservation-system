package pl.edu.agh.student.zyngier;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import pl.edu.agh.student.zyngier.services.DB;
import pl.edu.agh.student.zyngier.services.Flights;

import java.io.IOException;
import java.util.ArrayList;

public class BookedFlightController {

    @FXML
    private TableView<Flights> bookedFlightTable;

    @FXML
    private TableColumn<Flights, String> flightNumberColumn;

    @FXML
    private TableColumn<Flights, String> flightDateColumn;

    @FXML
    private TableColumn<Flights, String> airprotDepartureColumn;

    @FXML
    private TableColumn<Flights, String> airportArrivalColumn;

    @FXML
    private TableColumn<Flights, Boolean> seatRowColumn;

    @FXML
    private TableColumn<Flights, Boolean> seatColumnColumn;

    @FXML
    private TableColumn<Flights, Boolean> priceColumn;

    private ArrayList<Flights> bookedFlights;


    @FXML
    public void initialize(){
        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        flightDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        airprotDepartureColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        airportArrivalColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        seatRowColumn.setCellValueFactory(new PropertyValueFactory<>("seatRow"));
        seatColumnColumn.setCellValueFactory(new PropertyValueFactory<>("seatColumn"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        flightNumberColumn.setStyle( "-fx-alignment: CENTER;");
        flightDateColumn.setStyle( "-fx-alignment: CENTER;");
        airprotDepartureColumn.setStyle( "-fx-alignment: CENTER;");
        airportArrivalColumn.setStyle( "-fx-alignment: CENTER;");
        seatRowColumn.setStyle( "-fx-alignment: CENTER;");
        seatColumnColumn.setStyle( "-fx-alignment: CENTER;");
        priceColumn.setStyle( "-fx-alignment: CENTER;");

        DB db =  new DB();
        db.openConnection();

        bookedFlights = new ArrayList<>();
        bookedFlights = db.getYourBookedFlight();

        db.closeConnection();

        bookedFlightTable.getItems().clear();
        for(Flights it : bookedFlights) {
            System.out.println(it.getFlightNumber());
            bookedFlightTable.getItems().add(it);
        }
    }

    @FXML
    public void goToMenuSceneButton(javafx.event.ActionEvent actionEvent) {
        goToMenuScene();
    }

    private void goToMenuScene(){
        /* Back to menu scene */
        try {
            BorderPane root = new BorderPane(FXMLLoader.load(getClass().getResource("fxml/menu.fxml")));
            Scene searchFlightScene = new Scene(root, 800, 400);
            System.out.println("[searchFlightScene] -> [menuScene]");

            Main.getState().setScene(searchFlightScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
