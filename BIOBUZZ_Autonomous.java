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
import java.lang.Math;

@Autonomous(name="BIOBUZZ_Auto")

    public class BIOBUZZ_Autonomous extends LinearOpMode {

        // classify the motors
        private DcMotor Front_Left; 
	    private DcMotor Front_Right; 
	    private DcMotor Back_Left; 
    	private DcMotor Back_Right; 
        // & the sensors too don't forget those
    	private static final boolean USE_WEBCAM = true;
	    private AprilTagProcessor aprilTag;
    	private VisionPortal visionPortal;

        //The Robot needs to know: Pieces in the Robot, Pieces in the Hive, weight of each ball in relation to tipping the hive(ex. we can tip the hive with 5 nectar, so it gets to be worth "0.2", but if we coudl tip with 4 nectar, it would be "0.25")
        double Pollen = (1 / 8); 
        double Nectar = (1 / 5); 
        double HiveBalls = (Nectar * 3); //3 nectar start in the Hive
        double RoBallsW = (Pollen * 4); //we get to start with 4 Pollen, so we should have them :P
        double RoBallsN = 4; //number of gamepieces we have stored

        //robots X and Y location within the field
        double RBT_X = 0; //when looking from the audience, this will be going left or right, with 0 being in the red garden
        double RBT_Y = 0; //when looking from the audience, this will be closer or farther, with 0 being in the red garden
        double RBT_Vel_X = 0; //used for launch balls estimation
        double RBT_Vel_Y = 0; //same as before

        string Team = "Null"; //Set to Null incase the Robot fails to find team affiliation, if it does then it should immediately go to park.

    @Override
    public void runOpMode() {

		Front_Left = hardwareMap.get(DcMotor.class, "Front_Left");
		Front_Right = hardwareMap.get(DcMotor.class, "Front_Right");
		Back_Left = hardwareMap.get(DcMotor.class, "Back_Left");
		Back_Right = hardwareMap.get(DcMotor.class, "Back_Right");
		Front_Right.setDirection(DcMotor.Direction.REVERSE);
		Back_Left.setDirection(DcMotor.Direction.REVERSE);
        initAprilTag();



        waitForStart();

		findHives(Team);

        if (opModeIsActive()) {
            while (opModeIsActive()) {
				telemetryAprilTag();
                /* ----AUTO Explaination---- */
                /*  By Rain
                Start with the Robot aimed towards the upwards hive
                the robot can use the initial detection to know what team its on. 
                
                Now that we know what team we are on(for this im going to say Blue)
                watch a timer for how long we have left until end of auto, if its close to the end(20s?) go park
                Lets see if must launch(do we have 4 balls) if so launch
                Now check if we can tip with a launch(does hive + robot >=1?) launch
                Since we cant tip the hive, and have room, lets go collect Balls

                Use OpenCV to try and find Nectar & Pollen(this is going to be annoying)
                switch statement to check for Nectar, Pollen, collect the best one, add to the RoBall
                check if we have enough to launch, if not go back to collecting
                */






                }
            }
        }
    
	private void MoveToPosistion(double X, double Y) {
		//take where we currently are, where we want to be, and find the path towards it without hitting something(Probably OpenCV for robots, and  just writing out the known location of flowers & Hive with buffer)
		//probably just gonna do A* alg because it works lmao.
		
	
	
	}

    private double findHives() { //Find our team Hive Cells to aim at. If we cant find our hive then find the enemy hive to know where ours is(and where we are) in reference
        telemetry.addData("Finding Hive For Team", Team);
        telemetry.update();
        //detect that we have a "null" Team(first run through), so we should search for a team
        if (Team == "Null") {
            //magical search for cell cluster
            //find that we can see Tags 34,35,36,37 cluster on red team, or 42,43,44,45 on blue
			//if we cant find either of these, we throw up a big sad face and move towards parking while attempting to find it again
            //set team to the found if so

            telemetry.addData("Team Change", Team);
            telemetry.update();
        }
        if (Team == "Blue") {

        }
        if (Team == "Red") {

        }
        return(RBT_X);
    }

    private double LaunchBalls() {
        telemetry.addData("Launching", "Posistion Check");
        telemetry.update();
		if ("able to shoot" != true) { //check for April Tags being visible in a way that tells us we can shoot at the opening
			//MoveToLaunch Code
		}
        telemetry.addData("Launching", RoBallsN);
        telemetry.update();

        while (RoBallsN > 0) {
            //launch stuff, idk I dont have the stuff to do it yet :P
            RoBallsN = RoBallsN - 1;
        }


        return(RoBallsN);
    }

    private void CollectBalls() {
        //this is the OpenCV garbage I have to write ;-;
        while (RoBallsN < 4){
            //OpenCV

            
        }
        
    }

	/**
	 * Initialize the AprilTag processor.
	 */
	private void initAprilTag() {

		// Create the AprilTag processor.
		aprilTag = new AprilTagProcessor.Builder()

			// The following default settings are available to un-comment and edit as needed.
			.setDrawAxes(true)
			//.setDrawCubeProjection(false)
			//.setDrawTagOutline(true)
			//.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
			//.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
			//.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

			// == CAMERA CALIBRATION ==
			// If you do not manually specify calibration parameters, the SDK will attempt
			// to load a predefined calibration for your camera.
			//.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
			// ... these parameters are fx, fy, cx, cy.

			.build();

		// Adjust Image Decimation to trade-off detection-range for detection-rate.
		// eg: Some typical detection data using a Logitech C920 WebCam
		// Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
		// Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
		// Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
		// Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
		// Note: Decimation can be changed on-the-fly to adapt during a match.
		//aprilTag.setDecimation(3);

		// Create the vision portal by using a builder.
		VisionPortal.Builder builder = new VisionPortal.Builder();

		// Set the camera (webcam vs. built-in RC phone camera).
		if (USE_WEBCAM) {
			builder.setCamera(hardwareMap.get(WebcamName.class, "Camera"));
		} else {
			builder.setCamera(BuiltinCameraDirection.BACK);
		}

		// Choose a camera resolution. Not all cameras support all resolutions.

		// Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
		//builder.enableLiveView(true);

		// Set the stream format; MJPEG uses less bandwidth than default YUY2.
		//builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

		// Choose whether or not LiveView stops if no processors are enabled.
		// If set "true", monitor shows solid orange screen if no processors enabled.
		// If set "false", monitor shows camera view without annotations.
		//builder.setAutoStopLiveView(false);

		// Set and enable the processor.
		builder.addProcessor(aprilTag);

		// Build the Vision Portal, using the above settings.
		visionPortal = builder.build();

		// Disable or re-enable the aprilTag processor at any time.
		//visionPortal.setProcessorEnabled(aprilTag, true);

	}   // end method initAprilTag()


	/**
	 * Add telemetry about AprilTag detections.
	 */
	private void telemetryAprilTag() {

		List<AprilTagDetection> currentDetections = aprilTag.getDetections();
		telemetry.addData("# AprilTags Detected", currentDetections.size());

		// Step through the list of detections and display info for each one.
		for (AprilTagDetection detection : currentDetections) {
			if (detection.metadata != null) {
				telemetry.addLine(String.format("\n==== (ID %d) %s Proper Data", detection.id, detection.metadata.name));
				telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
				telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
				telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
			} else {
				telemetry.addLine(String.format("\n==== (ID %d) Based on Center", detection.id));
				telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
			}
		} 

		// Add "key" information to telemetry
		telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
		telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
		telemetry.addLine("RBE = Range, Bearing & Elevation");

	}   // end method telemetryAprilTag()

}   // end class