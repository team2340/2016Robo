package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;
import org.usfirst.frc.team2340.robot.subsystems.TalonDataUtils;

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
    Robot.drive.changeMode(TalonDataUtils.GetVbusData());
  }
  
  @Override
  protected void execute() {
    double angle = Robot.oi.gyro.getAngle();
    long elapsed = (System.currentTimeMillis() - startTime)/1000;
    
    SmartDashboard.putNumber("Auto Elapsed", elapsed);
    SmartDashboard.putNumber("Gyro angle", angle);
    
    while(!adjustRotation())
    {
      System.out.println("Adjusting...");
    }
  }
  
  private boolean adjustRotation()
  {
    if(SmartDashboard.getNumber("CenterX") > 170) { //+rotate: go left
      Robot.drive.setArcadeSpeed(0.25, 0);
      
    }
    else if(SmartDashboard.getNumber("CenterX") < 150){ //-rotate: go right
      
      Robot.drive.setArcadeSpeed(-0.25, 0);
      
    }
    else{
      System.out.println("Good Enough!");
      return true;
    }
    
    return false;
  }
  
  @Override
  protected boolean isFinished() {
    return System.currentTimeMillis() -startTime >= 15000; 
  }

  @Override
  protected void end() {
    Robot.drive.changeMode(TalonDataUtils.GetVbusData());
    Robot.drive.setArcadeSpeed(0, 0);
  }

  @Override
  protected void interrupted() {
  }
}
