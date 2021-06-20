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
@TeleOp(name="TeleMecArcade", group="Linear OpmodeArcade")
public class Test7641_Software_Arcade extends LinearOpMode {
    /* Declare OpMode members. */
    Test7641_Hardware robot = new Test7641_Hardware();   // Use a Pushbot's hardware
    double clawOffset = 0;                       // Servo mid position
    final double CLAW_SPEED = 0.02;                   // sets rate to move servo

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
        boolean flip = false;
        boolean arcade_tank_switch = true;
        double powerdrive = 1;
        boolean flipOne = false;
        while (opModeIsActive()) {
            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            drive = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            // Combine drive and turn for blended motion.
            colorval = robot.Csensor.alpha();
            colorval4 = robot.Csensor2.alpha();
            //sensorvision=robot.distanceSensor.getDistance(DistanceUnit.INCH);
            // Normalize the values so neither exceed +/- 1.0
            r = Math.hypot((4 * gamepad1.left_stick_x), gamepad1.left_stick_y);
            r2 = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);
            robotAngle = Math.atan2(gamepad1.left_stick_y, (4 * gamepad1.left_stick_x)) - Math.PI / 4;
            robotAngle2 = Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x) - Math.PI / 4;
            rightX = gamepad1.right_stick_x;
            leftX = (4 * gamepad1.left_stick_x);
            v3 = r * Math.sin(robotAngle) + leftX;
            v4 = r * Math.cos(robotAngle) - leftX;
            v1 = r2 * Math.cos(robotAngle2) + rightX;
            v2 = r2 * Math.sin(robotAngle2) - rightX;
            left = -gamepad1.left_stick_y / 4 * 3;
            right = -gamepad1.right_stick_y / 4 * 3;
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0) {
                left /= max;
                right /= max;
            }
            // Output the safe vales to the motor drives.
            if (gamepad1.x == true) {
                flip = !flip;
                sleep(200);
            }
            telemetry.addData("Powerdrive", "%.2f", powerdrive);
            if (gamepad1.b == true) {
                flipOne = !flipOne;
                powerdrive = 0.25;
                sleep(200);
                //telemetry.update();
            }
            if (flipOne == false) {
                powerdrive = 1;
                //telemetry.update();
            } else {
                powerdrive = 0.25;
                //telemetry.update();
            }
            if (gamepad1.y == true) {
                arcade_tank_switch = !arcade_tank_switch;
                sleep(200);
                //telemetry.update();
            }
            if (arcade_tank_switch == false) {
                if (flip == true) {
                    robot.frontLeft.setPower(-v1 * powerdrive);
                    robot.backLeft.setPower(-v2 * powerdrive);
                    robot.frontRight.setPower(-v4 * powerdrive);
                    robot.backRight.setPower(-v3 * powerdrive);
                    //telemetry.addData("Front: ", "%.2f", "Shooter");
                } else {
                    robot.frontLeft.setPower(v3 * powerdrive);
                    robot.backLeft.setPower(v4 * powerdrive);
                    robot.frontRight.setPower(v2 * powerdrive);
                    robot.backRight.setPower(v1 * powerdrive);
                    //telemetry.addData("Front: ", "%.2f", "Intake");
                }
            } else {
                if (flip == true) {
                    robot.frontLeft.setPower(-v4 * powerdrive - gamepad1.right_stick_y * powerdrive);
                    robot.backLeft.setPower(-v3 * powerdrive - gamepad1.right_stick_y * powerdrive);
                    robot.frontRight.setPower(-v3 * powerdrive + gamepad1.right_stick_y * powerdrive);
                    robot.backRight.setPower(-v4 * powerdrive + gamepad1.right_stick_y * powerdrive);
                    //telemetry.addData("Front: ", "%.2f", "Shooter");
                } else {
                    robot.frontLeft.setPower(v4 * powerdrive - gamepad1.right_stick_y * powerdrive);
                    robot.backLeft.setPower(v3 * powerdrive - gamepad1.right_stick_y * powerdrive);
                    robot.frontRight.setPower(v3 * powerdrive + gamepad1.right_stick_y * powerdrive);
                    robot.backRight.setPower((v4 + gamepad1.right_stick_y) * powerdrive);
                    //telemetry.addData("Front: ", "%.2f", "Intake");
                }
            }
            if (gamepad1.left_trigger == 1) {
                robot.intakeMotor.setPower(1);
            } else if (gamepad1.left_bumper == true) {
                robot.intakeMotor.setPower(-1);
            } else {
                robot.intakeMotor.setPower(0);
            }
            if (gamepad2.right_bumper == true) {
                robot.trayMotor.setPower(1);
            } else if (gamepad2.right_trigger == 1) {
                robot.trayMotor.setPower(-1);
            } else {
                robot.trayMotor.setPower(0);
            }
            if (gamepad2.left_bumper == true) {
                robot.shooterMotor.setPower(1);
            } else if (gamepad2.left_bumper == false && gamepad2.left_stick_y == 0) {
                robot.shooterMotor.setPower(0);
            }
            //robot.shooterMotor.setPower(-gamepad2.left_stick_y);
            if (gamepad2.x == true) {
                robot.wobbleClaw.setPosition(0);
                sleep(100);
            }
            if (gamepad2.b == true) {
                robot.wobbleClaw.setPosition(1);
                sleep(100);
            }
            if (gamepad2.y == true) {
                robot.wobbleArm.setPower(0.7);
            } else if (gamepad2.a == true) {
                robot.wobbleArm.setPower(-0.7);
            } else {
                robot.wobbleArm.setPower(0);
            }

            if (gamepad1.a == true) {
                robot.servoMotorOne.setPosition(0.5);
                robot.servoMotorTwo.setPosition(0.5);
                sleep(500);
            }
           /*

           if(gamepad1.dpad_up == true)
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
            //telemetry.addData("Color", "%.2f", colorval);
            //telemetry.addData("Color", "%.2f", colorval4);
            telemetry.update();
            sleep(50);
        }
    }
}