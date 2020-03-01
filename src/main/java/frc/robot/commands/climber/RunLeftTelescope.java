package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LeftClimberSubsystem;

import static frc.robot.lib.controllers.FightStick.*;

public class RunLeftTelescope extends CommandBase {
    LeftClimberSubsystem leftClimber;

    public RunLeftTelescope(LeftClimberSubsystem leftClimber) {
        this.leftClimber = leftClimber;
        addRequirements(leftClimber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (POVCenter.get()) {
            leftClimber.leftTelescopeStop();
        }
        if (POVUp.get()) {
            leftClimber.leftTelescopeUp();
        }
        if (POVDown.get()) {
            leftClimber.leftTelescopeDown();
        }

    }

    public boolean isFinished() {
        return leftClimber.getEncoderValue() < -250;
    }


    public void end() {
        leftClimber.leftTelescopeStop();
    }

}