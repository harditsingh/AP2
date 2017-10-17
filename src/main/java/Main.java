
public class Main {
	SetResources resources = new SetResources();


	private void start() {
		java.util.Scanner consoleInput = new java.util.Scanner(System.in);

		while (consoleInput.hasNext()) {
			String line = consoleInput.nextLine();

			if(line.equals("exit")) {
				break;
			}
			else if(line.startsWith("/")) {
				continue;
			}
			else if(line.equals("")) {
				System.err.println("No statement!");
			}
			else {
				try {
					resources.processInput(line);
				}
				catch(APException e) {
					System.err.println(e.getMessage());
				}
			}
		}


		consoleInput.close();
	}


	public static void main(String[] argv) {
		new Main().start();
	}
}
