package cl.eilers.tatanpoker09.utils;

import org.bukkit.Bukkit;

import cl.eilers.tatanpoker09.Scrimmage;
@SuppressWarnings("deprecation")
public class Timer {
	private Scrimmage plugin;
	public Timer(Scrimmage instance) {
		plugin = instance;
	}
	public boolean Start(int seconds){
		plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				if(seconds != -1){
					if(seconds != 0){
						if(plugin.getConfig().getBoolean("TatanPGM.CancelTimer")== false){
							seconds--;
						}
						Bukkit.broadcastMessage("Cycling to " +plugin.getConfig().getString("TatanPGM.NextMap")+" in "+seconds);
						secs--;
					}
				}
			}
		},0L, 20L);
	}
}