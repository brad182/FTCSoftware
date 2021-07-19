package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled; //
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; //
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; //
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Test File", group = "Test")

public class TESTFile extends LinearOpMode {
    TESTHardwareMap testRobot = new TESTHardwareMap(); // the constructor will automatically init

    @Override
    public void runOpMode() {
        testRobot.init(hardwareMap);
        waitForStart(); // wait for the start button
        while (opModeIsActive()) {  // keep going until the stop button is pressed
            /*
            double horizontal = -gamepad1.left_stick_x;
            double vertical = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            testRobot.backLeftMotor.setPower(vertical + turn - horizontal);  // arcade drive algorithm with mecanum wheels
            testRobot.frontLeftMotor.setPower(vertical + turn + horizontal);
            testRobot.backRightMotor.setPower(vertical - turn + horizontal);
            testRobot.frontRightMotor.setPower(vertical - turn - horizontal);
             */

        }
    }
}
