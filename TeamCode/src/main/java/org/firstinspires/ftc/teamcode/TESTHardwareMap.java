/*
This file is used for testing motors not in the complete TeleOp or Autonomous.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TESTHardwareMap {
    public DcMotor testMotor = null;

    public ElapsedTime runtime = new ElapsedTime();
    HardwareMap hwMap = null;

    public TESTHardwareMap () { // constructor
        //init (map);
    }

    public void init (HardwareMap ahwMap) {
        hwMap = ahwMap;
        testMotor = hwMap.get(DcMotor.class, "testMotor");
        testMotor.setDirection(DcMotorSimple.Direction.FORWARD);  // set as forward
        testMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);  // do not use encoders for this
        testMotor.setPower(0.0);
    }


}
