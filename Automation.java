package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;


@Autonomous(name = "Automation")
public class Automation extends LinearOpMode{
		private DcMotor _00_motor;
		private DcMotor _01_motor;
		private DcMotor shoulder_0;
		private DcMotor shoulder_1;
		private CRServo claew;

	public void runOpMode(){
		//Map our hardware on the robot to the variables previously created
		waitForStart();
			_00_motor = hardwareMap.get(DcMotor.class, "00_motor");
			_01_motor = hardwareMap.get(DcMotor.class, "01_motor");
			shoulder_0 = hardwareMap.get(DcMotor.class, "shoulder_0");
			shoulder_1 = hardwareMap.get(DcMotor.class, "shoulder_1");
			claew = hardwareMap.get(CRServo.class, "claew");
		if (opModeIsActive()); {
		Foward(1,0.15);
		Right(90);
		Foward(1,0.25);
		Left(90);
		Foward(2, 0.4);
		Right(90);
		Foward(1,0.1);
		Right(90);
		Foward(3,0.5);
		}
	}
	public void Foward(int time,double speed) {
		_00_motor.setPower(-speed);
		_01_motor.setPower(speed);
		sleep(time * 1000);
		_00_motor.setPower(0);
		_01_motor.setPower(0);
	}

	public void Right(long angle) {
		_00_motor.setPower(0.25);
		_01_motor.setPower(0.25);
		sleep(angle * 20);
		_00_motor.setPower(0);
		_01_motor.setPower(0);

	}

	public void Left(long angle) {
		_00_motor.setPower(-0.25);
		_01_motor.setPower(-0.25);
		sleep(angle * 20);
		_00_motor.setPower(0);
		_01_motor.setPower(0);
	}

	public void Back(int time, double speed) {
		_00_motor.setPower(speed);
		_01_motor.setPower(-speed);
		sleep(time / 90);
		_00_motor.setPower(0);
		_01_motor.setPower(0);
	}

	public void Up(int time) {
		shoulder_0.setPower(-1);
		shoulder_1.setPower(-1);
		sleep(time * 1000);
		shoulder_0.setPower(0);
		shoulder_1.setPower(0);
	}
	public void Down(int time) {
		shoulder_0.setPower(1);
		shoulder_1.setPower(1);
		sleep(time * 1000);
		shoulder_0.setPower(0);
		shoulder_1.setPower(0);
	}
	public void Place() {
		claew.setPower(0.1);
		shoulder_0.setPower(1);
		shoulder_1.setPower(1);
		sleep(750);
		Foward(1,-0.5);
		claew.setPower(-0.25);
		sleep(300);
		shoulder_0.setPower(0);
		shoulder_1.setPower(0);
	}
}
