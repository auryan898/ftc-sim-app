package com.qualcomm.robotcore.hardware;

public class HardwareDevice {
    public static DcMotor getDcMotor(String deviceName){
        return new DcMotor(new DcMotorController(new LegacyModule(""),0), 0);
    }
    public static Servo getServo(String deviceName){

        return new Servo();
    }
}
