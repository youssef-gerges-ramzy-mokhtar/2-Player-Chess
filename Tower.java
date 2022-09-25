import javax.swing.*;

class Tower extends Piece {

	// Constructor
	public Tower(Boolean white) {
		super(white);

		if (white == true) {
			symbol = new ImageIcon("Images 1/WT.gif", "wt");
		} else {
			symbol = new ImageIcon("Images 1/BT.gif", "bt");
		}
	}

	// the canMove() method checks if a Piece can move from the currentSpot to the finalSpot
	public Boolean canMove(Board board, Spot currentSpot, Spot finalSpot) {

		int[] changes = checkMoveValidity(currentSpot, finalSpot);
		if (changes == null) return false;

		int horizontalChange = changes[0];
		int verticalChange = changes[1];

		// We get the positions of the Chess Pieces on the Board, by getting the positions of the Spots they are on
		final int currentSpotRow = currentSpot.getRowPos();
		final int finalSpotRow = finalSpot.getRowPos();
		final int currentSpotCol = currentSpot.getColumnPos();
		final int finalSpotCol = finalSpot.getColumnPos();

		// This next part checks if any other pieces is blocking the movement of the Tower //

		// We Check if there is no horizontalChange then the Tower is moving vertically
		// We Check if there is no verticalChange then the Tower is moving horizontally
		Boolean verticalMove = (horizontalChange == 0);
		Boolean horizontalMove = (verticalChange == 0);

		if (verticalMove) {
			// We check if it is moving up or down
			// If the Final Spot Row is larger than the Current Spot Row then the Tower is moving Up
			Boolean movingUp = finalSpotRow > currentSpotRow;

			// Here if the Tower is moving up or if the tower is moving down we check
			// all the Spots between the Current Spot and the Final Spot If any of those spots
			// contain a Chess Piece then the Tower cannot move
			if (movingUp) {
				for (int i = currentSpotRow + 1; i < finalSpotRow; i++) {
					if (board.getSpot(i, currentSpotCol).getPiece() != null) return false;
				}
			} else {
				for (int i = currentSpotRow - 1; i > finalSpotRow; i--) {
					if (board.getSpot(i, currentSpotCol).getPiece() != null) return false;
				}
			}

			return true;
		}

		if (horizontalMove) {
			// We check if it is moving left or right
			// If the Final Spot Column is larger than the Current Spot Column then the Tower is moving right
			Boolean movingRight = finalSpotCol > currentSpotCol;

			// Here if the Towere is moving right or if the tower is moving left we check
			// all the Spots between the Current Spot and the Final Spot If any of those spots
			// contain a Chess Piece then the Tower cannot move
			if (movingRight) {
				for (int i = currentSpotCol + 1; i < finalSpotCol; i++) {
					if (board.getSpot(currentSpotRow, i).getPiece() != null) return false;
				}
			} else {
				for (int i = currentSpotCol - 1; i > finalSpotCol; i--) {
					if (board.getSpot(currentSpotRow, i).getPiece() != null) return false;
				}
			}

			return true;

		}

		return false;

	}

}