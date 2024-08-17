package com.farhannr28.queengame.controller;

import com.farhannr28.queengame.models.GridInput;
import com.farhannr28.queengame.services.Backtrack;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    /* JAVAFX ELEMENTS ATTRIBUTES */
    @FXML
    private Pane modifyItemContainer;
    @FXML
    private Button modifyButton;
    @FXML
    private Button solveButton;
    @FXML
    private GridPane mainGrid;
    @FXML
    private Pane gridPane;
    @FXML
    private Pane gridBorder;
    @FXML
    private Label selectedFileLabel;
    @FXML
    private ChoiceBox<String> pieceChoice;
    @FXML
    private Circle slider;

    private TranslateTransition translateTransition;

    /* CONTROLLER ATTRIBUTES */
    private GridController gc;
    private final FileController fc = new FileController();

    /* INPUT ATTRIBUTES */
    private GridInput gi;
    private ArrayList<Color> colors;
    private boolean isBacktrackAlgorithm;
    private String piece;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        translateTransition = new TranslateTransition(Duration.millis(200), slider);
        pieceChoice.getItems().addAll("GAME QUEEN", "STANDARD QUEEN", "ROOK", "BISHOP", "KNIGHT");
        pieceChoice.setValue("GAME QUEEN");
        isBacktrackAlgorithm = true;
        modifyItemContainer.setVisible(false);
        solveButton.setDisable(true);
        modifyButton.setDisable(true);
        gc = new GridController(mainGrid, gridPane, gridBorder);
    }

    public void handleSelectedFile(javafx.scene.input.MouseEvent mouseEvent){
        if (fc.selectFile()) {
            gi = new GridInput(fc.getRow(), fc.getCol(), fc.getNumOfColors(),fc.getRegions());
            this.selectedFileLabel.setText(fc.getFileName());
            this.solveButton.setDisable(false);
            this.modifyButton.setDisable(false);
            this.colors = gc.generateRandomColors(gi.getNumOfColors());
            gc.renderGrid(gi.getRegions(), this.colors);
        } else {
            solveButton.setDisable(true);
            modifyButton.setDisable(true);
            this.selectedFileLabel.setText("Input Invalid");
        }
        this.selectedFileLabel.setLayoutX(135 - selectedFileLabel.getWidth()/2);
    }

    public void handleSolveClicked(javafx.scene.input.MouseEvent mouseEvent){
        solveButton.setCursor(Cursor.WAIT);
        if (pieceChoice.getValue().equals("GAME QUEEN")) {
            piece = "NEIGHBORQUEEN";
        } else if (pieceChoice.getValue().equals("STANDARD QUEEN")) {
            piece = "QUEEN";
        } else {
            piece = pieceChoice.getValue();
        }
        String searchMode;
        if (isBacktrackAlgorithm){
            Backtrack b = new Backtrack(piece, gi);
            gc.displaySolution(piece, b.getSolution());
        } else {
            // TODO: Genetic Algorithm
        }

        solveButton.setCursor(Cursor.DEFAULT);
    }

    public void handleToggleClicked(javafx.scene.input.MouseEvent mouseEvent) {
        if (isBacktrackAlgorithm) {
            translateTransition.setToX(40);
        } else {
            translateTransition.setToX(0);
        }
        isBacktrackAlgorithm = !isBacktrackAlgorithm;
        translateTransition.play();
    }
}
