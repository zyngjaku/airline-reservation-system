package pl.edu.agh.student.zyngier;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
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

import pl.edu.agh.student.zyngier.database.DB;
import pl.edu.agh.student.zyngier.database.Sha1;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.util.Objects.hash;

public class Main extends Application {
    public static final String AIRLINE_NAME = "Example Airline";

    Scene loginScreen, registerScreen;

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane root = new BorderPane(FXMLLoader.load(getClass().getResource("login.fxml")));
        new MenuController().ifUserIsNotLoggedIn(primaryStage, root);
        loginScreen = new Scene(root);

        root = new BorderPane(FXMLLoader.load(getClass().getResource("register.fxml")));
        new MenuController().ifUserIsNotLoggedIn(primaryStage, root);
        registerScreen = new Scene(root);

        primaryStage.setTitle(AIRLINE_NAME + " Reservation System");
        primaryStage.setScene(loginScreen);
        primaryStage.show();

        System.out.println(primaryStage.getWidth());
        System.out.println(primaryStage.getHeight());
        Sha1 hash = new Sha1();
        System.out.println(hash.sha1("admin"));
        DB db = new DB();
        System.out.println(db.checkIfEmailAndPasswordIsCorrect("admin@gmail.com", "d033e22ae348aeb5660fc2140aec35850c4da997"));

/*
        Label label1= new Label("This is the first scene");
        //Button button1= new Button("Go to scene 2");
        //button1.setOnAction(e -> primaryStage.setScene(scene2));
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene1= new Scene(layout1, 300, 250);

//Scene 2
        Label label2= new Label("This is the second scene");
        //Button button2= new Button("Go to scene 1");
        //button2.setOnAction(e -> primaryStage.setScene(scene1));
        VBox layout2= new VBox(20);
        layout2.getChildren().addAll(label2, button2);
        scene2= new Scene(layout2,300,250);


        primaryStage.setScene(scene1);
        primaryStage.show();
        */
    }

    public static void main(String[] args) {
        launch(args);
    }

}

/*
MENU

BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 250, Color.WHITE);

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);

        // File menu - new, save, exit
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(newMenuItem, saveMenuItem,
                new SeparatorMenuItem(), exitMenuItem);

        Menu webMenu = new Menu("Web");
        CheckMenuItem htmlMenuItem = new CheckMenuItem("HTML");
        htmlMenuItem.setSelected(true);
        webMenu.getItems().add(htmlMenuItem);

        CheckMenuItem cssMenuItem = new CheckMenuItem("CSS");
        cssMenuItem.setSelected(true);
        webMenu.getItems().add(cssMenuItem);

        Menu sqlMenu = new Menu("SQL");
        ToggleGroup tGroup = new ToggleGroup();
        RadioMenuItem mysqlItem = new RadioMenuItem("MySQL");
        mysqlItem.setToggleGroup(tGroup);

        RadioMenuItem oracleItem = new RadioMenuItem("Oracle");
        oracleItem.setToggleGroup(tGroup);
        oracleItem.setSelected(true);

        sqlMenu.getItems().addAll(mysqlItem, oracleItem,
                new SeparatorMenuItem());

        Menu tutorialManeu = new Menu("Tutorial");
        tutorialManeu.getItems().addAll(
                new CheckMenuItem("Java"),
                new CheckMenuItem("JavaFX"),
                new CheckMenuItem("Swing"));

        sqlMenu.getItems().add(tutorialManeu);

        menuBar.getMenus().addAll(fileMenu, webMenu, sqlMenu);

        primaryStage.setScene(scene);
        primaryStage.show();
 */
