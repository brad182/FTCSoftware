/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="TeleMec", group="Linear Opmode")

public class Test7641_SoftwareMec extends LinearOpMode {

    /* Declare OpMode members. */
    Test7641_Hardware  robot           = new Test7641_Hardware();   // Use a Pushbot's hardware
    double          clawOffset      = 0;                       // Servo mid position
    final double    CLAW_SPEED      = 0.02 ;                   // sets rate to move servo

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        double max;
        double colorval;
        double colorval2;
        double colorval3;
        double colorval4;
        //double sensorvision;
        double r, r2;
        double robotAngle;
        double robotAngle2;
        double rightX;
        double leftX;
        double v1;
        double v2;
        double v3;
        double v4;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();
        robot.servoMotorOne.setPosition(0.5);
        robot.servoMotorTwo.setPosition(0.5);
        sleep(500);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        boolean flip=false;
        double powerdrive=1;
        boolean flipOne = false;
        while (opModeIsActive()) {

            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            //drive = -gamepad1.left_stick_y;
            //turn  =  gamepad1.right_stick_x;

            // Combine drive and turn for blended motion.
            colorval= robot.Csensor.alpha();
            colorval4= robot.Csensor2.alpha();
            //sensorvision=robot.distanceSensor.getDistance(DistanceUnit.INCH);

            // Normalize the values so neither exceed +/- 1.0
            r = Math.hypot((4*gamepad1.left_stick_x), gamepad1.left_stick_y);
            r2 = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);
            robotAngle = Math.atan2(gamepad1.left_stick_y, (4*gamepad1.left_stick_x)) - Math.PI / 4;
            robotAngle2 = Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x) - Math.PI / 4;
            rightX = gamepad1.right_stick_x;
            leftX = (4*gamepad1.left_stick_x);
            v3 = r * Math.sin(robotAngle) + leftX;
            v4 = r * Math.cos(robotAngle) - leftX;
            v1 = r2 * Math.cos(robotAngle2) + rightX;
            v2 = r2 * Math.sin(robotAngle2) - rightX;
            
            /*left  = -gamepad1.left_stick_y/4*3;
            right = -gamepad1.right_stick_y/4*3;
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0)
            {
                left /= max;
                right /= max;
            }*/


            // Output the safe vales to the motor drives.
            if(gamepad1.x==true){
                flip=!flip;
                sleep(200);
            }
            telemetry.addData("Powerdrive", "%.2f", powerdrive);
            if(gamepad1.b==true){
                flipOne=!flipOne;
                powerdrive = 0.25;
                sleep(200);
                telemetry.update();
            }
            if(flipOne==false){
                powerdrive=1;
            }else{
                powerdrive=0.25;

            }

            if(flip==true){
                robot.frontLeft.setPower(-v1*powerdrive);
                robot.backLeft.setPower(-v2*powerdrive);
                robot.frontRight.setPower(-v4*powerdrive);
                robot.backRight.setPower(-v3*powerdrive);
                //telemetry.addData("Front: ", "%.2f", "Shooter");
            }else{
                robot.frontLeft.setPower(v3*powerdrive);
                robot.backLeft.setPower(v4*powerdrive);
                robot.frontRight.setPower(v2*powerdrive);
                robot.backRight.setPower(v1*powerdrive);
                //telemetry.addData("Front: ", "%.2f", "Intake");
            }
            if(gamepad1.left_trigger==1){
                robot.intakeMotor.setPower(1);
            }else if(gamepad1.left_bumper==true){
                robot.intakeMotor.setPower(-1);
            }else{
                robot.intakeMotor.setPower(0);
            }
            if(gamepad2.right_bumper==true){
                robot.trayMotor.setPower(1);
            }else if(gamepad2.right_trigger==1){
                robot.trayMotor.setPower(-1);
            }else{
                robot.trayMotor.setPower(0);
            }
            if(gamepad2.left_bumper==true)
            {
                robot.shooterMotor.setPower(1);
            }else if(gamepad2.left_bumper==false){
                robot.shooterMotor.setPower(-gamepad2.left_stick_y);
            }
            if(gamepad2.x==true){
                robot.wobbleClaw.setPosition(0);
                sleep(100);
            }
            if(gamepad2.b==true){
                robot.wobbleClaw.setPosition(1);
                sleep(100);
            }
            if(gamepad2.y==true){
                robot.wobbleArm.setPower(0.7);
            }else if(gamepad2.a==true){
                robot.wobbleArm.setPower(-0.7);
            }else{
                robot.wobbleArm.setPower(0);
            }

            if(gamepad1.a==true){
                robot.servoMotorOne.setPosition(0.5);
                robot.servoMotorTwo.setPosition(0.5);
                sleep(500);
            }
           /* if(gamepad1.dpad_up == true)
            {
                robot.frontLeft.setPower(0.3);
                robot.backLeft.setPower(0.3);
                robot.frontRight.setPower(0.3);
                robot.backRight.setPower(0.3);
            }
            
            else{
                robot.frontLeft.setPower(0);
                robot.backLeft.setPower(0);
                robot.frontRight.setPower(0);
                robot.backRight.setPower(0);
            }
            if(gamepad1.dpad_down == true)
            {
                robot.frontLeft.setPower(-0.3);
                robot.backLeft.setPower(-0.3);
                robot.frontRight.setPower(-0.3);
                robot.backRight.setPower(-0.3);
            }
            
            else{
                robot.frontLeft.setPower(0);
                robot.backLeft.setPower(0);
                robot.frontRight.setPower(0);
                robot.backRight.setPower(0);
            }
            if(gamepad1.dpad_right == true)
            {
                robot.frontLeft.setPower(0.3);
                robot.backLeft.setPower(-0.3);
                robot.frontRight.setPower(-0.3);
                robot.backRight.setPower(0.3);
            }
            
            else if{
                robot.frontLeft.setPower(0);
                robot.backLeft.setPower(0);
                robot.frontRight.setPower(0);
                robot.backRight.setPower(0);
            }
            if(gamepad1.dpad_left == true)
            {
                robot.frontLeft.setPower(-0.3);
                robot.backLeft.setPower(0.3);
                robot.frontRight.setPower(0.3);
                robot.backRight.setPower(-0.3);
            }
            
            else{
                robot.frontLeft.setPower(0);
                robot.backLeft.setPower(0);
                robot.frontRight.setPower(0);
                robot.backRight.setPower(0);
            }
            */
            // Use gamepad left & right Bumpers to open and close the claw
            //    if (gamepad1.right_bumper)
            //        clawOffset += CLAW_SPEED;
            //    else if (gamepad1.left_bumper)
            //        clawOffset -= CLAW_SPEED;

            // Move both servos to new position.  Assume servos are mirror image of each other.
            //        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            //robot.leftClaw.setPosition(robot.MID_SERVO + clawOffset);
            //robot.rightClaw.setPosition(robot.MID_SERVO - clawOffset);

            // Use gamepad buttons to move arm up (Y) and down (A)
            /*if (gamepad1.y)
                robot.leftArm.setPower(robot.ARM_UP_POWER);
            else if (gamepad1.a)
                robot.leftArm.setPower(robot.ARM_DOWN_POWER);
            else
                robot.leftArm.setPower(0.0);
*/
            // Send telemetry message to signify robot running;
            //telemetry.addData("claw",  "Offset = %.2f", clawOffset);
            telemetry.addData("Color", "%.2f", colorval);
            telemetry.addData("Color", "%.2f", colorval4);
            //telemetry.addData("in", "%.2f in", Csensor.alpha() );
            //telemetry.addData("in", "%.2f in", Csensor2.alpha() );
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}