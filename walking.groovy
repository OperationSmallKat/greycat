
import java.time.Duration;

import java.util.ArrayList;

import javafx.application.Platform;

import org.reactfx.util.FxTimer;

import com.neuronrobotics.sdk.addons.kinematics.DHParameterKinematics;
import com.neuronrobotics.sdk.addons.kinematics.MobileBase;
import com.neuronrobotics.sdk.addons.kinematics.math.RotationNR;
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR;
import com.neuronrobotics.sdk.util.ThreadUtil;
import com.neuronrobotics.sdk.addons.kinematics.IDriveEngine;

double stepOverHeight=7.5;
	long stepOverTime=150;// Servo loop times number of points times Nyquest doubeling
	Double zLock=   9;
	Closure calcHome = { DHParameterKinematics leg -> 
			TransformNR h=leg.calcHome() 
	 		TransformNR  legRoot= leg.getRobotToFiducialTransform()
			TransformNR tr = leg.forwardOffset(new TransformNR())
			tr.setZ(zLock)
			//Bambi-on-ice the legs a bit

			if(legRoot.getY()>0){
				tr.translateY(10)
			}else{
				tr.translateY(-10)
			}

			if(legRoot.getX()>0){
				//tr.translateX(10)
			}
			
			return tr;
	
	}
	boolean usePhysicsToMove = true;
	long stepCycleTime =600
	long walkingTimeout =stepCycleTime*2
	int numStepCycleGroups = 2
	
	double standardHeadTailAngle = -20
	double staticPanOffset = 30
	double coriolisGain = 1.1
	boolean headStable = true
	double maxBodyDisplacementPerStep = 40
	double minBodyDisplacementPerStep = 30
	def ar =  [stepOverHeight,
	stepOverTime,
	zLock,
	calcHome,
	usePhysicsToMove,
	stepCycleTime,
	numStepCycleGroups,
	standardHeadTailAngle,
	staticPanOffset,
	coriolisGain,
	headStable,
	maxBodyDisplacementPerStep,
	minBodyDisplacementPerStep,
	walkingTimeout]

return ScriptingEngine
          .gitScriptRun(
            "https://github.com/OperationSmallKat/SmallKat_V2.git", // git location of the library
            "Bowler/DynamicWalking.groovy" , // file to load
            ar
         )
                       
