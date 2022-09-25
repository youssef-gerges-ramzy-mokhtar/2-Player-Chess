import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

class Text extends JPanel {
	// Data Members //
	private JLabel titleLabel;

	// Constructors //
	public Text(String str) {
		this(str, Color.black, Color.lightGray, 40, 0, 0, 0, 0);
	}

	public Text(String str, Color textColor, Color background, int fontSize, int top, int left, int bottom, int right) {
		titleLabel = new JLabel(str, SwingConstants.CENTER);

		// Setting some Properties for the titleLabel
		titleLabel.setBackground(background);
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
		titleLabel.setForeground(textColor);

		// Setting the Layout of this JPanel, and adding the titleLable to this JPanel
		// Setting the Border of this JPanel
		setLayout(new BorderLayout());
		add(titleLabel, BorderLayout.CENTER);
		setBorder(new EmptyBorder(top, left, bottom, right));
	}


	// Setters //
	public void setText(String str) {
		titleLabel.setText(str);
	}

}