package com.farhannr28.queengame.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private Button searchButton;
    @FXML
    private GridPane mainGrid;
    @FXML
    private Pane gridPane;
    @FXML
    private Pane gridBorder;
    @FXML
    private Label selectedFileLabel;

    /* CONTROLLER ATTRIBUTES */
    private GridController gc;
    private final FileController fc = new FileController();

    /* INPUT ATTRIBUTES */
    private int row;
    private int col;
    private int numOfColors;
    private ArrayList<Color> colors;
    private ArrayList<ArrayList<Integer>> regions;
    private String fileName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modifyItemContainer.setVisible(false);
        searchButton.setDisable(true);
        modifyButton.setDisable(true);
        gc = new GridController(mainGrid, gridPane, gridBorder);
    }

    public void handleSelectedFile(javafx.scene.input.MouseEvent mouseEvent){
        if (fc.selectFile()) {
            this.row = fc.getRow();
            this.col = fc.getCol();
            this.numOfColors = fc.getNumOfColors();
            this.regions = fc.getRegions();
            this.selectedFileLabel.setText(fc.getFileName());
            this.selectedFileLabel.setTextFill(Color.rgb(239, 239, 239));
            this.searchButton.setDisable(false);
            this.modifyButton.setDisable(false);
            this.colors = gc.generateRandomColors(numOfColors);
            gc.renderGrid(this.regions, this.colors);
        } else {
            this.selectedFileLabel.setText("Input Invalid");
            this.selectedFileLabel.setTextFill(Color.rgb(96,18,18));
        }
        this.selectedFileLabel.setLayoutX(135 - selectedFileLabel.getWidth()/2);
    }
}
