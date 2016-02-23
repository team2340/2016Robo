package org.usfirst.frc.team2340.robot.commands;

import org.usfirst.frc.team2340.robot.Robot;
import org.usfirst.frc.team2340.robot.RobotMap;

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
		exist1 = false;
		exist2 = false;
		for(int i = 0; i < 6; i++){
			try{
				if(exist1 == false){
					sessionfront = NIVision.IMAQdxOpenCamera("cam" + Integer.toString(i), NIVision.IMAQdxCameraControlMode.CameraControlModeController);
					exist1 = true;
					System.out.println("cam" + Integer.toString(i) + " found");
				}else{
					sessionback = NIVision.IMAQdxOpenCamera("cam" + Integer.toString(i), NIVision.IMAQdxCameraControlMode.CameraControlModeController);
					exist2 = true;
					System.out.println("cam" + Integer.toString(i) + " found");
					break;
				}
			}catch(Exception e){
				System.out.println("No cam" + Integer.toString(i) + "! " + e);
			}
		}
//		try{
//			sessionfront = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
//		}catch(Exception e){
//			System.out.println("No Camera 1! " + e);
//			exist1 = false;
//		}
//        try{
//        	sessionback = NIVision.IMAQdxOpenCamera("cam2", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
//        }catch(Exception e){
//        	System.out.println("No Camera 2! "+ e);
//        	exist2 = false;
//        }
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
			if(controller.getRawButton(RobotMap.BUTTON_7)){ 
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
