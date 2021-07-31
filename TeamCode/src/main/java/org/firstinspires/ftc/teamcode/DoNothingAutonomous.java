package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

// test
@Autonomous (name = "Do Nothing Autonomous", group = "Autonomous")
public class DoNothingAutonomous extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        waitForStart();
        sleep(2000);
    }
}
