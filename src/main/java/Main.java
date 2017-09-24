import java.util.Scanner;

public class Main {
	SetResources resources = new SetResources();


	private void start() {
		Scanner consoleInput = new Scanner(System.in);
		
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
	
	private void actualStart() {
		Scanner consoleInput = new Scanner(System.in);
		consoleInput.useDelimiter(" |\n|\r|\n\r");

		while (consoleInput.hasNext()) {
			String line = consoleInput.next();
			
			if(Character.isAlphabetic(line.charAt(0))) {
				String name = consoleInput.next();
				consoleInput.useDelimiter("");
				consoleInput.next();
				consoleInput.useDelimiter(" |\n|\r|\n\r");
				String data = consoleInput.nextLine();
				resources.setStore(name, resources.processEBNF(data));
			}
		}
	}

	void test() {
		Scanner consoleInput = new Scanner(System.in);
		resources.processInput(consoleInput.nextLine());
//		SetScanner trial = new SetScanner(consoleInput.nextLine());
//		System.out.println(trial.nextString());
//		System.out.println(trial.returnNextType());
//		System.out.println(trial.nextString());
//		System.out.println(trial.returnNextType());
//		System.out.println(trial.nextSet());
		
	}
	
	public static void main(String[] argv) {
		new Main().start();
	}
}
