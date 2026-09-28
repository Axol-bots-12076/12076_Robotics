package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

@Autonomous(name="Auto")

public class Code_2627 extends LinearOpMode{
    // classify the motors type shi
    private DcMotor Front_Left; 
	private DcMotor Front_Right; 
	private DcMotor Back_Left; 
	private DcMotor Back_Right; 
    // & the sensors too don't forget those
	private static final boolean USE_WEBCAM = true;
	private AprilTagProcessor aprilTag;
	private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

            }
        }
    }
    @Autonomous(name = "Basic Movement")
    public class Auto extends LinearOpMode {
    }
    // (kayyajo) I'm stupid so I don't know exactly how to do autonomous stuff, but I'm pretty sure it's just a series of commands. So, in this instance, it would be as follows: exit starting zone > navigate to game piece > collect game piece > navigate to throw zone > aim to bucket > check game pieces > throw available game pieces > begin navigating to another game piece.


    //What I(Rain) am imagining for Auto: Start by figuring out where the Blue(im assuming were on the blue team for this example) 

}