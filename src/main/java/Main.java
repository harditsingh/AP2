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

		}


		consoleInput.close();
	}

	public static void main(String[] argv) {
		new Main().start();
	}
}
