// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  int i_left_leader = 1, i_left_follower = 2, i_right_leader = 15, i_right_follower = 16;

  CANSparkMax left_leader = new CANSparkMax(i_left_leader, MotorType.kBrushless);
  CANSparkMax left_follower = new CANSparkMax(i_left_follower, MotorType.kBrushless);
  CANSparkMax right_leader = new CANSparkMax(i_right_leader, MotorType.kBrushless);
  CANSparkMax right_follower = new CANSparkMax(i_right_follower, MotorType.kBrushless);

  PS4Controller driver = new PS4Controller(0);
  PS4Controller operator = new PS4Controller(1);

  Timer autoTimer = new Timer();

  @Override
  public void robotInit() {
    left_follower.follow(left_leader);
    right_follower.follow(right_leader);

    left_leader.setInverted(false);
    right_leader.setInverted(true);
  }

  @Override
  public void autonomousInit() {
    autoTimer.restart();
  }

  @Override
  public void autonomousPeriodic() {
    if (autoTimer.get() < 8) {// seconds
    left_leader.set(0.1);
    right_leader.set(0.1);
    } else {
      left_leader.set(0);
      right_leader.set(0);
    }
  }

  @Override
  public void teleopPeriodic() {
    double speed = driver.getLeftY();
    double rotation = driver.getRightX();
    double left = speed + rotation;
    double right = speed - rotation;

    if (left > 1 || left < -1) { right = right / Math.abs(left); left = left / Math.abs(left); }
    if (right > 1 || right < -1) { left = left / Math.abs(right); right = right / Math.abs(right); }

    left_leader.set(left);
    right_leader.set(right);
  }
}