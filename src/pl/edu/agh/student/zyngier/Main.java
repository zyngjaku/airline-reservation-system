package pl.edu.agh.student.zyngier;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import jdk.nashorn.internal.parser.JSONParser;
import pl.edu.agh.student.zyngier.database.DB;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

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
        root = new BorderPane(FXMLLoader.load(getClass().getResource("login.fxml")));
        //new MenuController().ifUserIsNotLoggedIn(mainStage, root);
        loginScreen = new Scene(root, stageWidth, stageHeight);

        root = new BorderPane(FXMLLoader.load(getClass().getResource("register.fxml")));
        registerScreen = new Scene(root, stageWidth, stageHeight);

        /* Show stage */
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