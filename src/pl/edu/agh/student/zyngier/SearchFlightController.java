package pl.edu.agh.student.zyngier;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import pl.edu.agh.student.zyngier.database.DB;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class SearchFlightController {

    @FXML
    private Label messageLabel;

    @FXML
    private ChoiceBox departureAirportChoice;

    @FXML
    private ChoiceBox arrivalAirportChoice;

    @FXML
    private DatePicker departureDate;

    @FXML
    public void initialize(){
        messageLabel.setContentDisplay(ContentDisplay.CENTER);
        messageLabel.setTextFill(Color.BLACK);

        messageLabel.setText("Hi " + System.getProperty("firstName") + " " + System.getProperty("lastName"));

        DB db =  new DB();
        ArrayList<String> departureAirports = db.getDepartureAirports();

        for(String it : departureAirports){
            departureAirportChoice.getItems().add(it);
        }
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
        departureDate.setValue(null);

        if(arrivalAirportChoice.getValue() != null) {
            DB db = new DB();
            ArrayList<DayOfWeek> flightDate = db.getFlightDays(departureAirportChoice.getValue().toString(), arrivalAirportChoice.getValue().toString());

            System.out.println(flightDate);

            departureDate.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) < 0 || !flightDate.contains(date.getDayOfWeek()));
                }
            });
        }
    }


}
