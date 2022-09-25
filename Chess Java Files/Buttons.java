import java.util.*;
import javax.swing.*;
import java.awt.*;

// Buttons Class is a JPanel containg the Buttons in the Program //

class Buttons extends JPanel {
	// Data Members //
	private JButton[] buttons;
	
	// Constructor //
	public Buttons() {
		// Setting the Layout of this JPanel
		setLayout(new GridLayout(4, 1));

		// Adding 4 Button Objects to the buttons array
		buttons = new JButton[4];
		buttons[0] = new Button("Restart Game", new Color(230, 0, 35));
		buttons[1] = new Button("Offer Draw", new Color(75, 129, 244));
		buttons[2] = new Button("Accept Draw", new Color(51, 178, 73));
		buttons[3] = new Button("Reject Draw", new Color(255, 189, 3));

		// Adding the buttons to this JPanel
		for (int i = 0; i < buttons.length; i++) {
			add(buttons[i]);
		}
	}


	// Getters //
	public JButton[] getButtons() {
		return buttons;
	}
}