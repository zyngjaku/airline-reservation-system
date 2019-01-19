package pl.edu.agh.student.zyngier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import pl.edu.agh.student.zyngier.services.DB;
import pl.edu.agh.student.zyngier.services.Flights;

import java.io.IOException;
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

    /* Upper table (first way) */
    @FXML
    private TableView<Flights> firstWayFlightTable;

    @FXML
    private TableColumn<Flights, String> firstWayFlightTable_fromColumn;

    @FXML
    private TableColumn<Flights, String> firstWayFlightTable_toColumn;

    @FXML
    private TableColumn<Flights, String> firstWayFlightTable_departureTimeColumn;

    @FXML
    private TableColumn<Flights, String> firstWayFlightTable_arrivalTimeColumn;

    @FXML
    private TableColumn<Flights, Boolean> firstWayFlightTable_priceColumn;

    /* Lower table (return way) */
    @FXML
    private TableView<Flights> returnWayFlightTable;

    @FXML
    private TableColumn<Flights, String> returnWayFlightTable_fromColumn;

    @FXML
    private TableColumn<Flights, String> returnWayFlightTable_toColumn;

    @FXML
    private TableColumn<Flights, String> returnWayFlightTable_departureTimeColumn;

    @FXML
    private TableColumn<Flights, String> returnWayFlightTable_arrivalTimeColumn;

    @FXML
    private TableColumn<Flights, String> returnWayFlightTable_priceColumn;

    @FXML
    private Label firstWayFlightSummaryLabel;

    @FXML
    private Label returnWayFlightSummaryLabel;

    @FXML
    private Button bookFlightButton;

    private Flights firstWayFlight = null;
    private Flights returnWayFlight = null;

    @FXML
    public void initialize(){
        firstWayFlightSummaryLabel.setText(null);
        returnWayFlightSummaryLabel.setText(null);
        bookFlightButton.setVisible(false);

        DB db =  new DB();
        db.openConnection();

        ArrayList<String> departureAirports = db.getDepartureAirports();

        for(String it : departureAirports){
            departureAirportChoice.getItems().add(it);
        }

        db.closeConnection();

        firstWayFlightTable.setDisable(true);
        firstWayFlightTable_fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        firstWayFlightTable_toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        firstWayFlightTable_departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        firstWayFlightTable_arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        firstWayFlightTable_priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        firstWayFlightTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                actionOnChooseFlight(firstWayFlightTable, firstWayFlightSummaryLabel);
            }
        });

        //Set TableView (lower)
        returnWayFlightTable.setDisable(true);
        returnWayFlightTable_fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        returnWayFlightTable_toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        returnWayFlightTable_departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        returnWayFlightTable_arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        returnWayFlightTable_priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        returnWayFlightTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                actionOnChooseFlight(returnWayFlightTable, returnWayFlightSummaryLabel);
            }
        });
    }

    private void actionOnChooseFlight(TableView<Flights> table, Label label){
        if (table.getSelectionModel().getSelectedItem() != null) {
            Flights selectedFlight = table.getSelectionModel().getSelectedItem();
            System.out.println("Run choosing seats stage");

            SeatsController seatsController = new SeatsController(selectedFlight.getFlightNumber(), selectedFlight.getFlightDate());

            seatsController.seatStage.setOnHiding( event -> {
                if(seatsController.getSeatColumn() != 'X' && seatsController.getSeatRow() != 0) {
                    setSummaryText(label, selectedFlight, seatsController.getSeatRow(), seatsController.getSeatColumn());

                    if(table == firstWayFlightTable) {
                        firstWayFlight = selectedFlight;
                        firstWayFlight.setSeat(seatsController.getSeatRow(), seatsController.getSeatColumn());
                    }
                    else {
                        returnWayFlight = selectedFlight;
                        returnWayFlight.setSeat(seatsController.getSeatRow(), seatsController.getSeatColumn());
                    }
                }

                System.out.println("Close choosing seats stage");
            });
        }
    }

    private void setSummaryText(Label label, Flights flight, int seatRow, char seatColumn){
        StringBuilder sb = new StringBuilder();
        sb.append("From: ").append(flight.getFrom());
        sb.append("\nTo: ").append(flight.getTo());
        sb.append("\nDeparture time: ").append(flight.getFlightDate()).append(" ").append(flight.getDepartureTime());
        sb.append("\nArrival time: ").append(flight.getFlightDate()).append(" ").append(flight.getArrivalTime());
        sb.append("\nTotal price: ").append(flight.getPrice());
        sb.append("\nSeat no.: ").append(seatRow).append(seatColumn);

        label.setText(String.valueOf(sb));
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
        db.openConnection();

        ArrayList<String> arrivalAirport = db.getArrivalAirports(departureAirportChoice.getValue().toString());

        arrivalAirportChoice.getItems().clear();
        for(String it : arrivalAirport){
            arrivalAirportChoice.getItems().add(it);
        }

        db.closeConnection();
    }

    @FXML
    public void arrivalAirportChoice(javafx.event.ActionEvent actionEvent) {
        flyOutDate.setValue(null);

        if(arrivalAirportChoice.getValue() != null) {
            DB db = new DB();
            db.openConnection();

            manageDatePicker(db, flyOutDate, departureAirportChoice, arrivalAirportChoice);
            manageDatePicker(db, flyBackDate, arrivalAirportChoice, departureAirportChoice);

            db.closeConnection();
        }
    }

    private void manageDatePicker(DB db, DatePicker datePicker, ChoiceBox departureAirportChoice, ChoiceBox arrivalAirportChoice){
        ArrayList<DayOfWeek> flightDate = db.getFlightDays(departureAirportChoice.getValue().toString(), arrivalAirportChoice.getValue().toString());

        if(datePicker == flyBackDate && flightDate.isEmpty()){
            oneWayRadioButton.setSelected(true);
            flyBackDate.setDisable(true);
        }
        else {
            datePicker.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) < 0 || !flightDate.contains(date.getDayOfWeek()));
                }
            });
        }
    }

    @FXML
    public void findFlightButton(javafx.event.ActionEvent actionEvent) {
        //Checking if all fields are not empty
        if(!departureAirportChoice.getSelectionModel().isEmpty() && !arrivalAirportChoice.getSelectionModel().isEmpty() && flyOutDate.getValue() != null && (flyBackDate.getValue() != null || oneWayRadioButton == (RadioButton) typeOfTripGroup.getSelectedToggle())) {
            firstWayFlightTable.getItems().clear();
            returnWayFlightTable.getItems().clear();
            firstWayFlightSummaryLabel.setText(null);
            returnWayFlightSummaryLabel.setText(null);

            bookFlightButton.setVisible(true);

            DB db = new DB();
            db.openConnection();

            ArrayList<Flights> flights;
            flights = db.getFlights(departureAirportChoice.getValue().toString(), arrivalAirportChoice.getValue().toString(), flyOutDate.getValue().toString());

            for(Flights it : flights) {
                firstWayFlightTable.getItems().add(it);
            }

            firstWayFlightTable.setDisable(false);
            returnWayFlightTable.setDisable(true);

            firstWayFlightSummaryLabel.setText("Please double click on flight to choose it");

            // Checking if user clicked return
            if (returnRadioButton == (RadioButton) typeOfTripGroup.getSelectedToggle()) {
                System.out.println(arrivalAirportChoice.getValue().toString() + "->" + departureAirportChoice.getValue().toString());

                flights.clear();
                flights = db.getFlights(arrivalAirportChoice.getValue().toString(), departureAirportChoice.getValue().toString(), flyBackDate.getValue().toString());

                for(Flights it : flights) {
                    returnWayFlightTable.getItems().add(it);
                }

                returnWayFlightTable.setDisable(false);

                returnWayFlightSummaryLabel.setText("You should also choose your return flight");
            }
        }
    }

    @FXML
    public void bookFlightButton(javafx.event.ActionEvent actionEvent) {
       if(firstWayFlight != null) {
           DB db = new DB();
           db.openConnection();

           db.bookFlight(firstWayFlight);
           System.out.println("I booked for user first way flight!");

           if (returnRadioButton == (RadioButton) typeOfTripGroup.getSelectedToggle() && returnWayFlight != null) {
               db.bookFlight(returnWayFlight);
               System.out.println("I booked for user return flight!");
           }

           db.closeConnection();

           /* Back to menu */ //TODO: Go to all booked flights
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

}
