// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.tj
 */

public class Robot extends TimedRobot {
  Compressor compressor = new Compressor(11, PneumaticsModuleType.REVPH); 
  private final WPI_TalonSRX leftBackM = new WPI_TalonSRX(10);
  private final WPI_TalonSRX leftFrontM = new WPI_TalonSRX(11);
  private final WPI_TalonSRX rightBackM = new WPI_TalonSRX(2);
  private final WPI_TalonSRX rightFrontM = new WPI_TalonSRX(5);
  private final XboxController xboxCtr = new XboxController(0);
  
  private final DoubleSolenoid m_Solenoid = 
  new DoubleSolenoid(11, PneumaticsModuleType.REVPH, 2, 0);
  @Override
  public void robotInit() {
    compressor.enableAnalog(90.0, 120.0);
    

  }

  @Override
  public void teleopPeriodic() {
    int turboSpeed;
    if(xboxCtr.getAButton() == true){ // moves pnumatics
      m_Solenoid.set(Value.kForward);
    }
    if(xboxCtr.getBButton() == true){
      m_Solenoid.set(Value.kReverse);
    }
    if(xboxCtr.getXButton() == true){
      compressor.disable();
    }
    if(xboxCtr.getYButton() == true){
      compressor.enableAnalog(90.0, 120.0);
    }
    if(xboxCtr.getRightBumper() == true){
      turboSpeed = 1;
    }
    else{
       turboSpeed = 0;
    }
    double z = xboxCtr.getRightX();
    double y = -xboxCtr.getLeftY();
    if(Math.abs(y) <= .05 && Math.abs(z) <= 0.05){
      leftBackM.set(0);
      leftFrontM.set(0);
      rightFrontM.set(0);
      rightBackM.set(0);
    }
    else if(turboSpeed == 0){
      
     rightFrontM.set((y - (z))*.5); 
     rightBackM.set((y - (z))*.5); 
    leftFrontM.set((y + (z))*.5); 
      leftBackM.set((-y - (z))*.5); 
    }
      else{
        rightFrontM.set(y - (z)); 
        rightBackM.set(y - (z));
       leftFrontM.set(y + (z)); 
         leftBackM.set(-y - (z)); 
    }
  }
}
