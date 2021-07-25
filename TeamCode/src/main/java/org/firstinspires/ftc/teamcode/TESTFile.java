package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled; //
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; //
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; //
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Test File", group = "Test")

public class TESTFile extends LinearOpMode {
    //static final double pusherResetPosition = 1.0;
    static final double pusherPushPosition = 0.0;

    TESTHardwareMap robot = new TESTHardwareMap(); // the constructor will automatically init

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart(); // wait for the start button
        while (opModeIsActive()) {  // keep going until the stop button is pressed
            /*
            double horizontal = -gamepad1.left_stick_x;
            double vertical = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            robot.backLeftMotor.setPower(vertical + turn - horizontal);  // arcade drive algorithm with mecanum wheels
            robot.frontLeftMotor.setPower(vertical + turn + horizontal);
            robot.backRightMotor.setPower(vertical - turn + horizontal);
            robot.frontRightMotor.setPower(vertical - turn - horizontal);
             */

            robot.magazineServo.setPosition(TESTHardwareMap.pusherResetPosition);

            //robot.magazineServo.setPosition(TESTHardwareMap.pusherPushPosition);

            /*
            if (gamepad1.right_trigger > 0) {
                robot.shooterMotor.setPower(1);
            }
            else {
                robot.shooterMotor.setPower(0);
            }

            if (gamepad1.right_bumper) {
                robot.magazineServo.setPosition(TESTHardwareMap.pusherResetPosition);  // ensure that the servo is in the rest position

                robot.magazineServo.setPosition(TESTHardwareMap.pusherPushPosition);  // push the ring into the shooter
                sleep(10);
                robot.magazineServo.setDirection(Servo.Direction.REVERSE);
                robot.magazineServo.setPosition(TESTHardwareMap.pusherResetPosition);  // reset the servo to the normal position
                robot.magazineServo.setDirection(Servo.Direction.FORWARD);
            }
             */
        }
    }
}
