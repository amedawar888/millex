package Ui;

import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gui extends JPanel {

	private JLabel popLabel;
	
	public Gui() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel titlePanel = new JPanel(new FlowLayout());
		titlePanel.add(new JLabel("City of Millex"));
		
		popLabel = new JLabel("Population: -- males, -- females");
		JPanel popPanel = new JPanel(new FlowLayout());
		popPanel.add(popLabel);
		
		this.add(titlePanel);
		this.add(popPanel);
	}
	
	public void update(HashMap<String, Integer> pop) {
		popLabel.setText("Population: " + pop.get("males") + " males, " + pop.get("females") + " females");
		
		this.repaint();
	}
}


