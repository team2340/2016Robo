package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;
import org.usfirst.frc.team2340.robot.RobotUtils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BlueAllianceBoilerSide extends Command {
	long startTime = 0;
	boolean lDone = false, rDone = false;
	boolean crDone, clDone, rotationComplete, inMotion;
	boolean rotateDone = false;
	double desiredSpot = 0;
	boolean methodRotationComplete, methodDriving, methodcrDone, methodclDone;
	double methodDesiredSpot;
	int driveCount = 0;
	boolean adjustAndDrive;

	public BlueAllianceBoilerSide()
	{
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
		startTime = System.currentTimeMillis();
		rotateDone = lDone = rDone = crDone = clDone = rotationComplete = inMotion = false;
//		desiredSpot = RobotUtils.getEncPositionFromIN(RobotUtils.distanceMinusRobot(105));
		desiredSpot = RobotUtils.getEncPositionFromIN(RobotUtils.distanceMinusRobot(111));
		Robot.oi.frontLeft.set(desiredSpot);
		Robot.oi.frontRight.set(-desiredSpot);
	}

protected boolean RotateRight(){
	double angle = Robot.oi.gyro.getAngle();
	if(angle >=62){
		Robot.drive.setForPosition();
		Robot.oi.frontLeft.set(0);
		Robot.oi.frontRight.set(0);
		System.out.print("ROTATE done");
		return true;
	}
	else{
		Robot.drive.setForSpeed();
		Robot.oi.frontLeft.set(2.5* (64 - angle)+10);
		Robot.oi.frontRight.set(2.5* (64 - angle)+10);
		return false;
	}
}
	@Override
	protected void execute() {
		double angle = Robot.oi.gyro.getAngle();
		long elapsed = (System.currentTimeMillis() - startTime)/1000;

		SmartDashboard.putNumber("Auto Elapsed", elapsed);
		SmartDashboard.putNumber("Gyro angle", angle);
		SmartDashboard.putNumber("left position", Robot.oi.frontLeft.getPosition());
		SmartDashboard.putNumber("right position ",Robot.oi.frontRight.getPosition());
		
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
		if(rDone && lDone && !rotateDone){
			rotateDone = RotateRight(); 
		}
		if(rotateDone && !crDone && !clDone) {
			System.out.println("DRIVE AGAIN");
			desiredSpot = RobotUtils.getEncPositionFromIN(45);
			Robot.oi.frontLeft.set(desiredSpot);
			Robot.oi.frontRight.set(-desiredSpot);
		}
		
		if ((!clDone || !crDone) && (lDone && rDone && rotateDone)) {
			if(Robot.oi.frontRight.getPosition()<=-desiredSpot){
				Robot.oi.frontRight.setPosition(0);
				Robot.oi.frontRight.set(0);
				System.out.println("CRDONE");
				crDone = true;
			}
			if(Robot.oi.frontLeft.getPosition()>=desiredSpot){
				Robot.oi.frontLeft.setPosition(0);
				Robot.oi.frontLeft.set(0);
				System.out.println("CLDONE");
				clDone = true;
			}
		}

//		if(crDone && clDone && !rotationComplete){
//			Robot.drive.setForSpeed();
//			rotationComplete = adjustRotation();
//			if(!rotationComplete)
//			{
//				System.out.println("Adjusting...");
//			}
//		}
//		
//		if(rotationComplete && !inMotion){
//			Robot.drive.setForPosition();
//			desiredSpot = RobotUtils.getEncPositionFromIN(Robot.drive.finalDistance - 8);
//			System.out.println("FINAL drive");
//			Robot.oi.frontLeft.set(desiredSpot);
//			Robot.oi.frontRight.set(-desiredSpot);
//			inMotion = true;
//		}
		
		if(crDone && clDone && !rotationComplete && driveCount < 10  && Robot.drive.finalDistance > 5){
			System.out.println("adjust count " + driveCount);
			if(driveCount < 2) {
				adjustAndDrive = adjustAndDriveHalfWay(-1, -1, 15);
			} else {
				desiredSpot = RobotUtils.getEncPositionFromIN(Robot.drive.finalDistance - 10);
				System.out.println("FINAL drive");
				Robot.oi.frontLeft.set(desiredSpot);
				Robot.oi.frontRight.set(-desiredSpot);
				rotationComplete = true;
				//adjustAndDrive = adjustAndDriveHalfWay(315,330,10);
			}
			if ( adjustAndDrive ) {
				resetAdjustAndDriveHalfway();
				driveCount++;
			}
		}
	}
	
	private void resetAdjustAndDriveHalfway() {
		methodRotationComplete = false;
		methodDriving = false;
		methodclDone = false;
		methodcrDone = false;
	}
	
	private boolean adjustAndDriveHalfWay(int left, int right, int rpm) {
		if( !methodRotationComplete ) {
			Robot.drive.setForSpeed();
			methodRotationComplete = adjustRotation(left, right, rpm);
		}
		if (methodRotationComplete && !methodDriving) {
			Robot.drive.setForPosition();
			methodDriving = true;
			methodDesiredSpot = RobotUtils.getEncPositionFromIN( (Robot.drive.finalDistance - 8)/2);
			Robot.oi.frontLeft.set(methodDesiredSpot);
			Robot.oi.frontRight.set(-methodDesiredSpot);
		}
		if ((!methodclDone || !methodcrDone) && (methodRotationComplete && methodDriving)) {
			if(Robot.oi.frontRight.getPosition()<=-methodDesiredSpot){
				Robot.oi.frontRight.setPosition(0);
				Robot.oi.frontRight.set(0);
				methodcrDone = true;
			}
			if(Robot.oi.frontLeft.getPosition()>=methodDesiredSpot){
				Robot.oi.frontLeft.setPosition(0);
				Robot.oi.frontLeft.set(0);
				methodclDone = true;
			}
		}
		
		return methodRotationComplete && methodclDone && methodcrDone;
	}

	private boolean adjustRotation(int left, int right, int rpm)
	{
		int defaultLeft = 310;
		int defaultRight = 340;
		if (left != -1) {
			defaultLeft = left;
		}
		if (right != -1){
			defaultRight = right;
		}
		int defaultRpm = 40;
		if (rpm != -1){
			defaultRpm = rpm;
		}
		//CENTER IS 320
		if ( Robot.drive.centerX != -1 ) {
			if(Robot.drive.centerX > defaultRight) { //+rotate: go right
				System.out.println("Adjusting 330 " + Robot.drive.centerX);
				rotateRight(defaultRpm);
			}
			else if(Robot.drive.centerX < defaultLeft){ //-rotate: go left
				System.out.println("Adjusting 310 "  + Robot.drive.centerX);
				rotateLeft(defaultRpm);
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

