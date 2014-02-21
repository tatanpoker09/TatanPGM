package cl.eilers.tatanpoker09.utils;

public class CodingUtils {
	public static boolean isNumeric(String str){  
		try{  
			@SuppressWarnings("unused")
			double Number = Double.parseDouble(str); 
		}catch(NumberFormatException nfe){  
			return false;  
		}  
		return true;  
	}

	public static String StringConcatenating(String[] inputString){
		String mapName = null;
			int i = 0;
			while(i < inputString.length) {
				mapName += " " + inputString[i];
				i++;
			}
			mapName = mapName.substring(5);
		return mapName;
	}
}
