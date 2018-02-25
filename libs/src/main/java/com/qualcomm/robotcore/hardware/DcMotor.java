package com.qualcomm.robotcore.hardware;

import cli.System.*;

import java.lang.String;
// import cli.FtcCompCtl.*;

public class DcMotor {
    int motPort;
    int nxtPort;
    String serialNum;
    DcMotorController motorController;
    double power;

    public DcMotor(DcMotorController motorController, int port){
        power = 0;
        motPort = port;
        nxtPort = motorController.getPort();

        this.motorController = motorController;
    }
    public void setPower(double power){
        this.power = power;
    }
    public double getPower(){

        return this.power;
    }
}