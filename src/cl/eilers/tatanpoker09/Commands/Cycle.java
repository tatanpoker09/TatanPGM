package cl.eilers.tatanpoker09.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.map.*;
import cl.eilers.tatanpoker09.Scrimmage;
import cl.eilers.tatanpoker09.utils.CodingUtils;
import cl.eilers.tatanpoker09.utils.Timer;



public class Cycle implements CommandExecutor {
	private Scrimmage plugin;

	public Cycle(Scrimmage instance) {
		plugin = instance;
	}

	int countdown;

	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		//Countdown and stuff :3
		if(Scrimmage.tList.size() == 0){
			return runTimer(sender, cmd, label, args);
		}else{
			for(Timer t: Scrimmage.tList){
				t.cancel();
			}
			Scrimmage.tList.clear();
			return runTimer(sender, cmd, label, args);
		}
	}

	private boolean runTimer(final CommandSender sender, Command cmd, String label, String[] args){
		if(sender instanceof Player){
			if(args.length<2){
				if(CodingUtils.isNumeric(args[0])){
					if(args[0].equals("0")){
						MapLoader.Load(plugin.getConfig().getString("TatanPGM.NextMap"),((Player)sender).getWorld() );
					} else if(Integer.parseInt(args[0])<0){
						sender.sendMessage(ChatColor.RED+"You cannot cycle a match in less than zero seconds.");
					} else {
						countdown = Integer.parseInt(args[0]);
						Scrimmage.tList.add(new Timer(this.plugin, countdown, ((Player)sender).getWorld()));
						Scrimmage.tList.get(0).runTaskTimer(plugin, 0L, 20L);
						plugin.getConfig().set("TatanPGM.CancelCountdown", false);
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED+"Number expected, string received instead.");
					return false;
				}
			} else if(args.length>1){
				sender.sendMessage(ChatColor.RED+"Too many arguments.");
				sender.sendMessage("/cycle [-f] [seconds] - defaults to 15 seconds");
				return false;
			} else if(args.length==0){
				countdown = 15;
				Scrimmage.tList.add(new Timer(this.plugin, countdown, ((Player)sender).getWorld()));
				Scrimmage.tList.get(0).runTaskTimer(plugin, 0L, 20L);
				return true;
			}
		} else {
			sender.sendMessage("You must be a player to cast this command.");
		}
			return false;
		}
	}