
public class Identifier implements IdentifierInterface {
	private String name;
	private String type;
	
	Identifier(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean compareName(String name) {
		if(this.name.equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}

}
