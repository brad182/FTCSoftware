package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled; //
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; //
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; //
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RobotHardware;

@TeleOp(name="Mecanum TeleOp", group = "Mecanum")

public class MecanumTeleOp extends LinearOpMode {
    RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() {
        telemetry.addData("Say", "Hello");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            double horizontal = gamepad1.left_stick_x;
            double vertical = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            robot.backLeftMotor.setPower(vertical + turn - horizontal);
            robot.frontLeftMotor.setPower(vertical + turn + horizontal);
            robot.backRightMotor.setPower(vertical - turn + horizontal);
            robot.frontRightMotor.setPower(vertical - turn - horizontal);

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
