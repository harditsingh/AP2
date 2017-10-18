/**	@elements : objects of type char
 *	@structure : linear
 *	@domain : 	The characters are stored in an object of StringBuffer.
 *				All the characters in the object StringBuffer together represent
 *				the name.
 *	@constructor - Identifier();
 *	<dl>
 *		<dt><b>PRE-conditie</b><dd>		-
 *		<dt><b>POST-conditie</b><dd> 	The new Identifer object has the characters
 *										stored in an object of type StringBuffer.
 * </dl>
 **/

public interface IdentifierInterface {

	/**	@precondition -
	 *  @postcondition - TRUE: The String 'name' confirms to the guidelines of an identifier.
	 *  				 FALSE: The String 'name' has characters which are not allowed or starts with a digit.
	 **/
	public static boolean validateIdentifier(String name) throws APException {
		Scanner scanner = new Scanner(name);
		boolean valid = true;
		if(!scanner.isAlpha()) {
			valid = false;
		}

		while(scanner.hasNext()) {
			if(!scanner.isAlpha() && !scanner.isDigit()) {
				valid = false;
				break;
			}
			scanner.movePointer();
		}

		if(!valid) {
			throw new APException("Identifier \"" + name + "\" is invalid or contains illegal characters, please try again");
		}

		return true;
	}


	/**	@precondition - The StringBuffer object 'name' should not be null.
	 *  @postcondition - The function returns a String constructed from the the StringBuffer 'name'.
	 **/
	String getName();


	/**	@precondition - 
	 *  @postcondition - The StringBuffer object 'name' is initialized with a new object containing the string passed as a parameter.
	 **/
	void setName(String name);

	/**	@precondition - The StringBuffer object 'name' should not be null
	 *  @postcondition - TRUE: The String passed as a parameter is same as the string obtained from StringBuffer 'name'
	 *  				 FALSE: The String passed as a parameter is not the same as the string obtained from StringBuffer 'name'
	 **/
	boolean compareName(String name);


}
