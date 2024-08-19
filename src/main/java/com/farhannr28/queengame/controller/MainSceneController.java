package com.farhannr28.queengame.controller;

import com.farhannr28.queengame.models.GridInput;
import com.farhannr28.queengame.services.Backtrack;
import com.farhannr28.queengame.services.RegionProcessor;
import com.farhannr28.queengame.utils.Util;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
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
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
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
    @FXML
    private Button incrementRow;
    @FXML
    private Button decrementRow;
    @FXML
    private Button incrementColumn;
    @FXML
    private Button decrementColumn;
    @FXML
    private Label rowLabel;
    @FXML
    private Label columnLabel;
    @FXML
    private GridPane colorSelector;
    @FXML
    private Button newColorButton;
    @FXML
    private Label alertLabel;

    /* Transition Attributes */
    // private TranslateTransition translateTransition;
    private ParallelTransition modifyParallelTranslation;

    /* CONTROLLER ATTRIBUTES */
    private GridController gc;
    private final FileController fc = new FileController();

    /* INPUT ATTRIBUTES */
    private GridInput gi;
    private boolean isBacktrackAlgorithm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // translateTransition = new TranslateTransition(Duration.millis(200), slider);
        TranslateTransition modifyContainerTranslation = new TranslateTransition(Duration.millis(300), modifyItemContainer);
        FadeTransition modifyFade = new FadeTransition(Duration.millis(300), modifyItemContainer);
        modifyFade.setFromValue(0);
        modifyFade.setToValue(1);
        modifyContainerTranslation.setFromX(250);
        modifyContainerTranslation.setToX(0);
        modifyParallelTranslation = new ParallelTransition(modifyFade, modifyContainerTranslation);
        Util.setModifyVisible(false);
        pieceChoice.getItems().addAll("GAME QUEEN", "STANDARD QUEEN", "ROOK", "BISHOP", "KNIGHT");
        pieceChoice.setValue("GAME QUEEN");
        isBacktrackAlgorithm = true;
        Util.setSelectedColor(1);
        alertLabel.setVisible(false);
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
            gc.generateRandomColors(gi.getNumOfColors());
            gc.renderGrid(GridInput.getRegions());
        } else {
            solveButton.setDisable(true);
            modifyButton.setDisable(true);
            this.selectedFileLabel.setText("Input Invalid");
        }
        this.selectedFileLabel.setLayoutX(135 - selectedFileLabel.getWidth()/2);
    }

    public void handleSolveClicked(javafx.scene.input.MouseEvent mouseEvent){
        gc.cleanSolution();
        RegionProcessor.process(GridInput.getRegions(), gi.getNumOfColors());
        if (RegionProcessor.validateRegion(gi.getNumOfColors())){
            alertLabel.setVisible(false);
            solveButton.setCursor(Cursor.WAIT);
            String piece;
            if (pieceChoice.getValue().equals("GAME QUEEN")) {
                piece = "NEIGHBORQUEEN";
            } else if (pieceChoice.getValue().equals("STANDARD QUEEN")) {
                piece = "QUEEN";
            } else {
                piece = pieceChoice.getValue();
            }
            if (isBacktrackAlgorithm){
                Backtrack b = new Backtrack(piece, gi);
                if (b.getSolutionExist()){
                    gc.displaySolution(piece, b.getSolution());
                } else {
                    alertLabel.setText("NO SOLUTION EXIST");
                    alertLabel.setLayoutX((gridBorder.getWidth() - alertLabel.getWidth()) / 2);
                    alertLabel.setLayoutY((gridBorder.getHeight() - alertLabel.getHeight()) / 2);
                    alertLabel.setVisible(true);
                    alertLabel.toFront();
                }
            } else {
                // TODO: Genetic Algorithm
            }
            solveButton.setCursor(Cursor.DEFAULT);
        } else {
            alertLabel.setText("REGION INVALID");
            alertLabel.setLayoutX((gridBorder.getWidth() - alertLabel.getWidth()) / 2);
            alertLabel.setLayoutY((gridBorder.getHeight() - alertLabel.getHeight()) / 2);
            alertLabel.setVisible(true);
            alertLabel.toFront();
        }
    }

    public void handleToggleClicked(javafx.scene.input.MouseEvent mouseEvent) {
//        if (isBacktrackAlgorithm) {
//            translateTransition.setToX(40);
//        } else {
//            translateTransition.setToX(0);
//        }
//        isBacktrackAlgorithm = !isBacktrackAlgorithm;
//        translateTransition.play();
    }

    public void handleModifyClicked(javafx.scene.input.MouseEvent mouseEvent){
        if (Util.getModifyVisible()){
            modifyItemContainer.setVisible(false);
            Util.setModifyVisible(false);
        } else {
            modifyItemContainer.setVisible(true);
            modifyParallelTranslation.play();
            syncModifyPanel();
            Util.setModifyVisible(true);
        }
    }

    private void syncModifyPanel(){
        rowLabel.setText(""+gi.getRow()+"");
        columnLabel.setText(""+gi.getCol()+"");
        renderColorSelector();
        if (gi.getNumOfColors() == 15){
            newColorButton.setDisable(true);
        }
    }

    private void updateCrementButton(){
        decrementRow.setDisable(gi.getRow() == 1);
        incrementRow.setDisable(gi.getRow() == 15);
        decrementColumn.setDisable(gi.getCol() == 1);
        incrementColumn.setDisable(gi.getCol() == 15);
    }

    public void handleIncrementRow (javafx.scene.input.MouseEvent mouseEvent){
        gi.incrementRow();
        gc.renderGrid(GridInput.getRegions());
        syncModifyPanel();
        updateCrementButton();
    }

    public void handleIncrementColumn (javafx.scene.input.MouseEvent mouseEvent){
        gi.incrementColumn();
        gc.renderGrid(GridInput.getRegions());
        syncModifyPanel();
        updateCrementButton();
    }

    public void handleDecrementRow (javafx.scene.input.MouseEvent mouseEvent){
        gi.decrementRow();
        gc.renderGrid(GridInput.getRegions());
        syncModifyPanel();
        updateCrementButton();
    }

    public void handleDecrementColumn (javafx.scene.input.MouseEvent mouseEvent){
        gi.decrementColumn();
        gc.renderGrid(GridInput.getRegions());
        syncModifyPanel();
        updateCrementButton();
    }

    public void renderColorSelector() {
        colorSelector.getChildren().clear();
        Color c;
        for (int i=1; i<GridController.getColors().size(); i++){
            c = GridController.getColors().get(i);
            colorSelector.add(createColorCell(c, i), (i-1)%5, (i-1)/5);
        }
    }

    private Rectangle createColorCell(Color c, int i){
        Rectangle r = new Rectangle(40, 40);
        r.setFill(c);
        if (Util.getSelectedColor() == i){
            r.setStroke(Color.rgb(239, 239, 239));
            r.toFront();
        } else {
            r.setStroke(Color.rgb(16,16,16));
        }
        r.setStrokeWidth(2);
        r.setArcHeight(5);
        r.setArcWidth(5);
        r.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
            Util.setSelectedColor(i);
            renderColorSelector();
        });
        return r;
    }

    public void handleNewColor(javafx.scene.input.MouseEvent mouseEvent){
        GridController.addColor();
        gi.incrementNumberOfColors();
        syncModifyPanel();
    }
}
