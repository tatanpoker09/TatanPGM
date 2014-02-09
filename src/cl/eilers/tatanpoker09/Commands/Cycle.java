package cl.eilers.tatanpoker09.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.Scrimmage;
import cl.eilers.tatanpoker09.utils.Timer;



public class Cycle implements CommandExecutor {
	private Scrimmage plugin;
	public Cycle(Scrimmage instance) {
		plugin = instance;
	}
	private World mapBefore;
	int countdown;
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		//Countdown and stuff :3
		if(args.length>0){
			countdown = Integer.parseInt(args[0]);
			if(sender instanceof Player){
				Player playerSender = (Player)sender;
				mapBefore = playerSender.getWorld();
			} else {
				sender.sendMessage("You must be a player to cast this command.");
			}
		} else {
			countdown = 15;
		}
		new Timer(this.plugin, countdown, mapBefore).runTaskTimer(plugin, 0L, 20L);
		plugin.getConfig().set("TatanPGM.CancelCountdown", false);
		return true;
	}
}