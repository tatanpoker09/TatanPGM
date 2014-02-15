package cl.eilers.tatanpoker.map;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class MapXMLLoading {
	static String maxPlayers;
	static String teamColor;
	static String teamName;
	static String[][] teamInfo = new String[2][3];

	public static String[][] getTeamInfo(String nextMap){
		return teamXML(nextMap);
	}
	public static String[][] teamXML(String nextMap){
		try{
			File mapXML = new File(nextMap+"/map.xml");
			if(mapXML.exists()){
				System.out.println("Found XML");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(mapXML);
				NodeList teamsNode = doc.getElementsByTagName("teams");
				for (int temp = 0; temp < teamsNode.getLength(); temp++) {
					Node nNode = teamsNode.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element team = (Element)nNode;
						teamColor = team.getAttribute("color");
						maxPlayers = team.getAttribute("max");
						NodeList nameNode = team.getElementsByTagName("team");
						teamName = nameNode.item(temp).getTextContent();
						teamInfo[temp][0]= teamColor;
						teamInfo[temp][1]= maxPlayers;
						teamInfo[temp][2]= teamName;
					}
				}	
			} else {
				System.out.println("Couldn't find XML File");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teamInfo;
	}
}
