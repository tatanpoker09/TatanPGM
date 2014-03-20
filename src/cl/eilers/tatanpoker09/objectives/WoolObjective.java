package cl.eilers.tatanpoker09.objectives;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.match.Match;

public class WoolObjective{
	private Block blockLocation;
	private DyeColor color;
	private String name;
	private Team team;
	private boolean isPlaced = false;
	public boolean isPlaced() {
		return isPlaced;
	}
	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}
	private static ArrayList<WoolObjective> wools = new ArrayList<WoolObjective>();


	public WoolObjective(Block blockLocation, DyeColor color, String name, Team team){
		this.blockLocation = blockLocation;
		this.color = color;
		this.name = name;
		this.team = team;
	}
	public Block getBlock(){
		return blockLocation;
	}
	public String getName(){
		return name;
	}
	public DyeColor getColor(){
		return color;
	}

	public Team getTeam(){
		return team;
	}
	public static void addWool(WoolObjective wool){
		wools.add(wool);
		Objective.addObjectives(1);
	}
	public static ArrayList<WoolObjective> getWools(){
		return wools;
	}
	public static void registerNewWool(Block blockLocation, DyeColor color, String name, Team team){
		WoolObjective wool = new WoolObjective(blockLocation, color, name, team);
		addWool(wool);
		System.out.println("[TatanPGM] Found Wool!" + name);
	}

	public static boolean teamHasWon(Team team){
		int x = 0;
		for(WoolObjective wool: getWools()){
			if(wool.getTeam().equals(team)){
				if(wool.isPlaced()){
					x++;
				}
			}
		}
		if(x >=Objective.getTotalObjectives()/2){
			Match.setHasAWinner(true);
			Match.setWinner(team.getDisplayName());
			return true;
		}
		return false;
	}
}