package org.usfirst.frc.team2340.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick driveController = new Joystick(RobotMap.DRIVE_PORT);
	public Joystick acquisitionController = new Joystick(RobotMap.ACQUISITION_PORT);
	public ADXRS450_Gyro gyro = null;
   
	public CANTalon frontLeft = null;
	public CANTalon backLeft = null;
	public CANTalon backRight = null;
	public CANTalon frontRight = null;
	
	public CANTalon arm = null;
	public CANTalon aqWheels = null;
	
    
    ////CREATING BUTTONS
   // One type of button is a joystick button which is any button on a joystick.
   // You create one by telling it which joystick it's on and which button
   // number it is.
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
