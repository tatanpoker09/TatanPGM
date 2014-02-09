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
	public static String[] teamXML(String nextMap){
		try{
		File mapXML = new File("/maps"+nextMap+"/map.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(mapXML);
		NodeList teamsNode = doc.getElementsByTagName("teams");

		for (int temp = 0; temp < teamsNode.getLength(); temp++) {
			Node nNode = teamsNode.item(temp);
			Element team = (Element)teamsNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				teamColor = team.getAttribute("color");
				maxPlayers = team.getAttribute("max");
				teamName = ""+ team.getElementsByTagName("team");
			
			}
		}
			
				
			
    } catch (Exception e) {
	e.printStackTrace();
    }
		return new String[] {teamColor, maxPlayers, teamName};
	}
}
