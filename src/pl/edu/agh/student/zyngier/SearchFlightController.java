package pl.edu.agh.student.zyngier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import pl.edu.agh.student.zyngier.database.DB;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class SearchFlightController {

    @FXML
    private ChoiceBox departureAirportChoice;

    @FXML
    private ChoiceBox arrivalAirportChoice;

    @FXML
    private DatePicker flyOutDate;

    @FXML
    private DatePicker flyBackDate;

    @FXML
    private RadioButton returnRadioButton;

    @FXML
    private RadioButton oneWayRadioButton;

    @FXML
    private ToggleGroup typeOfTripGroup;

    @FXML
    private TableView firstWayFlight;

    @FXML
    public void initialize(){
        //messageLabel.setContentDisplay(ContentDisplay.CENTER);
        //messageLabel.setTextFill(Color.BLACK);

        //messageLabel.setText("Hi " + System.getProperty("firstName") + " " + System.getProperty("lastName"));

        DB db =  new DB();
        ArrayList<String> departureAirports = db.getDepartureAirports();

        for(String it : departureAirports){
            departureAirportChoice.getItems().add(it);
        }
    }

    @FXML
    private void returnRadioButton(ActionEvent event) {
        flyBackDate.setDisable(false);
    }

    @FXML
    private void oneWayRadioButton(ActionEvent event) {
        flyBackDate.setDisable(true);
    }


    @FXML
    public void departureAirportChoice(javafx.event.ActionEvent actionEvent) {
        DB db =  new DB();
        ArrayList<String> arrivalAirport = db.getArrivalAirports(departureAirportChoice.getValue().toString());

        arrivalAirportChoice.getItems().clear();
        for(String it : arrivalAirport){
            arrivalAirportChoice.getItems().add(it);
        }
    }

    @FXML
    public void arrivalAirportChoice(javafx.event.ActionEvent actionEvent) {
        flyOutDate.setValue(null);

        if(arrivalAirportChoice.getValue() != null) {
            manageDatePicker(flyOutDate, departureAirportChoice, arrivalAirportChoice);
            manageDatePicker(flyBackDate, arrivalAirportChoice, departureAirportChoice);
        }
    }

    private void manageDatePicker(DatePicker datePicker, ChoiceBox departureAirportChoice, ChoiceBox arrivalAirportChoice){
        DB db = new DB();

        ArrayList<DayOfWeek> flightDate = db.getFlightDays(departureAirportChoice.getValue().toString(), arrivalAirportChoice.getValue().toString());

        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 || !flightDate.contains(date.getDayOfWeek()));
            }
        });
    }

    @FXML
    public void findFlightButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("Finding flight");

        if(returnRadioButton == (RadioButton) typeOfTripGroup.getSelectedToggle())
            System.out.println("CLicked");
    }
}
