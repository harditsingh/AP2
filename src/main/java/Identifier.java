
public class Identifier implements IdentifierInterface {
	private StringBuffer name;



	Identifier(String name) {
		this.name = new StringBuffer(name);

	}

	public static boolean validateIdentifier(String name) {
		Scanner scanner = new Scanner(name);
		if(!scanner.isAlpha()) {
			return false;
		}

		while(scanner.hasNext()) {
			if(!scanner.isAlpha() && !scanner.isDigit()) {
				return false;
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
