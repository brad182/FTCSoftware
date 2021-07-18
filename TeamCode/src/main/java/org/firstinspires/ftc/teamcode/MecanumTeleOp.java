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

    // motor power values
    static final double shooterPower = 1.0;
    static final double intakePower = 1.0;
    static final double wobbleArmPower = 1.0;

    // servo values
    static final double openClawPositionLeft = 1.0;
    static final double openClawPositionRight = 1.0;
    static final double closeClawPositionLeft = 0.0;
    static final double closeClawPositionRight = 0.0;

    static final double ringPushingPosition = 1.0;
    static final double ringResetPosition = 0.0;

    // other values
    static final int delay = 50;  // milliseconds

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);  // initialize before the start button

        waitForStart(); // wait for the start button

        while (opModeIsActive()) {  // keep going until the stop button is pressed
            double horizontal = -gamepad1.left_stick_x;
            double vertical = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            robot.backLeftMotor.setPower(vertical + turn - horizontal);  // arcade drive algorithm for mecanum wheels
            robot.frontLeftMotor.setPower(vertical + turn + horizontal);
            robot.backRightMotor.setPower(vertical - turn + horizontal);
            robot.frontRightMotor.setPower(vertical - turn - horizontal);

            /*
            // shooter
            if (gamepad2.right_trigger > 0) {
                robot.shooterMotor.setPower(shooterPower);
            }
            else {
                robot.shooterMotor.setPower(0);
            }

            // ring pusher
            if (gamepad2.right_bumper) {
                robot.magazineServo.setPosition(ringPushingPosition);  // push the ring into the shooter
                sleep(delay);
                robot.magazineServo.setPosition(ringResetPosition);  // reset the servo to the normal position
            }

            // intake
            if (gamepad2.left_bumper) {
                robot.conveyorMotor.setPower(intakePower);
            }
            else {
                robot.conveyorMotor.setPower(0);
            }

            // extake
            if (gamepad2.left_trigger > 0) {
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
        }
    }
}
