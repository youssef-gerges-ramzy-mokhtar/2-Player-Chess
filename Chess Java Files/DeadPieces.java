import java.util.*;
import javax.swing.*;
import java.awt.*;

class DeadPieces extends JPanel {
	// Data Members //
	private Text title;
	private JPanel deadPiecesGrid;
	private Buttons buttons;

	// Constructor //
	public DeadPieces() {
		// Initialising the Data Members
		title = new Text("Dead Pieces", new Color(203, 0, 0), Color.white, 30, 0, 20, 10, 20);
		deadPiecesGrid = new JPanel(new GridLayout(3, 3));
		buttons = new Buttons();

		// Setting the Layout of this JPanel
		setLayout(new BorderLayout());

		// Adding the title, deadPiecesGrid and buttons to this JPanel
		add(title, BorderLayout.NORTH);
		add(deadPiecesGrid, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);

		// Setting the Border Color of this JPanel
		setBorder(BorderFactory.createLineBorder(Color.black));			

	}

	// addDeadPieces() receives a LinkedList of Piece Objects
	// and add the Dead Pieces to the deadPiecesGrid JPanel
	public void addDeadPieces(LinkedList<Piece> deadPieces) {
		deadPiecesGrid.removeAll();
		deadPiecesGrid.repaint();

		Iterator iterator = deadPieces.iterator();
		while (iterator.hasNext()) {
			Piece deadPiece = (Piece) iterator.next();
			deadPiecesGrid.add(new JLabel(deadPiece.getSymbol()));
		}
	}

	// resetDeadPieces() clears the deadPiecesGrid JPanel
	public void resetDeadPieces() {
		deadPiecesGrid.removeAll();
		deadPiecesGrid.repaint();
	}

	// Getters //
	public Buttons getButtonsPanel() {
		return buttons;
	}
}