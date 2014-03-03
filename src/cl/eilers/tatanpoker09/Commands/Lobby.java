package cl.eilers.tatanpoker09.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Lobby implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender player, Command cmdName, String arg1, String[] args) {
		World lobby = new WorldCreator(Bukkit.getWorlds().get(0).getName()).createWorld();
		Player playerSender = (Player) player;
		playerSender.teleport(lobby.getSpawnLocation());
		return true;
	}
}
