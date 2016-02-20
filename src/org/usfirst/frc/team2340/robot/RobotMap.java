package org.usfirst.frc.team2340.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
/*Controller Ports*/
	public static final int DRIVE_PORT = 1;
	public static final int ACQUISITION_PORT = 2;
	
/*TALON IDs*/
	//Wheel Ids
	public static final int BACK_RIGHT_TAL_ID = 2; //New 3 //Old 2
	public static final int FRONT_RIGHT_TAL_ID = 3; //New 4 //Old 3
	public static final int FRONT_LEFT_TAL_ID = 4;  //New 5 //Old 4
	public static final int BACK_LEFT_TAL_ID = 5; //New 2 //Old 5
	//Acquisition Ids
	public static final int ARM_TAL_ID = 6;
	public static final int AQ_WHEELS_TAL_ID = 7;

/*Analog Channels*/
	public static final int GYRO_CHANNEL = 0;
}
