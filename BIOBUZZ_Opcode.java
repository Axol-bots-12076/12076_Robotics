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

@TeleOp(name="Robotics Code 2026-27")

public class Code_2627 extends LinearOpMode{

	//Motors should be written in this order just to make things easy on yourself later -R
	private DcMotor Front_Left; //CH00
	private DcMotor Front_Right; //CH01
	private DcMotor Back_Left; //EH03
	private DcMotor Back_Right; //EH02

	double LSY = 0;
	double LSX = 0;
	double RSX = 0;

	//AprilTag stuff, look at initAprilTag for the rest(Taken from FTC code) -R
	private static final boolean USE_WEBCAM = true;
	private AprilTagProcessor aprilTag;
	private VisionPortal visionPortal;

	@Override
	public void runOpMode() {

		Front_Left = hardwareMap.get(DcMotor.class, "Front_Left");
		Front_Right = hardwareMap.get(DcMotor.class, "Front_Right");
		Back_Left = hardwareMap.get(DcMotor.class, "Back_Left");
		Back_Right = hardwareMap.get(DcMotor.class, "Back_Right");
		Front_Right.setDirection(DcMotor.Direction.REVERSE);
		Back_Left.setDirection(DcMotor.Direction.REVERSE);
		initAprilTag();

		// Wait for the DS start button to be touched.
		telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
		telemetry.addData(">", "Touch Play to start OpMode");
		telemetry.update();
		waitForStart();

		if (opModeIsActive()) {
			while (opModeIsActive()) {
 
				telemetryAprilTag();
				
				// Save CPU resources; can resume streaming when needed.
				if (gamepad1.dpad_down) {
					visionPortal.stopStreaming();
				} else if (gamepad1.dpad_up) {
					visionPortal.resumeStreaming();
				}	

				

				//Wheel Control, for now this is going to be based on the left stick being your directional movement, with the rightstick controlling roation.
				LSY = gamepad1.left_stick_y;
				LSX = gamepad1.left_stick_x;
				RSX = gamepad1.right_stick_x;		

				//Linear Movement, currently the motors are out of whack so it does not work
				Front_Left.setPower((LSY + LSX)/2);//same as back right
				Front_Right.setPower((LSY - LSX)/2);//same as back left
				Back_Left.setPower((LSY - LSX)/2);//^^
				Back_Right.setPower((LSY + LSX)/2);//^^

				//Rotational Movement
				if ((RSX > 0.1) || (RSX < -0.1)) {//deadzone on the RSX so it doesnt override the LS movement
					Front_Left.setPower(-RSX);
					Front_Right.setPower(RSX);
					Back_Left.setPower(-RSX);
					Back_Right.setPower(RSX);
				}

				telemetry.addData("RSX", RSX);
				telemetry.addData("LSY", LSY);
				telemetry.addData("LSX", LSX);
				telemetry.update();

			}
		}

		// Save more CPU resources when camera is no longer needed.
		visionPortal.close();

	}   // end method runOpMode()

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
