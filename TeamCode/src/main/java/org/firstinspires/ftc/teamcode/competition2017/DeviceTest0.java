package org.firstinspires.ftc.teamcode.competition2017;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LegacyModule;


@TeleOp(name = "Sample Test 1", group = "Competition2017-18")
@Autonomous(name = "Concept: NullOp", group = "Concept")
//@Disabled
public class DeviceTest0 extends OpMode {
    LegacyModule legacy;
    DcMotorController motcont1;
    DcMotor motor1;

    @Override
    public void init(){
        legacy = new LegacyModule("");
        motcont1 = new DcMotorController(legacy,0);
        motor1 = new DcMotor(motcont1,0);

        telemetry.addData("Status","Initializing");
    }
    @Override
    public void start(){
        motor1.setPower(1.0);
        telemetry.addData("Status","Starting");
    }
    @Override
    public void loop(){
        motor1.setPower(0);
        telemetry.addData("Status","Looping");
    }

}
