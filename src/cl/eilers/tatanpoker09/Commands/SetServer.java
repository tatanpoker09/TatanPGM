package cl.eilers.tatanpoker09.commands;

import java.io.File;  

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SetServer implements CommandExecutor {
	private File DontModify = new File("plugins/TatanPGM/DontModify.yml");

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			sender.sendMessage("You didn't type the server name!");
			return false;
		}
		if(args.length > 2){
			sender.sendMessage("The server name can be at max 2 words!");
			return false;
		}
		if(sender instanceof Player){
			YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(DontModify);
			Player p = (Player)sender;
			Location signLoc = p.getLocation();
			signLoc.setY(signLoc.getY()+1);
			Block signBlock = p.getWorld().getBlockAt(signLoc);
			Sign sign = (Sign)signBlock.getState();
			//This gets all arguments together into a single String.
			String argsTogether = "";
			int i = 0;
			while(i < args.length) {
				argsTogether += " " + args[i];
				i++;
			}
			argsTogether = argsTogether.substring(1);
			//It ends in here and continues with the sign code
			float yaw = ((Player) sender).getLocation().getYaw();
			if(yaw>-45.00 && yaw<45.00){
				signBlock.setTypeIdAndData(Material.WALL_SIGN.getId(), (byte) 0x3, false);
			}
			sign.setLine(0, argsTogether);
			sign.update();
			yamlFile.set("TatanPGM.Servers."+argsTogether, sign.getLocation());
		} else {
			sender.sendMessage("You must be a player to cast this command!");
		}
		return true;
	}
}