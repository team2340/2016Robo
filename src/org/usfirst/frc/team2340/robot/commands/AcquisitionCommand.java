package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;
import org.usfirst.frc.team2340.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class AcquisitionCommand extends Command {
	private Joystick controller;
	
	public AcquisitionCommand(){
		requires(Robot.acquisition);
	}
	
	@Override
	protected void initialize() {
		controller = Robot.oi.acquisitionController;
	}

	@Override
	protected void execute() {
		double z = (3-controller.getZ())/2;
		double y = controller.getY()/z;
		Robot.acquisition.setArmPower(y);
		double pwrlvl = 1;
		if(controller.getRawButton(RobotMap.BUTTON_2)){ 
			pwrlvl = .5;
		}
		if(controller.getTrigger()){
			Robot.acquisition.setBoulderMotor(pwrlvl);
		}
		else if(controller.getRawButton(RobotMap.BUTTON_7)){ 
			Robot.acquisition.setBoulderMotor(-1 * pwrlvl);
		}else{
			Robot.acquisition.setBoulderMotor(0);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
