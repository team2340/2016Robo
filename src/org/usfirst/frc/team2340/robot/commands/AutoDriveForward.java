package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDriveForward extends Command {
	long startTime = 0;
	boolean rAtz = false;
	boolean lAtz = false;
	
	
	public AutoDriveForward() {
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
		startTime = System.currentTimeMillis();
			Robot.oi.frontLeft.set(60);
			Robot.oi.frontRight.set(60);
	}
	@Override
	protected void execute() {
		double angle = Robot.oi.gyro.getAngle();
		long elapsed = (System.currentTimeMillis() - startTime)/1000;
//		if(angle > 0.1) //veering to the right
//		{
//			System.out.println("Go left!");
//			Robot.drive.setArcadeSpeed(0.2, 0.750); //go left
//		}
//		else if(angle < -0.1) //veering to the left
//		{
//			System.out.println("Go right!");
//			Robot.drive.setArcadeSpeed(-0.2, 0.750); //go right
//		}
//		else
//		{
//			System.out.println("Go straight!");
//			Robot.drive.setArcadeSpeed(0, 0.750);
//		}
		//SmartDashboard.putNumber("left encoder val11", Robot.oi.frontLeft.getPosition());
		//SmartDashboard.putNumber("right encoder val22", Robot.oi.frontRight.getPosition());
		
		SmartDashboard.putNumber("left speed", Robot.oi.frontLeft.getSpeed());
		SmartDashboard.putNumber("left position", Robot.oi.frontLeft.getPosition());
		
//		if(Robot.oi.frontLeft.getPosition()<=2.7){
//			Robot.oi.frontLeft.set(200);
//			Robot.oi.frontRight.set(200);
//		}
		if(Robot.oi.frontLeft.getPosition()>=2.7){
			Robot.oi.frontLeft.set(0);
			lAtz = true;
		}
		if(Robot.oi.frontRight.getPosition()<=-2.7){
			Robot.oi.frontRight.set(0);
			rAtz = true;
		}
		if(Robot.oi.frontLeft.getPosition()>=2.2&&Robot.oi.frontLeft.getPosition()<=2.7){
			Robot.oi.frontLeft.set(10);
			
		}
		if(Robot.oi.frontRight.getPosition()<=-2.2&&Robot.oi.frontRight.getPosition()>=-2.7){
			Robot.oi.frontRight.set(10);
		}
		if(lAtz && rAtz) {
			boolean finshed=Rotateright();
			if(finshed==true){
				 
			}
		}
		
		SmartDashboard.putNumber("Auto Elapsed", elapsed);
		SmartDashboard.putNumber("Gyro angle", angle);
	} 

	boolean Rotateright(){
		Robot.oi.frontLeft.set(10);
		Robot.oi.frontRight.set(-10);
		double angle = Robot.oi.gyro.getAngle();
		if(angle >=90){
			Robot.oi.frontLeft.set(0);
			Robot.oi.frontRight.set(0);
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	protected boolean isFinished() {
		return System.currentTimeMillis() -startTime >= 15000; 
	}

	@Override
	protected void end() {
		Robot.drive.setArcadeSpeed(0, 0);
	}

	@Override
	protected void interrupted() {
	}
}
