package org.usfirst.frc.team2340.robot.subsystems;

import org.usfirst.frc.team2340.robot.Robot;
import org.usfirst.frc.team2340.robot.RobotMap;
import org.usfirst.frc.team2340.robot.commands.ArcadeDriveCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {

	static private DriveSubsystem subsystem;
	RobotDrive robotDrive;

	static public DriveSubsystem getInstance() {
		if (subsystem == null) {
			subsystem = new DriveSubsystem();
		}
		return subsystem;
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveCommand());
	}

	private DriveSubsystem() {
		createLeftSide();
		createRightSide();
		robotDrive = new RobotDrive(Robot.oi.frontLeft, Robot.oi.frontRight);
		robotDrive.setMaxOutput(350);
		robotDrive.setSafetyEnabled(false);
	}

	private void createLeftSide() {
		try {
			Robot.oi.frontLeft = new CANTalon(RobotMap.FRONT_LEFT_TAL_ID);
			Robot.oi.frontLeft.changeControlMode(CANTalon.TalonControlMode.Speed);
			Robot.oi.frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
			Robot.oi.frontLeft.reverseSensor(true);
			Robot.oi.frontLeft.configEncoderCodesPerRev(250);
			Robot.oi.frontLeft.configNominalOutputVoltage(+0.0f, -0.0f);
			Robot.oi.frontLeft.configPeakOutputVoltage(+12.0f, -12.0f);
			Robot.oi.frontLeft.setProfile(0);
			Robot.oi.frontLeft.setP(2.0);
			Robot.oi.frontLeft.setI(0.01);
			Robot.oi.frontLeft.setD(0.0);
			Robot.oi.frontLeft.setF(0);
////			
//			//follow the speed control of front left
//			Robot.oi.backLeft = new CANTalon(RobotMap.BACK_LEFT_TAL_ID);
//			Robot.oi.backLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
//			Robot.oi.backLeft.set(RobotMap.FRONT_LEFT_TAL_ID);
		} catch (Exception ex) {
			System.out.println("createLeftSide FAILED");
		}
	}

	private void createRightSide() {
		try {
			Robot.oi.frontRight = new CANTalon(RobotMap.FRONT_RIGHT_TAL_ID);
			
			Robot.oi.frontRight.changeControlMode(CANTalon.TalonControlMode.Speed);
			Robot.oi.frontRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
			Robot.oi.frontRight.reverseSensor(true);
			Robot.oi.frontRight.configEncoderCodesPerRev(250);
			Robot.oi.frontRight.configNominalOutputVoltage(+0.0f, -0.0f);
			Robot.oi.frontRight.configPeakOutputVoltage(+12.0f, -12.0f);
			Robot.oi.frontRight.setProfile(0);
			Robot.oi.frontRight.setP(2.0);
			Robot.oi.frontRight.setI(0.01);
			Robot.oi.frontRight.setD(0.00);
			Robot.oi.frontRight.setF(0);
			//Robot.oi.frontRight.setInverted(true);
			
			
//			Robot.oi.backRight = new CANTalon(RobotMap.BACK_RIGHT_TAL_ID);
//			Robot.oi.backRight.changeControlMode(CANTalon.TalonControlMode.Follower);
//			Robot.oi.backRight.set(RobotMap.FRONT_RIGHT_TAL_ID);
		} catch (Exception ex) {
			System.out.println("createRightSide FAILED");
		}
	}
	
	public void setBrakeMode(boolean brake) {
		Robot.oi.frontRight.enableBrakeMode(brake);
		Robot.oi.backRight.enableBrakeMode(brake);
		Robot.oi.frontLeft.enableBrakeMode(brake);
		Robot.oi.backLeft.enableBrakeMode(brake);
	}
	
	public void setArcadeSpeed(double x, double y){
		robotDrive.arcadeDrive(y, x);
	}
}
