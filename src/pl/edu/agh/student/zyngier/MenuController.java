package pl.edu.agh.student.zyngier;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize(){
        messageLabel.setContentDisplay(ContentDisplay.CENTER);
        messageLabel.setTextFill(Color.BLACK);

        messageLabel.setText("Hello " + System.getProperty("firstName") + " " + System.getProperty("lastName"));
    }

    @FXML
    public void searchFlightButton(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane root = new BorderPane(FXMLLoader.load(getClass().getResource("fxml/search_flight.fxml")));
        Scene searchFlightScene = new Scene(root, 800, 400);

        System.out.println("[menuScene] -> [searchFlightScene]");

        Main.getState().setScene(searchFlightScene);
    }

    @FXML
    public void bookedFlightButton(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane root = new BorderPane(FXMLLoader.load(getClass().getResource("fxml/booked_flight.fxml")));
        Scene searchFlightScene = new Scene(root, 800, 400);

        System.out.println("[menuScene] -> [bookedFlightScene]");

        Main.getState().setScene(searchFlightScene);
    }

    @FXML
    public void profileButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("Profile id: " + System.getProperty("id"));
        System.out.println("First name: " + System.getProperty("firstName"));
        System.out.println("Last name: " + System.getProperty("lastName"));
        System.out.println("PESEL: " + System.getProperty("pesel"));

    }

    @FXML
    public void logoutButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("[menuScreen] -> [loginScreen]");

        System.clearProperty("id");
        System.clearProperty("firstName");
        System.clearProperty("lastName");
        System.clearProperty("pesel");

        Main.getState().setScene(Main.loginScreen);
    }

}
