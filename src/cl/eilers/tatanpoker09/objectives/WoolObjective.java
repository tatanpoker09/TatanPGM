package cl.eilers.tatanpoker09.objectives;

import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.scoreboard.Team;

public class WoolObjective{
	private Block blockLocation;
	private DyeColor color;
	private String name;
	private Team team;
	
	
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
	public DyeColor getDyeColor(){
		return color;
	}
	
	public Team getTeam(){
		return team;
	}
}
