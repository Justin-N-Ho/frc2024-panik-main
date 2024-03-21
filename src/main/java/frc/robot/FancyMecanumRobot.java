package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class FancyMecanumRobot extends TimedRobot {
  int //i_left_leader = 1, i_left_follower = 2, i_right_leader = 15, i_right_follower = 16,
      i_top_left_leader = 1, i_top_left_follower = 2, i_bottom_left_leader = 5, i_bottom_left_follower = 6, //TODO placeholders, replace
      i_top_right_leader = 15, i_top_right_follower = 16, i_bottom_right_leader = 11, i_bottom_right_follower = 10, //TODO placeholders, replace
      i_arm_follower_left = 3, i_arm_leader_right = 14, 
      i_shooter_follower = 4, i_shooter_leader = 13, 
      i_intake = 12;

  CANSparkMax top_left_leader = new CANSparkMax(i_top_left_leader, MotorType.kBrushless);
  CANSparkMax top_left_follower = new CANSparkMax(i_top_left_follower, MotorType.kBrushless);
  CANSparkMax bottom_left_leader = new CANSparkMax(i_bottom_left_leader, MotorType.kBrushless);
  CANSparkMax bottom_left_follower = new CANSparkMax(i_bottom_left_follower, MotorType.kBrushless);

  CANSparkMax top_right_leader = new CANSparkMax(i_top_right_leader, MotorType.kBrushless);
  CANSparkMax top_right_follower = new CANSparkMax(i_top_right_follower, MotorType.kBrushless);
  CANSparkMax bottom_right_leader = new CANSparkMax(i_bottom_right_leader, MotorType.kBrushless);
  CANSparkMax bottom_right_follower = new CANSparkMax(i_bottom_right_follower, MotorType.kBrushless);


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
    top_left_leader.restoreFactoryDefaults();
    top_left_follower.restoreFactoryDefaults();
    bottom_left_leader.restoreFactoryDefaults();
    bottom_left_follower.restoreFactoryDefaults();
    top_right_leader.restoreFactoryDefaults();
    top_right_follower.restoreFactoryDefaults();
    bottom_right_leader.restoreFactoryDefaults();
    bottom_right_follower.restoreFactoryDefaults();

    arm_follower.restoreFactoryDefaults();
    arm_leader.restoreFactoryDefaults();

    //shooter_follower.restoreFactoryDefaults();
    //shooter_leader.restoreFactoryDefaults();

    //intake.restoreFactoryDefaults();
    
    

    top_left_follower.follow(top_left_leader);
    bottom_left_follower.follow(bottom_left_leader);
    top_right_follower.follow(top_right_leader);
    bottom_right_follower.follow(bottom_right_leader);
    top_left_leader.setInverted(false);
    bottom_left_leader.setInverted(false);
    top_right_leader.setInverted(true);
    bottom_right_leader.setInverted(true);

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
      top_right_leader.set(0.2);
      bottom_right_leader.set(0.2);
      top_left_leader.set(0.2);
      bottom_left_leader.set(0.2);
    } else {
      top_right_leader.set(-1);
      bottom_right_leader.set(-1);
      top_left_leader.set(1);    
      bottom_left_leader.set(1);
    }
  }

  @Override
  public void teleopInit(){
    MecanumDrive mt = new MecanumDrive(top_left_leader, bottom_left_leader, top_right_leader, bottom_right_leader);
  }

  @Override
  public void teleopPeriodic() {
    double speedVertical = - driver.getLeftY();
    double speedHorizontal = - driver.getLeftX();
    double rotation = - driver.getRightX();
    MecanumDrive.driveCartesianIK(speedVertical, speedHorizontal, rotation);

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
//test