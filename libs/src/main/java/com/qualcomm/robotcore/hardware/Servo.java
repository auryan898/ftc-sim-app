package com.qualcomm.robotcore.hardware;

import cli.FTCCompCTL.*;
import cli.System.Object;

public class Servo {
    private double position;
    public Servo(){

    }
    public void setPosition(double pos){
        position = pos;
    }
}