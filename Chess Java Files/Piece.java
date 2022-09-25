import javax.swing.*;

abstract class Piece {
	// Data Members //
	private Boolean white;
	protected ImageIcon symbol;

	// Constructor
	public Piece(Boolean white) {
		this.white = white;
	}

	// Getters
	public Boolean isWhite() {
		return white;
	}

	public ImageIcon getSymbol() {
		return symbol;
	}

	// Check Move Validity applies to all Chess Pieces, except the Pawn
	// Because the Pawn moves only in one direction
	// checkMoveValidity() method return the Horizontal Change and the Vertical Change in an array of integer
	// If there is any problem with the validity of the spots it returns null
	protected int[] checkMoveValidity(Spot currentSpot, Spot finalSpot) {
		// we get the pieces at the currentSpot and at the finalSpot
		final Piece currentPiece = currentSpot.getPiece();
		final Piece finalPiece = finalSpot.getPiece();

		if (currentPiece == null) return null; // We check if the currentPiece is not null

		// We check if the currentPiece is the same as the Object that the canMove() Method was called on
		// This is used to prevent the client program from calling the canMove() method
		// on a Piece and passing a Current Spot containing another random Piece
		if (currentPiece != this) return null;

		// We check if the finalPiece has the same color as the currentPiece
		if (finalPiece != null) {
			if (currentPiece.isWhite() == finalPiece.isWhite()) return null;
		}

		// We get the positions of the Chess Pieces on the Board, by getting the positions of the Spots they are on
		final int currentSpotRow = currentSpot.getRowPos();
		final int finalSpotRow = finalSpot.getRowPos();
		final int currentSpotCol = currentSpot.getColumnPos();
		final int finalSpotCol = finalSpot.getColumnPos();

		// We Calculate the Horizontal Change and Vertical Change from the Current Spot to the Final Spot
		final int horizontalChange = Math.abs(currentSpotCol - finalSpotCol);
		final int verticalChange = Math.abs(currentSpotRow - finalSpotRow);

		// We check if the Current Spot & Final Spot are not the same position
		if (horizontalChange == 0 && verticalChange == 0) return null;

		int[] changes = {horizontalChange, verticalChange};
		return changes;
	}

	// Abstract Method
	abstract public Boolean canMove(Board board, Spot currentSpot, Spot finalSpot);
}