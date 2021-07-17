package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled; //
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; //
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; //
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Mecanum TeleOp", group = "Mecanum")

public class TESTFile extends LinearOpMode {
    TESTHardwareMap testRobot = new TESTHardwareMap(); // the constructor will automatically init

    @Override
    public void runOpMode() {
        testRobot.init(hardwareMap);
        waitForStart(); // wait for the start button
        while (opModeIsActive()) {  // keep going until the stop button is pressed
            if (gamepad1.a) {  // button A pressed
                testRobot.testMotor.setPower(1.0);
            }
        }
    }
}
