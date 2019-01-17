package pl.edu.agh.student.zyngier;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Arrays;

public class SeatsController {

    private Stage seatStage;
    private Scene seatScene;
    private StackPane layout;

    private int sceneWidth;
    private int sceneHeight;
    private int seatSize;

    private Button[][] batony;
    private boolean[][] ifSeatIsFree;

    private int clicked_x=-1;
    private int clicked_y=-1;

    public SeatsController(){
        /* Init variables */
        layout = new StackPane();
        batony = new Button[30][6];
        seatSize = 25;
        sceneWidth = (30+2)*(seatSize+5);
        sceneHeight = (6+2)*(seatSize+5);
        ifSeatIsFree = new boolean[30][6];

        /* Set all as true in array */
        for (boolean[] row: ifSeatIsFree)
            Arrays.fill(row, true);

        /* Prepare scene */
        checkWhichSeatsAreOccupied();
        showSeatsOnScene();

        seatScene = new Scene(layout, sceneWidth, sceneHeight);
        seatStage = new Stage();

        seatStage.setTitle("Choose your seat in plane");
        seatStage.setScene(seatScene);
        seatStage.show();

        /* Controller */
        detectWhichSeatUserChoose();
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
                batony[i][j]=new Button(getColumnSymbol(j));

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
        //TODO: Connect with database
        ifSeatIsFree[0][2] = false;
        ifSeatIsFree[0][3] = false;
        ifSeatIsFree[10][5] = false;
        ifSeatIsFree[22][4] = false;
        ifSeatIsFree[22][5] = false;
    }

    private void detectWhichSeatUserChoose(){
        for(int i=0; i<30; i++) {
            for (int j = 0; j < 6; j++) {
                int finalI = i;
                int finalJ = j;
                batony[i][j].setOnAction(e -> {
                    if(ifSeatIsFree[finalI][finalJ]) {
                        if (clicked_x != -1 || clicked_y != -1) batony[clicked_x][clicked_y].setStyle("-fx-background-color: #009900; ");
                        batony[finalI][finalJ].setStyle("-fx-background-color: #FD6A02; ");
                        clicked_x = finalI;
                        clicked_y = finalJ;
                    }
                });
            }
        }
    }

    private String getColumnSymbol(int rowNumber){
        switch (rowNumber) {
            case 0:
               return "A";

            case 1:
                return "B";

            case 2:
                return "C";

            case 3:
                return "D";

            case 4:
                return "E";

            case 5:
                return "F";
        }

        return null;
    }
}
