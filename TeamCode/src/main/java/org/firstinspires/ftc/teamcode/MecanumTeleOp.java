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
    RobotHardware robot = new RobotHardware(); // the constructor will automatically init

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
        robot.init(hardwareMap);
        telemetry.addData("Program", "Started");
        telemetry.update();

        waitForStart(); // wait for the start button
        while (opModeIsActive()) {  // keep going until the stop button is pressed
            double horizontal = gamepad1.left_stick_x;
            double vertical = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            robot.backLeftMotor.setPower(vertical + turn - horizontal);  // mecanum drive algorithm
            robot.frontLeftMotor.setPower(vertical + turn + horizontal);
            robot.backRightMotor.setPower(vertical - turn + horizontal);
            robot.frontRightMotor.setPower(vertical - turn - horizontal);

            if (gamepad1.left_bumper == true) {  // shooter
                robot.shooterMotor.setPower(shooterPower);
            }
            else if (gamepad1.right_bumper == true) {  // intake
                robot.conveyorMotor.setPower(intakePower);
            }
            else if (gamepad1.right_trigger > 0) { // extake
                robot.conveyorMotor.setPower(-intakePower);
            }
            else if (gamepad1.x == true) {  // wobble arm up
                robot.wobbleMotor.setPower(wobbleArmPower);
            }
            else if (gamepad1.y == true) {  // wobble arm down
                robot.wobbleMotor.setPower(-wobbleArmPower);
            }
            else if (gamepad1.dpad_up) {  // ring pusher
                robot.magazineServo.setPosition(ringPushingPosition);  // push the ring into the shooter
                sleep(delay);
                robot.magazineServo.setPosition(ringResetPosition);  // reset the servo to the normal position
            }
            else if (gamepad1.a == true) {  // open claw
                robot.clawServo1.setPosition(openClawPositionLeft);
                robot.clawServo2.setPosition(openClawPositionRight);
            }
            else if (gamepad1.b == true) {  // close claw
                robot.clawServo1.setPosition(closeClawPositionLeft);
                robot.clawServo2.setPosition(closeClawPositionRight);
            }
            sleep(delay);
        }
    }
}
