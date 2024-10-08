package com.farhannr28.queengame.services;

import com.farhannr28.queengame.models.Cell;
import com.farhannr28.queengame.models.GridInput;

import java.util.ArrayList;
import java.util.Collections;

public class Backtrack {

    private boolean solutionExist;
    private ArrayList<Cell> solution;
    private PieceValidator pv;

    public Backtrack(String piece, GridInput gi) {

        pv = new PieceValidator(piece, gi.getRow(), gi.getCol(), gi.getNumOfColors());
        solution = new ArrayList<>();
        solutionExist = true;

        if (piece.equals("BISHOP")){
            if (gi.getRow() + gi.getCol() - 1 >= gi.getNumOfColors()){
                backtrackDiagonalRegion(gi);
            } else {
                solutionExist = false;
            }
        } else if (piece.equals("KNIGHT")){
            backtrackKnight(gi);
        } else {
            if (gi.getNumOfColors()-1 <= gi.getRow() && gi.getNumOfColors()-1 <= gi.getCol()){
                backtrackRegion(gi, piece);
            } else {
                solutionExist = false;
            }
        }

        if (solution.isEmpty()){
            solutionExist = false;
        }
    }

    /* DEBUGGING PURPOSES */
    private void printSolution(){
        for (int i=0; i<solution.size(); i++){
            solution.get(i).printCell();
        }
    }

    public boolean getSolutionExist(){
        return solutionExist;
    }

    public ArrayList<Cell> getSolution() {
        return solution;
    }

//    private void backtrackRow(GridInput gi, String piece) {
//        ArrayList<Integer> ans = new ArrayList<>(Collections.nCopies(gi.getRow(), 0));
//        int row = 0;
//        int col = -1;
//        boolean found = false;
//        while (row != -1){
//            col++;
//            while (col < gi.getCol() && !found){
//                if (pv.validateLinear(col, true) && pv.validateEmptyRegion(gi.getRegions().get(row).get(col)) && pv.validateNonLinear(row, col)){
//                    pv.setNeighbor(col);
//                    pv.setVertical(col, true);
//                    pv.setFilledRegions(gi.getRegions().get(row).get(col), true);
//                    if (piece.equals("QUEEN")){
//                        pv.setDiagonal(row, col, true);
//                    }
//                    ans.set(row,col);
//                    if (row == gi.getRow()-1){
//                        found = true;
//                        for (int i=0; i<ans.size(); i++){
//                            solution.add(new Cell(i, ans.get(i)));
//                        }
//                    } else {
//                        row++;
//                        col = 0;
//                    }
//                } else {
//                    col++;
//                }
//            }
//            row--;
//            if (row > -1){
//                col = ans.get(row);
//                pv.setVertical(col, false);
//                pv.setFilledRegions(gi.getRegions().get(row).get(col) ,false);
//                pv.setNeighbor(col);
//                if (piece.equals("QUEEN")){
//                    pv.setDiagonal(row, col, false);
//                }
//            }
//        }
//    }
//
//    private void backtrackColumn(GridInput gi, String piece) {
//        ArrayList<Integer> ans = new ArrayList<>(Collections.nCopies(gi.getCol(), 0));
//        int col = 0;
//        int row = -1;
//        boolean found = false;
//        while (col != -1){
//            row++;
//            while (row < gi.getRow() && !found){
//                if (pv.validateLinear(row, false) && pv.validateEmptyRegion(gi.getRegions().get(row).get(col)) && pv.validateNonLinear(row, col)){
//                    pv.setNeighbor(row);
//                    pv.setHorizontal(row, true);
//                    pv.setFilledRegions(gi.getRegions().get(row).get(col), true);
//                    if (piece.equals("QUEEN")){
//                        pv.setDiagonal(row, col, true);
//                    }
//                    ans.set(col,row);
//                    if (col == gi.getCol()-1){
//                        found = true;
//                        for (int i=0; i<ans.size(); i++){
//                            solution.add(new Cell(ans.get(i), i));
//                        }
//                    } else {
//                        col++;
//                        row = 0;
//                    }
//                } else {
//                    row++;
//                }
//            }
//            col--;
//            if (col > -1){
//                row = ans.get(col);
//                pv.setHorizontal(row, false);
//                pv.setFilledRegions(gi.getRegions().get(row).get(col) ,false);
//                pv.setNeighbor(row);
//                if (piece.equals("QUEEN")){
//                    pv.setDiagonal(row, col, false);
//                }
//            }
//        }
//    }

    private void backtrackRegion(GridInput gi, String piece) {
        ArrayList<Integer> regionIndexes = new ArrayList<>(Collections.nCopies(gi.getNumOfColors(), 0));
        int row,col;
        Cell c;
        int region = 1;
        int index = -1;
        boolean found = false;
        while (region != 0){
            index++;
            while (index < RegionProcessor.getRegionPaths().get(region).size() && !found){
                c = RegionProcessor.getRegionPaths().get(region).get(index);
                row = c.getRow();
                col = c.getCol();
                if (pv.validateLinear(col, true) && pv.validateLinear(row, false) && pv.validateNonLinear(row, col)){
                    if (piece.equals("NEIGHBORQUEEN")){
                        pv.setNeighbor(row, col, true);
                    }
                    pv.setVertical(col, true);
                    pv.setHorizontal(row, true);
                    if (piece.equals("QUEEN")){
                        pv.setDiagonal(row, col, true);
                    }
                    regionIndexes.set(region,index);
                    if (region == gi.getNumOfColors()-1){
                        found = true;
                        for (int i=1; i<regionIndexes.size(); i++){
                            c = RegionProcessor.getRegionPaths().get(i).get(regionIndexes.get(i));
                            solution.add(c);
                        }
                    } else {
                        region++;
                        index = 0;
                    }
                } else {
                    index++;
                }
            }
            region--;
            if (region > 0){
                index = regionIndexes.get(region);
                c = RegionProcessor.getRegionPaths().get(region).get(index);
                row = c.getRow();
                col = c.getCol();
                pv.setVertical(col, false);
                pv.setHorizontal(row, false);
                if (piece.equals("NEIGHBORQUEEN")){
                    pv.setNeighbor(row, col, false);
                }
                if (piece.equals("QUEEN")){
                    pv.setDiagonal(row, col, false);
                }
            }
        }
    }

    private void backtrackDiagonalRegion(GridInput gi){
        ArrayList<Integer> regionIndexes = new ArrayList<>(Collections.nCopies(gi.getNumOfColors(), 0));
        int row,col;
        Cell c;
        int region = 1;
        int index = -1;
        boolean found = false;
        while (region != 0){
            index++;
            while (index < RegionProcessor.getRegionPaths().get(region).size() && !found){
                c = RegionProcessor.getRegionPaths().get(region).get(index);
                row = c.getRow();
                col = c.getCol();
                if (pv.validateNonLinear(row, col)){
                    pv.setDiagonal(row, col, true);
                    regionIndexes.set(region,index);
                    if (region == gi.getNumOfColors()-1){
                        found = true;
                        for (int i=1; i<regionIndexes.size(); i++){
                            c = RegionProcessor.getRegionPaths().get(i).get(regionIndexes.get(i));
                            solution.add(c);
                        }
                    } else {
                        region++;
                        index = 0;
                    }
                } else {
                    index++;
                }
            }
            region--;
            if (region > 0){
                index = regionIndexes.get(region);
                c = RegionProcessor.getRegionPaths().get(region).get(index);
                row = c.getRow();
                col = c.getCol();
                pv.setDiagonal(row, col, false);
            }
        }
    }

    private void backtrackKnight(GridInput gi){
        ArrayList<Integer> regionIndexes = new ArrayList<>(Collections.nCopies(gi.getNumOfColors(), 0));
        int row,col;
        Cell c;
        int region = 1;
        int index = -1;
        boolean found = false;
        while (region != 0){
            index++;
            while (index < RegionProcessor.getRegionPaths().get(region).size() && !found){
                c = RegionProcessor.getRegionPaths().get(region).get(index);
                row = c.getRow();
                col = c.getCol();
                if (pv.validateNonLinear(row, col)){
                    pv.setLArea(row, col, true);
                    regionIndexes.set(region,index);
                    if (region == gi.getNumOfColors()-1){
                        found = true;
                        for (int i=1; i<regionIndexes.size(); i++){
                            c = RegionProcessor.getRegionPaths().get(i).get(regionIndexes.get(i));
                            solution.add(c);
                        }
                    } else {
                        region++;
                        index = 0;
                    }
                } else {
                    index++;
                }
            }
            region--;
            if (region > 0){
                index = regionIndexes.get(region);
                c = RegionProcessor.getRegionPaths().get(region).get(index);
                row = c.getRow();
                col = c.getCol();
                pv.setLArea(row, col, false);
            }
        }
    }
}
