package pl.edu.agh.student.zyngier;

import javafx.fxml.FXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import javafx.scene.layout.BorderPane;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main extends Application {
    public static final String AIRLINE_NAME = "Example Airline";

    private static final int stageWidth = 800;
    private static final int stageHeight = 400;

    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Scene loginScreen = null, registerScreen = null;
    private static BorderPane root = null;
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;

        /* Prepare scenes */
        root = new BorderPane(FXMLLoader.load(getClass().getResource("fxml/login.fxml")));
        loginScreen = new Scene(root, stageWidth, stageHeight);

        root = new BorderPane(FXMLLoader.load(getClass().getResource("fxml/register.fxml")));
        registerScreen = new Scene(root, stageWidth, stageHeight);

        /* Show stage */
        mainStage.setResizable(false);
        mainStage.setTitle(AIRLINE_NAME + " Reservation System");
        mainStage.setScene(loginScreen);
        mainStage.show();
    }

    public static Stage getState() {

        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}