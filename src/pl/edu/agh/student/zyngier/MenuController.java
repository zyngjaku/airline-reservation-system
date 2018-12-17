package pl.edu.agh.student.zyngier;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;


public class MenuController {

    MenuController(){

    }

    public void ifUserIsNotLoggedIn(Stage primaryStage,  BorderPane root) {
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);

        Menu login = new Menu("Login");
        Menu register = new Menu("Register");


        menuBar.getMenus().addAll(login, register);
    }

    public void ifUserIsLoggedIn(Stage primaryStage,  BorderPane root) {
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);

        Menu logout = new Menu("Logout");

        menuBar.getMenus().addAll(logout);
    }

}
