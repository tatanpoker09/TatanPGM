package cl.eilers.tatanpoker09.map;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.scoreboard.Team;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class MapXMLLoading {
	static String[][] teamInfo = new String[ScoreboardUtils.mainBoard.getTeams().size()-1][3];

	public static File currentMap;
	private static int heightLimit;

	public static Document LoadXML(File mapXML){
		Document doc = null;
		if(mapXML.exists()){
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(mapXML);
			} catch (ParserConfigurationException | IOException | SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			System.out.println("Couldn't find XML File");
			System.out.println(mapXML.getPath());
		}
		return doc;
	}

	/*public static ArrayList<Objective> getObjectiveTypes(File mapFile){
		Document mapXML = LoadXML(mapFile);
		if(mapXML!=null){

		}
	}
	 */

	public static void loadExtras(File mapXMLFile){
		Document mapXML = MapXMLLoading.LoadXML(mapXMLFile);
		NodeList maxHeightNode = mapXML.getElementsByTagName("maxbuildheight");
		String maxHeightString = maxHeightNode.item(0).getTextContent();
		System.out.println("Found height limit!: "+ maxHeightString);
		heightLimit = Integer.parseInt(maxHeightString)-1;
	}

	public static String[][] getTeamInfo(File mapFile){
		return teamXML(mapFile);
	}	

	public static String[][] teamXML(File mapFile){
		Document mapXML = LoadXML(mapFile);
		NodeList teamsNode = mapXML.getElementsByTagName("teams");
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


	public static Location getSpawnLocation(Team team){
		File mapFile = new File(currentMap.getName()+"/map.xml");
		Document mapXML = LoadXML(mapFile);
		Location defaultLocation = null;
		World scrimmageWorld = Bukkit.getWorld(currentMap.getName());
		NodeList spawnNode = null;
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
		return defaultLocation;
	}

	public static void setHeightLimit(int height){
		heightLimit = height;
	}

	public static int getHeightLimit(){
		return heightLimit;
	}
}