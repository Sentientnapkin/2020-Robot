package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.MechanismConstants.*;

public class RightClimberSubsystem extends SubsystemBase {

    private final CANSparkMax rightTelescope = new CANSparkMax(rightTelescopeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rightWinch  = new CANSparkMax(rightWinchMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);

    public RightClimberSubsystem() { }

    public void rightTelescopeUp() { rightTelescope.set(1); }
    public void rightTelescopeDown() { rightTelescope.set(-1); }
    public void rightTelescopeStop() { rightTelescope.set(0); }

    public void rightWinchExtend() { rightWinch.set(1); }
    public void rightWinchRetract() { rightWinch.set(-1); }
    public void rightWinchStop() { rightWinch.set(0); }

}
