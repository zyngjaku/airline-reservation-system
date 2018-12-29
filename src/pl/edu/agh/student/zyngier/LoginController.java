package pl.edu.agh.student.zyngier;

import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.paint.Color;
import pl.edu.agh.student.zyngier.database.DB;

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
    public void loginUserButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("LoginController: Hold on... validating your data");
        String email = emailField.getText();
        String password = passwordField.getText();

        DB db = new DB();

        if(!db.checkIfEmailAndPasswordIsCorrect(email, password)) setResultMessage(messageLabel,false,"Wrong email or password!");
        else {
            setResultMessage(messageLabel, true, "");

            Main.session.setProperty("id", db.getUserID(email, password));
            //System.out.println(Main.session.getProperty("id"));
        }
    }

    @FXML
    public void goToRegisterSceneButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("[loginScreen] -> [registerScreen]");
        setResultMessage(messageLabel, true, "");

        Main.getState().setScene(Main.registerScreen);
    }

    private void setResultMessage(Label messageLabel, Boolean status, String message){
        if(!message.equals("")) System.out.println(this.getClass().getSimpleName() + ": " + message);

        if(status) messageLabel.setTextFill(Color.GREEN);
        else messageLabel.setTextFill(Color.RED);
        messageLabel.setText(message);
    }

}
