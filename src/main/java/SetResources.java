
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class SetResources {

	private static final String OPERATOR_TOKENS = "+-|*^";

	HashMap<Identifier, Set<?>> mainHashMap;

	SetResources() {
		mainHashMap = new HashMap<Identifier, Set<?>>();
	}

	void printSet(Set<?> currentSet) {
		Set<?> tempSet = new Set<>(currentSet);
		
		while(!tempSet.isEmpty()) {
			System.out.print(tempSet.retrieve());
			tempSet.remove();
			if(!tempSet.isEmpty()) {
				System.out.print(" ");
			}
		}
		System.out.println();
	}

	private Set<?> findSet(String keyString) throws APException {
		for(Identifier key: mainHashMap.keySet()){
			if(key.compareName(keyString)) {
				return mainHashMap.get(key);
			}
		}
		throw new APException("error undefined variable");
	}

	private void putValueInMap(String name,Set newValue) {
		for(Identifier key : mainHashMap.keySet()) {
			if(key.compareName(name)) {
				mainHashMap.get(key).replaceValue(newValue);
				return;
			}
		}
		mainHashMap.put(new Identifier(name), newValue);		
	}

	public void processInput(String data) throws APException {
		Scanner in = new Scanner(data);
		in.skipWhiteSpace();
		
		while(in.hasNext()) {
			if(in.isAlpha() || in.isDigit()) {
				String currentSet = in.nextString();
				IdentifierInterface.validateIdentifier(currentSet);
				in.skipWhiteSpace();
				if(in.isEquality()) {
					in.movePointer();
					in.skipWhiteSpace();
					String statement = in.getStatement();
					if(statement.equals("")) {
						throw new APException("Incomplete statement!");
					}
					Set<?> answer = processEBNF(statement);
					putValueInMap(currentSet, answer);
				}
				else {
					throw new APException("Equality sign expected!");
				}
			}
			else if(in.isQuestion()) {
				in.movePointer();
				in.skipWhiteSpace();
				String statement = in.getStatement();
				if(statement.equals("")) {
					throw new APException("Incomplete Statement!");
				}
				Set<?> answer = processEBNF(statement);
				printSet(answer);
			}
			else if(in.currentChar() == '/') {
				break;
			}
			else {
				throw new APException("error no statement");
			}
		}
	}

	public Set<?> processEBNF(String data) throws APException {
		Scanner in = new Scanner(data);
		ArrayList<Token<?>> expression = new ArrayList<Token<?>>();
		in.skipWhiteSpace();
		boolean operatorExpected = false;

		while (in.hasNext()) {

			if (in.isAlpha()) {
				if(operatorExpected) {
					throw new APException("error no end of line");
				}
				String currentSet = in.nextString();
				IdentifierInterface.validateIdentifier(currentSet);
				Set<?> requiredSet = findSet(currentSet);
				Set<?> tempSet = new Set<>(requiredSet);				
				Token<Set<?>> tempToken = new Token<Set<?>>(tempSet, Token.SET_TYPE);
				expression.add(tempToken);
				operatorExpected = true;
			} 
			else if (in.isOperator()) {
				if(!operatorExpected) {
					throw new APException("Unxpected Operator!");
				}
				String operator = in.nextString();
				Token<String> tempToken = new Token<String>(operator, Token.OPERATOR_TYPE, setPrecedence(operator));
				expression.add(tempToken);
				operatorExpected = false;
			} 
			else if(in.isSet()) {
				if(operatorExpected) {
					throw new APException("Operator Expected!");
				}
				Set<?> tempSet = in.nextSet();
				Token<Set<?>> tempToken = new Token<Set<?>>(tempSet, Token.SET_TYPE);
				expression.add(tempToken);
				operatorExpected = true;
			}
			else if (in.isParentheses()) {
				if(operatorExpected) {
					throw new APException("Operator Expected!");
				}
				Set<?> tempSet = processEBNF(in.nextExpression());
				Token<Set<?>> tempToken = new Token<Set<?>>(tempSet, Token.SET_TYPE);
				expression.add(tempToken);
				operatorExpected = true;
			}
			else {
				throw new APException("error no end of line");
			}

			in.skipWhiteSpace();
		}

		expression = shuntingYard(expression);
		Set<?> answerSet = rpnProcessor(expression);
		return answerSet;
	}

	private int setPrecedence(String token) {
		int precedence = 0;

		for (int i = 0; i < OPERATOR_TOKENS.length(); i++) {
			if(i%3 == 0) {
				precedence++;
			}
			if (token.charAt(0) == OPERATOR_TOKENS.charAt(i)) {
				return precedence;
			}
		}

		return 0;
	}

	private Set<?> rpnProcessor(ArrayList<Token<?>> expression) throws APException {
		Stack<Set<?>> operationStack = new Stack<Set<?>>();

		for(int i = 0; i < expression.size(); i++) {
			Token<?> currentToken = expression.get(i);
			if(currentToken.getType() == Token.SET_TYPE) {
				operationStack.push((Set<?>) expression.get(i).getData());
			}
			else if(currentToken.getType() == Token.OPERATOR_TYPE) {
				Set<?> operand2 = operationStack.pop();
				Set<?> operand1 = operationStack.pop();

				operationStack.push(calculateAnswer(operand1, operand2, expression.get(i)));
			}
		}

		if(operationStack.size() == 1) {
			return operationStack.peek();
		}
		else {
			throw new APException("Invalid input, remaining tokens on stack!");
		}
	}

	private Set<?> calculateAnswer(Set operand1, Set operand2, Token operator) {
		Set answer = null;

		switch(((String) operator.getData()).charAt(0)) {
		case '+':
			answer = (Set) operand1.union(operand2);
			break;
		case '-':
			answer = (Set) operand1.complement(operand2);
			break;
		case '*':
			answer = (Set) operand1.intersection(operand2);
			break;
		case '|':
			answer = (Set) operand1.symmetricDiffence(operand2);
			break;
		default:
			answer = null;
		}


		return answer;
	}

	private ArrayList<Token<?>> shuntingYard(ArrayList<Token<?>> expression) {
		ArrayList<Token<?>> outputList = new ArrayList<Token<?>>();
		Stack<Token<?>> operatorStack = new Stack<Token<?>>();

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
