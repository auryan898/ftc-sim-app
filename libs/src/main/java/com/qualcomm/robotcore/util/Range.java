package com.qualcomm.robotcore.util;

public class Range {
    public static double clip(double value, double min, double max){
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
}
