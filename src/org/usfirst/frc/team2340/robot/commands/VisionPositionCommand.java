package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionPositionCommand extends Command {
  long startTime = 0;
  
  public VisionPositionCommand() {
    requires(Robot.drive);
  }
  
  @Override
  protected void initialize() {
    startTime = System.currentTimeMillis();
    Robot.drive.setForSpeed();
  }
  
  @Override
  protected void execute() {
    double angle = Robot.oi.gyro.getAngle();
    long elapsed = (System.currentTimeMillis() - startTime)/1000;
    
    SmartDashboard.putNumber("Auto Elapsed", elapsed);
    SmartDashboard.putNumber("Gyro angle", angle);
    
    if(!adjustRotation())
    {
      System.out.println("Adjusting...");
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
		  else if(Robot.drive.centerX < 300){ //-rotate: go left
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
	  setSpeed(0);
  }

  @Override
  protected void interrupted() {
  }
}
