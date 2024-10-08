package com.farhannr28.queengame.controller;

import com.farhannr28.queengame.models.Cell;
import com.farhannr28.queengame.models.GridInput;
import com.farhannr28.queengame.utils.Util;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import static com.farhannr28.queengame.utils.Util.randomInt;


public class GridController {
    private GridPane mainGrid;
    private Pane gridPane;
    private Pane gridBorder;
    private int cellSize;
    private static ArrayList<Color> colors;

    public GridController(GridPane _mainGrid, Pane _gridPane, Pane _gridBorder){
        this.mainGrid = _mainGrid;
        this.gridPane = _gridPane;
        this.gridBorder = _gridBorder;
        mainGrid.getChildren().remove(0);
        mainGrid.add(createGridCell(40, 0, 0), 0, 0);

        /* TESTING PURPOSES */
//        final int WIDTH = 10;
//        final int HEIGHT = 10;
//        final int NUMCOLORS = 6;
//        ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
//        for (int i=0; i<HEIGHT; i++){
//            grid.add(new ArrayList<>());
//            for (int j=0; j<WIDTH; j++){
//                grid.get(i).add(random.nextInt(NUMCOLORS));
//            }
//        }
//        ArrayList<Color> colors = new ArrayList<>();
//        for (int i=0; i<NUMCOLORS; i++){
//            colors.add(Color.hsb(randomInt(random, 0, 360), (double) randomInt(random, 42, 98) / 100, (double) randomInt(random, 40, 90) / 100));
//        }
//        renderGrid(grid, colors);
    }

    public static ArrayList<Color> getColors(){
        return colors;
    }

    public static Color randomSingleColor(){
        return Color.hsb(randomInt(0, 360), (double) randomInt(42, 98) / 100, (double) randomInt(40, 90) / 100);
    }

    public void generateRandomColors(int numOfColors) {
        ArrayList<Color> ret = new ArrayList<>();
        ret.add(Color.rgb(16, 16, 16));
        for (int i=1; i<numOfColors; i++){
            ret.add(randomSingleColor());
        }
        colors = ret;
    }

    public static void addColor(){
        colors.add(randomSingleColor());
    }

    public void renderGrid(ArrayList<ArrayList<Integer>> grid){
        int newNumRows = grid.size();
        int newNumCols = grid.get(0).size();
        cellSize = Math.min(600 / (Math.max(newNumRows, newNumCols)), 150);
        int oldNumRows = mainGrid.getRowCount();
        int oldNumCols = mainGrid.getColumnCount();

        cleanSolution();

        if (newNumRows < oldNumRows){
            for (int row = oldNumRows-1; row >= newNumRows; row--) {
                int finalRow = row;
                mainGrid.getRowConstraints().remove(finalRow);
                mainGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == finalRow);
            }
        } else {
            for (int row = oldNumRows; row < newNumRows; row++) {
                mainGrid.getRowConstraints().add(new RowConstraints());
                for (int col = 0; col < oldNumCols; col++) {
                    mainGrid.add(createGridCell(cellSize, row, col), col, row);
                }
            }
        }

        if (newNumCols < oldNumCols){
            for (int col = oldNumCols-1; col >= newNumCols; col--) {
                int finalCol = col;
                mainGrid.getColumnConstraints().remove(finalCol);
                mainGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == finalCol);
            }
        } else {
            for (int col = oldNumCols; col < newNumCols; col++) {
                mainGrid.getColumnConstraints().add(new ColumnConstraints());
                for (int row = 0; row < newNumRows; row++) {
                    mainGrid.add(createGridCell(cellSize, row, col), col, row);
                }
            }
        }

        mainGrid.setPrefHeight(cellSize * newNumRows);
        mainGrid.setPrefWidth(cellSize * newNumCols);

        gridBorder.setPrefHeight(cellSize * newNumRows + 10);
        gridBorder.setPrefWidth(cellSize * newNumCols + 10);
        gridBorder.setMaxHeight(cellSize * newNumRows + 10);
        gridBorder.setMaxWidth(cellSize * newNumCols + 10);

        gridPane.setPrefHeight(cellSize * newNumRows + 50);
        gridPane.setPrefWidth(cellSize * newNumCols + 50);
        gridPane.setMaxHeight(cellSize * newNumRows + 50);
        gridPane.setMaxWidth(cellSize * newNumCols + 50);

        for (int i=0; i<newNumRows; i++){
            mainGrid.getRowConstraints().get(i).setPrefHeight(cellSize);
        }
        for (int i=0; i<newNumCols; i++){
            mainGrid.getColumnConstraints().get(i).setPrefWidth(cellSize);
        }

        mainGrid.getChildren().forEach(node -> {
            int i = GridPane.getRowIndex(node);
            int j = GridPane.getColumnIndex(node);
            Color color = colors.get(grid.get(i).get(j));
            ((Rectangle) node).setFill(color);
            node.setLayoutX(0);
            node.setLayoutY(0);
            ((Rectangle) node).setHeight(cellSize);
            ((Rectangle) node).setWidth(cellSize);
            node.getStyleClass().add("Grid-Cells");
            ((Rectangle) node).setStrokeWidth((double) cellSize / 20);
        });

        mainGrid.setLayoutX(5);
        mainGrid.setLayoutY(5);
    }

    public void displaySolution(String piece, ArrayList<Cell> solution){
        cleanSolution();
        Image image = new Image("com/farhannr28/queengame/img/" + piece + ".png");
        for (Cell c : solution){
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(0.6 * cellSize);
            imageView.setFitHeight(0.6 * cellSize);
            mainGrid.add(imageView, c.getCol(), c.getRow());
            GridPane.setHalignment(imageView, HPos.CENTER); // Horizontal alignment
            GridPane.setValignment(imageView, VPos.CENTER);
        }
    }

    public void cleanSolution(){
        javafx.collections.transformation.FilteredList<javafx.scene.Node> nodesToRemove = mainGrid.getChildren().filtered(node -> node instanceof ImageView);
        mainGrid.getChildren().removeAll(nodesToRemove);
    }

    private Rectangle createGridCell(int size, int i, int j){
        Rectangle r = new Rectangle(size, size, Color.rgb(16,16,16));
        r.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
            if (Util.getModifyVisible()){
                ArrayList<ArrayList<Integer>> newRegion = GridInput.getRegions();
                newRegion.get(i).set(j, Util.getSelectedColor());
                GridInput.setRegion(newRegion);
                renderGrid(GridInput.getRegions());
            }
        });

        return r;
    }

}
