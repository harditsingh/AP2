import java.util.ArrayList;

public class Token<E> {
	static final int SET_TYPE = 1;
	static final int OPERATOR_TYPE = 2;
	
	private E tokenData;
	private int tokenType;
	private int tokenPrecedence;
	
	Token(E element, int tokenType) {
		this.tokenData = element;
		this.tokenType = tokenType;
		this.tokenPrecedence = -1;
	}
	
	Token(E tokenData, int tokenType, int tokenPrecedence) {
		this.tokenData = tokenData;
		this.tokenType = tokenType;
		this.tokenPrecedence = tokenPrecedence;
	}
	
	public int getType() {
		return tokenType;
	}
	
	public E getData() {
		return tokenData;
	}

	public int getPrecedence() {
		return tokenPrecedence;
	}
}
