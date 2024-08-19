package com.farhannr28.queengame.models;

import java.util.ArrayList;
import java.util.Collections;

public class GridInput {
    private int row;
    private int col;
    private int numOfColors;
    private static ArrayList<ArrayList<Integer>> regions;

    public GridInput(int row, int col, int numOfColors, ArrayList<ArrayList<Integer>> regions) {
        this.row = row;
        this.col = col;
        this.numOfColors = numOfColors;
        GridInput.regions = regions;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNumOfColors() {
        return numOfColors;
    }

    public static ArrayList<ArrayList<Integer>> getRegions() {
        return regions;
    }

    public void incrementRow(){
        this.row++;
        GridInput.regions.add(new ArrayList<>(Collections.nCopies(this.col, 0)));
    }

    public void incrementColumn(){
        this.col++;
        for (int i=0; i<this.row; i++){
            GridInput.regions.get(i).add(0);
        }
    }

    public void decrementRow(){
        this.row--;
        GridInput.regions.remove(GridInput.regions.size()-1);
    }

    public void decrementColumn(){
        this.col--;
        int n = GridInput.regions.get(0).size()-1;
        for (int i=0; i<this.row; i++){
            GridInput.regions.get(i).remove(n);
        }
    }

    public static void setRegion(ArrayList<ArrayList<Integer>> _regions){
        regions = _regions;
    }

    public void incrementNumberOfColors(){
        this.numOfColors++;
    }
}
