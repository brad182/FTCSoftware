package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous (name = "Autonomous", group = "Autonomous")
public class RobotAutonomous extends LinearOpMode {
    RobotHardware robot = new RobotHardware();
    public final int distanceDriveToShootingPosition = 20;
    public final int shooterServoPosition = 0;  // the position the servo is at to hit a ring
    public final int resetShooterServoPosition = 1;  // the position the servo is at when it is reset
    public final double ticksPerRevolution = 560;  // the ticks per revolution for our motors

    public final int wheelDiameter = 3;

    // autonomous values

    // amount strafe to line up with box
    public final double zeroStrafe = 6.0;
    public final double oneStrafe = 6.0;
    public final double fourStrafe = 6.0;

    // amount forward to get to wobble drop spot
    public final double zeroForward = 60.0;  // 5 feet
    public final double oneForward = 60.0;
    public final double fourForward = 60.0;

    public void driveForward (double inches) {  // drives the robot forward given a distance
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int)(rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        robot.frontLeftMotor.setTargetPosition(encoderDrive);  // set the target position
        robot.backLeftMotor.setTargetPosition(encoderDrive);
        robot.frontRightMotor.setTargetPosition(encoderDrive);
        robot.backRightMotor.setTargetPosition(encoderDrive);

        robot.frontLeftMotor.setPower(1.0);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(1.0);
        robot.frontRightMotor.setPower(1.0);
        robot.backRightMotor.setPower(1.0);

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.frontLeftMotor.isBusy() || robot.backLeftMotor.isBusy() || robot.frontRightMotor.isBusy() || robot.backRightMotor.isBusy()) {  // wait

        }

        robot.frontLeftMotor.setPower(0);  // reset power to 0
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }

    public void driveBackwards (double inches) {  // given a distance, drive the robot backwards
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int)(rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        robot.frontLeftMotor.setTargetPosition(-encoderDrive);  // set the target position, use negative because we are driving backwards
        robot.backLeftMotor.setTargetPosition(-encoderDrive);
        robot.frontRightMotor.setTargetPosition(-encoderDrive);
        robot.backRightMotor.setTargetPosition(-encoderDrive);

        robot.frontLeftMotor.setPower(1.0);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(1.0);
        robot.frontRightMotor.setPower(1.0);
        robot.backRightMotor.setPower(1.0);

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.frontLeftMotor.isBusy() || robot.backLeftMotor.isBusy() || robot.frontRightMotor.isBusy() || robot.backRightMotor.isBusy()) {  // wait

        }

        robot.frontLeftMotor.setPower(0);  // reset power to 0
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }

    public void strafeRight (double inches) {  // given a distance, strafe the robot to the right

    }

    public void strafeLeft (double inches) {  // given a distance, strfe the robot to the left

    }

    public void shootThreeRings () throws InterruptedException {  // shoots the three rings in the magazine
        robot.shooterMotor.setPower(1);

        robot.magazineServo.setPosition(resetShooterServoPosition);  // first ring
        wait(20);
        robot.magazineServo.setPosition(shooterServoPosition);

        robot.magazineServo.setPosition(resetShooterServoPosition);  // first ring
        wait(20);
        robot.magazineServo.setPosition(shooterServoPosition);

        robot.magazineServo.setPosition(resetShooterServoPosition);  // first ring
        wait(20);
        robot.magazineServo.setPosition(shooterServoPosition);
    }

    public void dropWobble () {  // when at position, drop the wobble

    }

    public void autonomousZeroRings () {  // deliver wobble to zero box
        strafeRight(zeroStrafe);  // align with box
        driveForward(zeroForward);  // drive to wobble drop spot
        dropWobble();  // drop the wobble
    }

    public void autonomousOneRing () {  // deliver wobble to one box
        strafeRight(oneStrafe);  // align with box
        driveForward(oneForward);  // drive to wobble drop spot
        dropWobble();  // drop the wobble
    }

    public void autonomousFourRings () {  // deliver wobble to
        strafeRight(fourStrafe);  // align with box
        driveForward(fourForward);  // drive to wobble drop spot
        dropWobble();  // drop the wobble
    }

    public void runOpMode() throws InterruptedException {
        boolean zeroRings = false;  // ring detection
        boolean oneRing = false;
        boolean fourRings = true;
        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  // turn on encoder mode
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        driveForward(distanceDriveToShootingPosition);  // drive forward to get to the shooting location
        shootThreeRings();  // shoot three rings

        if (zeroRings) {  // zero rings
            autonomousZeroRings();
        }
        else if (oneRing) {  // one ring
            autonomousOneRing();
        }
        else {  // four rings
            autonomousFourRings();
        }
    }


}