package org.firstinspires.ftc.teamcode;

        import com.disnodeteam.dogecv.CameraViewDisplay;
        import com.disnodeteam.dogecv.DogeCV;
        import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DigitalChannel;
        import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="CraterAuto")

public class CraterAuto extends LinearOpMode
{
    // Detector object
    private SamplingOrderDetector detector;
    Definitions robot = new Definitions();
    ElapsedTime runTime = new ElapsedTime();
    DigitalChannel leadScrewLimitBot;
    int i = 0;

    final int LANDING = 0;
    final int SAMPLING = 1;


    @Override
    public void runOpMode()
    {
        leadScrewLimitBot = hardwareMap.get(DigitalChannel.class, "leadScrewLimitBot");
        leadScrewLimitBot.setMode(DigitalChannel.Mode.INPUT);

        robot.robotHardwareMapInit(hardwareMap);
        robot.autoInit();

        telemetry.addData("Status", "DogeCV 2018.0 - Sampling Order Example");

        // Setup detector
        detector = new SamplingOrderDetector(); // Create the detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize detector with app context and camera
        detector.useDefaults(); // Set detector to use default settings

        detector.downscale = 0.4; // How much to downscale the input frames

        // Optional tuning
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.001;

        detector.ratioScorer.weight = 15;
        detector.ratioScorer.perfectRatio = 1.0;

        detector.enable(); // Start detector
        //do it with switch


        waitForStart();

        while(opModeIsActive())
        {
            robot.leadScrewMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            while(leadScrewLimitBot.getState())
            {
                robot.leadScrewMotor.setPower(-1);
            }
                robot.leadScrewMotor.setPower(0);



            telemetry.addData("LeadScrewLimitBot pressed?", leadScrewLimitBot.getState());
            telemetry.addData("Lead Screw", robot.leadScrewMotor.getCurrentPosition());
            telemetry.addData("Current Order", detector.getCurrentOrder().toString()); // The current result for the frame
            telemetry.addData("Last Order", detector.getLastOrder().toString()); // The last known result
            telemetry.update();
        }

        detector.disable();
    }

}
