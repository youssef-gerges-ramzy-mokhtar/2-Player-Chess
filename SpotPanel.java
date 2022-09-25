import javax.swing.*;
import java.awt.*;

// The SpotPanel class is a JPanel representing one square in a chess board as GUI //

class SpotPanel extends JPanel {
	// Data Members //
	private Spot spot;
	private JLabel content;

	// Constructor //
	public SpotPanel(Piece piece, int row, int col, Color color) {
		// Initializing the spot Data Member
		spot = new Spot(piece, row, col);

		// Setting some Properties for this JPanel
		setLayout(new BorderLayout());
		setBackground(color);
		setBorder(BorderFactory.createLineBorder(Color.black));

		// Adding an Image on this JPanel based on the Piece
		content = new JLabel();
		content.setIcon(null);
		content.setHorizontalAlignment(JLabel.CENTER);

		if (spot.getPiece() != null) {
			content.setIcon(spot.getPiece().getSymbol());
		}

		add(content);
	}


	// Getters
	public Spot getSpot() {
		return spot;
	}

	// Setters
	public void setContent(ImageIcon icon) {
		content.setIcon(icon);
	}

	public void setColor(Color color) {
		setBackground(color);
	}

}