package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class FancyFeastRobot extends TimedRobot {
  int i_left_leader = 1, i_left_follower = 2, i_right_leader = 15, i_right_follower = 16,
      i_arm_follower_left = 3, i_arm_leader_right = 14, 
      i_shooter_follower = 4, i_shooter_leader = 13, 
      i_intake = 12;

  CANSparkMax left_leader = new CANSparkMax(i_left_leader, MotorType.kBrushless);
  CANSparkMax left_follower = new CANSparkMax(i_left_follower, MotorType.kBrushless);
  CANSparkMax right_leader = new CANSparkMax(i_right_leader, MotorType.kBrushless);
  CANSparkMax right_follower = new CANSparkMax(i_right_follower, MotorType.kBrushless);

  CANSparkMax arm_leader = new CANSparkMax(i_arm_leader_right, MotorType.kBrushless);
  CANSparkMax arm_follower = new CANSparkMax(i_arm_follower_left, MotorType.kBrushless);

  /*CANSparkMax shooter_leader = new CANSparkMax(i_shooter_leader, MotorType.kBrushless);
  CANSparkMax shooter_follower = new CANSparkMax(i_shooter_follower, MotorType.kBrushless);

  CANSparkMax intake = new CANSparkMax(i_intake, MotorType.kBrushless);*/

  DifferentialDrive dt;

  PS4Controller driver = new PS4Controller(0);
  PS4Controller operator = new PS4Controller(1);

  Timer autoTimer = new Timer();

  @Override
  public void robotInit() {
    left_leader.restoreFactoryDefaults();
    right_leader.restoreFactoryDefaults();
    left_follower.restoreFactoryDefaults();
    right_follower.restoreFactoryDefaults();

    arm_follower.restoreFactoryDefaults();
    arm_leader.restoreFactoryDefaults();

    //shooter_follower.restoreFactoryDefaults();
    //shooter_leader.restoreFactoryDefaults();

    //intake.restoreFactoryDefaults();
    
    

    left_follower.follow(left_leader);
    right_follower.follow(right_leader);
    left_leader.setInverted(false);
    right_leader.setInverted(true);

    arm_follower.follow(arm_leader);
    arm_leader.setInverted(false);
    arm_follower.setInverted(false);

    /*shooter_follower.follow(shooter_leader);
    shooter_leader.setInverted(true);
    shooter_follower.setInverted(true);*/


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
    double speed = - driver.getLeftY();
    double rotation = - driver.getRightX();
    dt.arcadeDrive(speed, rotation, true);

    arm_leader.set(operator.getRightY());

    //hold to speed up/shoot
    if (operator.getR2Button()){
      //shooter_leader.set(0.5);
    }
    //turns on intake to shoot
    if (operator.getR2ButtonReleased()){
      //intake.set(-0.3);
    }
    //intake
    if (operator.getL2Button()){
      //intake.set(0.3);
    }
    //turn off intake and shooter
    if (operator.getL1ButtonPressed()){
     // intake.set(0);
      //shooter_leader.set(0);
    }
    //outtake
    if (operator.getR1ButtonPressed()){
      //intake.set(-0.3);
    }
  }
}