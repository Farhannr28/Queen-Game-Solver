package com.farhannr28.queengame.controller;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileController {

    private int row;
    private int col;
    private int numOfColors;
    private ArrayList<ArrayList<Integer>> regions;

    private void readFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            line = br.readLine();
            String[] splitted = line.split(" ");
            row = Integer.parseInt(splitted[0]);
            col = Integer.parseInt(splitted[1]);
            line = br.readLine();
            numOfColors = Integer.parseInt(line);
            regions = new ArrayList<>();
            Map<Character, Integer> colorMap = new HashMap<>();
            while ((line = br.readLine()) != null) {
                ArrayList<Integer> intList = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    if (c != ' ') {
                        colorMap.computeIfAbsent(c, k -> colorMap.size());
                        intList.add(colorMap.get(c));
                    }
                }
                regions.add(intList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String selectFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            readFile(selectedFile);
        }
        return selectedFile.getName();
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getNumOfColors() {
        return this.numOfColors;
    }

    public ArrayList<ArrayList<Integer>> getRegions() {
        return this.regions;
    }
}
