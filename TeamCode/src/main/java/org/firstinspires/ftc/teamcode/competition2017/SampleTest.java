package org.firstinspires.ftc.teamcode.competition2017;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.*;



//@TeleOp(name = "SampleTest", group = "Competition2017-18")
@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class SampleTest extends OpMode {
    @Override
    public void init(){
        System.out.println("I am the initial!");
    }
    @Override
    public void loop(){
        System.out.println("I am loopy...");
    }
    @Override
    public void start(){
        System.out.println("I am just starting out!");
    }
}
