
public class SetScanner {
	private String data;
	private int pointer;
	private String[] delimiter;

	SetScanner(String data) {
		this.data = data;
		pointer = 0;
		delimiter = new String[20];
		for(int i = 0; i<20; i++) {
			delimiter[i] = "";
		}
		this.useDelimiter(" /\n/\r/=/+/*/-/|");
	}

	public String nextString() {
		String newString = "";

		if(isOperator()) {
			newString += currentChar();
			movePointer();
		}
		else {
			while(hasNext() && notDelimited()) {
				newString += currentChar();
				movePointer();
			}
		}
		return newString;
	}

	public String getStatement() {
		String tempString = data.substring(pointer);
		pointer += tempString.length();
		return tempString;
	}

	public String nextExpression() {
		String newString = "";
		if(data.charAt(pointer) == '(') {
			pointer++;
			while(pointer<data.length() && currentChar() != ')') {
				newString += data.charAt(pointer);
				pointer++;
			}
		}
		return newString;
	}

	public Set nextSet() {
		String setString = "";

		if(currentChar() == '{') {
			pointer++;
			while(pointer < data.length() && currentChar() != '}') {
				setString += currentChar();
				pointer++;
			}
			pointer++;
		}

		Long[] temp = parseNumbers(setString);

		Set<Long> newSet = new Set<Long>();
		for(Long element : temp) {
			newSet.insert(element);
		}

		return newSet;
	}

	private Long[] parseNumbers(String set) {
		SetScanner scanSet = new SetScanner(set);
		String currentLong = "";
		Long[] numberArray = new Long[0];

		while(scanSet.hasNext()) {
			if(!scanSet.isDigit()) {//Contradicting statement
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
			scanSet.skipWhiteSpace();
		}

		if(!currentLong.equals("")) {
			Long[] tempArray = new Long[numberArray.length + 1];
			for(int i = 0; i<numberArray.length; i++) {
				tempArray[i] = numberArray[i];
			}
			tempArray[numberArray.length] = Long.parseLong(currentLong);
			currentLong = "";
			numberArray = tempArray;
		}
		return numberArray;
	}

	public void movePointer() {
		pointer++;
	}

	public String returnNextType() { //Might deprecate this method, if a method is created for eliminating whitespace
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

	public boolean isQuestion() {
		if(currentChar() == '?') {
			return true;
		}
		return false;
	}

	public boolean isEquality() {
		if(currentChar() == '=') {
			return true;
		}
		return false;
	}

	public boolean isDigit() {
		if(Character.isDigit(currentChar())) {
			return true;
		}
		return false;
	}

	public boolean isAlpha() {
		if(Character.isAlphabetic(currentChar())) {
			return true;
		}
		return false;
	}

	public boolean isSet() {
		if(currentChar() == '{') {
			return true;
		}
		return false;
	}

	public boolean isParentheses() {
		if(currentChar() == '(') {
			return true;
		}
		return false;
	}

	public boolean isOperator() {
		if(currentChar() == '+' || currentChar() == '-' || currentChar() == '*' || currentChar() == '|') {
			return true;
		}
		return false;
	}

	public char currentChar() {
		if(pointer<data.length()) {
			char current = data.charAt(pointer);
			return current;
		}
		return (char) -1;
	}

	boolean hasNext() {
		if(pointer<data.length()) {
			if(data.charAt(pointer) != '\n' && data.charAt(pointer) != '\r') {
				return true;
			}
		}
		return false;

	}

	void useDelimiter(String delimiter) {
		int i = 0;

		for(int j = 0; j<delimiter.length(); j++) {
			if(delimiter.charAt(j) != '/') {
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

	public void skipWhiteSpace() {
		while(this.hasNext()) {
			if(currentChar() != ' ') {
				break;
			}
			pointer++;
		}
	}



}
