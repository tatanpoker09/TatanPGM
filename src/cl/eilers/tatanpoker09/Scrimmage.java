package cl.eilers.tatanpoker09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import cl.eilers.tatanpoker09.commands.Cancel;
import cl.eilers.tatanpoker09.commands.Cycle;
import cl.eilers.tatanpoker09.commands.End;
import cl.eilers.tatanpoker09.commands.G;
import cl.eilers.tatanpoker09.commands.Join;
import cl.eilers.tatanpoker09.commands.Lobby;
import cl.eilers.tatanpoker09.commands.Maps;
import cl.eilers.tatanpoker09.commands.SetLobby;
import cl.eilers.tatanpoker09.commands.SetServer;
import cl.eilers.tatanpoker09.commands.Setnext;
import cl.eilers.tatanpoker09.commands.Start;
import cl.eilers.tatanpoker09.listeners.BlockListener;
import cl.eilers.tatanpoker09.listeners.ChatListener;
import cl.eilers.tatanpoker09.listeners.CommandsListener;
import cl.eilers.tatanpoker09.listeners.DeathListener;
import cl.eilers.tatanpoker09.listeners.InventoryListener;
import cl.eilers.tatanpoker09.listeners.PlayerListener;
import cl.eilers.tatanpoker09.utils.MapUtils;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;
import cl.eilers.tatanpoker09.utils.Timer;

public final class Scrimmage extends JavaPlugin implements Listener {
	/*TODO
	 * Work on objectives
	 * Objectives into scoreboards.
	 * Get tab with colors.
	 * Fix /g
	 * Work on regions
	 * Big bug on objectives
	 */


	private File DontModify = new File("plugins/TatanPGM/DontModify.yml");
	public static List<Timer> tList = new ArrayList<Timer>();
	public static ArrayList<String> mapNames = new ArrayList<String>();
	public static ArrayList<String> mapFolders = new ArrayList<String>();
	@Override
	public void onEnable(){
		//Commands
		getCommand("setserver").setExecutor(new SetServer());
		getCommand("setnext").setExecutor(new Setnext());
		getCommand("cycle").setExecutor(new Cycle());
		getCommand("cancel").setExecutor(new Cancel());
		getCommand("lobby").setExecutor(new Lobby());
		getCommand("join").setExecutor(new Join());
		getCommand("start").setExecutor(new Start());
		getCommand("end").setExecutor(new End());
		getCommand("g").setExecutor(new G());
		getCommand("maps").setExecutor(new Maps());
		getCommand("setlobby").setExecutor(new SetLobby());
		getCommand("scrimmage").setExecutor(new cl.eilers.tatanpoker09.commands.Scrimmage());
		//Config Thingies
		createYML(DontModify);
		this.getConfig().addDefault("TatanPGM.serverName", "A TatanPGM Server!");
		//Listeners
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new CommandsListener(), this);
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new InventoryListener(), this);
		pm.registerEvents(new DeathListener(), this);
		pm.registerEvents(new PlayerListener(), this);
		//Registers main teams.

		if(ScoreboardUtils.mainTeamsExist()==false){
			ScoreboardUtils.mainBoard.registerNewTeam("FirstTeam");
			ScoreboardUtils.mainBoard.registerNewTeam("SecondTeam");
			ScoreboardUtils.mainBoard.registerNewTeam("Observers");
			getConfig().set("TatanPGM.HasCreatedTeams", true);
			loadConfiguration();
			this.saveDefaultConfig();
		}
		mapsLoad();
		
	}
	@Override
	public void onDisable(){
	}
	//More config Thingies
	public void loadConfiguration(){
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
	}
	
	public boolean mapsFolderExists(){
		File mapsFolder = new File("maps/");
		if(mapsFolder.exists()){
			return true;
		}
		return false;
	}

	public void createYML(File name){
		if (!name.exists()) {
			try {
				name.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void mapsLoad(){
		mapNames.clear();
		for(String mapName : MapUtils.MapList()){
			File mapXML = new File("maps/"+mapName+"/map.xml");
			if(mapXML.exists()){
				mapFolders.add(mapName);
				mapNames.add(ScoreboardUtils.getMapName(mapXML));
			}
		}
	}
}