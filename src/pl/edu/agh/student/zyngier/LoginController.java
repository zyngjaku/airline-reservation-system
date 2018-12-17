package pl.edu.agh.student.zyngier;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.edu.agh.student.zyngier.database.DB;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize(){
        errorLabel.setContentDisplay(ContentDisplay.CENTER);
    }

    @FXML
    public void loginButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("LoginController: Hold on... validating your data");
        String email = emailField.getText();
        String password = passwordField.getText();

        DB db = new DB();

        if(db.checkIfEmailAndPasswordIsCorrect(email, password)) {
            System.out.println("LoginController: access obtained");
            errorLabel.setText("");
        }
        else {
            System.out.println("LoginController: Wrong email or password!");
            errorLabel.setText("Wrong email or password!");
        }
    }


}
