package pl.edu.agh.student.zyngier;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.edu.agh.student.zyngier.service.DB;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize(){
        messageLabel.setContentDisplay(ContentDisplay.CENTER);
    }

    @FXML
    public void loginUserButton(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println("LoginController: Hold on... validating your data");
        String email = emailField.getText();
        String password = passwordField.getText();

        DB db = new DB();
        db.openConnection();

        if(!db.checkIfEmailAndPasswordIsCorrect(email, password)) setResultMessage(messageLabel,false,"Wrong email or password!");
        else {
            setResultMessage(messageLabel, true, "");

            /* Clear login and password */
            emailField.setText(null);
            passwordField.setText(null);

            /* Prepare scene */
            BorderPane root = new BorderPane(FXMLLoader.load(getClass().getResource("fxml/menu.fxml")));
            Scene menuScene = new Scene(root, 800, 400);

            /* Go to new scene */
            System.out.println("[loginScreen] -> [menuScene]");
            Main.getState().setScene(menuScene);
        }

    }

    @FXML
    public void goToRegisterSceneButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("[loginScreen] -> [registerScreen]");
        setResultMessage(messageLabel, true, "");

        /* Clear login and password */
        emailField.setText(null);
        passwordField.setText(null);

        Main.getState().setScene(Main.registerScreen);
    }

    private void setResultMessage(Label messageLabel, Boolean status, String message){
        if(!message.equals("")) System.out.println(this.getClass().getSimpleName() + ": " + message);

        if(status) messageLabel.setTextFill(Color.GREEN);
        else messageLabel.setTextFill(Color.RED);
        messageLabel.setText(message);
    }

}
