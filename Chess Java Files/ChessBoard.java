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
		numRows = 8;
		numColumns = 8;
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
		board[0][0] = new SpotPanel(new Tower(true), 0, 0, color2);
		board[0][1] = new SpotPanel(new Jumper(true), 0, 1, color1);
		board[0][2] = new SpotPanel(new Runner(true), 0, 2, color2);
		board[0][3] = new SpotPanel(new Queen(true), 0, 3, color1);
		board[0][4] = new SpotPanel(new King(true), 0, 4, color2);
		board[0][5] = new SpotPanel(new Runner(true), 0, 5, color1);
		board[0][6] = new SpotPanel(new Jumper(true), 0, 6, color2);
		board[0][7] = new SpotPanel(new Tower(true), 0, 7, color1);

		Color currentColor = color1;
		for (int i = 0; i < numColumns; i++) {
			board[1][i] = new SpotPanel(new Pawn(true), 1, i, currentColor);

			if (currentColor == color1) currentColor = color2;
			else currentColor = color1;
		}

		currentColor = color2;
		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < numColumns; j++) {
				board[i][j] = new SpotPanel(null, i, j, currentColor);
				
				if (currentColor == color1) currentColor = color2;
				else currentColor = color1;
			}

			if (currentColor == color1)	currentColor = color2;
			else currentColor = color1;
		}

		currentColor = color2;
		int blackPawnRow = numRows - 1 - 1;
		for (int i = 0; i < numColumns; i++) {
			board[blackPawnRow][i] = new SpotPanel(new Pawn(false), blackPawnRow, i, currentColor);

			if (currentColor == color1) currentColor = color2;
			else currentColor = color1;
		}	

		board[7][0] = new SpotPanel(new Tower(false), 7, 0, color1);
		board[7][1] = new SpotPanel(new Jumper(false), 7, 1, color2);
		board[7][2] = new SpotPanel(new Runner(false), 7, 2, color1);
		board[7][3] = new SpotPanel(new Queen(false), 7, 3, color2);
		board[7][4] = new SpotPanel(new King(false), 7, 4, color1);
		board[7][5] = new SpotPanel(new Runner(false), 7, 5, color2);
		board[7][6] = new SpotPanel(new Jumper(false), 7, 6, color1);
		board[7][7] = new SpotPanel(new Tower(false), 7, 7, color2);
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