package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Definitions
{

    ElapsedTime runtime = new ElapsedTime(); // Defines the Up Time of the program


    public final int FORWARD = 0;
    public final int BACKWARD = 1;
    public final int STRAFELEFT = 2;
    public final int STRAFERIGHT = 3;
    public final int ROTATE = 4;

    DcMotor leftFrontMotor = null;
    DcMotor leftBackMotor = null;
    DcMotor rightFrontMotor = null;
    DcMotor rightBackMotor = null;
    DcMotor scoringArmMotor = null;
    DcMotor leadScrewMotor = null;
    Servo scoringArmReleaseServo = null;
    Servo ballStopperServo = null;
    Servo scoringArmLatchServo = null;
    ColorSensor rightColorSensor = null;
    ColorSensor leftColorSensor = null;
    //TouchSensor leadScrewLimitTop;
    //TouchSensor leadScrewLimitBot;

    //Constructor to initialize variables

    public Definitions()
    {

    }


    public void robotHardwareMapInit(HardwareMap Map)
    {
        //Naming Scheme for configuring robot controller app
        leftBackMotor = Map.dcMotor.get("leftBackMotor");
        leftFrontMotor = Map.dcMotor.get("leftFrontMotor");
        rightBackMotor = Map.dcMotor.get("rightBackMotor");
        rightFrontMotor = Map.dcMotor.get("rightFrontMotor");
        scoringArmMotor = Map.dcMotor.get("scoringArmMotor");
        leadScrewMotor = Map.dcMotor.get("leadScrewMotor");
        scoringArmReleaseServo = Map.servo.get("scoringArmReleaseServo");
        ballStopperServo = Map.servo.get("ballStopperServo");
        scoringArmLatchServo = Map.servo.get("scoringArmLatchServo");
        rightColorSensor = Map.colorSensor.get("rightColorSensor");
        leftColorSensor = Map.colorSensor.get("leftColorSensor");
        //leadScrewLimitTop = Map.touchSensor.get("leadScrewLimitTop");
        //leadScrewLimitBot = Map.touchSensor.get("leadScrewLimitBot");
    }

    public void testHardwareMapInit(HardwareMap Map)
    {
        //Naming Scheme for configuring robot controller app
        leftBackMotor = Map.dcMotor.get("leftBackMotor");
        leftFrontMotor = Map.dcMotor.get("leftFrontMotor");
        rightBackMotor = Map.dcMotor.get("rightBackMotor");
        rightFrontMotor = Map.dcMotor.get("rightFrontMotor");
        scoringArmMotor = Map.dcMotor.get("scoringArmMotor");
        leadScrewMotor = Map.dcMotor.get("leadScrewMotor");
        ballStopperServo = Map.servo.get("ballStopperServo");
        scoringArmLatchServo = Map.servo.get("scoringArmLatchServo");
    }


    public void autoInit() {
        resetEncoders();
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leadScrewMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightColorSensor.enableLed(true);
        leftColorSensor.enableLed(true);
    }

    public void cubeLeft()
    {

    }

    public void cubeRight()
    {

    }

    public void cubeCenter()
    {

    }

    public void unLatch()
    {
         leadScrewMotor.setTargetPosition(6000);
         leadScrewMotor.setPower(-1);

         if(!leadScrewMotor.isBusy())
         {
             setStrafeLeft();
             moveInches(6, 0.5);
         }
    }

    public int inchesToTicks(double inches)
    {
        return (int) ((1440 / (Math.PI * 4)) * inches);
    }


    public void moveInches(double inches, double power)
    {
        leftBackMotor.setTargetPosition(inchesToTicks(inches));
        leftBackMotor.setPower(power);
        rightBackMotor.setTargetPosition(inchesToTicks(inches));
        rightBackMotor.setPower(power);
        leftFrontMotor.setTargetPosition(inchesToTicks(inches));
        leftFrontMotor.setPower(power);
        rightFrontMotor.setTargetPosition(inchesToTicks(inches));
        rightFrontMotor.setPower(power);
    }

    public void moveInches(int direction, double inches, double power)
    {
        switch(direction)
        {
            case FORWARD:
                setDriveForward();
                moveInches(inches, power);
            case BACKWARD:
                setDriveBackward();
                moveInches(inches, power);
            case STRAFELEFT:
                setStrafeLeft();
                moveInches(inches, power);
            case STRAFERIGHT:
                setStrafeRight();
                moveInches(inches, power);
            default:
                break;
        }
    }

    public void teleOpInit()
    {
        init();
        servoInit();
    }


    void init()
    {
         leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         scoringArmMotor.setPower(0);
         leadScrewMotor.setPower(0);
    }


    void servoInit()
    {
        scoringArmReleaseServo.setPosition(0);
        ballStopperServo.setPosition(0);
        scoringArmLatchServo.setPosition(1);
    }


    void setDriveForward()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    void setDriveBackward()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    void setRot()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    void setStrafeLeft()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    void setStrafeRight()
    {
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    void setPower(double power)
    {
        leftBackMotor.setPower(power);
        leftFrontMotor.setPower(power);
        rightBackMotor.setPower(power);
        rightFrontMotor.setPower(power);
    }


    void setPos(int pos)
    {
        leftBackMotor.setTargetPosition(pos);
        leftFrontMotor.setTargetPosition(pos);
        rightBackMotor.setTargetPosition(pos);
        rightFrontMotor.setTargetPosition(pos);
    }


    void setRotPos(int pos)
    {
        leftBackMotor.setTargetPosition((pos*1120)/360);
        leftFrontMotor.setTargetPosition((pos*1120)/360);
        rightBackMotor.setTargetPosition((pos*1120)/360);
        rightFrontMotor.setTargetPosition((pos*1120)/360);
    }


    void runPos()
    {
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    void resetEncoders()
    {
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
