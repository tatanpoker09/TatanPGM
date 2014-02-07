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
			if(sender instanceof Player){
				String NextMap = plugin.getConfig().getString("TatanPGM.NextMap");
				countdown = Integer.parseInt(args[0]);
				Player playerSender = (Player)sender;
				final World mapBefore = playerSender.getWorld();
				sender.sendMessage(mapBefore.getName());
				File src = new File("maps/"+NextMap);
				//Random variables needed.
				String beforeMap = mapBefore.getName();
				//TIMER HERE
				new Timer(this.plugin, countdown).runTaskTimer(this.plugin, 10, 20);
				while(plugin.getConfig().getBoolean("TatanPGM.CountdownFinished")==false){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				plugin.getConfig().set("TatanPGM.CountdownFinished", false);
				plugin.saveConfig();
				if(mapBefore.getName().equals(NextMap)) {
					NextMap = NextMap + "1";
				}
				File mapDone = new File(beforeMap);
				File dest = new File(NextMap);
				dest.mkdir();
				sender.sendMessage(ChatColor.GREEN + "Loading map: " + ChatColor.BOLD + dest);

				//Copies the map from /maps to the main folder.
				try {
					FileUtils.copyFolder(src, dest);
				} catch (IOException e) {
					e.printStackTrace();
				}


				//Unloads last played world
				if(!mapBefore.getName().equals("spawn")){
					System.out.println("Unloading map!" + mapBefore.getName());
					Bukkit.unloadWorld(mapBefore, true);
					//Last played world is deleted
					FileUtils.delete(mapDone);
				}
				World nextWorld = new WorldCreator(NextMap).createWorld();
				for(Player playersOnWorld : Bukkit.getOnlinePlayers()){
					playersOnWorld.teleport(nextWorld.getSpawnLocation());
				}
			} else {
				sender.sendMessage("You must be a player to cast this command.");
			}
		} else {
			sender.sendMessage("Please add the time (in seconds)");
		}
		return true;
	}
}