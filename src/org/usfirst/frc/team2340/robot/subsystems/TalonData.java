package org.usfirst.frc.team2340.robot.subsystems;

import com.ctre.CANTalon;

public class TalonData {
  public CANTalon.TalonControlMode mode;
  public int profile;
  public double P;
  public double I;
  public double D;
  public double F;
  public double nomOutV;
  public double peakOutV;
  public int encoderCPerR;
  public CANTalon.FeedbackDevice feedType;
  public int followerId;
  public boolean inverted;

  //Default Constructor
  public TalonData(CANTalon.TalonControlMode _mode, int _profile, CANTalon.FeedbackDevice _feedType, int _encoderCPerR,
      double _P, double _I, double _D, double _F, double _nomOutV, double _peakOutV, int _followerId, boolean _inverted)
  {
    mode = _mode;
    feedType = _feedType;
    encoderCPerR = _encoderCPerR;
    P = _P;
    I = _I;
    D = _D;
    nomOutV = _nomOutV;
    peakOutV = _peakOutV;

    followerId = _followerId;
    F = _F;
    profile = _profile;
    inverted = _inverted;
  }

  //Encoder Constructor
  public TalonData(CANTalon.TalonControlMode _mode, CANTalon.FeedbackDevice _feedType, int _encoderCPerR,
      double _P, double _I, double _D, double _nomOutV, double _peakOutV)
  {
    this(_mode, 0, _feedType, _encoderCPerR, _P, _I, _D, 0.0, _nomOutV, _peakOutV, 0, false);
  }

  //Follower Constructor
  public TalonData(CANTalon.TalonControlMode _mode, int _followerId)
  {
    this(_mode, 0, null, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, _followerId, false);
  }

  //Default CanTalon Constructor (PercentVBus Mode)
  public TalonData()
  {
    this(CANTalon.TalonControlMode.PercentVbus, 0, null, 0, 0, 0, 0, 0, 0, 12, 0, false);
  }
}