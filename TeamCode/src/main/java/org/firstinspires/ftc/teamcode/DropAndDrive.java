package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="DropAndDrive")

public class DropAndDrive extends LinearOpMode
{
    // Detector object
    GoldAlignDetector detector;
    Definitions robot = new Definitions();
    ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException
    {
        /**
         * Initialization
         */
        robot.robotHardwareMapInit(hardwareMap);//Initializes robot hardware map

        //Setup detector
        detector = new GoldAlignDetector();// Create the detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());// Initialize detector with app context and camera
        detector.useDefaults();// Set detector to use default settings
        detector.downscale = 0.4;//How much to downscale the input frames
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;//Camera input tuning
        detector.maxAreaScorer.weight = 0.001;
        detector.ratioScorer.weight = 15;
        detector.ratioScorer.perfectRatio = 1.0;
        detector.enable();//Start detector

        //Resets the lead screw to lowest position
        robot.leadScrewMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (robot.leadScrewLimitBot.getState()) {
            robot.leadScrewMotor.setPower(0.75);
        }
        robot.leadScrewMotor.setPower(0);
        robot.teamMarkerServo.setPower(0);
        //Resets robot encoder values before match
        robot.resetEncoders();//Resets encoder tick values, sets motor PID mode to STOP_AN1D_RESET_ENCODERS

        waitForStart();//Waits until driver clicks the start button1

        /**
         * Autonomous starts - Match time of 0 seconds
         */

        //Robot drops from the lander, but is still attached
        while(robot.leadScrewMotor.getCurrentPosition() > -7150 && opModeIsActive())
        {
            robot.leadScrewMotor.setTargetPosition(-7150);
            robot.leadScrewMotor.setPower(-1);
        }
        robot.leadScrewMotor.setPower(0);

        robot.moveInches(robot.FORWARD,40,1);
        sleep(1500);
        robot.setPower(0);

        detector.disable();
    }
}