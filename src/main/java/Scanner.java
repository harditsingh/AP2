
public class Scanner {
	private String data;
	private int pointer;
	private String[] delimiter;

	Scanner(String data) {
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
		int parenthesesCounter = 0;

		if(data.charAt(pointer) == '(') {
			pointer++;
			while(pointer<data.length()) {
				if(currentChar() == '(') {
					parenthesesCounter++;
				}
				if(currentChar() == ')') {
					if(parenthesesCounter > 0) {
						parenthesesCounter--;	
					}
					else {
						break;
					}
				}
				newString += data.charAt(pointer);
				pointer++;
			}
			movePointer();
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

		return parseNumbers(setString);
	}

	private Set parseNumbers(String set) {
		Scanner scanSet = new Scanner(set);
		String currentLong = "";
		Set<Long> newSet = new Set<Long>();
		
		while(scanSet.hasNext()) {
			if(!scanSet.isDigit()) {
				scanSet.movePointer();
				if(!currentLong.equals("")) {
					newSet.insert(Long.parseLong(currentLong));
					currentLong = "";
				}
			}
			else {
				currentLong += scanSet.currentChar();
				scanSet.movePointer();
			}
			scanSet.skipWhiteSpace();
		}

		if(!currentLong.equals("")) {
			newSet.insert(Long.parseLong(currentLong));
			currentLong = "";
		}
		
		return newSet;
	}

	public void movePointer() {
		pointer++;
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
