import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SetResources {

	HashMap<Identifier, Set> mainHashMap;

	SetResources() {
		mainHashMap = new HashMap<Identifier, Set>();
	}

	/*void parseLine(String currentLine) {
		Scanner in = new Scanner(currentLine);

		String name = in.next();
		String element = in.next();
		String type;

		if(Character.isDigit(element.charAt(0))) {

		}

		while(in.hasNext()) {

		}



		while (in.hasNext()) {
			String newToken = in.next();
			Scanner stringScanner = new Scanner(newToken);



			if (newToken.charAt(0) == '?') {
				stringScanner.useDelimiter("/|?|\n|\r|\n\r");
				stringScanner.next();
				String identifier = stringScanner.next();

				for(Identifier key: mainHashMap.keySet()){
					if(key.compareName(identifier)) {
						mainHashMap.get(key).printSet();
						break;
					}
				}

			}
			else if(newToken.charAt(0) == '/') {

			}
			else if(Character.isLetter(newToken.charAt(0))) {
				String identifierName = stringScanner.next();
				stringScanner.useDelimiter(" = {");

				String element = stringScanner.next();
				stringScanner.useDelimiter(", | ");

				while(element.equals("}")) {

				}

			}
			else {
				System.out.println("Error identifying token type");
			}
		}


	}*/

	void setValueHandler(String identifierName, String setData) {	//This function changes values of Set in HashMap, or creates a new Set if t doesn't exist
		String dataType = "Unknown";
		Set newSet = null;

		if(Character.isDigit(setData.charAt(0))) {
			dataType = "Number";
			Scanner dataScanner = new Scanner(setData);
			dataScanner.useDelimiter(" |\n|\r|\n\r");
			ArrayList<Integer> elementList = new ArrayList<Integer>();
			
			
			while(dataScanner.hasNext()) {
				elementList.add(dataScanner.nextInt());
			}
			Integer[] a = new Integer[elementList.size()];
			newSet = new Set<Integer>(elementList.toArray(a));
		}
		else if(Character.isAlphabetic(setData.charAt(0))) {
			dataType = "Alphabet";
			Scanner dataScanner = new Scanner(setData);
			dataScanner.useDelimiter(" |\n|\r|\n\r");
			ArrayList<String> elementList = new ArrayList<String>();
			
			
			while(dataScanner.hasNext()) {
				elementList.add(dataScanner.next());
			}
			
			String[] a = new String[elementList.size()];			
			newSet = new Set<String>(elementList.toArray(a));
		}

		Identifier newIdentifier = new Identifier(identifierName, dataType);

		mainHashMap.put(newIdentifier, newSet);
	}

	
	void printSet(String identifierName) {
		for(Identifier key: mainHashMap.keySet()){
			if(key.compareName(identifierName)) {
				mainHashMap.get(key).printSet();
				return;
			}
		}
		
		System.out.println("Set not found!");
	}

	public void add(String next, String next2) {
		for(Identifier key1: mainHashMap.keySet()){
			if(key1.compareName(next)) {
				for(Identifier key2: mainHashMap.keySet()){
					if(key2.compareName(next2)) {
						mainHashMap.get(key1).add(mainHashMap.get(key2));
						return;
					}
				}
				return;
			}
		}
		
		System.out.println("Set not found!");
		
	}

	public void subtract(String next, String next2) {
		for(Identifier key1: mainHashMap.keySet()){
			if(key1.compareName(next)) {
				for(Identifier key2: mainHashMap.keySet()){
					if(key2.compareName(next2)) {
						mainHashMap.get(key1).subtract(mainHashMap.get(key2));
						return;
					}
				}
				return;
			}
		}
		
		System.out.println("Set not found!");
	}

	
	
	
	
}
