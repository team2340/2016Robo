package org.usfirst.frc.team2340.robot.subsystems;

import java.util.ArrayList;

import org.usfirst.frc.team2340.robot.Robot;
import org.usfirst.frc.team2340.robot.RobotMap;
import org.usfirst.frc.team2340.robot.commands.ArcadeDriveCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {
	static private DriveSubsystem subsystem;
	RobotDrive robotDrive;
	private ArrayList<CANTalon> talons;

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
	  robotDrive = new RobotDrive(Robot.oi.frontLeft, Robot.oi.frontRight);
	  
	  createTalon(Robot.oi.frontLeft, RobotMap.FRONT_LEFT_TAL_ID, TalonDataUtils.GetPositionData());
	  createTalon(Robot.oi.frontRight, RobotMap.FRONT_RIGHT_TAL_ID, TalonDataUtils.GetPositionData());

	  // Create follower motors;
	  createTalon(Robot.oi.backLeft, RobotMap.BACK_LEFT_TAL_ID, TalonDataUtils.GetFollowerData(RobotMap.FRONT_LEFT_TAL_ID));
	  createTalon(Robot.oi.backRight, RobotMap.BACK_RIGHT_TAL_ID, TalonDataUtils.GetFollowerData(RobotMap.FRONT_RIGHT_TAL_ID));

	  robotDrive.setSafetyEnabled(false);
	}

	private void createTalon(CANTalon _talon, int _id, TalonData _data)
	{
	  try {
  	  if(_talon == null) {
  	    _talon = new CANTalon(_id);
  	    talons.add(_talon);
  	  }
  	  
	    _talon.changeControlMode(_data.mode);
	    
	    if(_data.mode == CANTalon.TalonControlMode.Follower)
	    {
	      _talon.set(_data.followerId);
	    }
	    else
	    {
	      if(_data.feedType != null) Robot.oi.frontLeft.setFeedbackDevice(_data.feedType);
	      Robot.oi.frontLeft.reverseSensor(true);
	      Robot.oi.frontLeft.configEncoderCodesPerRev(_data.encoderCPerR);
	      Robot.oi.frontLeft.configNominalOutputVoltage(_data.nomOutV, -_data.nomOutV);
	      Robot.oi.frontLeft.configPeakOutputVoltage(_data.peakOutV, -_data.peakOutV);
	      Robot.oi.frontLeft.setProfile(_data.profile);
	      Robot.oi.frontLeft.setP(_data.P);
	      Robot.oi.frontLeft.setI(_data.I);
	      Robot.oi.frontLeft.setD(_data.D);
	      Robot.oi.frontLeft.setF(_data.F);
	    }
	  } catch (Exception ex) {
	    System.out.println("creating talon id "+_id+" FAILED");
	  }
	}
	
	public void changeMode(TalonData _data) {
	  for(CANTalon talon : talons) {
	    if(talon.getControlMode() != CANTalon.TalonControlMode.Follower) {
	      changeMode(_data, talon);
	    }
	  }
	}
	
	public void changeMode(TalonData _data, CANTalon _talon) {
	  createTalon(_talon, _talon.getDeviceID(), _data);
	  if(_data.mode == CANTalon.TalonControlMode.PercentVbus) {
	    robotDrive.setMaxOutput(RobotDrive.kDefaultMaxOutput);
	  }
	  //else { //if(_data.mode == CANTalon.TalonControlMode.Position) {
      //robotDrive.setMaxOutput(350);
    //}
	}
	
	public void setBrakeMode(boolean brake) {
		Robot.oi.frontRight.enableBrakeMode(brake);
		Robot.oi.backRight.enableBrakeMode(brake);
		Robot.oi.frontLeft.enableBrakeMode(brake);
		Robot.oi.backLeft.enableBrakeMode(brake);
	}
	
	public void setArcadeSpeed(double rotation, double speed){
		robotDrive.arcadeDrive(speed, rotation);
	}
}
