
public class SetScanner {
	String data;
	int pointer;
	String[] delimiter;

	SetScanner(String data) {
		this.data = data;
		pointer = 0;
		delimiter = new String[20];
		for(int i = 0; i<20; i++) {
			delimiter[i] = "";
		}
	}

	String nextString() {
		String newString = "";

		while(pointer<data.length() && notDelimited()) {
			newString += data.charAt(pointer);
			pointer++;
		}
		return newString;
	}

	String nextSet() {
		String newSet = "";

		if(data.charAt(pointer) == '{') {
			pointer++;
			while(pointer < data.length() && data.charAt(pointer) != '}') {
				newSet += data.charAt(pointer);
				pointer++;
			}
		}

		Long[] temp = parseNumbers(newSet);

		for(Long element : temp) {
			System.out.println(element);
		}

		return newSet;
	}

	Long[] parseNumbers(String set) {
		SetScanner scanSet = new SetScanner(set);
		String currentLong = "";
		Long[] numberArray = new Long[0];

		while(scanSet.hasNext()) {
			if(!scanSet.returnNextType().equals("Digit")) {
				scanSet.movePointer();
				if(!currentLong.equals("")) {
					Long[] tempArray = new Long[numberArray.length + 1];
					for(int i = 0; i<numberArray.length; i++) {
						tempArray[i] = numberArray[i];
					}
					tempArray[numberArray.length] = Long.parseLong(currentLong);
					currentLong = "";
					numberArray = tempArray;
				}
			}
			else {
				currentLong += scanSet.currentChar();
				scanSet.movePointer();
			}

		}

		return numberArray;
	}

	void movePointer() {
		pointer++;
	}

	String returnNextType() {
		String type = null;

		if(Character.isAlphabetic(data.charAt(pointer))) {
			type = "String";
		}
		else if(Character.isDigit(data.charAt(pointer))) {
			type = "Digit";
		}
		else if(currentChar() == '{') {
			type = "Set";
		}
		else if(currentChar() == '\n' || currentChar() == '\r') {
			type = "EOL";
		}
		else if(currentChar() == '=') {
			type = "EqualTo";
		}
		else if(currentChar() == ' ') {
			pointer++;
			if(this.hasNext()) {
				type = returnNextType();
			}
			else {
				type = "EOL";
			}
		}
		else {
			type = "Unknown";
		}

		return type;
	}


	public char currentChar() {
		return data.charAt(pointer);
	}

	boolean hasNext() {
		if(pointer<data.length()) {
			return true;
		}
		else {
			return false;
		}
	}

	void useDelimiter(String delimiter) {
		int i = 0;

		for(int j = 0; j<delimiter.length(); j++) {
			if(delimiter.charAt(j) != '|') {
				this.delimiter[i] += delimiter.charAt(j);
			}
			else {
				i++;
			}
		}
	}

	boolean notDelimited() {
		for(int i = 0; i<delimiter.length; i++) {
			if(delimiter[i] != "") {
				if(data.substring(pointer).startsWith(delimiter[i])) {
					return false;
				}
			}
		}
		return true;
	}



}
