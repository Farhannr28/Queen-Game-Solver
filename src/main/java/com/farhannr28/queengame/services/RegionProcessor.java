package com.farhannr28.queengame.services;

import com.farhannr28.queengame.models.Cell;

import java.util.ArrayList;
import java.util.Objects;

public class RegionProcessor {

    private static int nRow;
    private static int nCol;
    private static boolean[][] vis;
    private static ArrayList<ArrayList<Integer>> regions;
    private static int regionCount;
    private static ArrayList<ArrayList<Cell>> regionPaths;

    private static void dfs(Cell c, int r){
        if (!vis[c.getRow()][c.getCol()]){
            vis[c.getRow()][c.getCol()] = true;
            regionPaths.get(r).add(c);
            ArrayList<Cell> arr = c.getAdjacentCells(nRow, nCol);
            for (Cell c2 : arr){
                if (Objects.equals(regions.get(c2.getRow()).get(c2.getCol()), r)){
                    dfs(c2, r);
                }
            }
        }
    }

    public static void process(ArrayList<ArrayList<Integer>> _regions, int numColors){
        regions = _regions;
        nRow = regions.size();
        nCol = regions.get(0).size();
        vis = new boolean[nRow][nCol];
        regionCount = 0;
        regionPaths = new ArrayList<>();
        for (int i=0; i<numColors; i++){
            regionPaths.add(new ArrayList<>());
        }
        for (int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                if (!vis[i][j]){
                    regionCount++;
                    dfs(new Cell(i,j), regions.get(i).get(j));
                }
            }
        }

//        for (ArrayList<Cell> region : regionPaths){
//            for (Cell c : region){
//                c.printCell();
//            }
//            System.out.println();
//        }
    }

    public static boolean validateRegion(int numColors){
        return regionCount == numColors;
    }

    public static ArrayList<ArrayList<Cell>> getRegionPaths(){
        return regionPaths;
    }
}
