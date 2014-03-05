package cl.eilers.tatanpoker09.map;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.scoreboard.Team;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class MapXMLLoading {
	static String[][] teamInfo = new String[ScoreboardUtils.mainBoard.getTeams().size()-1][3];

	public static Document LoadXML(File mapXML) throws ParserConfigurationException{
		Document doc = null;
		if(mapXML.exists()){
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			try {
				doc = dBuilder.parse(mapXML);
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Couldn't find XML File");
		}
		return doc;
	}

	public static String[] getObjectiveTypes(File mapXMLFile){
		String[] objectiveTypes = new String[3]; 
		try {
			int n = 0;
			int possibleError = 0;
			NodeList objectives;
			Document mapXML = LoadXML(mapXMLFile);
			objectives = mapXML.getElementsByTagName("cores");
			if(objectives.getLength()>0){
				objectiveTypes[n] = "cores";
				n++;
			} else {
				possibleError++;
			}
			objectives = mapXML.getElementsByTagName("wools");
			if(objectives.getLength()>0){
				objectiveTypes[n] = "wools";
				n++;
			} else {
				possibleError++;
			}
			objectives = mapXML.getElementsByTagName("destroyables");
			if(objectives.getLength()>0){
				objectiveTypes[n] = "destroyables";
			} else {
				possibleError++;
			}
			if(possibleError >= 3){
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED+"WE COULDN'T FIND ANY OBJECTIVES. ASSUMING IT IS A TDM");
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objectiveTypes;
	}



	public static String[][] getTeamInfo(File nextMap){
		return teamXML(nextMap);
	}

	public static String[][] teamXML(File nextMap){
		try {
			if(LoadXML(nextMap)!=null){
				Document doc = LoadXML(nextMap);
				NodeList teamsNode = doc.getElementsByTagName("teams");
				for (int temp = 0; temp < teamsNode.getLength(); temp++) {
					int item = 0;
					Node nNode = teamsNode.item(temp);
					Element team = (Element)nNode;
					NodeList nameNode = team.getElementsByTagName("team");
					while(item<ScoreboardUtils.mainBoard.getTeams().size()-1){
						teamInfo[item][0]=nameNode.item(item).getAttributes().getNamedItem("color").getNodeValue();  
						teamInfo[item][1]=nameNode.item(item).getAttributes().getNamedItem("max").getNodeValue();
						teamInfo[item][2]= nameNode.item(item).getTextContent();
						System.out.println("[TatanPGM] Found Team!: "+teamInfo[item][2]);
						item++;

					}
				}
			}	
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teamInfo;
	}
	public static void spawnTeleporting(){
		for(Team team :  ScoreboardUtils.mainBoard.getTeams()){
			Location spawnLocation = getSpawnLocation(team);
			for(OfflinePlayer player : team.getPlayers()){
				if(player.getPlayer()!=null){
					if(spawnLocation!=null){
						player.getPlayer().teleport(spawnLocation);
					} else{
					}
				}
			}
		}

	}
	public static Location getLocationFromString(String location, World world){
		String[] locationStringArray = location.split(",");
		double[] parsed = new double[3];
		for (int a = 0; a < 3; a++) {
			parsed[a] = Double.parseDouble(locationStringArray[a]);
		}

		return new Location (world ,parsed[0], parsed[1], parsed[2]);
	}

	public static Location getSpawnLocation(Team team) {
		String worldName = Bukkit.getPluginManager().getPlugin("TatanPGM").getConfig().getString("TatanPGM.CurrentMap");
		World scrimmageWorld = Bukkit.getWorld(worldName);
		NodeList spawnNode = null;
		Location defaultLocation = null;
		try {
			File mapXMLFile = new File(scrimmageWorld.getName() + "/map.xml");
			Document mapXML = LoadXML(mapXMLFile);
			NodeList spawnsNode = mapXML.getElementsByTagName("spawns");
			Node nNode = spawnsNode.item(0);
			Element spawn = (Element)nNode;
			if(team.getName().equalsIgnoreCase("Observers")){
				spawnNode = spawn.getElementsByTagName("default");
			} else {
				spawnNode = spawn.getElementsByTagName("spawn");
			}
			int n = 0;
			if(team.getName().equals("FirstTeam")){
				n = 0;
			} else if(team.getName().equals("SecondTeam")){
				n = 1;
			} else if(team.getName().equals("Observers")) {
				n = 0;
			} else {
				n = 10;
			}
			if(n < 9){
				String defaultSpawn = spawnNode.item(n).getChildNodes().item(0).getAttributes().getNamedItem("base").getNodeValue();
				Float yaw = Float.parseFloat(spawnNode.item(n).getAttributes().getNamedItem("yaw").getNodeValue());
				defaultLocation = getLocationFromString(defaultSpawn, scrimmageWorld);
				defaultLocation.setYaw(yaw);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultLocation;
	}
}