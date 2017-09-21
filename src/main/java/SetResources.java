import java.util.HashMap;
import java.util.Scanner;

public class SetResources {
	
	HashMap mainHashMap;
	
	SetResources() {
		mainHashMap = new HashMap();
	}

	void parseLine(String currentLine) {
		// Create scanner from input parameter, and declare the ListToken result
		Scanner in = new Scanner(currentLine);
		
		// While loop that runs through the string, identifies each character, and fills the ListToken result with tokens
		while (in.hasNext()) {
			String newToken = in.next();
			Scanner stringScanner = new Scanner(newToken);
			stringScanner.useDelimiter("/|?|\n|\r|\n\r");
			
			
			if (newToken.charAt(0) == '?') {
				stringScanner.next();
				
			}
			else if(newToken.charAt(0) == '/') {
				
			}
			else if(Character.isLetter(newToken.charAt(0))) {
				
			}
			else {
				System.out.println("Error identifying token type");
			}
		}

		
	}



}
