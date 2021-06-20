/*Notes:
 * 1320 is perfect distance for 1 tile if speed is +- 0.5
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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



@Autonomous(name="Basic: Linear OpMode2", group="Linear Opmode2")

public class Test7641_Auton_Encoder extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "4";
    private static final String LABEL_SECOND_ELEMENT = "1";
    private static final String VUFORIA_KEY =
            " AYatEuv/////AAABmT+AWmCDTkv2vYyblEvtrsNLJAlf7wR2LkHEsYDRCtrwvXhJLDMukOX18IhahgnbE2S2Nlw1HC1qHid4Yjhco1+ynBT2FzfJnITxCwFSWlmZvRrXch2E++2mJtvRVcjCJrbjq4wcbcxzRykkPRCTjgGjfWa4W/JmbRstY8+nUZ5f7La0854LYFwtEHJjnjyUyD+caXuipBG06UInhY0HYoQvwQlg4SIG42AHJHQ6MQa7iuCu10+ycOf3VuBdh2QdjzZkcylXsPtx49pLN8+LKFlBHuo40g3dzaNmzPE9Iogd50C/SN5LezkEGd9EvVBJPbrrUZyXGuAtb0WPY1Cp635tk3SjfgzspU5/dZ4TXuOs ";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
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
    public void turningTestMethod(int dist, int speed, boolean direction) {
        if (direction ==true){
            int distuse= (int)Math.floor(dist*420);
            robot.frontLeft.setTargetPosition(distuse);
            robot.backLeft.setTargetPosition(-distuse);
            robot.frontRight.setTargetPosition(-distuse);
            robot.backRight.setTargetPosition(distuse);
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontLeft.setPower(speed);
            robot.backLeft.setPower((speed));
            robot.frontRight.setPower((speed));
            robot.backRight.setPower((speed));
            sleep(4000);
            robot.frontLeft.setPower(0);
            robot.backLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backRight.setPower(0);
        }
        else if(direction == false){
            int distuse= (int)Math.floor(dist*420);
            robot.frontLeft.setTargetPosition(-distuse);
            robot.backLeft.setTargetPosition(distuse);
            robot.frontRight.setTargetPosition(distuse);
            robot.backRight.setTargetPosition(-distuse);
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontLeft.setPower(speed);
            robot.backLeft.setPower((speed));
            robot.frontRight.setPower((speed));
            robot.backRight.setPower((speed));
            sleep(4000);
            robot.frontLeft.setPower(0);
            robot.backLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backRight.setPower(0);
        }

    }
    public void forwardAndBackward(double dist, double speed){
        int distuse=(int)Math.floor(dist*400);
        //robot.frontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        //robot.backLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        //robot.frontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        //robot.backRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        robot.frontLeft.setTargetPosition(distuse);
        robot.backLeft.setTargetPosition(distuse);
        robot.frontRight.setTargetPosition(distuse);
        robot.backRight.setTargetPosition(distuse);
        //robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeft.setPower(speed);
        robot.backLeft.setPower((speed));
        robot.frontRight.setPower((speed));
        robot.backRight.setPower((speed));
        //while(robot.backRight.isBusy()||robot.frontRight.isBusy()||robot.backLeft.isBusy()||robot.frontLeft.isBusy()){
        //}
        sleep((long)(distuse/speed));
        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backRight.setPower(0);

    }
    public void colorDetection(double power){
        robot.frontLeft.setPower(power);
        robot.frontRight.setPower(power);
        robot.backLeft.setPower(power);
        robot.backRight.setPower(power);
        double alpha =0;
        boolean sensed=false;
        boolean sensed2=false;
        while(opModeIsActive()){

            if(sensed==false && robot.Csensor.alpha()<139){
                robot.frontLeft.setPower(power);
                robot.backLeft.setPower(power);
            }
            else{
                robot.frontLeft.setPower(0);
                robot.backLeft.setPower(0);
                sensed=true;
            }
            if(sensed2==false && robot.Csensor2.alpha()<6){
                robot.frontRight.setPower(power);
                robot.backRight.setPower(power);
            }
            else{
                robot.frontRight.setPower(0);
                robot.backRight.setPower(0);
                sensed2=true;
            }
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
                    return stackval;
                }
            }
        }
        return stackval;
    }
    public void attachmentMethod(int rings){
        robot.shooterMotor.setPower(0.7);
        sleep(1000);
        //robot.intakeMotor.setPower(0.5);
        for(int i=0;i<rings;++i){
            robot.intakeMotor.setPower(0.5);
            robot.trayMotor.setPower(-0.5);
            //robot.shooterMotor.setPower(0.7);
            sleep(3000);
            robot.intakeMotor.setPower(0);
            robot.trayMotor.setPower(0);
        }
        robot.intakeMotor.setPower(0);
        robot.trayMotor.setPower(0);
        robot.shooterMotor.setPower(0);
    }


    public void intakeLock(){
        robot.servoMotorOne.setPosition(0);
        //robot.servoMotorTwo.setPosition(1);
        sleep(500);
    }
    public void moveWobbleArm(double speed)
    {
        robot.wobbleArm.setPower(-speed);
        sleep(1000);
    }
    Test7641_Hardware robot = new Test7641_Hardware();
    public void runOpMode() {
        robot.init(hardwareMap);
        initVuforia();
        initTfod();
        robot.wobbleClaw.setPosition(0.25);
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
            tfod.setZoom(2.5, 1.5);
        }
        //int goal = senseStack();
        //telemetry.addData("Goal", goal);
        waitForStart();
        //     forwardAndBackward(1,0.5);
        //senseStack();
        String goal = senseStack();
        telemetry.addData("Goal", goal);
        telemetry.update();
        //sleep(10000);
        if(goal == "4")
        {
            forwardAndBackward(1, 0.5);
        }
        else if(goal == "1")
        {
            forwardAndBackward(1, 0.5);
            turningTestMethod(1,1,true);
            forwardAndBackward(1, 0.5);
        }
        else if(goal == "0")
        {
            forwardAndBackward(2, 0.5);
        }
        //moveWobbleArm(0.5);
        //intakeLock();
        //attatchmentMethod(3); // 3 rings
        //forwardAndBackward(2,0.10); //forward 2 tile (wobble push)
        //transition period of 0.5 seconds
        //colorDetection(-0.3); //backward 1 tile (Launch Line)
        //turningTestMethod(1,1,false);

    }


}