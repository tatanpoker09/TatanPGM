package cl.eilers.tatanpoker09.commands;



import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.map.MapXMLLoading;
import cl.eilers.tatanpoker09.match.Match;
import cl.eilers.tatanpoker09.utils.CodingUtils;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class Join implements CommandExecutor{
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length>0){
			Player playerSender = (Player)sender;
			if(Match.getMatchStatus().equalsIgnoreCase("PLAYING")){
				playerSender.setHealth(0);
			}
			String teamName = CodingUtils.StringConcatenating(args);
			ScoreboardUtils.joinTeam((Player)sender, teamName);
			
			playerSender.setBedSpawnLocation(MapXMLLoading.getSpawnLocation(ScoreboardUtils.mainBoard.getPlayerTeam(playerSender)));
		} else {
			Team teamOne = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("FirstTeam");
			Team teamTwo = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("SecondTeam");
			if(teamOne.getPlayers().size()>teamTwo.getPlayers().size()){
				ScoreboardUtils.joinTeam((Player)sender, teamTwo.getDisplayName());
			} else {
				ScoreboardUtils.joinTeam((Player)sender, teamOne.getDisplayName());
			}

		}
		return true;
	}

}

