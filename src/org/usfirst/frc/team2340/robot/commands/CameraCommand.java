package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class CameraCommand extends Command {
	
	int currSession, sessionfront, sessionback;
	Image frame;
	boolean exist1, exist2;

	Joystick controller;
	
	public CameraCommand(){
		controller = Robot.oi.driveController;
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		exist1 = true;
		exist2 = true;
		try{
			sessionfront = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		}catch(Exception e){
			exist1 = false;
		}
        try{
		sessionback = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        }catch(Exception e){
        	exist2 = false;
        }
        if(exist1 && exist2){
			currSession = sessionfront;
		NIVision.IMAQdxConfigureGrab(currSession);
		try{
			NIVision.IMAQdxStartAcquisition(currSession);
			} catch (Exception e){
				e.printStackTrace();
			}
        }
	}
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		try{
			if(controller.getRawButton(7)){ //TODO: don't use magic number, put 7 somewhere (OI class)
				switchView();
			}
			NIVision.IMAQdxGrab(currSession, frame, 1);
			CameraServer.getInstance().setImage(frame);
		}catch(Exception e){
			boolean thing = true;
			exist1 = exist2 = false;
			try{
				switchView(thing);
			}catch(Exception f){}
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		if(exist1 && exist2){
			NIVision.IMAQdxStopAcquisition(currSession);
		    NIVision.IMAQdxCloseCamera(sessionback);
		    NIVision.IMAQdxCloseCamera(sessionfront);
		}
	}

	@Override
	protected void interrupted() {
		if(exist1 && exist2){
			NIVision.IMAQdxStopAcquisition(currSession);
		    NIVision.IMAQdxCloseCamera(sessionback);
		    NIVision.IMAQdxCloseCamera(sessionfront);
		}
	}
	public void switchView(){
		if(currSession == sessionfront){
			NIVision.IMAQdxStopAcquisition(currSession);
			currSession = sessionback;
			NIVision.IMAQdxConfigureGrab(currSession);
		} else if(currSession == sessionback){
			NIVision.IMAQdxStopAcquisition(currSession);
			currSession = sessionfront;
			NIVision.IMAQdxConfigureGrab(currSession);
		}
	}
	public void switchView(boolean forceChange){
		if(currSession == sessionfront){
			if(!forceChange){
				NIVision.IMAQdxStopAcquisition(currSession);
			}
			currSession = sessionback;
			NIVision.IMAQdxConfigureGrab(currSession);
		} else if(currSession == sessionback){
			if(!forceChange){
				NIVision.IMAQdxStopAcquisition(currSession);
			}
			currSession = sessionfront;
			NIVision.IMAQdxConfigureGrab(currSession);
		}
	}

}
