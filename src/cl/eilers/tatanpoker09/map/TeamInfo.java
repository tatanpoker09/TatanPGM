package cl.eilers.tatanpoker09.map;


public class TeamInfo {
	private int maxPlayers;
	private String teamName;
	
	public TeamInfo(String teamName, int maxPlayers) {
		this.maxPlayers = maxPlayers;
		this.teamName = teamName;
	}
	public int getMaxPlayers(){
		return maxPlayers;
	}
	public String getTeamName(){
		return teamName;
	}
}
