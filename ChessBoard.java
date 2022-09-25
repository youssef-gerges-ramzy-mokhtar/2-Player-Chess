import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

// The ChessBoard Class is for representing the Chess Board as a GUI //

class ChessBoard extends JPanel implements Board {
	// Data Members //
	// Styling Properties used for Each SpotPanel

	private static Color color1 = new Color(254,206,158);	
	private static Color color2 = new Color(210, 140, 70);

	private int numRows;
	private int numColumns;
	private SpotPanel[][] board;

	// Constructor //
	public ChessBoard() {
		// Initializing the Data Members
		numRows = 5;
		numColumns = 4;
		board = new SpotPanel[numRows][numColumns];
		init();

		// Settingg the Layout of the this JPanel
		setLayout(new GridLayout(numRows, numColumns));

		// Adding the SpotPanel to this JPanel
		for (int i = numRows - 1; i >= 0; i--) {
			for (int j = 0; j < numColumns; j++) {
				add(board[i][j]);
			}
		}

		// Setting the Border of this JPanel
		setBorder(new EmptyBorder(10, 200, 200, 200));
	}

	// init() is used to initialize the Chess Board with the Correct Chess Pieces in that Corect Positions
	private void init() {
		// Setting White Chess Pieces
		board[0][0] = new SpotPanel(new Tower(true), 0, 0, color2);
		board[0][1] = new SpotPanel(new Runner(true), 0, 1, color1);
		board[0][2] = new SpotPanel(new Jumper(true), 0, 2, color2);
		board[0][3] = new SpotPanel(new King(true), 0, 3, color1);
		board[1][3] = new SpotPanel(new Pawn(true), 1, 3, color2);

		// Setting Black Chess Pieces
		board[4][0] = new SpotPanel(new King(false), 4, 0, color2);
		board[4][1] = new SpotPanel(new Jumper(false), 4, 1, color1);
		board[4][2] = new SpotPanel(new Runner(false), 4, 2, color2);
		board[4][3] = new SpotPanel(new Tower(false), 4, 3, color1);
		board[3][0] = new SpotPanel(new Pawn(false), 3, 0, color1);

		// Setting Empty Spots
		board[1][0] = new SpotPanel(null, 1, 0, color1);
		board[1][1] = new SpotPanel(null, 1, 1, color2);
		board[1][2] = new SpotPanel(null, 1, 2, color1);

		board[3][1] = new SpotPanel(null, 3, 1, color2);
		board[3][2] = new SpotPanel(null, 3, 2, color1);
		board[3][3] = new SpotPanel(null, 3, 3, color2);

		Color currentColor = color2;
		for (int col = 0; col < numColumns; col++) {
			board[2][col] = new SpotPanel(null, 2, col, currentColor);
		
			if (currentColor == color1) {
				currentColor = color2;
			} else {
				currentColor = color1;
			}

		}
	}

	// resetColor() is used to reset the Color of each SpotPanel
	public void resetColor() {
		Color currentColor = color2;

		for (int i = numRows - 1; i >= 0; i--) {
			
			if (i % 2 == 0) {
				currentColor = color2;
			} else {
				currentColor = color1;
			}

			for (int j = 0; j < numColumns; j++) {

				board[i][j].setColor(currentColor);

				if (currentColor == color1) {
					currentColor = color2;
				} else {
					currentColor = color1;
				}

			}
		}

	}

	// resetBoard() is used to reset the Chess Board GUI
	public void resetBoard() {
		removeAll();
		updateUI();
		init();

		for (int i = numRows - 1; i >= 0; i--) {
			for (int j = 0; j < numColumns; j++) {
				add(board[i][j]);
			}
		}

	}

	// Getters
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Spot getSpot(int x, int y) {
		return board[x][y].getSpot();
	}

	public SpotPanel getSpotPanel(int x, int y) {
		return board[x][y];
	}
}



// Board is an interface, because we can't exted 2 or more classes in Java
// That is why ChessBoard implements Board