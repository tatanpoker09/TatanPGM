//Problems: COMMAND BUG


package cl.eilers.tatanpoker09;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import cl.eilers.tatanpoker09.Commands.Cycle;
import cl.eilers.tatanpoker09.Commands.SetServer;
import cl.eilers.tatanpoker09.Commands.Setnext;

public final class Scrimmage extends JavaPlugin implements Listener {
	private File DontModify = new File("plugins/TatanPGM/DontModify.yml");
	
	@Override
	public void onEnable(){
		//Commands
		getCommand("setserver").setExecutor(new SetServer());
		getCommand("setnext").setExecutor(new Setnext(this));
		getCommand("cycle").setExecutor(new Cycle(this));
		//Config Thingies
		createYML(DontModify);
		this.getConfig().addDefault("TatanPGM.serverName", "A TatanPGM Server!");
		loadConfiguration();
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		//Listeners
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	@Override
	public void onDisable(){
	}
	//More config Thingies
	public void loadConfiguration(){
	     getConfig().options().copyDefaults(true);
	     saveConfig();
	     reloadConfig();
	}

	public void createYML(File name){
		if (!name.exists()) {
			try {
				name.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler //JoinMessage, also teleports the player to the spawn.
	public void onJoin(PlayerJoinEvent event){
		System.out.println("OnJoin Has been triggered!");
		World spawn = new WorldCreator("spawn").createWorld();
		event.getPlayer().teleport(spawn.getSpawnLocation());
		String serverName = getConfig().getString("TatanPGM.serverName");
		event.getPlayer().sendMessage(ChatColor.BLUE+"########################");
		event.getPlayer().sendMessage(ChatColor.RED+"Welcome to " + serverName);
		event.getPlayer().sendMessage(ChatColor.BLUE+"########################");
	}
}