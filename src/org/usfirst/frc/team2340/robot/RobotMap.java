package org.usfirst.frc.team2340.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
//magic numbers~~
	public static final int BUTTON_2 = 2; 
	public static final int BUTTON_7 = 7;
	public static final int BUTTON_3 = 3;
	public static final int BUTTON_4 = 4;
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
	/*the adxl345 gyro pinout is signal pin closest to word "rate" on the pcb, the
	 *ground is closest to the edge of the pcb   */
	public static final int GYRO_CHANNEL = 0;
}
