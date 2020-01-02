def cat =ScriptingEngine.gitScriptRun(	"https://github.com/OperationSmallKat/SmallKat_V2.git", 
								"loadRobot.groovy", 
["https://github.com/OperationSmallKat/greycat.git",
		"MediumKat.xml","GameController_22","hidDevice"]);
println "Cat loaded, searching for game controller"
def gameController =null
try{
	 gameController = ScriptingEngine.gitScriptRun(
	            "https://gist.github.com/e26c0d8ef7d5283ef44fb22441a603b8.git", // git location of the library
	            "LoadGameController.groovy" , // file to load
	            // Parameters passed to the function
	            ["GameController_22"]
	            );
// Changed ubstream
}catch (Exception ex){
	ex.printStackTrace()
	return
}
if(gameController==null){
	println "Exiting script"
	return 
}
int [] data = gameController.getData() 
double toSeconds=0.02//100 ms for each increment
println "Starting controller loop..."
while (!Thread.interrupted() ){
	Thread.sleep((long)(toSeconds*1000))
	data = gameController.getData() 
	//println data
	double xdata = data[3]
	double rzdata = data[2]
	double rxdata = data[0]
	double rydata = data[1]
	/*
	if(xdata<0)
		xdata+=256
	if(rzdata<0)
		rzdata+=256
	if(rxdata<0)
		rxdata+=256
	if(rydata<0)7
		rydata+=256
		*/
	double scale = 0.15
	double displacement = 40*(scale*xdata/255.0-scale/2)
	double displacementY =-10*(scale*rxdata/255.0-scale/2)
	
	double rot =((scale*2.0*rzdata/255.0)-scale)*-2.5
	double rotx =((rxdata/255.0)-scale/2)*5
	double roty =((rydata/255.0)-scale/2)*-5
	if(Math.abs(displacement)<0.1 ){
		displacement=0
	}
	if( Math.abs(rot)<0.1){
		rot=0
	}
	try{
		if(Math.abs(displacement)>0.16 || Math.abs(rot)>0.16 ||Math.abs(displacementY)>0.16  ){
			println "displacement "+displacement+" rot "+rot+" straif = "+displacementY
			
			
			TransformNR move = new TransformNR(displacement,displacementY,0,new RotationNR(rotx,rot,roty))
			cat.DriveArc(move, toSeconds);
		}
		if(Math.abs(rotx)>2 || Math.abs(roty)>2){
			//println "tilt "+rotx+" rot "+roty
			TransformNR move = new TransformNR(displacement,displacementY,0,new RotationNR(rotx,0,roty))
			//cat.getWalkingDriveEngine().pose(move)
		}

	}catch(Throwable t){
		
		BowlerStudio.printStackTrace(t)
		
	}
	
}
