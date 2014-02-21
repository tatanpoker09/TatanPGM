package cl.eilers.tatanpoker09.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MapXMLLoading {
	static String maxPlayers;
	static String teamColor;
	static String teamName;
	static String[][] teamInfo = new String[2][3];

	public static Document LoadXML(String nextMap) throws ParserConfigurationException{
		File mapXML = new File(nextMap+"/map.xml");
		Document doc = null;
		if(mapXML.exists()){
			System.out.println("Found XML");
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


	public static String[][] getTeamInfo(String nextMap){
		return teamXML(nextMap);
	}
	/*public static String[] getObjectives(String nextMap){
		Document xml = null;
		try {
			xml = LoadXML(nextMap);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] objectivesString = null;
		return objectivesString;
	}*/
	
	public static ArrayList<String> getObjectivesType(String nextMap){
		ArrayList<String> objType = new ArrayList<String>();
		Document mapXML = null;
		try {
			mapXML = LoadXML(nextMap);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//GET IN HERE NUMBER OF OBJ PER TYPE.
		NodeList objsNodeList = mapXML.getElementsByTagName("cores");
		if(objsNodeList.getLength()>1){
			objType.add("cores" + objsNodeList.getLength());
		}
		objsNodeList = mapXML.getElementsByTagName("wools");
		if(objsNodeList.getLength()<1){
			objType.add("wools" + objsNodeList.getLength());
		}
		objsNodeList = mapXML.getElementsByTagName("destroyables");
		if(objsNodeList.getLength()<1){
			objType.add("destroyables" + objsNodeList.getLength());
		} 
		return objType;
	}




	public static String[][] teamXML(String nextMap){
		try {
			if(LoadXML(nextMap)!=null){
				Document doc = LoadXML(nextMap);
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
}