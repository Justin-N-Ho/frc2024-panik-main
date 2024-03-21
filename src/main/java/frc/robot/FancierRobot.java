package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class FancierRobot extends TimedRobot {
  int i_left_leader = 2, i_left_follower = 1, i_right_leader = 15, i_right_follower = 16;

  CANSparkMax left_leader = new CANSparkMax(i_left_leader, MotorType.kBrushless);
  //CANSparkMax left_follower = new CANSparkMax(i_left_follower, MotorType.kBrushless);
  CANSparkMax right_leader = new CANSparkMax(i_right_leader, MotorType.kBrushless);
  //CANSparkMax right_follower = new CANSparkMax(i_right_follower, MotorType.kBrushless);

  DifferentialDrive dt;

  PS4Controller driver = new PS4Controller(0);
  PS4Controller operator = new PS4Controller(1);

  Timer autoTimer = new Timer();

  @Override
  public void robotInit() {
    left_leader.restoreFactoryDefaults();
    right_leader.restoreFactoryDefaults();
    //left_follower.follow(left_leader);
    //right_follower.follow(right_leader);

    left_leader.setInverted(false);
    right_leader.setInverted(true);

  }

  @Override
  public void autonomousInit() {
    autoTimer.restart();
  }

  @Override
  public void autonomousPeriodic() {
    if (autoTimer.get() < 6) {// seconds
      right_leader.set(0.2);
      left_leader.set(0.2);
    } else {
      right_leader.set(-1);
      left_leader.set(1);    
    }
  }
  @Override
  public void teleopInit(){
    dt = new DifferentialDrive(left_leader, right_leader);
  }
  @Override
  public void teleopPeriodic() {
    double speed = - driver.getRightY() * 0.7;
    double rotation = - driver.getLeftX() * 0.4;
    dt.curvatureDrive(speed, rotation, true);
  }
}
