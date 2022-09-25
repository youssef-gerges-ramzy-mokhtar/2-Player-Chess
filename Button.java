import javax.swing.*;
import java.awt.*;

class Button extends JButton {
	// Data Members //

	// Styling Properties used for Each Button
	private static Dimension buttonSize = new Dimension(250, 40);
	private static Font buttonFont = new Font("Arial", Font.BOLD, 15);


	// Constructor
	public Button(String buttonTitle, Color buttonColor) {
		// Setting some properties for this Button 
		setText(buttonTitle);
		setBackground(buttonColor);
		setFont(buttonFont);
		setPreferredSize(buttonSize);
		setFocusPainted(false);
	}

}
