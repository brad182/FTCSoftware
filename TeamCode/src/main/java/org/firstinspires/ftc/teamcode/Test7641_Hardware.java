package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


    public class Test7641_Hardware {
        /* Public OpMode members. */
        public DcMotor frontLeft = null;
        public DcMotor backLeft = null;
        public DcMotor frontRight = null;
        public DcMotor backRight = null;
        public ColorSensor Csensor = null;
        public ColorSensor Csensor2 = null;
        public DistanceSensor distanceSensor = null;
        public DcMotor shooterMotor = null;
        public DcMotor intakeMotor = null;
        public DcMotor trayMotor = null;
        public DcMotor wobbleArm = null;
        public Servo servoMotorOne;
        public Servo servoMotorTwo;
        public double servoPosition = 0.0;
        // public DcMotor extendIntake = null;
        public Servo wobbleClaw;

        public static final double MID_SERVO = 0.5;
        public static final double ARM_UP_POWER = 0.45;
        public static final double ARM_DOWN_POWER = -0.45;

        /* local OpMode members. */
        HardwareMap hwMap = null;
        private ElapsedTime period = new ElapsedTime();

        /* Constructor */
        /* Initialize standard Hardware interfaces */
        public void init(HardwareMap ahwMap) {
            // Save reference to Hardware map
            hwMap = ahwMap;
            Csensor = hwMap.get(ColorSensor.class, "Csensor");
            Csensor2 = hwMap.get(ColorSensor.class, "Csensor2");
            //distanceSensor = hwMap.get(DistanceSensor.class, "distancesensor");
            // Define and Initialize Motors
            frontLeft = hwMap.get(DcMotor.class, "frontLeft");
            backLeft = hwMap.get(DcMotor.class, "backLeft");
            frontRight = hwMap.get(DcMotor.class, "frontRight");
            backRight = hwMap.get(DcMotor.class, "backRight");
            shooterMotor = hwMap.get(DcMotor.class, "shooterMotor");
            intakeMotor = hwMap.get(DcMotor.class, "intakeMotor");
            trayMotor = hwMap.get(DcMotor.class, "trayMotor");
            wobbleArm = hwMap.get(DcMotor.class, "wobbleArm");
            servoMotorOne = hwMap.servo.get("servoMotorOne");
            servoMotorTwo = hwMap.servo.get("servoMotorTwo");
            wobbleClaw = hwMap.servo.get("wobbleClaw");
            //leftArm    = hwMap.get(DcMotor.class, "left_arm");
            frontLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
            frontRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
            backLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
            backRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
            shooterMotor.setDirection(DcMotor.Direction.REVERSE);
            intakeMotor.setDirection(DcMotor.Direction.REVERSE);
            trayMotor.setDirection(DcMotor.Direction.FORWARD);
            wobbleArm.setDirection(DcMotor.Direction.FORWARD);

            // Set all motors to zero power
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            shooterMotor.setPower(0);
            intakeMotor.setPower(0);
            trayMotor.setPower(0);
            wobbleArm.setPower(0);
            //leftArm.setPower(0);

            // Set all motors to run without encoders.
            // May want to use RUN_USING_ENCODERS if encoders are installed.
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            shooterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            trayMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            wobbleArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            //leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // Define and initialize ALL installed servos.
            //leftClaw  = hwMap.get(Servo.class, "left_hand");
            //rightClaw = hwMap.get(Servo.class, "right_hand");
            //leftClaw.setPosition(MID_SERVO);
            //rightClaw.setPosition(MID_SERVO);
            //servoMotorOne.setPosition(0.76);
            //servoMotorTwo.setPosition(0.24);
        }
    }

