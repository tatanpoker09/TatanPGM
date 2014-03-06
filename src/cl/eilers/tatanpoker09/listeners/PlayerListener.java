package cl.eilers.tatanpoker09.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class PlayerListener implements Listener {
	@EventHandler //JoinMessage, also teleports the player to the spawn.
	public void onPlayerJoin(PlayerJoinEvent event){
		System.out.println("OnJoin Has been triggered!");
		World spawn = new WorldCreator(Bukkit.getServer().getWorlds().get(0).getName()).createWorld();
		event.getPlayer().teleport(spawn.getSpawnLocation());
		event.getPlayer().setGameMode(GameMode.CREATIVE);
		event.getPlayer().getInventory().clear();
		if(ScoreboardUtils.teamExists("Observers")){
			ScoreboardUtils.mainBoard.getTeam("Observers").addPlayer(event.getPlayer());;

		}
		String serverName = Bukkit.getPluginManager().getPlugin("TatanPGM").getConfig().getString("TatanPGM.serverName");
		event.getPlayer().sendMessage(ChatColor.BLUE+"########################");
		event.getPlayer().sendMessage(ChatColor.RED+"Welcome to " + serverName);
		event.getPlayer().sendMessage(ChatColor.BLUE+"########################");
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		Team playerTeam = ScoreboardUtils.mainBoard.getPlayerTeam(event.getPlayer());
		playerTeam.removePlayer(event.getPlayer());
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event){
		Entity getDamaged = event.getEntity();
		Entity getDamageDealer = event.getDamager();
		if (getDamaged instanceof Player) {
			Team damagedPlayer = ScoreboardUtils.mainBoard.getPlayerTeam(((Player) getDamaged).getPlayer());
			Team damagerPlayer = ScoreboardUtils.mainBoard.getPlayerTeam(((Player)getDamageDealer).getPlayer());
			if(damagedPlayer.equals("Observers")){
				event.setCancelled(true);
			} else if(damagedPlayer.equals(damagerPlayer)){
				event.setCancelled(true);
			}
		}
	}
}
