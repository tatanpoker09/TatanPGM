package cl.eilers.tatanpoker09.utils;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
	 
    private final JavaPlugin plugin;
 
    private int counter;
 
    public Timer(JavaPlugin plugin, int counter) {
        this.plugin = plugin;
        if (counter < 1) {
        	throw new IllegalArgumentException("You must supply a number");
        } else {
            this.counter = counter;
        }
    }
 
    @Override
    public void run() {
        // What you want to schedule goes here
        if (counter > 0) { 
        	plugin.getServer().broadcastMessage(ChatColor.DARK_AQUA+"Cycling to "+ChatColor.AQUA+plugin.getConfig().getString("TatanPGM.NextMap")+ChatColor.DARK_AQUA+" in "+ChatColor.DARK_RED+counter--+ChatColor.DARK_AQUA+" seconds!");
        } else {
        	plugin.getConfig().set("TatanPGM.CountdownFinished", true);
        	plugin.saveConfig();
        	this.cancel();
        }
    }
 
}