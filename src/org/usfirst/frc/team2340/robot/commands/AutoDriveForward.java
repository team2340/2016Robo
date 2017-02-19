package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;
import org.usfirst.frc.team2340.robot.RobotUtils;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDriveForward extends Command {
	long startTime = 0;
	boolean rDone, lDone, rotationComplete, inMotion;
	double desiredSpot = 0;

	public AutoDriveForward() {
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {
		startTime = System.currentTimeMillis();
		rotationComplete= false;
		lDone = rDone = false;
		inMotion = false;
		desiredSpot = RobotUtils.getEncPositionFromIN(RobotUtils.distanceMinusRobot(88));
		Robot.oi.frontLeft.set(desiredSpot);
		Robot.oi.frontRight.set(-desiredSpot);
	}
	@Override
	protected void execute() {
		double angle = Robot.oi.gyro.getAngle();
		long elapsed = (System.currentTimeMillis() - startTime)/1000;

		SmartDashboard.putNumber("left position", Robot.oi.frontLeft.getPosition());
		SmartDashboard.putNumber("right position ",Robot.oi.frontRight.getPosition()); 

		SmartDashboard.putNumber("Auto Elapsed", elapsed);
		SmartDashboard.putNumber("Gyro angle", angle);
		if (!lDone || !rDone) {
			if(Robot.oi.frontRight.getPosition()<=-desiredSpot){
				Robot.oi.frontRight.setPosition(0);
				Robot.oi.frontRight.set(0);
				rDone = true;
			}
			if(Robot.oi.frontLeft.getPosition()>=desiredSpot){
				Robot.oi.frontLeft.setPosition(0);
				Robot.oi.frontLeft.set(0);
				lDone = true;
			}
		}
		if(rotationComplete && !inMotion){
			Robot.drive.setForPosition();
			desiredSpot = RobotUtils.getEncPositionFromIN(Robot.drive.finalDistance- 0);
			Robot.oi.frontLeft.set(desiredSpot);
			Robot.oi.frontRight.set(-desiredSpot);
			inMotion = true;
		}

		if(rDone && lDone && !rotationComplete){
			Robot.drive.setForSpeed();
			rotationComplete= adjustRotation();
			if(!rotationComplete)
			{
				System.out.println("Adjusting...");
			}
		}
	} 

	private boolean adjustRotation()
	{
		//CENTER IS 320
		if ( Robot.drive.centerX != -1 ) {
			if(Robot.drive.centerX > 340) { //+rotate: go right
				System.out.println("Adjusting 330 " + Robot.drive.centerX);
				rotateRight(40);
			}
			else if(Robot.drive.centerX < 310){ //-rotate: go left
				System.out.println("Adjusting 310 "  + Robot.drive.centerX);
				rotateLeft(40);
			}
			else{
				System.out.println("Good Enough!");
				setSpeed(0);
				return true;
			}
		} else {
			setSpeed(0);
			System.out.println("Nothing detected");
		}

		return false;
	}
	void setSpeed(double rpm) {
		Robot.oi.frontLeft.set(+rpm);
		Robot.oi.frontRight.set(-rpm);
	}
	void rotateRight(double rpm) {
		Robot.oi.frontLeft.set(rpm);
		Robot.oi.frontRight.set(rpm);
	}
	void rotateLeft(double rpm) {
		Robot.oi.frontLeft.set(-rpm);
		Robot.oi.frontRight.set(-rpm);
	}

	@Override
	protected boolean isFinished() {
		return System.currentTimeMillis() -startTime >= 15000; 
	}

	@Override
	protected void end() {
		Robot.drive.setForVBus();
	}

	@Override
	protected void interrupted() {
	}
}
