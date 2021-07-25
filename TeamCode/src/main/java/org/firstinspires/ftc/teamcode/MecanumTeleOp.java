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
    static final int delayBetweenRings = 1000;
    static final int delayBetweenReversing = 10;

    // toggle buttons
    static final double[] toggleSpeeds = {1.0, 0.15};  // full speed and 15 percent speed
    static int speedPointer = 0;
    static final double[] toggleDirection = {1.0, -1.0};  // default directions and reversed directions
    static int directionPointer = 0;

    // other values
    static final int delay = 20;  // milliseconds

    static final double pusherPushPosition = 0.0;
    static final double pusherResetPosition = 1.0;

    @Override

    public void runOpMode() {
        robot.init(hardwareMap);  // initialize before the start button

        waitForStart(); // wait for the start button

        while (opModeIsActive()) {  // keep going until the stop button is pressed
            double horizontal = gamepad1.left_stick_x;
            double vertical = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            /*
            telemetry.addData("horizontal", horizontal);  // print values
            telemetry.addData("vertical", vertical);
            telemetry.addData("turn", turn);
             */

            robot.backLeftMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical + turn - horizontal));  // arcade drive algorithm for mecanum wheels
            robot.frontLeftMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical + turn + horizontal));
            robot.backRightMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical - turn + horizontal));
            robot.frontRightMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical - turn - horizontal));

            // toggle ninja mode
            if (gamepad1.x) {
                telemetry.addData("speedPointer", speedPointer);
                speedPointer = (speedPointer + 1) % 2;  // this way, we just toggle between indexes 0 and 1 in the speed array
            }

            // toggle reversal
            if (gamepad1.b) {
                telemetry.addData("directionPointer", directionPointer);
                directionPointer = (directionPointer + 1) % 2;  // toggle the pointer between 0 and 1
            }

            // shooter

            if (gamepad1.right_trigger > 0) {
                telemetry.addData("shooter button pressed", gamepad1.right_trigger);
                robot.shooterMotor.setPower(shooterPower);
            }
            else {
                robot.shooterMotor.setPower(0);
            }

            // ring pusher
            if (gamepad1.right_bumper) {
                robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // ensure that the servo is in the reset position

                robot.magazineServo.setPosition(RobotHardware.pusherPushPosition);  // push the ring into the shooter
                sleep(500);
                //robot.magazineServo.setDirection(Servo.Direction.REVERSE);
                robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // reset the servo to the normal position
                sleep(500);
                //robot.magazineServo.setDirection(Servo.Direction.FORWARD);
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



            // automatic shoot three rings

            if (gamepad2.dpad_up) {
                robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // make sure it is reset
                robot.shooterMotor.setPower(shooterPower);  // start spinning up the shooter

                sleep(shooterSpeedUpTime);  // wait 2 seconds to speed up

                // POSSIBLY NEED TO ADD REVERSE DIRECTIONS
                robot.magazineServo.setPosition(RobotHardware.pusherPushPosition);  // first ring
                sleep(delayBetweenReversing);
                robot.magazineServo.setDirection(Servo.Direction.REVERSE);  // reverse to go the other way
                robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);  // reset the servo
                robot.magazineServo.setDirection(Servo.Direction.FORWARD);  // reset to the direction that you want it to pushed in for the next ring
                sleep(delayBetweenRings); // delay between rings

                robot.magazineServo.setPosition(RobotHardware.pusherPushPosition);  // second ring
                sleep(delayBetweenReversing);
                robot.magazineServo.setDirection(Servo.Direction.REVERSE);
                robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);
                robot.magazineServo.setDirection(Servo.Direction.FORWARD);
                sleep(delayBetweenRings);

                robot.magazineServo.setPosition(RobotHardware.pusherPushPosition);  // third ring
                sleep(delayBetweenReversing);
                robot.magazineServo.setDirection(Servo.Direction.REVERSE);
                robot.magazineServo.setPosition(RobotHardware.pusherResetPosition);
                robot.magazineServo.setDirection(Servo.Direction.FORWARD);
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
