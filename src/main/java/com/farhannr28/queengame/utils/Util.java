package com.farhannr28.queengame.utils;

import java.util.Random;

public class Util {

    private static Random random = new Random();

    public static int randomInt(int min, int max) {
        return Util.random.nextInt((max - min) + 1) + min;
    }
}
