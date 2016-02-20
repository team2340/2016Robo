package org.usfirst.frc.team2340.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AcquisitionCommandGroup extends CommandGroup {
	public AcquisitionCommandGroup() {
		addSequential(new ArmPositionCommand());
		addSequential(new AcquireBoulderCommand());
		
	}

}
