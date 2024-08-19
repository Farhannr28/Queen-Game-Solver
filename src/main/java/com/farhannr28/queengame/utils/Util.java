package com.farhannr28.queengame.utils;

import java.util.Random;

public class Util {
    private static boolean modifyVisible;
    private static int selectedColor;
    private static Random random = new Random();

    public static int randomInt(int min, int max) {
        return Util.random.nextInt((max - min) + 1) + min;
    }
    
    public static boolean getModifyVisible(){
        return modifyVisible;
    }

    public static int getSelectedColor(){
        return selectedColor;
    }

    public static void setModifyVisible(boolean b){
        modifyVisible = b;
    }

    public static void setSelectedColor(int i){
        selectedColor = i;
    }
}
