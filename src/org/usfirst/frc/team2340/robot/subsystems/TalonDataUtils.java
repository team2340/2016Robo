package org.usfirst.frc.team2340.robot.subsystems;

import com.ctre.CANTalon;

public class TalonDataUtils {
  public static TalonData GetPositionData() {
    return new TalonData(CANTalon.TalonControlMode.Position, 
        CANTalon.FeedbackDevice.QuadEncoder, 250, 2.0, 0.0001, 0.0, 0.0, 12.0);
  }
  
  public static TalonData GetFollowerData(int _id) {
    return new TalonData(CANTalon.TalonControlMode.Follower, _id);
  }
  
  public static TalonData GetVbusData() {
    return new TalonData();
  }
}
