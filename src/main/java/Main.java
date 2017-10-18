
public class Main {
	SetResources resources = new SetResources();


	private void start() {
		java.util.Scanner consoleInput = new java.util.Scanner(System.in);

		while (consoleInput.hasNextLine()) {
			String line = consoleInput.nextLine();

			if(line.equals("exit")) {
				break;
			}
			else if(line.equals("")) {
				System.out.println("error no statement");
			}
			else {
				try {
					resources.processInput(line);
				}
				catch(APException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		consoleInput.close();
	}


	public static void main(String[] argv) {
		new Main().start();
	}
}
