
package org.usfirst.frc.team2340.robot;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2340.robot.RobotUtils.AutoMode;
import org.usfirst.frc.team2340.robot.commands.AutoDriveForward;
import org.usfirst.frc.team2340.robot.commands.CameraCommand;
import org.usfirst.frc.team2340.robot.subsystems.*;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

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
  CameraCommand cameraCommand = null;
  SendableChooser<AutoMode> autoMode = new SendableChooser<AutoMode>();
  
  private static final int IMG_WIDTH = 320;
  private static final int IMG_HEIGHT = 240;
  private VisionThread visionThread;
  GripPipeline grip;
  private double centerX = 0.0;
  private final Object imgLock = new Object();

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

    cameraCommand = new CameraCommand();
  }

  public void disabledInit(){
  }

  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  public void autonomousInit() {
    cameraCommand.start();

    oi.gyro.reset();
    Robot.oi.frontRight.setPosition(0);
    Robot.oi.frontLeft.setPosition(0);
    AutoMode am = (AutoMode) autoMode.getSelected();

    if (am == AutoMode.DRIVE_FORWARD) {
      autonomousCommand = new AutoDriveForward();
      if (autonomousCommand != null) autonomousCommand.start();
    }
    else if (am == AutoMode.DISABLED) {} //Do Nothing if disabled

    if (autonomousCommand != null) autonomousCommand.start();
    
SmartDashboard.putNumber("CenterX", 0);
    
    grip = new GripPipeline();
    
    UsbCamera camera = cameraCommand.getCurCam();
    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    
    
    visionThread = new VisionThread(camera,grip, grip -> {
      if(!grip.filterContoursOutput().isEmpty()){
        ArrayList<MatOfPoint> contours = grip.filterContoursOutput();
        ArrayList<MatOfPoint> targets = new ArrayList<MatOfPoint>();
        for(MatOfPoint point : contours){
          double expectedRation = 2.54;
          double tolerance = 2;
          Rect r = Imgproc.boundingRect(point);
          double ration = r.height/r.width;

          if(ration < expectedRation + tolerance && ration > expectedRation - tolerance){
            targets.add(point);
          }
        }

        if(targets.size() == 2){
          Rect r = Imgproc.boundingRect(grip.filterContoursOutput().get(0));

          Rect q = Imgproc.boundingRect(grip.filterContoursOutput().get(1));
          synchronized(imgLock){
            centerX = (r.x + (r.width/2) + q.x + (q.width/2))/2.0;
          }
        }
        SmartDashboard.putNumber("CenterX", centerX);
      }
    });
    visionThread.start();
  }

  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  public void teleopInit() {
    if (autonomousCommand != null) autonomousCommand.cancel();
  }

  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  public void testInit() {
    cameraCommand.start();
  }

  public void testPeriodic() {
    LiveWindow.run();
  }
}
