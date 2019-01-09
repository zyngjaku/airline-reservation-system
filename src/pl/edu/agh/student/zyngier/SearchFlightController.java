package pl.edu.agh.student.zyngier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
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
    private TableColumn<Flights, String> firstWayFlightTable_priceColumn;

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
    public void initialize(){
        //messageLabel.setContentDisplay(ContentDisplay.CENTER);
        //messageLabel.setTextFill(Color.BLACK);

        //messageLabel.setText("Hi " + System.getProperty("firstName") + " " + System.getProperty("lastName"));

        DB db =  new DB();
        db.openConnection();

        ArrayList<String> departureAirports = db.getDepartureAirports();

        for(String it : departureAirports){
            departureAirportChoice.getItems().add(it);
        }

        db.closeConnection();

        //Set TableView (upper)
        firstWayFlightTable.setDisable(true);
        firstWayFlightTable_fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        firstWayFlightTable_toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        firstWayFlightTable_departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        firstWayFlightTable_arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        firstWayFlightTable_priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        firstWayFlightTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1 && firstWayFlightTable.getSelectionModel().getSelectedItem() != null) {
                Flights selectedPerson = firstWayFlightTable.getSelectionModel().getSelectedItem();
                System.out.println(selectedPerson.getFrom());
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
            if (event.getClickCount() > 1 && returnWayFlightTable.getSelectionModel().getSelectedItem() != null) {
                Flights selectedPerson = returnWayFlightTable.getSelectionModel().getSelectedItem();
                System.out.println(selectedPerson.getFrom());
            }
        });
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
        //Checking if all fields are not empty
        if(!departureAirportChoice.getSelectionModel().isEmpty() && !arrivalAirportChoice.getSelectionModel().isEmpty() && flyOutDate.getValue() != null && (flyBackDate.getValue() != null || oneWayRadioButton == (RadioButton) typeOfTripGroup.getSelectedToggle())) {
            firstWayFlightTable.getItems().clear();
            returnWayFlightTable.getItems().clear();

            DB db = new DB();
            db.openConnection();

            ArrayList<Flights> flights;
            flights = db.getFlights(departureAirportChoice.getValue().toString(), arrivalAirportChoice.getValue().toString(), flyOutDate.getValue().toString());

            for(Flights it : flights) {
                firstWayFlightTable.getItems().add(it);
            }

            firstWayFlightTable.setDisable(false);
            returnWayFlightTable.setDisable(true);

            // Checking if user clicked return
            if (returnRadioButton == (RadioButton) typeOfTripGroup.getSelectedToggle()) {
                System.out.println(arrivalAirportChoice.getValue().toString() + "->" + departureAirportChoice.getValue().toString());

                flights.clear();
                flights = db.getFlights(arrivalAirportChoice.getValue().toString(), departureAirportChoice.getValue().toString(), flyBackDate.getValue().toString());

                for(Flights it : flights) {
                    returnWayFlightTable.getItems().add(it);
                }

                returnWayFlightTable.setDisable(false);
            }
        }
    }

    /*
    private void addButtonToTable(TableView<Flights> tableName) {
        TableColumn<Flights, Void> firstWayFlightTable_bookColumn = new TableColumn("");

        Callback<TableColumn<Flights, Void>, TableCell<Flights, Void>> cellFactory = new Callback<TableColumn<Flights, Void>, TableCell<Flights, Void>>() {
            @Override
            public TableCell<Flights, Void> call(final TableColumn<Flights, Void> param) {
                final TableCell<Flights, Void> cell = new TableCell<Flights, Void>() {
                    private final Button btn = new Button("select");{
                        btn.setOnAction((ActionEvent event) -> {
                            Flights data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        firstWayFlightTable_bookColumn.setCellFactory(cellFactory);

        tableName.getColumns().add(firstWayFlightTable_bookColumn);
    }
    */
}
