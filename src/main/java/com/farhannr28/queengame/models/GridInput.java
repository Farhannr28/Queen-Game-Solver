package com.farhannr28.queengame.models;

import java.util.ArrayList;

public class GridInput {
    private int row;
    private int col;
    private int numOfColors;
    private ArrayList<ArrayList<Integer>> regions;

    public GridInput(int row, int col, int numOfColors, ArrayList<ArrayList<Integer>> regions) {
        this.row = row;
        this.col = col;
        this.numOfColors = numOfColors;
        this.regions = regions;
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

    public ArrayList<ArrayList<Integer>> getRegions() {
        return regions;
    }
}
