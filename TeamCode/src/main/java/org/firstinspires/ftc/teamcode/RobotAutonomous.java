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
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

// test 
@Autonomous (name = "Robot Autonomous", group = "Autonomous")
public class RobotAutonomous extends LinearOpMode {
    static RobotHardware robot = new RobotHardware();

    // camera
    public TFObjectDetector tfod;
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    public VuforiaLocalizer vuforia;
    private static final String LABEL_FIRST_ELEMENT = "4";
    private static final String LABEL_SECOND_ELEMENT = "1";
    public final String vuforiaKey = "AQQmsVn/////AAABmQjk2+3dZE+Tk5oj3L8j0DJvG4NWcCztbIl7BYnLuRUbBBF7ocAhc5kq25SO33annXS4Vn8kAruErc1ETaO+pralkAh4QcvBa9mL4/g+e01KmfAIBGHsJzRIHoravhIvOhdHODQzQu77u3h/hYmD9MSFE+e5d+yQOmWTl5dKZWUwLMiYY4KEXtOMTkP99vK3Jk8lINPpyDyFp6cDrxSpwz7rs9A8HCD8aXiuK8RDRyc3bTEe7aphVTrEzWADQHMwozaegUBlgtnAtlMHa4Ea8Hl21jWRu00haLb9lVNTsIyak5h8ZeJFcGj17AxYQ+iYt6YihHPw2MOrQzFhSKL+NwjWlDYHjlcehVjQ9Xq2d4xo";

    // driving values
    public final int distanceDriveToShootingPosition = 20;

    // servo values
    public final int shooterServoPosition = 0;  // the position the servo is at to hit a ring
    public final int resetShooterServoPosition = 1;  // the position the servo is at when it is reset
    public final int pause = 50;  // milliseconds

    // motor values
    public final double ticksPerRevolution = 537.6;  // the ticks per revolution for our motors, the REV HEX Planetary 20:1
    public final double wheelDiameter = 3.937;  // mecanum wheels are 100 millimeters in diameter
    public final double wobbleRotations = 0.5;
    public final double power = 1.0;
    public final double shooterPower = 1.0;

    // amount strafe to line up with box
    public final double zeroStrafe = 6.0;
    public final double oneStrafe = 6.0;
    public final double fourStrafe = 6.0;

    // amount forward to get to wobble drop spot
    public final double zeroForward = 60.0;  // 5 feet
    public final double oneForward = 60.0;
    public final double fourForward = 60.0;

    // shooter values
    public final int shooterSpeedUpTime = 2000;
    public final int servoDelay = 500;

    public void driveForward (double inches) {  // drives the robot forward given a distance
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // TRY NEW ENCODER CABLE
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int) (rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        robot.frontLeftMotor.setTargetPosition(encoderDrive);  // set the target position
        robot.backLeftMotor.setTargetPosition(encoderDrive);
        robot.frontRightMotor.setTargetPosition(encoderDrive);
        robot.backRightMotor.setTargetPosition(encoderDrive);

        robot.frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backRightMotor.setPower(power);

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (robot.frontLeftMotor.isBusy() || robot.backLeftMotor.isBusy() || robot.frontRightMotor.isBusy() || robot.backRightMotor.isBusy()) {  // wait
            // pause in a while loop while the motors are still running

            /*
            telemetry.addData("frontLeftMotor", robot.frontLeftMotor.getCurrentPosition());
            telemetry.addData("frontRightMotor", robot.frontRightMotor.getCurrentPosition());
            telemetry.addData("backLeftMotor", robot.backLeftMotor.getCurrentPosition());
            telemetry.addData("backRightMotor", robot.backRightMotor.getCurrentPosition());
             */

            telemetry.update();

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

        robot.frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backRightMotor.setPower(power);

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

    /* in mecanum wheels, if the front wheels and the back wheels are
     going in opposite directions and outwards, the robot goes
     to the right
     */
    public void strafeRight (double inches) {  // given a distance, strafe the robot to the right
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int) (rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        // the front and the back are going in opposite directions and outwards, will go right
        robot.frontLeftMotor.setTargetPosition(-encoderDrive);  // set the target position
        robot.backLeftMotor.setTargetPosition(encoderDrive);
        robot.frontRightMotor.setTargetPosition(-encoderDrive);
        robot.backRightMotor.setTargetPosition(encoderDrive);

        robot.frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backRightMotor.setPower(power);

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

    /* in mecanum wheels, if the front wheels and the back wheels are
     going in opposite directions and inwards, the robot goes
     to the left
     */
    public void strafeLeft (double inches) {  // given a distance, strfe the robot to the left
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int)(rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        // the front and the back motors are going in opposite directions and inwards, will go left
        robot.frontLeftMotor.setTargetPosition(encoderDrive);  // set the target position
        robot.backLeftMotor.setTargetPosition(-encoderDrive);
        robot.frontRightMotor.setTargetPosition(encoderDrive);
        robot.backRightMotor.setTargetPosition(-encoderDrive);

        robot.frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backRightMotor.setPower(power);

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.frontLeftMotor.isBusy() || robot.backLeftMotor.isBusy() || robot.frontRightMotor.isBusy() || robot.backRightMotor.isBusy()) {  // wait
            // pause while motors are running
        }

        robot.frontLeftMotor.setPower(0);  // reset power to 0
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }


    private void initializeVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = vuforiaKey;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }



    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public String getStackSize(){
        String stackSize = "0";
        if (tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

            if (updatedRecognitions != null) {
                //telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {

                    /*
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f", recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f", recognition.getRight(), recognition.getBottom());
                    */

                    stackSize = recognition.getLabel();
                    telemetry.update();
                    return stackSize;
                }
            }

        }
        return stackSize;
    }

    public void shootThreeRings () throws InterruptedException {  // shoots the three rings in the magazine
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

    public void releaseWobble () {

    }

    public void dropWobble () {  // when at position, drop the wobble
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int encoderDrive = (int) (wobbleRotations * ticksPerRevolution);  // calculate the total ticks, cast to int

        // the front and the back are going in opposite directions and outwards, will go right
        robot.wobbleMotor.setTargetPosition(encoderDrive);  // set the target position

        robot.frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backRightMotor.setPower(power);

        robot.wobbleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position

        releaseWobble();

        robot.wobbleMotor.setTargetPosition(0);  // reset

        robot.wobbleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.wobbleMotor.isBusy()) {  // wait

        }

        robot.wobbleMotor.setPower(0);  // reset power to 0
    }

    public void autonomousZeroRings () {  // deliver wobble to zero box
        strafeRight(zeroStrafe);  // align with box
        driveForward(zeroForward);  // drive to wobble drop spot
        dropWobble();  // drop the wobble
        // get second wobble (if doing two wobbles)
        driveBackwards(10);  // drive to line
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

    /*
    static void initialize () {
        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  // turn on encoder mode
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.wobbleMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.shooterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);  // does not use encoders
    }
     */

    public void runOpMode() throws InterruptedException {
        /*
        boolean zeroRings = false;  // ring detection, use boolean values as placeholders for now
        boolean oneRing = false;
        boolean fourRings = true;
         */

        robot.init(hardwareMap);
        //initialize();


        initializeVuforia();
        initTfod();
        tfod.activate();
        tfod.setZoom(2, 2);
        String stackSize = getStackSize();
        telemetry.addData("stackSize", stackSize);

        waitForStart();
        driveForward(150.0);
        driveBackwards(150.0);

        //driveForward(distanceDriveToShootingPosition);  // drive forward to get to the shooting location
        //shootThreeRings();  // shoot three rings, same for all three autons

        /*
        if (stackSize.equals("0")) {  // zero rings
            autonomousZeroRings();
        }
        else if (stackSize.equals("1")) {  // one ring
            autonomousOneRing();
        }
        else if (stackSize.equals("4")){  // four rings
            autonomousFourRings();
        }
        */
    }


}
