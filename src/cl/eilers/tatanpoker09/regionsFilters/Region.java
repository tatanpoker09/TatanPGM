package cl.eilers.tatanpoker09.regionsFilters;

import java.util.ArrayList;

import org.bukkit.block.Block;

public class Region {
	private static ArrayList<Region> regions = new ArrayList<Region>();
	private Block max;
	private Block min;
	private String name;
	
	public Region(Block min, Block max, String name){
		this.min = min;
		this.max = max;
		this.name = name;
	}
	
	public void createNewRegion(Block min, Block max, String name){
		regions.add(new Region(min, max, name));
	}
	
	public String getName(){
		return name;
	}

	public static ArrayList<Region> getRegions() {
		return regions;
	}

	public Block getMax() {
		return max;
	}

	public Block getMin() {
		return min;
	}
}
