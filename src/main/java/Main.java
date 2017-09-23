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
				resources.setStore(name, resources.dataToSet(data));
			}
			else if(line.equals("print")) {
				resources.printSet(consoleInput.next());
			}
			else if(line.equals("add")) {
				resources.union(consoleInput.next(), consoleInput.next());
			}
			else if(line.equals("sub")) {
				resources.complement(consoleInput.next(), consoleInput.next());
			}
			else if(line.equals("inter")) {
				resources.intersection(consoleInput.next(), consoleInput.next());
			}
			else if(line.equals("symdiff")) {
				resources.symmetricDifference(consoleInput.next(), consoleInput.next());
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
//		resources.processEBNF(consoleInput.nextLine());
		SetScanner trial = new SetScanner(consoleInput.nextLine());
		trial.useDelimiter(" |\n|\r");
		System.out.println(trial.nextString());
		System.out.println(trial.returnNextType());
		System.out.println(trial.nextString());
		System.out.println(trial.returnNextType());
		System.out.println(trial.nextSet());
		
	}
	
	public static void main(String[] argv) {
		new Main().test();
	}
}
