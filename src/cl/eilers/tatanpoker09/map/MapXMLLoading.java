package cl.eilers.tatanpoker09.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.minecraft.util.org.apache.commons.lang3.StringUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scoreboard.Team;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cl.eilers.tatanpoker09.objectives.WoolObjective;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class MapXMLLoading {
	static String[][] teamInfo = new String[ScoreboardUtils.mainBoard.getTeams().size()-1][3];

	public static File currentMap;
	private static int heightLimit;
	private static Document mapXML;
	//===============================================
	public static File getCurrentMap() {
		return currentMap;
	}
	public static void setCurrentMap(File currentMap) {
		MapXMLLoading.currentMap = currentMap;
	}
	//================================================
	public static void setMapXML(File mapXML){
		MapXMLLoading.mapXML = LoadXML(mapXML);
	}
	public static Document getMapXML(){
		return mapXML;
	}
	//================================================
	public static Document LoadXML(File mapXML){
		Document doc = null;
		if(mapXML.exists()){
			System.out.println("");
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

	public static void loadExtras(File mapXMLFile){
		Document mapXML = getMapXML();
		NodeList maxHeightNode = mapXML.getElementsByTagName("maxbuildheight");
		String maxHeightString = maxHeightNode.item(0).getTextContent();
		System.out.println("Found height limit!: "+ maxHeightString);
		heightLimit = Integer.parseInt(maxHeightString)-1;
	}

	public static String[][] getTeamInfo(){
		return teamXML();
	}

	public static String[][] teamXML(){
		Document mapXML = getMapXML();
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
				new TeamInfo(teamInfo[item][2] ,Integer.parseInt(teamInfo[item][1]));
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

	//Loads wools from XML File
	public static void loadWools(File mapXMLFile){
		Document mapXML = getMapXML();
		NodeList woolsNodeList = mapXML.getElementsByTagName("wools");
		//Cycles through every node named "wools".
		for(int i = 0;woolsNodeList.getLength() > i;i++){
			Node woolByTeam = woolsNodeList.item(i);
			ArrayList<Node> nodeList = new ArrayList<Node>();
			nodeList.add(woolByTeam);
			for(int n = 0;woolByTeam.getChildNodes().getLength()>n;n++){
				Node woolChildNode = woolByTeam.getChildNodes().item(n);
				if(woolChildNode instanceof Element){
					nodeList.add(woolChildNode.getFirstChild());
					nodeList.add(woolChildNode);
				}
			}
			loadWoolNodes(nodeList);
		}
	}
	private static void loadWoolNodes(ArrayList<Node> nodes) {
		/*	TODO
		 * Add Block, add childNodes.
		 */
		String woolName = null;
		Block woolBlock = null;
		String color = null;
		Team team = null;
		int numberOfWools = 0;
		for(Node node : nodes){
			if(node.getNodeName().equals("wool")){
				numberOfWools++;
			}
			if(node.hasAttributes()){
				NamedNodeMap nodeAttributes = node.getAttributes();
				ArrayList<Node> attributeNames = parseAttributeNames(nodeAttributes);
				for(Node attribute : attributeNames){
					switch(attribute.getNodeName()){
					case("team"):
						team = getTeamByStartsWith(attribute.getNodeValue());
					break;
					case("color"):
						color = attribute.getNodeValue();
					break;
					}
				}
			}
			if(node.getNodeName().equals("block")){
				woolBlock = getLocationFromString(node.getTextContent(), Bukkit.getWorld(getCurrentMap().getName())).getBlock();
			}
			if(numberOfWools>0){
				String firstLetterToUpperCase = String.valueOf(color.charAt(0)).toUpperCase();
				woolName = firstLetterToUpperCase + color.substring(1) + " Wool";
				WoolObjective.registerNewWool(woolBlock, DyeColor.valueOf(color.toUpperCase()), woolName, team);
			}
		}
	}

	private static ArrayList<Node> parseAttributeNames(NamedNodeMap nodeAttributes) {
		ArrayList<Node> attributeNames = new ArrayList<Node>();
		for(int n=0;nodeAttributes.getLength()>n;n++){
			attributeNames.add(nodeAttributes.item(n));
		}
		return attributeNames;
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
		Document mapXML = getMapXML();
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

	public static Team getTeamByStartsWith(String teamInputName){
		Team teamOutput = null;
		for(Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()){
			String teamName = ChatColor.stripColor(team.getDisplayName());
			if(StringUtils.startsWithIgnoreCase(teamName, teamInputName)){
				teamOutput = team;
			}
		}
		return teamOutput;
	}

	@SuppressWarnings("deprecation")
	public static DyeColor getDyeColorFromWool(Block wool){
		DyeColor outputColor = null;
		if(wool.getType().equals(Material.WOOL)){
			outputColor = DyeColor.getByData(wool.getData());
		} else {
			System.out.println("TYPE IS NOT WOOL!");
		}
		return outputColor;
	}

	public static int getHeightLimit(){
		return heightLimit;
	}
	/*TODO
	public static void loadRegions(){
		Document mapXML = getMapXML();
		mapXML.getElementsByTagName("regions");
	}*/
}