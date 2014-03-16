package cl.eilers.tatanpoker09.objectives;

public enum Objective {
	core("Destroy the Core"), wool("Capture the Wool"), monument("Destroy the Monument");
	private String gamemode;
	private static String type;

	Objective(String stringInput) {
		gamemode = stringInput;
	}

	public String getTypeName() {
		return gamemode;
	}
	public static String getType(){
		return type;
	}
	public void setType(){
		
	}
}
