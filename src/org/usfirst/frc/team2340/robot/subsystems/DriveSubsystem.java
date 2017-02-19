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
	public double centerX;
	public double finalDistance;
	public double speedP = 7.0;
	public double speedI = 0.005;
	public double speedD = 0.0;
	public double speedF = 0.0;
	public double speedMaxOutput = 350;
	public double speedPeakOutputVoltage = 12.0f;
	
	public double positionP = 2.0;
	public double positionI = .0001;
	public double positionD = 0.0;
	public double positionF = 0.0;
	public float positionPeakOutputVoltage = 5.0f;
	
	public double vBusMaxOutput = 1.0;

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
		centerX = -1;
		finalDistance = 0.0;
		createLeftSide();
		createRightSide();
		setForPosition();
		robotDrive = new RobotDrive(Robot.oi.frontLeft, Robot.oi.frontRight);
		robotDrive.setSafetyEnabled(false);
	}

	private void createLeftSide() {
		try {
			Robot.oi.frontLeft = new CANTalon(RobotMap.FRONT_LEFT_TAL_ID);
			Robot.oi.frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
			Robot.oi.frontLeft.reverseSensor(true);
			Robot.oi.frontLeft.configEncoderCodesPerRev(250);
			Robot.oi.frontLeft.configNominalOutputVoltage(+0.0f, -0.0f);
		    Robot.oi.frontLeft.setProfile(0);
//			Robot.oi.frontLeft.configNominalOutputVoltage(+0.0f, -0.0f);
//			Robot.oi.frontLeft.configPeakOutputVoltage(+7.0f, -7.0f);
//			Robot.oi.frontLeft.setProfile(0);
		    
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
			
			Robot.oi.frontRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
			Robot.oi.frontRight.reverseSensor(true);
			Robot.oi.frontRight.configEncoderCodesPerRev(250);
			Robot.oi.frontRight.configNominalOutputVoltage(+0.0f, -0.0f);
		    Robot.oi.frontRight.setProfile(0);
			
//			Robot.oi.frontRight.configNominalOutputVoltage(+0.0f, -0.0f);
//			Robot.oi.frontRight.configPeakOutputVoltage(+7.0f, -7.0f);
//			Robot.oi.frontRight.setProfile(0););
			//Robot.oi.frontRight.setInverted(true);
			
			
//			Robot.oi.backRight = new CANTalon(RobotMap.BACK_RIGHT_TAL_ID);
//			Robot.oi.backRight.changeControlMode(CANTalon.TalonControlMode.Follower);
//			Robot.oi.backRight.set(RobotMap.FRONT_RIGHT_TAL_ID);
		} catch (Exception ex) {
			System.out.println("createRightSide FAILED");
		}
	}
	
	public void setForSpeed() {
		Robot.oi.frontLeft.changeControlMode(CANTalon.TalonControlMode.Speed);
		Robot.oi.frontRight.changeControlMode(CANTalon.TalonControlMode.Speed);
		Robot.oi.frontRight.setF(speedF);
	    Robot.oi.frontRight.setP(speedP);
	    Robot.oi.frontRight.setI(speedI); 
	    Robot.oi.frontRight.setD(speedD);
	    Robot.oi.frontLeft.setF(speedF);
	    Robot.oi.frontLeft.setP(speedP);  
	    Robot.oi.frontLeft.setI(speedI);  
	    Robot.oi.frontLeft.setD(speedD);
	    robotDrive.setMaxOutput (speedMaxOutput);
	    Robot.oi.frontLeft.configPeakOutputVoltage(speedPeakOutputVoltage, -speedPeakOutputVoltage);
	    Robot.oi.frontRight.configPeakOutputVoltage(speedPeakOutputVoltage, -speedPeakOutputVoltage);	
	}
	
	public void setForPosition() {
		Robot.oi.frontLeft.changeControlMode(CANTalon.TalonControlMode.Position);
		Robot.oi.frontRight.changeControlMode(CANTalon.TalonControlMode.Position);
		Robot.oi.frontRight.setF(positionF);
	    Robot.oi.frontRight.setP(positionP);
	    Robot.oi.frontRight.setI(positionI); 
	    Robot.oi.frontRight.setD(positionD);
	    Robot.oi.frontLeft.setF(positionF);
	    Robot.oi.frontLeft.setP(positionP);  
	    Robot.oi.frontLeft.setI(positionI);  
	    Robot.oi.frontLeft.setD(positionD);   
	    Robot.oi.frontLeft.configPeakOutputVoltage(positionPeakOutputVoltage, -positionPeakOutputVoltage);
	    Robot.oi.frontRight.configPeakOutputVoltage(positionPeakOutputVoltage, -positionPeakOutputVoltage);
	    Robot.oi.frontRight.setPosition(0);
	    Robot.oi.frontLeft.setPosition(0);
	    
	}
	
	public void setForVBus() {
		Robot.oi.frontLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        Robot.oi.frontRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        robotDrive.setMaxOutput (vBusMaxOutput);
		setArcadeSpeed(0,0);
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
