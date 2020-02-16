package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.ArrayDeque;
import java.util.Deque;

public class AutoDriveForwardUltrasonic extends CommandBase {

    private DrivetrainSubsystem drivetrain;


    //Ultrasonic PID
    private PIDController pid = new PIDController(0.05, 0.0, 0.01); // 0.39, 0.0, 0.01
    private long startTime;
    private double tolerance = 1;
    private double trapezoidTime = 1000;
    private double distancefromwall;
    private double encoderSetPoint;

    public AutoDriveForwardUltrasonic(DrivetrainSubsystem drivetrain, double distancefromwall) {
        this.drivetrain = drivetrain;
        this.distancefromwall = distancefromwall;
        pid.setTolerance(0.5);
        addRequirements(drivetrain);
    }

    public void initialize() {
        //Custom Trapizoid Initilizations
        startTime = System.currentTimeMillis();

        //Setpoint for Ultranoic PID
        double setpoint = distancefromwall;
        pid.setSetpoint(setpoint);

        //Setpoint for Encoder PID
        encoderSetPoint = drivetrain.getRightEncoderDistance()-drivetrain.getLeftEncoderDistance();

    }

    public void execute() {

        long elapsedTime = System.currentTimeMillis() - startTime;
        double power;
        //PID calculations
        if(elapsedTime <= trapezoidTime){
            power = Math.min(Math.abs(pid.calculate(drivetrain.getAverageUltrasonicDistance())*(elapsedTime/trapezoidTime)), 0.4);
        }
        else{
            power = Math.min(Math.abs(pid.calculate(drivetrain.getAverageUltrasonicDistance())), 0.4);
        }
        //System.out.println("Right: "+drivetrain.RightEncoderCorrection(encoderSetPoint));
        //System.out.println("Left: "+drivetrain.LeftEncoderCorrection(encoderSetPoint));

        drivetrain.tankDrive(power + drivetrain.LeftEncoderCorrection(encoderSetPoint),
                power + drivetrain.RightEncoderCorrection(encoderSetPoint));
    }

    public boolean isFinished() {
        //Check if ultrasonic values are within tolerance to stop
        if(drivetrain.getUltrasonicDistance() <= distancefromwall+tolerance && drivetrain.getUltrasonicDistance() >= distancefromwall-tolerance){
            return true;
        }
        return false;
    }

    public void end(boolean interrupted) {
        //Set speed to 0 after command
        drivetrain.tankDrive(0, 0);
        System.out.println("Reached setpoint");
    }

}