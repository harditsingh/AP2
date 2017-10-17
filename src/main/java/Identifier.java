
public class Identifier implements IdentifierInterface {
	private StringBuffer name;



	Identifier(String name) {
		this.name = new StringBuffer(name);

	}

	public static boolean validateIdentifier(String name) throws APException {
		Scanner scanner = new Scanner(name);
		if(!scanner.isAlpha()) {
			throw new APException("Identifier \"" + name + "\" is invalid or contains illegal characters, please try again");
		}

		while(scanner.hasNext()) {
			if(!scanner.isAlpha() && !scanner.isDigit()) {
				throw new APException("Identifier \"" + name + "\" is invalid or contains illegal characters, please try again");
			}
			scanner.movePointer();
		}
		return true;
	}

	//get char
	public String getName() {
		return name.toString();
	}

	public void setName(String name) {
		this.name = new StringBuffer(name);
	}

	public boolean compareName(String name) {
		String tempString = this.name.toString();
		if(tempString.equals(name)) {
			return true;
		}
		return false;
	}

}
