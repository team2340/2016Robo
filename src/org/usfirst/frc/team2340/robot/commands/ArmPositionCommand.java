package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ArmPositionCommand extends Command {
	private Joystick controller;
	
	public ArmPositionCommand(){
		requires(Robot.acquisition);
	}
	
	@Override
	protected void initialize() {
		controller = Robot.oi.acquisitionController;
	}

	@Override
	protected void execute() {
		if(controller.getRawButton(5)){ //TODO: magic number
			System.out.println("Button five pressed~~");
			Robot.acquisition.setArmPower(1); //this is for forward
		}
		else if(controller.getRawButton(6)){ //TODO: magic number
			System.out.println("Button six pressed~");			
			Robot.acquisition.setArmPower(-1); //this is for back
		}
		else{
			System.out.println("Button none pressed~");
			Robot.acquisition.setArmPower(0); //this should stop it
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
