package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoMoat extends Command {
	long startTime = 0;
	
	public AutoMoat(){
		requires(Robot.drive);
	}
	@Override
	protected void initialize() {
		startTime = System.currentTimeMillis();

	}

	@Override
	protected void execute() {
		double angle = Robot.oi.gyro.getAngle();
		long elapsed = (System.currentTimeMillis() - startTime)/1000;
		if(angle > 0.1) //veering to the right
		{
			System.out.println("Go left!");
		//	Robot.drive.setArcadeSpeed(0.2, 1.00); //go left
		}
		else if(angle < -0.1) //veering to the left
		{
			System.out.println("Go right!");
		//	Robot.drive.setArcadeSpeed(-0.2, 1.00); //go right
		}
		else
		{
			System.out.println("Go straight!");
		//	Robot.drive.setArcadeSpeed(0, 1.00);
		}
		Robot.oi.frontLeft.set(10);
		Robot.oi.frontRight.set(10);
		//Robot.drive.setArcadeSpeed(0, .17);
		SmartDashboard.putNumber("Auto Elapsed", elapsed);
		SmartDashboard.putNumber("Gyro angle", angle);
		SmartDashboard.putNumber("left speed", Robot.oi.frontLeft.getSpeed());
		SmartDashboard.putNumber("left position", Robot.oi.frontLeft.getPosition());
		//SmartDashboard.putNumber("left target speed", Robot.oi.frontLeft.get());
		//SmartDashboard.putNumber("right speed", Robot.oi.frontRight.getSpeed());

	}

	@Override
	protected boolean isFinished() {
		return false;
		//return System.currentTimeMillis() -startTime >= 3100; 
	}

	@Override
	protected void end() {
		Robot.drive.setArcadeSpeed(0, 0);

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
