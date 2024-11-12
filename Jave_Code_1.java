package org.firstinspires.ftc.teamcode;
//Call Packages
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Jave_Code")
public class Jave_Code extends LinearOpMode{
	//Call servos/motors variables for the future
	private DcMotor _00_motor;
	private DcMotor _01_motor;
	private CRServo claew;
	float LeftPower = 0;
	float RightPower = 0;

	//Use the webcam on the robot
	//private static final boolean USE_WEBCAM = true;
	//Variables for apriltag & visionportal
	//private AprilTagProcessor aprilTag;
	//private VisionPortal visionPortal;

	@Override

	public void runOpMode(){
		//Map our hardware on the robot to the variables previously created
		 _00_motor = hardwareMap.get(DcMotor.class, "00_motor");
		 _01_motor = hardwareMap.get(DcMotor.class, "01_motor");
		 _0_shoulder = hardwareMap.get(DcMotor.class, "0_shoulder");
		 _1_shoulder = hardwareMap.get(DcMotor.class, "1_shoulder");
		 claew = hardwareMap.get(CRServo.class, "claew");

		//April Tags(ERRORS, FIX)
		//telemetryAprilTag();

		 //Call Telemetry to show numbers on the robot(QOL)
		 telemetry.update();

		 waitForStart();
		 //Set any motor flips needed
		_01_motor.setDirection(DcMotor.Direction.REVERSE);
		if (opModeIsActive()) {
			while (opModeIsActive()) {
				float LeftPower = gamepad1.left_stick_y;
				float RightPower = gamepad1.right_stick_y;

				//Movement for gamepad1(Trenton)
				_00_motor.setPower(gamepad1.right_stick_y);
				_01_motor.setPower(gamepad1.left_stick_y);

				//Arm control for gamepad2(Kelie)
				_0_shoulder.setPower(gamepad2.left_stick_y)
				_1_shoulder.setPower(gamepad2.left_stick_y)
				if (gamepad2.d_pad_left == true){
					_00_motor.setPower(0.25)
					_01_motor.setPower(-0.25)
					wait(100)
					_00_motor.setPower(0)
					_01_motor.setPower(0)
				}
				if (gamepad2.a == true) {
					claew.setPower(-0.25);
					telemetry.addData("Claw", "Open");
				}
				else {
					claew.setPower(0);
					telemetry.addData("Claw", "Closed");
				}

				//Telementry calls
				telemetry.addData("Left Motor", LeftPower); //The speed the robot is trying to move on the Left Motor
				telemetry.addData("Right Motor", RightPower); //The speed the robot is trying to move on the Right Motor

				telemetry.update();
		  }
		}
	}
}
