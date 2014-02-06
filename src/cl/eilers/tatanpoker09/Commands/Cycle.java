package cl.eilers.tatanpoker09.commands;

import java.io.File; 
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.Scrimmage;
import cl.eilers.tatanpoker09.utils.FileUtils;
import cl.eilers.tatanpoker09.utils.Timer;



public class Cycle implements CommandExecutor {
	private Scrimmage plugin;
	public Cycle(Scrimmage instance) {
		plugin = instance;
	}
	int countdown;
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		//Countdown and stuff :3
		if(args.length>0){
			countdown = Integer.parseInt(args[0]);
			Player playerSender = (Player)sender;
			final World mapBefore = playerSender.getWorld();
			sender.sendMessage(mapBefore.getName());
			File src = new File("maps/"+plugin.getConfig().getString("TatanPGM.NextMap"));
			//Random variables needed.
			String beforeMap = mapBefore.getName();
			File mapDone = new File(beforeMap);
			File dest = new File(plugin.getConfig().getString("TatanPGM.NextMap"));
			World nextWorld = new WorldCreator(plugin.getConfig().getString("TatanPGM.NextMap")).createWorld();

			//Copies the map from /maps to the main folder.
			if(Timer.this.Start(countdown)){
				sender.sendMessage(ChatColor.GREEN + "Loading map: " + ChatColor.BOLD + dest);
				dest.mkdir();
				try {
					FileUtils.copyFolder(src, dest);
				} catch (IOException e) {
					e.printStackTrace();
				}
				for(Player playersOnWorld : Bukkit.getOnlinePlayers()){
					playersOnWorld.teleport(nextWorld.getSpawnLocation());
				}
				//PROBLEM IN HERE, NOT SURE WHAT IT IS
				//Unloads last played world
				if(!mapBefore.getName().equals("spawn")){
					Bukkit.unloadWorld(mapBefore, true);
					//Last played world is deleted
					FileUtils.delete(mapDone);
				}
			}
			} else {
				sender.sendMessage("Please add the time (in seconds)");
			}
		return true;
	}
}