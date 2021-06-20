/*Notes:
 * 1320 is perfect distance for 1 tile if speed is +- 0.5
 */
// - is left for strafe

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



@Autonomous(name="Basic: Linear OpModeCheck", group="Linear Opmode2")

public class Test7641_AutonCheck extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "4";
    private static final String LABEL_SECOND_ELEMENT = "1";
    private static final String VUFORIA_KEY =
            " AYatEuv/////AAABmT+AWmCDTkv2vYyblEvtrsNLJAlf7wR2LkHEsYDRCtrwvXhJLDMukOX18IhahgnbE2S2Nlw1HC1qHid4Yjhco1+ynBT2FzfJnITxCwFSWlmZvRrXch2E++2mJtvRVcjCJrbjq4wcbcxzRykkPRCTjgGjfWa4W/JmbRstY8+nUZ5f7La0854LYFwtEHJjnjyUyD+caXuipBG06UInhY0HYoQvwQlg4SIG42AHJHQ6MQa7iuCu10+ycOf3VuBdh2QdjzZkcylXsPtx49pLN8+LKFlBHuo40g3dzaNmzPE9Iogd50C/SN5LezkEGd9EvVBJPbrrUZyXGuAtb0WPY1Cp635tk3SjfgzspU5/dZ4TXuOs ";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    static final double     COUNTS_PER_MOTOR_REV    = 560 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.25;
    static final double     TURN_SPEED              = 0.5;
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
    public void encoderStrafe(double speed,
                              double distance,
                              double timeoutS) {
        int newTarget;
        //int newRightTarget;
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newTarget = robot.frontLeft.getCurrentPosition() + (int)((distance-1) * COUNTS_PER_INCH);
            //newRightTarget = robot.frontRight.getCurrentPosition() + (int)((rightInches-1) * COUNTS_PER_INCH);

            // Turn On RUN_TO_POSITION

            // reset the timeout time and start motion.
            runtime.reset();
            if(distance<0){
                robot.backLeft.setTargetPosition(-Math.abs(newTarget));
                robot.frontLeft.setTargetPosition(Math.abs(newTarget));
                robot.backRight.setTargetPosition(Math.abs(newTarget));
                robot.frontRight.setTargetPosition(-Math.abs(newTarget));
                robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.frontLeft.setPower(Math.abs(speed));
                robot.backLeft.setPower(-Math.abs(speed*1.2));
                robot.frontRight.setPower(-Math.abs(speed));
                robot.backRight.setPower(Math.abs(speed*1.2));
            }else{
                robot.backLeft.setTargetPosition(Math.abs(newTarget));
                robot.frontLeft.setTargetPosition(-Math.abs(newTarget));
                robot.backRight.setTargetPosition(-Math.abs(newTarget));
                robot.frontRight.setTargetPosition(Math.abs(newTarget));
                robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.frontLeft.setPower(-Math.abs(speed));
                robot.backLeft.setPower(Math.abs(speed*1.2));
                robot.frontRight.setPower(Math.abs(speed));
                robot.backRight.setPower(-Math.abs(speed*1.2));
            }

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy())) {

                // Display it for the driver.
                //  telemetry.addData("Path1",  "Running to %7d :%7d", newTarget);
                //    telemetry.addData("Path2",  "Running at %7d :%7d",
                //                              robot.frontLeft.getCurrentPosition(),
                //                            robot.frontRight.getCurrentPosition());
                //telemetry.update();
            }

            // Stop all motion;
            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //  sleep(250);   // optional pause after each move
        }
    }
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            // Determine new target position, and pass to motor controller
            newLeftTarget = (int)((leftInches) * COUNTS_PER_INCH);
            newRightTarget = (int)((rightInches) * COUNTS_PER_INCH);
            robot.backLeft.setTargetPosition(newLeftTarget);
            robot.frontLeft.setTargetPosition(newLeftTarget);
            robot.backRight.setTargetPosition(newRightTarget);
            robot.frontRight.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            if(newLeftTarget>0){
                robot.frontLeft.setPower(Math.abs(speed));
                robot.backLeft.setPower(Math.abs(speed));
            }else{
                robot.frontLeft.setPower(-Math.abs(speed));
                robot.backLeft.setPower(-Math.abs(speed));
            }
            if(newRightTarget>0){
                robot.frontRight.setPower(Math.abs(speed));
                robot.backRight.setPower(Math.abs(speed));
            }else{
                robot.frontRight.setPower(-Math.abs(speed));
                robot.backRight.setPower(-Math.abs(speed));
            }

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeft.isBusy() && robot.frontRight.isBusy() && robot.backLeft.isBusy() && robot.backRight.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                //telemetry.addData("Path2",  "Running at %7d :%7d",
                //robot.frontLeft.getCurrentPosition(),
                //robot.frontRight.getCurrentPosition());
                //telemetry.update();
            }

            // Stop all motion;
            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //  sleep(250);   // optional pause after each move
        }
    }
    public void colorDetection(double power){
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.frontLeft.setPower(power);
        robot.frontRight.setPower(power);
        robot.backLeft.setPower(power);
        robot.backRight.setPower(power);
        double alpha =0;
        boolean sensed=false;
        boolean sensed2=false;
        while(opModeIsActive()){

            if(sensed==false && robot.Csensor.alpha()<18){
                robot.frontLeft.setPower(power);
                robot.backLeft.setPower(power);
                robot.frontRight.setPower(power);
                robot.backRight.setPower(power);
            }
            else{
                robot.frontLeft.setPower(0);
                robot.backLeft.setPower(0);
                robot.frontRight.setPower(0);
                robot.backRight.setPower(0);
                sensed=true;
            }
            /*if(sensed2==false && robot.Csensor2.alpha()<5500){
                robot.frontRight.setPower(power);
                robot.backRight.setPower(power);
            }
            else{
                robot.frontRight.setPower(0);
                robot.backRight.setPower(0);
                sensed2=true;
            }*/
        }
        robot.frontLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(0);
    }
    public String senseStack(){
        String stackval="0";
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());

                    stackval=recognition.getLabel();
                    telemetry.update();
                    return stackval; //change back to stackval
                }
            }
        }
        return stackval; //change back to stackval
    }
    public void attachmentMethod(int rings){
        robot.shooterMotor.setPower(1); //was 0.85
        sleep(2000); //was 2000 start shooter
        robot.intakeMotor.setPower(-1);
        robot.trayMotor.setPower(1);//shoot ring 1
        sleep(250);
        robot.trayMotor.setPower(-1);//run ring 2 backwards
        sleep(150);
        robot.intakeMotor.setPower(0);
        robot.trayMotor.setPower(0);
        sleep(2000);
        robot.shooterMotor.setPower(1);
        //was 0.95, turn on shooter
        sleep(1500);
        robot.intakeMotor.setPower(-1);
        robot.trayMotor.setPower(1);//shoot ring 2
        sleep(500); //was 335
        robot.intakeMotor.setPower(0);
        robot.trayMotor.setPower(0);
        sleep(1500);
        robot.shooterMotor.setPower(1);
        //was 0.95, turn on shooter
        sleep(2000);
        robot.intakeMotor.setPower(-1);
        robot.trayMotor.setPower(1);//shoot ring 3
        sleep(800);
        robot.intakeMotor.setPower(0);
        robot.trayMotor.setPower(0);
        robot.shooterMotor.setPower(0);
    }



    public void intakeLock(){
        robot.servoMotorOne.setPosition(0.5);
        robot.servoMotorTwo.setPosition(0.5);
        sleep(500);
    }
    public void moveWobbleArmDown()
    {
        robot.wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int encoderTicks=-80;
        robot.wobbleArm.setTargetPosition(encoderTicks);
        robot.wobbleArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.wobbleArm.setPower(-1);
        runtime.reset();
        while(opModeIsActive()&&(runtime.seconds()<2)&&robot.wobbleArm.isBusy()){
            telemetry.addData("Final Position", "Running at %7d", robot.wobbleArm.getCurrentPosition());
            telemetry.update();
        }
        robot.wobbleArm.setPower(0);
        robot.wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void moveWobbleArmUp()
    {
        robot.wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int encoderTicks=200;
        robot.wobbleArm.setTargetPosition(encoderTicks);
        robot.wobbleArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.wobbleArm.setPower(1);
        runtime.reset();
        while(opModeIsActive()&&(runtime.seconds()<2)&&robot.wobbleArm.isBusy()){
            telemetry.addData("Final Position", "Running at %7d", robot.wobbleArm.getCurrentPosition());
            telemetry.update();
        }
        robot.wobbleArm.setPower(0);
        robot.wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    Test7641_Hardware robot = new Test7641_Hardware();
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        initVuforia();
        initTfod();
        robot.wobbleClaw.setPosition(1);
        robot.servoMotorOne.setPosition(0.9);
        robot.servoMotorTwo.setPosition(0);
        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 1.78 or 16/9).

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            tfod.setZoom(2, 2);
        }
        String goal = senseStack();
        telemetry.addData("Goal", goal);
        telemetry.update();
        waitForStart();
        goal = senseStack();
        telemetry.addData("Goal", goal);
        telemetry.update();
        sleep(2000);
        moveWobbleArmDown();
        encoderDrive(0.25,1,1,5);
        intakeLock();

        telemetry.addData("Color", robot.Csensor.alpha());
        telemetry.update();
        colorDetection(0.3);
        telemetry.addData("Goal", goal);
        telemetry.update();

    }
}