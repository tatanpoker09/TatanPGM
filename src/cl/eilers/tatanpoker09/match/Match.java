package cl.eilers.tatanpoker09.match;

public class Match {
	public String getMatchStatus(){
		if(hasEnded()){
			return "END";
		}else if(hasStarted()==false){
			return "PREMATCH";
		}else if(hasStarted()){
			return "PLAYING";
		}
		return "ERROR";
	}
	
	public boolean hasStarted(){
		return true;
	}
	public boolean hasEnded(){
		return true;
	}
	
	public void endMatch(){
		
	}
	public void startMatch(){
		
	}
}