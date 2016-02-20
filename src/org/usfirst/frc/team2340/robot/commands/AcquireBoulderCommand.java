package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class AcquireBoulderCommand extends Command {
	private Joystick controller;
	public AcquireBoulderCommand(){
		requires(Robot.acquisition);
	}
	@Override
	protected void initialize() {
		controller = Robot.oi.acquisitionController;
	}

	@Override
	protected void execute() {
		double pwrlvl = 1;
		if(controller.getRawButton(2)){ //TODO: magic number
			pwrlvl = .5;
		}
		if(controller.getTrigger()){
			Robot.acquisition.setBoulderMotor(pwrlvl);
		}
		else if(controller.getRawButton(7)){ //TODO: magic number, also it is already being used
			Robot.acquisition.setBoulderMotor(-1 * pwrlvl);
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
