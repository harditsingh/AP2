
import java.util.Scanner;

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
			else {
				resources.processInput(line);
			}
		}


		consoleInput.close();
	}
	
	
	public static void main(String[] argv) {
		new Main().start();
	}
}
