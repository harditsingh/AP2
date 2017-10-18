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

		if(data.charAt(pointer) == '(') {
			parenthesesCounter++;
			movePointer();
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
				movePointer();
			}
			movePointer();
		}
		if(parenthesesCounter != 0) {
			throw new APException("error missing parenthesis");
		}
		return newString;
	}

	public Set<?> nextSet() throws APException {
		String setString = "";

		if(currentChar() == '{') {
			pointer++;
			while(pointer < data.length() && currentChar() != '}') {
				setString += currentChar();
				movePointer();
			}
			if(currentChar() == '}') {
				movePointer();
			}
			else {
				throw new APException("The set is incorrect!");
			}
		}

		setString += "e";

		return parseNumbers(setString);
	}

	private Set<?> parseNumbers(String set) throws APException {
		Scanner scanSet = new Scanner(set);
		String currentBigInteger = "";
		Set<BigInteger> newSet = new Set<BigInteger>();
		boolean digitExpected = true;
		boolean digitEnded = false;
		boolean firstPass = true;
		scanSet.skipWhiteSpace();

		while(scanSet.hasNext()) {
			if(scanSet.isDigit() && !digitEnded) {
				currentBigInteger += scanSet.currentChar();
				if(scanSet.currentChar() == '0' && currentBigInteger.equals("0")) {
					newSet.insert(new BigInteger(currentBigInteger));
					currentBigInteger = "";
					digitEnded = true;
				}
				else {
					digitEnded = false;
				}
				scanSet.movePointer();
				digitExpected = false;
			}
			else if(scanSet.isComma() && !digitExpected) {
				scanSet.movePointer();
				if(!currentBigInteger.equals("")) {
					newSet.insert(new BigInteger(currentBigInteger));
					currentBigInteger = "";
				}
				digitExpected = true;
				scanSet.skipWhiteSpace();
				digitEnded = false;
			}
			else if(scanSet.currentChar() == ' ' && !digitExpected) {
				scanSet.skipWhiteSpace();
				digitEnded = true;
			}
			else if(scanSet.currentChar() == 'e' && (!digitExpected || firstPass)) {
				if(!currentBigInteger.equals("")) {
					newSet.insert(new BigInteger(currentBigInteger));
					currentBigInteger = "";
				}
				break;
			}
			else {
				throw new APException("Error!");
			}
			firstPass = false;
		}
		
		if(digitExpected && !firstPass) {
			throw new APException("Digit expected!");
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



	public void skipWhiteSpace() {
		while(this.hasNext()) {
			if(currentChar() != ' ') {
				break;
			}
			pointer++;
		}
	}



}
