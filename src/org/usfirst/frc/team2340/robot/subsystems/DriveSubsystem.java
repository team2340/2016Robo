package org.usfirst.frc.team2340.robot.subsystems;

import org.usfirst.frc.team2340.robot.Robot;
import org.usfirst.frc.team2340.robot.RobotMap;
import org.usfirst.frc.team2340.robot.commands.ArcadeDriveCommand;
import edu.wpi.first.wpilibj.CANTalon;
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
	}

	private void createLeftSide() {
		try {
			Robot.oi.frontLeft = new CANTalon(RobotMap.FRONT_LEFT_TAL_ID);
			Robot.oi.backLeft = new CANTalon(RobotMap.BACK_LEFT_TAL_ID);
			Robot.oi.backLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
			Robot.oi.backLeft.set(RobotMap.FRONT_LEFT_TAL_ID);
		} catch (Exception ex) {
			System.out.println("createLeftSide FAILED");
		}
	}

	private void createRightSide() {
		try {
			Robot.oi.frontRight = new CANTalon(RobotMap.FRONT_RIGHT_TAL_ID);
			Robot.oi.backRight = new CANTalon(RobotMap.BACK_RIGHT_TAL_ID);
			Robot.oi.backRight.changeControlMode(CANTalon.TalonControlMode.Follower);
			Robot.oi.backRight.set(RobotMap.FRONT_RIGHT_TAL_ID);
		} catch (Exception ex) {
			System.out.println("createRightSide FAILED");
		}
	}
	
	public void setArcadeSpeed(double x, double y){
		robotDrive.arcadeDrive(y, x);
	}
}
