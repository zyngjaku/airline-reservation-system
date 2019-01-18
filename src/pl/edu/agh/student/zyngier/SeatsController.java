package pl.edu.agh.student.zyngier;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.agh.student.zyngier.services.DB;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class SeatsController {

    public Stage seatStage;
    private Scene seatScene;
    private StackPane layout;

    private double sceneWidth;
    private double sceneHeight;
    private int seatSize;

    private Button[][] batony;
    private boolean[][] ifSeatIsFree;

    private int clickedSeatRow =-1;
    private int clickedSeatColumn =-1;

    private int seatRow;
    private char seatColumn;

    private String flightNumber;
    private String flightDate;

    private Button confirmButton;
    private Label infoLabel;

    public SeatsController(String flightNumber, String flightDate){
        /* Init variables */
        layout = new StackPane();
        batony = new Button[30][6];
        seatSize = 25;
        sceneWidth = (30+1)*(seatSize+5);
        sceneHeight = (6+3.5)*(seatSize+5);
        ifSeatIsFree = new boolean[30][6];
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        confirmButton = new Button("Choose this seat");
        infoLabel = new Label("Choose your seat in the plane!");

        seatRow = 0;
        seatColumn = 'X';

        /* Set all as true in array */
        for (boolean[] row: ifSeatIsFree)
            Arrays.fill(row, true);

        /* Prepare scene */
        checkWhichSeatsAreOccupied();
        showSeatsOnScene();
        setUpButtonAndInfoLabel();

        seatScene = new Scene(layout, sceneWidth, sceneHeight);
        seatStage = new Stage();

        seatStage.setTitle("Choose your seat in plane");
        seatStage.setResizable(false);

        /* Block background window */
        seatStage.initModality(Modality.WINDOW_MODAL);
        seatStage.initOwner(Main.getState());
        seatStage.setScene(seatScene);

        seatStage.show();

        /* Controller */
        detectWhichSeatUserChoose();

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                seatStage.hide();
            }
        });
    }

    private void setClickedSeatRow(int value){
        clickedSeatRow = value;
        seatRow = value+1;
    }

    private void setClickedSeatColumn(int value){
        clickedSeatColumn = value;
        seatColumn = getColumnSymbol(value);
    }

    public int getSeatRow(){
        return seatRow;
    }

    public char getSeatColumn(){
        return seatColumn;
    }

    private void setUpButtonAndInfoLabel(){
        /* Setup button */
        confirmButton.setPrefWidth(3.5*(seatSize+5));
        confirmButton.setPrefHeight(seatSize+5);

        confirmButton.setTranslateX(30.5*(seatSize+5)-(3.5*(seatSize+5))/2-sceneWidth/2);
        confirmButton.setTranslateY(8.5*(seatSize+5)-sceneHeight/2);

        layout.getChildren().add(confirmButton);

        /* Setup label */
        infoLabel.setPrefWidth(sceneWidth/2);
        infoLabel.setPrefHeight(seatSize+5);

        infoLabel.setTranslateX(0.5*(seatSize+5)-sceneWidth/4);
        infoLabel.setTranslateY(8.5*(seatSize+5)-sceneHeight/2);

        layout.getChildren().add(infoLabel);
    }

    private void showSeatsOnScene(){
        for(int i=0; i<30; i++){
            /* Set label with number of row */
            Label seatRowLabel = new Label(Integer.toString(i+1));

            seatRowLabel.setTranslateX(i*(seatSize+5) - sceneWidth/2 + (seatSize+5));
            seatRowLabel.setTranslateY(3*(seatSize+5) - sceneHeight/2 + (seatSize+5));

            layout.getChildren().add(seatRowLabel);

            for(int j=0; j<6; j++){
                /* Set buttons as a seats */
                batony[i][j]=new Button(String.valueOf(getColumnSymbol(j)));

                /* Statement to detect aisle in plane */
                if(j<3) batony[i][j].setTranslateY((j+1)*(seatSize+5) - sceneHeight/2);
                else batony[i][j].setTranslateY((j+2)*(seatSize+5) - sceneHeight/2);

                batony[i][j].setTranslateX((i+1)*(seatSize+5) - sceneWidth/2);

                batony[i][j].setPrefWidth(seatSize);
                batony[i][j].setPrefHeight(seatSize);

                if(ifSeatIsFree[i][j]) batony[i][j].setStyle("-fx-background-color: #009900; ");
                else batony[i][j].setStyle("-fx-background-color: #990000; ");

                layout.getChildren().add(batony[i][j]);
            }
        }
    }

    private void checkWhichSeatsAreOccupied(){
        DB db = new DB();
        db.openConnection();

        Map<Integer, Character> occupiedSeats = db.getOccpiedSeatsInPlane(flightNumber, flightDate);

        db.closeConnection();

        Iterator it = occupiedSeats.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ifSeatIsFree[(int) pair.getKey()-1][getColumnIndex((Character) pair.getValue())] = false;
            it.remove();
        }
    }

    private void detectWhichSeatUserChoose(){
        for(int i=0; i<30; i++) {
            for (int j = 0; j < 6; j++) {
                int tmp_i = i;
                int tmp_j = j;
                batony[i][j].setOnAction(e -> {
                    if(ifSeatIsFree[tmp_i][tmp_j]) {
                        if (clickedSeatRow != -1 || clickedSeatColumn != -1)
                            batony[clickedSeatRow][clickedSeatColumn].setStyle("-fx-background-color: #009900; ");

                        batony[tmp_i][tmp_j].setStyle("-fx-background-color: #FD6A02; ");

                        setClickedSeatRow(tmp_i);
                        setClickedSeatColumn(tmp_j);

                        infoLabel.setText("You choose seat: "+getSeatRow()+getSeatColumn());
                    }
                });
            }
        }
    }

    private char getColumnSymbol(int columnNumber){
        switch (columnNumber) {
            case 0:
                return 'A';

            case 1:
                return 'B';

            case 2:
                return 'C';

            case 3:
                return 'D';

            case 4:
                return 'E';

            case 5:
                return 'F';
        }

        return 'X';
    }

    private int getColumnIndex(char rowSymbol){
        switch (rowSymbol) {
            case 'A':
                return 0;

            case 'B':
                return 1;

            case 'C':
                return 2;

            case 'D':
                return 3;

            case 'E':
                return 4;

            case 'F':
                return 5;
        }

        return -1;
    }
}
