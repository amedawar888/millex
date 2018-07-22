package Ui;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gui extends JPanel {

	public Gui() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel(new FlowLayout());
		titlePanel.add(new JLabel("City of Millex"));
		this.add(titlePanel);
	}
}


