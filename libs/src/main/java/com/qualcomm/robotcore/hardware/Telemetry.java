package com.qualcomm.robotcore.hardware;

public class Telemetry {
    public <T, N> void addData(T name, N value){
        System.out.println(name+":"+value);
    }
}
