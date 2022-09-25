import javax.swing.*;

class Pawn extends Piece {

	// Constructor
	public Pawn(Boolean white) {
		super(white);

		if (white == true) {
			symbol = new ImageIcon(getClass().getResource("Images 1/WP.gif"));
		} else {
			symbol = new ImageIcon(getClass().getResource("Images 1/BP.gif"));
		}
	}

	// the canMove() method checks if a Piece can move from the currentSpot to the finalSpot
	public Boolean canMove(Board board, Spot currentSpot, Spot finalSpot) {
		// we get the pieces at the currentSpot and at the finalSpot
		final Piece currentPiece = currentSpot.getPiece();
		final Piece finalPiece = finalSpot.getPiece();

		if (currentPiece == null) return false; // We check if the currentPiece is not null

		// We check if the currentPiece is the same as the Object that the canMove() Method was called on
		// This is used to prevent the client program from calling the canMove() method
		// on a Piece and passing a Current Spot containing another random Piece
		if (currentPiece != this) return false;

		// We check if the finalPiece has the same color as the currentPiece
		if (finalPiece != null) {
			if (currentPiece.isWhite() == finalPiece.isWhite()) return false;
		}

		// We get the positions of the Chess Pieces on the Board, by getting the positions of the Spots they are on
		final int currentSpotRow = currentSpot.getRowPos();
		final int finalSpotRow = finalSpot.getRowPos();
		final int currentSpotCol = currentSpot.getColumnPos();
		final int finalSpotCol = finalSpot.getColumnPos();

		// We Calculate the Horizontal Change and Vertical Change from the Current Spot to the Final Spot
		final int horizontalChange = finalSpotCol - currentSpotCol;
		final int verticalChange = finalSpotRow - currentSpotRow;

		// We check if the Current Spot & Final Spot are not the same position
		if (horizontalChange == 0 && verticalChange == 0) return false;

		// We get the Number of Rows in the Board
		final int boardSize = board.getNumRows();

		// We Check if the Color of the Current Piece is White or Black
		// Because White Pawns always move Up, while Black Pawns always move Down
		if (currentPiece.isWhite()) {
			// We check if the Vertical Change is 1 & Horizontal Change is 0 and there is not pieces in front of the Pawn
			if (verticalChange == 1 && horizontalChange == 0 && finalPiece == null) return true;

			// We Check if there is a final Piece, and then the Pawn can move Vertically 1 unit up
			// And can move either left or right by 1 unit
			// This will result in the Pawn moving diagonally 1 unit to capture a Piece
			if (finalPiece != null) {
				if (verticalChange == 1 && (horizontalChange == 1 || horizontalChange == -1)) return true;
			}

			// We Check if the Pawn in the Second Row and the Vertical Change is 2 and the Horizontal Change is 0
			// and that there is no pieces in front of the Pawn
			if (verticalChange == 2 && horizontalChange == 0 && finalPiece == null && currentSpotRow == 1 && board.getSpot(2, currentSpotCol).getPiece() == null) return true;

		} else {
			// We check if the Vertical Change is -1 & Horizontal Change is 0 and there is not pieces in front of the Pawn
			if (verticalChange == -1 && horizontalChange == 0 && finalPiece == null) return true;

			// We Check if there is a final Piece, and then the Pawn can move Vertically 1 unit down
			// And can move either left or right by 1 unit
			// This will result in the Pawn moving diagonally 1 unit to capture a Piece
			if (finalPiece != null) {
				if (verticalChange == -1 && (horizontalChange == 1 || horizontalChange == -1)) return true;
			}

			// We Check if the Pawn in the Row befeore the End and the Vertical Change is -2 and the Horizontal Change is 0
			// and that there is no pieces in front of the Pawn
			if (verticalChange == -2 && horizontalChange == 0 && finalPiece == null && currentSpotRow == boardSize - 2 && board.getSpot(boardSize - 3, currentSpotCol).getPiece() == null) return true;
		}


		return false;
	}

}