package cl.eilers.tatanpoker09.utils;

import org.bukkit.Bukkit;

import cl.eilers.tatanpoker09.Scrimmage;
@SuppressWarnings("deprecation")
public class Timer {
	private Scrimmage plugin;
	public Timer(Scrimmage instance) {
		plugin = instance;
	}
	transient int gameCountdownTimer;
	public boolean Start(int seconds){
		gameCountdownTimer = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
			int seconds;
			@Override
			public void run(){
				if(seconds != -1){
					if(seconds != 0){
						seconds--;
						Bukkit.broadcastMessage("Cycling to " +plugin.getConfig().getString("TatanPGM.NextMap")+" in "+seconds);
						seconds--;
					}
				}
			}
		},0L, 20L);
		return true;
	}
	public void Cancel(){
		Bukkit.getServer().getScheduler().cancelTask(gameCountdownTimer);
	}
}