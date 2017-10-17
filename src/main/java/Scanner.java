import java.math.BigInteger;

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

	private void removeNewLines() {
		String temp = "";
		for(int i = 0; i<data.length(); i++) {
			if(data.charAt(i) == '\n' || data.charAt(i) == '\r') {
				temp += '.';
			}
			else {
				temp += data.charAt(i);
			}
		}
		data = temp;
	}

	private void toLowercase() {
		String temp = "";
		for(int i = 0; i<data.length(); i++) {
			if(Character.isUpperCase(data.charAt(i))) {
				temp += Character.toLowerCase(data.charAt(i));
			}
			else {
				temp += data.charAt(i);
			}
		}
		data = temp;
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

	public String nextExpression() throws APException {
		String newString = "";
		int parenthesesCounter = 0;

		if(data.charAt(pointer) == '(') {//11 3 is not a valid number, so stop skipping white spaces when reading a number
			parenthesesCounter++;
			pointer++;
			while(pointer<data.length()) {
				if(currentChar() == '(') {
					parenthesesCounter++;
				}
				if(currentChar() == ')') {
					if(parenthesesCounter > 0) {
						parenthesesCounter--;	
						if(parenthesesCounter == 0) {
							break;
						}
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
		if(parenthesesCounter != 0) {
			throw new APException("Error: Missing Parentheses!");
		}
		return newString;
	}

	public Set nextSet() throws APException {
		String setString = "";

		if(currentChar() == '{') {
			pointer++;
			while(pointer < data.length() && currentChar() != '}') {
				setString += currentChar();
				pointer++;
			}
			if(currentChar() == '}') {
				pointer++;
			}
			else {
				throw new APException("The set is incorrect!");
			}
		}

		setString += "e";

		return parseNumbers(setString);
	}

	private Set parseNumbers(String set) throws APException {
		Scanner scanSet = new Scanner(set);
		String currentBigInteger = "";
		Set<BigInteger> newSet = new Set<BigInteger>();
		boolean readingDigit = true;
		boolean commaEncountered = false;
		
		scanSet.skipWhiteSpace();
		while(scanSet.hasNext()) {
			if(readingDigit) {
				
				if(scanSet.isDigit()) {
					currentBigInteger += scanSet.currentChar();
					if(scanSet.currentChar() == '0' && currentBigInteger.equals("0")) {
						newSet.insert(new BigInteger(currentBigInteger));
						currentBigInteger = "";
						readingDigit = false;
						commaEncountered = true;
					}
					scanSet.movePointer();
				}
				else if(scanSet.isComma()) {
					if(!currentBigInteger.equals("")) {
						newSet.insert(new BigInteger(currentBigInteger));
						currentBigInteger = "";
						readingDigit = false;
						commaEncountered = true;
					}
					else {
						throw new APException("No digit present before comma!");
					}
				}
				else if(scanSet.currentChar() == 'e') {
					if(!currentBigInteger.equals("")) {
						newSet.insert(new BigInteger(currentBigInteger));
						currentBigInteger = "";
					}
					readingDigit = false;
					break;
				}
				else if(scanSet.currentChar() == ' ') {
					scanSet.skipWhiteSpace();
				}
				else {
					throw new APException("Invalid Character!");
				}
			}
			else if(commaEncountered) {
				if(scanSet.isComma()) {
					scanSet.movePointer();
					scanSet.skipWhiteSpace();
				}
				else if(scanSet.currentChar() == 'e') {
					break;
				}
				else {
					throw new APException("No comma present after zero!");
				}

				if(scanSet.isDigit()) {
					commaEncountered = false;
					readingDigit = true;
				}
				else if(scanSet.isComma()) {
					throw new APException("No digit present before comma!");
				}
				else if(scanSet.currentChar() == 'e') {
					throw new APException("Set ends in comma, digit missing!");
				}
				else {
					throw new APException("Invalid Character!");
				}
			}
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

	public boolean isComma() {
		if(currentChar() == ',') {
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



	public void skipWhiteSpace() {//turn into boolean
		while(this.hasNext()) {
			if(currentChar() != ' ') {
				break;
			}
			pointer++;
		}
	}



}
