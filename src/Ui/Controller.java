package Ui;

import javax.swing.JFrame;

import Main.Game;

public class Controller {

	public JFrame window;
	private Gui ui;
	private Game game;
	
	public Controller(Game g) {
		game = g;
		
		window = new JFrame();
		window.setBounds(200,300,400,400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ui = new Gui();
		
		ui.update(game.exportPopulation());
		
		window.getContentPane().add(ui);
		
		window.setVisible(true);
		
	}
}
