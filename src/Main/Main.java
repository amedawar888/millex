package Main;

import Ui.Controller;

public class Main {

	public static void main(String[] args) {

		Game game = new Game();
		new Thread(game).start();
		Controller ui = new Controller(game);

	}

}
