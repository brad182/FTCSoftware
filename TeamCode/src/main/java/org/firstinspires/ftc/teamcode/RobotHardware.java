/*
FTC Dashboard: http://192.168.43.1:8080/dash
Onbot Java: 192.168.43.1:8080
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RobotHardware { // the hardware map

    // final values
    static final double pusherResetPosition = 1.0;
    static final double pusherPushPosition = 0.0;

    static final double claw1InitialPosition = 1.0;
    static final double claw2InitialPosition = 1.0;

    // drivetrain motors
    public DcMotor frontLeftMotor = null;  // four drivetrain motors
    public DcMotor frontRightMotor = null;
    public DcMotor backLeftMotor = null;
    public DcMotor backRightMotor = null;

    public DcMotor shooterMotor = null;  // shooter motor
    public DcMotor conveyorMotor = null;  // conveyor motor
    public DcMotor wobbleMotor = null;  // wobble arm motor

    public Servo magazineServo = null;
    //public Servo clawServo1 = null;
    //public Servo clawServo2 = null;

    public ElapsedTime runtime = new ElapsedTime();
    HardwareMap hwMap = null;

    public RobotHardware () { // empty constructor

    }

    public void init (HardwareMap ahwMap) {
        hwMap = ahwMap;

        // front left motor
        frontLeftMotor = hwMap.get(DcMotor.class, "frontLeftMotor");  // initialize the motor
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);  // set as forward
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  // do not use encoders for this
        frontLeftMotor.setPower(0.0);  // initialize to no power

        // front right motor
        frontRightMotor = hwMap.get(DcMotor.class, "frontRightMotor");
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setPower(0.0);

        // back left motor
        backLeftMotor = hwMap.get(DcMotor.class, "backLeftMotor");
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setPower(0.0);

        // back right motor
        backRightMotor = hwMap.get(DcMotor.class, "backRightMotor");
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setPower(0.0);

        // shooter motor
        shooterMotor = hwMap.get(DcMotor.class, "shooterMotor");
        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor.setPower(0.0);

        // magazine servo
        magazineServo = hwMap.get(Servo.class, "magazineServo");
        magazineServo.setDirection(Servo.Direction.FORWARD);  // magazine servo
        magazineServo.setPosition(pusherResetPosition);

        /*
        // intake motor
        conveyorMotor = hwMap.get(DcMotor.class, "conveyorMotor");
        conveyorMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        conveyorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        conveyorMotor.setPower(0.0);
         */

        /*
        // claw servo left
        clawServo1 = hwMap.get(Servo.class, "wobbleServo1");
        clawServo1.setDirection(Servo.Direction.FORWARD);
        clawServo1.setPosition(claw1InitialPosition);

        // claw servo right
        clawServo2 = hwMap.get(Servo.class, "wobbleServo2");
        clawServo2.setDirection(Servo.Direction.FORWARD);
        clawServo2.setPosition(claw2InitialPosition);
         */
    }

}
