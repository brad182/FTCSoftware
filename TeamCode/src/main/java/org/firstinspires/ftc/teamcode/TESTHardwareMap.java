/*
This file is used for testing motors not in the complete TeleOp or Autonomous.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TESTHardwareMap {
    public DcMotor frontLeftMotor = null;  // four drivetrain motors
    public DcMotor frontRightMotor = null;
    public DcMotor backLeftMotor = null;
    public DcMotor backRightMotor = null;

    public ElapsedTime runtime = new ElapsedTime();
    HardwareMap hwMap = null;

    public TESTHardwareMap () { // constructor
        //init (map);
    }

    public void init (HardwareMap ahwMap) {
        hwMap = ahwMap;
        frontLeftMotor = hwMap.get(DcMotor.class, "frontLeftMotor");
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);  // set as forward
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);  // do not use encoders for this
        frontLeftMotor.setPower(0.0);

        frontRightMotor = hwMap.get(DcMotor.class, "frontRightMotor");
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setPower(0.0);

        backLeftMotor = hwMap.get(DcMotor.class, "backLeftMotor");
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setPower(0.0);

        backRightMotor = hwMap.get(DcMotor.class, "backRightMotor");
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setPower(0.0);

    }


}
