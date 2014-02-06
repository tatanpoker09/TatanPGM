package cl.eilers.tatanpoker09.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.utils.Timer;

public class Cancel {
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		Timer timer = new Timer(null);
		timer.Cancel();
		return true;
	}
}
