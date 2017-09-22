import java.util.Scanner;

public class Main {
	SetResources resources = new SetResources();


	private void start() {
		Scanner consoleInput = new Scanner(System.in);
		consoleInput.useDelimiter(" |\n|\r|\n\r");

		while (consoleInput.hasNext()) {
			String line = consoleInput.next();

			if(line.equals("exit")) {
				break;
			}
			else if(line.equals("new")) {
				String name = consoleInput.next();
				consoleInput.useDelimiter("");
				consoleInput.next();
				consoleInput.useDelimiter(" |\n|\r|\n\r");
				String data = consoleInput.nextLine();
				resources.setValueHandler(name, data);
			}
			else if(line.equals("print")) {
				resources.printSet(consoleInput.next());
			}
			else if(line.equals("add")) {
				resources.add(consoleInput.next(), consoleInput.next());
			}
			else if(line.equals("sub")) {
				resources.subtract(consoleInput.next(), consoleInput.next());
			}

		}


		consoleInput.close();
	}

	public static void main(String[] argv) {
		new Main().start();
	}
}
