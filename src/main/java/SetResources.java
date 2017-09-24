import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class SetResources {

	private static final String OPERATOR_TOKENS = "+-*|^";

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


	Set dataToSet(String setData) {
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

		return newSet;
	}


	void setStore(String identifierName, Set newSet) {	//This function changes values of Set in HashMap, or creates a new Set if t doesn't exist
		String dataType = "Unknown";

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

	public void union(String next, String next2) {
		for(Identifier key1: mainHashMap.keySet()){
			if(key1.compareName(next)) {
				for(Identifier key2: mainHashMap.keySet()){
					if(key2.compareName(next2)) {
						mainHashMap.get(key1).union(mainHashMap.get(key2)).printSet();
						return;
					}
				}
				return;
			}
		}

		System.out.println("Set not found!");

	}

	public void complement(String next, String next2) {
		for(Identifier key1: mainHashMap.keySet()){
			if(key1.compareName(next)) {
				for(Identifier key2: mainHashMap.keySet()){
					if(key2.compareName(next2)) {
						mainHashMap.get(key1).complement(mainHashMap.get(key2)).printSet();
						return;
					}
				}
				return;
			}
		}

		System.out.println("Set not found!");
	}

	public void intersection(String next, String next2) {
		for(Identifier key1: mainHashMap.keySet()){
			if(key1.compareName(next)) {
				for(Identifier key2: mainHashMap.keySet()){
					if(key2.compareName(next2)) {
						mainHashMap.get(key1).intersection(mainHashMap.get(key2)).printSet();
						return;
					}
				}
				return;
			}
		}

		System.out.println("Set not found!");
	}

	public void symmetricDifference(String next, String next2) {
		for(Identifier key1: mainHashMap.keySet()){
			if(key1.compareName(next)) {
				for(Identifier key2: mainHashMap.keySet()){
					if(key2.compareName(next2)) {
						mainHashMap.get(key1).symmetricDiffence(mainHashMap.get(key2)).printSet();
						return;
					}
				}
				return;
			}
		}

		System.out.println("Set not found!");
	}

	private Set findSet(String keyString) {
		for(Identifier key: mainHashMap.keySet()){
			if(key.compareName(keyString)) {
				return mainHashMap.get(key);
			}
		}

		return null;
	}

	private void putValueInMap(String name,Set newValue) {
		for(Identifier key : mainHashMap.keySet()) {
			if(key.compareName(name)) {
				mainHashMap.get(key).replaceValue(newValue);
				return;
			}
		}
		mainHashMap.put(new Identifier(name, "Unknown"), newValue);		
	}

	public void processInput(String data) {
		SetScanner in = new SetScanner(data);

		while(in.hasNext()) {
			if(in.isAlpha()) {
				String currentSet = in.nextString();
				in.skipWhiteSpace();
				if(in.isEquality()) {
					in.movePointer();
					in.skipWhiteSpace();
					putValueInMap(currentSet, processEBNF(in.getStatement()));
				}
			}
			else if(in.isQuestion()) {
				in.movePointer();
				in.skipWhiteSpace();
				processEBNF(in.getStatement()).printSet();
			}
			else {
				in.movePointer();
			}
		}
	}

	public Set processEBNF(String data) {
		SetScanner in = new SetScanner(data);
		ArrayList<Token> expression = new ArrayList<Token>();

		// While loop that runs through the string, identifies each character, and fills the ListToken result with tokens
		while (in.hasNext()) {

			if (in.isAlpha()) {
				Set tempSet = findSet(in.nextString());
				if(tempSet != null) {
					Token<Set> tempToken = new Token<Set>(tempSet, Token.SET_TYPE);
					expression.add(tempToken);
				}
			} 
			else if (in.isOperator()) {
				String operator = in.nextString();
				Token<String> tempToken = new Token<String>(operator, Token.OPERATOR_TYPE, setPrecedence(operator));
				expression.add(tempToken);
			} 
			else if(in.isSet()) {
				Set tempSet = in.nextSet();
				Token<Set> tempToken = new Token<Set>(tempSet, Token.SET_TYPE);
				expression.add(tempToken);
			}
			else if (in.isParentheses()) {
				Set tempSet = processEBNF(in.nextExpression());
				Token<Set> tempToken = new Token<Set>(tempSet, Token.SET_TYPE);
				expression.add(tempToken);
			}
			else {
				System.out.println("Error identifying token type");
				in.movePointer();
			}

			in.skipWhiteSpace();
		}

		expression = shuntingYard(expression);
		Set answerSet = rpnProcessor(expression);
		return answerSet;
	}

	private int setPrecedence(String token) {
		int precedence = 0;

		for (int i = 0; i < OPERATOR_TOKENS.length(); i++) {
			if(i%2 == 0) {
				precedence++;
			}
			if (token.charAt(0) == OPERATOR_TOKENS.charAt(i)) {
				return precedence;
			}
		}

		return 0;
	}

	private Set rpnProcessor(ArrayList<Token> expression) {
		Stack<Set> operationStack = new Stack<Set>();

		for(int i = 0; i < expression.size(); i++) {
			Token currentToken = expression.get(i);
			if(currentToken.getType() == Token.SET_TYPE) {
				operationStack.push((Set) expression.get(i).getData());
			}
			else if(currentToken.getType() == Token.OPERATOR_TYPE) {
				Set operand2 = operationStack.pop();
				Set operand1 = operationStack.pop();

				operationStack.push(calculateAnswer(operand1, operand2, expression.get(i)));
			}
		}

		if(operationStack.size() == 1) {
			return operationStack.peek();
		}
		else {
			System.err.println(" Invalid input, remaining tokens on stack.");
			return null;
		}
	}

	private Set calculateAnswer(Set operand1, Set operand2, Token operator) {
		Set answer = null;

		switch(((String) operator.getData()).charAt(0)) {
		case '+':
			answer = operand1.union(operand2);
			break;
		case '-':
			answer = operand1.complement(operand2);
			break;
		case '*':
			answer = operand1.intersection(operand2);
			break;
		case '|':
			answer = operand1.symmetricDiffence(operand2);
			break;
		default:
			answer = null;
		}


		return answer;
	}

	private ArrayList<Token> shuntingYard(ArrayList<Token> expression) {
		ArrayList<Token> outputList = new ArrayList<Token>();
		Stack<Token> operatorStack = new Stack<Token>();

		for(int i = 0; i < expression.size(); i++) {
			if(expression.get(i).getType() == Token.SET_TYPE) {
				outputList.add(expression.get(i));
			}
			else if(expression.get(i).getType() == Token.OPERATOR_TYPE) {
				while(operatorStack.size() > 0) {
					if(operatorStack.peek().getPrecedence() >= expression.get(i).getPrecedence()) {
						outputList.add(operatorStack.pop());
					}
					else {
						break;
					}
				}
				operatorStack.push(expression.get(i));
			}
		}

		while(operatorStack.size() > 0) {
			outputList.add(operatorStack.pop());
		}

		return outputList;
	}



}
