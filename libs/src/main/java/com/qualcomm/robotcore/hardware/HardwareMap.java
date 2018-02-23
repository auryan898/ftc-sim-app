package com.qualcomm.robotcore.hardware;

public class HardwareMap {
    public HardwareMap(String filename){

    }

    public <T> T get(java.lang.Class<T> classOrInterface, java.lang.String deviceName){
        if(classOrInterface == DcMotor.class){
            return classOrInterface.cast(HardwareDevice.getDcMotor(deviceName));
        } else if (classOrInterface == Servo.class){
            return classOrInterface.cast(HardwareDevice.getServo(deviceName));
        } else {
            return null;
        }
    }
}
