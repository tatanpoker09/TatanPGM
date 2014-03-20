package cl.eilers.tatanpoker09.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLobby implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player playerSender = (Player)sender;
		if(playerSender.getWorld().equals(Bukkit.getWorlds().get(0))){
			Location playerLocation = playerSender.getLocation();
			int x = playerLocation.getBlockX();
			int y = playerLocation.getBlockY();
			int z = playerLocation.getBlockZ();
			playerSender.getWorld().setSpawnLocation(x, y, z);
		} else {
			sender.sendMessage(ChatColor.BOLD+""+ChatColor.BLUE+"[TatanPGM][Error]"+ChatColor.RESET+""+ChatColor.WHITE+" You can't set the spawn in a world that isn't the main one.");
		}
		return true;
	}
}
