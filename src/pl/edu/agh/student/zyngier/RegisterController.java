package pl.edu.agh.student.zyngier;

import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import pl.edu.agh.student.zyngier.database.DB;

public class RegisterController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize(){
        errorLabel.setContentDisplay(ContentDisplay.CENTER);
    }

    @FXML
    public void registerButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("RegisterController: Hold on... validating your data");

        if(emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            System.out.println("RegisterController: Please fill all data");

            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please fill all data!");
        }
        else {
            DB db = new DB();

            if(db.checkIfEmailExist(emailField.getText())){
                System.out.println("RegisterController: This email still exist");

                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("This email still exist");
            }
            else{

            }
        }
    }


}
