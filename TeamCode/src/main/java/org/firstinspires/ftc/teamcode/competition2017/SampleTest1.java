package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Sample Test 1", group = "Competition2017-18")
@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class SampleTest1 extends OpMode {
    @Override
    public void init(){
        System.out.println("0I am the initial!");
    }
    @Override
    public void loop(){
        System.out.println("0I am loopy...");
    }
    @Override
    public void start(){
        System.out.println("0I am just starting out!");
    }
}
