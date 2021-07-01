package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RobotHardware { // the hardware map

    // drivetrain motors
    public DcMotor frontLeftMotor = null;  // four drivetrain motors
    public DcMotor frontRightMotor = null;
    public DcMotor backLeftMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor shooterMotor = null;  // shooter motor
    public DcMotor conveyorMotor = null;  // conveyor motor
    public DcMotor wobbleMotor = null;  // wobble arm motor

    public Servo magazineServo = null;
    public Servo clawServo1 = null;
    public Servo clawServo2 = null;

    public ElapsedTime runtime = new ElapsedTime();
    HardwareMap hardwareMap = null;

    public RobotHardware () { // constructor
        init (hardwareMap);
    }

    private void init (HardwareMap hwMap) {
        hardwareMap = hwMap;
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");  // four drivetrain motors
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");  // shooter motor
        conveyorMotor = hardwareMap.get(DcMotor.class, "conveyorMotor");  // conveyor motor
        wobbleMotor = hardwareMap.get(DcMotor.class, "wobbleMotor");  // wobble arm motor

        magazineServo = hardwareMap.get(Servo.class, "magazineServo");
        clawServo1 = hardwareMap.get(Servo.class, "wobbleServo1");
        clawServo2 = hardwareMap.get(Servo.class, "wobbleServo2");


        // motor direction
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);  // four drivetrain motors
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        shooterMotor.setDirection(DcMotor.Direction.FORWARD);  // shooter motor
        conveyorMotor.setDirection(DcMotor.Direction.FORWARD);  // conveyor motor
        wobbleMotor.setDirection(DcMotor.Direction.FORWARD);  // wobble arm motor


        magazineServo.setDirection(Servo.Direction.FORWARD);  // magazine servo
        clawServo1.setDirection(Servo.Direction.FORWARD);  // two claw servos
        clawServo2.setDirection(Servo.Direction.FORWARD);

        // zero power behavior
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        conveyorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // motor mode
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // do not use encoders
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        conveyorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // set power
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        shooterMotor.setPower(0);
        conveyorMotor.setPower(0);

        magazineServo.setPosition (1.0); // initial servo position
        clawServo1.setPosition(1.0);
        clawServo2.setPosition(1.0);
    }

}
