package org.usfirst.frc.team2340.robot.subsystems;

import org.usfirst.frc.team2340.robot.Robot;
import org.usfirst.frc.team2340.robot.RobotMap;
import org.usfirst.frc.team2340.robot.commands.AcquisitionCommandGroup;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AcquisitionSubsystem extends Subsystem {
	static private AcquisitionSubsystem subsystem;
	
	private AcquisitionSubsystem() {
		createAQwheels();
		createArm();
	}

	public static AcquisitionSubsystem getInstance() {
		if (subsystem == null) {
			subsystem = new AcquisitionSubsystem();
		}
		return subsystem;
	}

	private void createArm() {
		try {
			Robot.oi.arm = new CANTalon(RobotMap.ARM_TAL_ID);
		} catch (Exception ex) {
			System.out.println(" crateArm FAILED");
		}
	}

	private void createAQwheels() {
		try {
			Robot.oi.aqWheels = new CANTalon(RobotMap.AQ_WHEELS_TAL_ID);
		} catch (Exception ex) {
			System.out.println(" createAQwheels FAILED");
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new AcquisitionCommandGroup());
	}
	
	public CANTalon getArmTalon(){
		return Robot.oi.arm;
	}

	public void setArmPower(int power){
		Robot.oi.arm.set(power);
	}
	
	public void setBoulderMotor(double speed){
		Robot.oi.aqWheels.set(speed); //TODO: is this right? I changed it from arm to aqWheels
	}
	
	public void open2(){
	}
	
	public void close2(){
	}
}
