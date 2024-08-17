package com.farhannr28.queengame.models;

import java.util.ArrayList;

public class Cell {
    private int row;
    private int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isAdjacentTo(Cell other) {
        int rowDiff = Math.abs(this.row - other.row);
        int colDiff = Math.abs(this.col - other.col);
        return (rowDiff <= 1 && colDiff <= 1);
    }

    public boolean isInSameRowOrColumn(Cell other) {
        return this.row == other.row || this.col == other.col;
    }

    public ArrayList<Cell> getAdjacentCells(int Nrow, int Ncol) {
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        if (this.row != 0){
            adjacentCells.add(new Cell(this.row - 1, this.col));
        }
        if (this.col != 0){
            adjacentCells.add(new Cell(this.row, this.col - 1));
        }
        if (this.row != Nrow-1){
            adjacentCells.add(new Cell(this.row + 1, this.col));
        }
        if (this.col != Ncol-1){
            adjacentCells.add(new Cell(this.row, this.col + 1));
        }
        return adjacentCells;
    }

    /* DEBUGGING PURPOSES */
    public void printCell(){
        System.out.println(row+","+col);
    }
}
