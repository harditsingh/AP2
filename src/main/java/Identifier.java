
public class Identifier implements IdentifierInterface {
	private StringBuffer name;

	Identifier(String name) {
		this.name = new StringBuffer(name);
	}

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
