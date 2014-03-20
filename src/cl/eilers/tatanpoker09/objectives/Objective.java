package cl.eilers.tatanpoker09.objectives;

public enum Objective {
	core("Destroy the Core"), wool("Capture the Wool"), monument("Destroy the Monument");
	private String gamemode;
	private static String type;
	private static int totalObjectives = 0;

	Objective(String stringInput) {
		gamemode = stringInput;
	}

	public String getTypeName() {
		return gamemode;
	}
	public static String getType(){
		return type;
	}
	public static void setType(String newType){
		type = newType;
	}

	public static int getTotalObjectives() {
		return totalObjectives;
	}
	public static void addObjectives(int amount){
		totalObjectives = totalObjectives+amount;
	}
}
