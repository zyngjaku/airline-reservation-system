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

public class SearchFlight {

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize(){
        messageLabel.setContentDisplay(ContentDisplay.CENTER);
        messageLabel.setText("Hello " + System.getProperty("firstName") + " " + System.getProperty("lastName"));
        //System.out.println("ID2:"+System.getProperty("id"));

    }

}
