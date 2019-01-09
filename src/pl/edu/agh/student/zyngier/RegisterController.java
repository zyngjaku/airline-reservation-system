package pl.edu.agh.student.zyngier;

import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import pl.edu.agh.student.zyngier.database.DB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField peselField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize(){
        messageLabel.setContentDisplay(ContentDisplay.CENTER);
    }

    @FXML
    public void registerNewUserButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("RegisterController: Hold on... validating your data");

        //Checking if labels aren't empty
        if(emailField.getText().isEmpty() || passwordField.getText().isEmpty() || peselField.getText().isEmpty()) setResultMessage(messageLabel, false, "Please fill all data!");
        else {
            Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
            Matcher matcher = pattern.matcher(emailField.getText());
            //Checking email format
            if(!matcher.matches()) setResultMessage(messageLabel, false, "Wrong format email adress!");
            else {
                //Checking if pesel is valid
                if (!isPeselValid(peselField.getText())) setResultMessage(messageLabel, false, "This PESEL is not valid");
                else {
                    //Checking if password has min 6 characters
                    if (passwordField.getText().length() < 6) setResultMessage(messageLabel, false, "Password is too short (min. 6 characters)");
                    else {
                        DB db = new DB();
                        db.openConnection();

                        //Checking if email exist in database
                        if (db.checkIfEmailExist(emailField.getText())) setResultMessage(messageLabel, false, "This email still exist");
                        else {
                            setResultMessage(messageLabel, true, "Successfully created account");
                            db.registerNewUser(emailField.getText(), passwordField.getText(), firstNameField.getText(), lastNameField.getText(), peselField.getText());
                        }

                        db.closeConnection();
                    }
                }
            }
        }
    }

    @FXML
    public void goToLoginSceneButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("[registerScreen] -> [loginScreen]");
        setResultMessage(messageLabel, false, "");

        Main.getState().setScene(Main.loginScreen);
    }

    private void setResultMessage(Label messageLabel, Boolean status, String message){
        if(!message.equals("")) System.out.println(this.getClass().getSimpleName() + ": " + message);

        if(status) messageLabel.setTextFill(Color.GREEN);
        else messageLabel.setTextFill(Color.RED);
        messageLabel.setText(message);
    }

    private boolean isPeselValid(String pesel) {
        //TODO: Check pesel

        return true;
    }

}
