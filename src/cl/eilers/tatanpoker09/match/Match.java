package cl.eilers.tatanpoker09.match;

public class Match {
	public static boolean hasStarted = false;
	public static boolean hasEnded = false;
	public static String getMatchStatus(){
		if(hasEnded){
			return "END";
		}else if(hasStarted==false){
			return "PREMATCH";
		}else if(hasStarted){
			return "PLAYING";
		}
		return "ERROR";
	}
	
	public static void endMatch(){
		hasStarted = false;
		hasEnded = true;
	}
	public static void startMatch(){
		hasEnded = false;
		hasStarted = true;
	}
}