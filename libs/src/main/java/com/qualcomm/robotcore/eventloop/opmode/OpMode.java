package com.qualcomm.robotcore.eventloop.opmode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Telemetry;

public class OpMode {
    public HardwareMap hardwareMap = new HardwareMap();
    public Gamepad gamepad1;
    public Gamepad gamepad2;
    public Telemetry telemetry;
    public OpMode () {

    }
    public void init(){}
    public void loop(){}
    public void start(){}
}
