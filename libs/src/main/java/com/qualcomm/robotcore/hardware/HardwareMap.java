package com.qualcomm.robotcore.hardware;

public class HardwareMap {
    public <T> T get(java.lang.Class<T> classOrInterface, java.lang.String deviceName){
        if(classOrInterface == DcMotor.class){
            return (T)HardwareDevice.getDcMotor(deviceName);
        } else if (classOrInterface == Servo.class){
            return (T)HardwareDevice.getServo(deviceName);
        } else {
            return null;
        }
    }
}
