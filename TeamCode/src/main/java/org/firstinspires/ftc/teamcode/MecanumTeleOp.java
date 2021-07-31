package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled; //
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; //
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; //
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RobotHardware;

@TeleOp(name="Mecanum TeleOp", group = "Mecanum")

public class MecanumTeleOp extends LinearOpMode {
    RobotHardware robot = new RobotHardware();

    // motor power values
    static final double shooterPower = 1.0;
    static final double intakePower = 1.0;
    static final double wobbleArmPower = 1.0;

    // claw values
    static final double openClawPositionLeft = 1.0;
    static final double openClawPositionRight = 1.0;
    static final double closeClawPositionLeft = 0.0;
    static final double closeClawPositionRight = 0.0;

    // shooter values
    static final int shooterSpeedUpTime = 2000;
    static final int servoDelay = 500;  // milliseconds

    // toggle buttons
    static final double[] toggleSpeeds = {1.0, 0.15};  // full speed and 15 percent speed
    static int speedPointer = 0;
    static final double[] toggleDirection = {1.0, -1.0};  // default directions and reversed directions
    static int directionPointer = 0;

    @Override

    public void runOpMode() {
        robot.init(hardwareMap);  // initialize before the start button

        waitForStart(); // wait for the start button

        while (opModeIsActive()) {  // keep going until the stop button is pressed
            //robot.shooterMotor.setPower(shooterPower);

            double horizontal = gamepad1.left_stick_x;
            double vertical = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            robot.backLeftMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical + turn - horizontal));  // arcade drive algorithm for mecanum wheels
            robot.frontLeftMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical + turn + horizontal));
            robot.backRightMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical - turn + horizontal));
            robot.frontRightMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical - turn - horizontal));

            // toggle ninja mode
            if (gamepad1.x) {
                speedPointer = (speedPointer + 1) % 2;  // this way, we just toggle between indexes 0 and 1 in the speed array
                telemetry.addData("speedPointer", speedPointer);
            }


            // toggle reversal
            if (gamepad1.b) {
                directionPointer = (directionPointer + 1) % 2;  // toggle the pointer between 0 and 1
                telemetry.addData("directionPointer", directionPointer);
            }

            // shooter

            if (gamepad1.right_trigger > 0.0) {
                telemetry.addData("shooter button pressed", gamepad1.right_trigger);
                robot.shooterMotor.setPower(shooterPower);
                telemetry.addData("shooter power", robot.shooterMotor.getPower());
            }
            else {
                robot.shooterMotor.setPower(0);
            }

            // ring pusher
            if (gamepad1.right_bumper) {
                robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // ensure that the servo is in the reset position

                robot.magazineServo.setPosition(RobotHardware.pusherPushPosition);  // push the ring into the shooter
                sleep(servoDelay);
                robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // reset the servo to the normal position
                sleep(servoDelay);;
            }

            // automatic shoot three rings
            if (gamepad1.dpad_up) {
                boolean go = true;  // use booleans

                while (go) {
                    robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // make sure it is reset
                    robot.shooterMotor.setPower(shooterPower);  // start spinning up the shooter

                    sleep(shooterSpeedUpTime);  // wait 2 seconds to speed up

                    robot.magazineServo.setPosition(RobotHardware.pusherPushPosition);  // first ring
                    sleep(servoDelay);
                    robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // reset the servo
                    sleep(servoDelay); // delay between rings

                    robot.magazineServo.setPosition(RobotHardware.pusherPushPosition);  // first ring
                    sleep(servoDelay);
                    robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // reset the servo
                    sleep(servoDelay); // delay between rings

                    robot.magazineServo.setPosition(RobotHardware.pusherPushPosition);  // first ring
                    sleep(servoDelay);
                    robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // reset the servo

                    robot.shooterMotor.setPower(0);  // stop the shooter
                    go = false;
                }
            }

            // intake
            /*
            if (gamepad1.left_bumper) {
                robot.conveyorMotor.setPower(intakePower);
            }
            else {
                robot.conveyorMotor.setPower(0);
            }

            // extake
            if (gamepad1.left_trigger > 0) {
                robot.conveyorMotor.setPower(-intakePower);
            }
            else {
                robot.conveyorMotor.setPower(0);
            }

            // wobble arm up
            if (gamepad2.y) {
                robot.wobbleMotor.setPower(wobbleArmPower);
            }
            else {
                robot.wobbleMotor.setPower(0);
            }

            // wobble arm down
            if (gamepad2.a) {
                robot.wobbleMotor.setPower(-wobbleArmPower);
            }
            else {
                robot.wobbleMotor.setPower(0);
            }

            // open claw
            if (gamepad2.x == true) {
                robot.clawServo1.setPosition(openClawPositionLeft);
                robot.clawServo2.setPosition(openClawPositionRight);
            }

            // close claw
            if (gamepad2.b == true) {
                robot.clawServo1.setPosition(closeClawPositionLeft);
                robot.clawServo2.setPosition(closeClawPositionRight);
            }

            sleep(delay);
             */

            telemetry.update();  // update the prints at the end of each cycle
        }
    }
}
