
package org.usfirst.frc.team2340.robot;

import org.usfirst.frc.team2340.robot.RobotUtils.AutoMode;
import org.usfirst.frc.team2340.robot.commands.AutoDriveForward;
import org.usfirst.frc.team2340.robot.commands.AutoMoat;
import org.usfirst.frc.team2340.robot.commands.CameraCommand;
import org.usfirst.frc.team2340.robot.subsystems.*;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static final OI oi = new OI();
	public static final DriveSubsystem drive = DriveSubsystem.getInstance();
	public static final AcquisitionSubsystem acquisition  =  AcquisitionSubsystem.getInstance();
	
    Command autonomousCommand = null;
    Command cameraCommand = null;
    SendableChooser autoMode = new SendableChooser();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        oi.gyro = new ADXRS450_Gyro();
        
        autoMode.addDefault("DriveForward", AutoMode.DRIVE_FORWARD);
		autoMode.addObject("Disabled", AutoMode.DISABLED);
		autoMode.addObject("Moat", AutoMode.MOAT);
        SmartDashboard.putData("Auto mode", autoMode);
    }
	
    public void disabledInit(){
    	if(cameraCommand != null){
    		cameraCommand.cancel();
		}
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        oi.gyro.reset();
        Robot.oi.frontRight.setPosition(0);
        Robot.oi.frontLeft.setPosition(0);
		AutoMode am = (AutoMode) autoMode.getSelected();
		
		if (am == AutoMode.DRIVE_FORWARD) {
			autonomousCommand = new AutoDriveForward();
			if (autonomousCommand != null) autonomousCommand.start();
		}
		else if(am == AutoMode.MOAT){
			autonomousCommand = new AutoMoat();
			if (autonomousCommand != null) autonomousCommand.start();
		}
		else if (am == AutoMode.DISABLED) {} //Do Nothing if disabled
		
		autonomousCommand = new AutoMoat();
		autonomousCommand = new AutoDriveForward();
		if (autonomousCommand != null) autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        cameraCommand = new CameraCommand();
		cameraCommand.start();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
