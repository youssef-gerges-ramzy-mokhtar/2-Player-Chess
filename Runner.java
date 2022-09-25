import javax.swing.*;

class Runner extends Piece {

	// Constructor
	public Runner(Boolean white) {
		super(white);
		
		if (white == true) {
			symbol = new ImageIcon("Images 1/WR.gif", "wr");
		} else {
			symbol = new ImageIcon("Images 1/BR.gif", "br");
		}
	}

	// the canMove() method checks if a Piece can move from the currentSpot to the finalSpot
	public Boolean canMove(Board board, Spot currentSpot, Spot finalSpot) {
		// We get the positions of the Chess Pieces on the Board, by getting the positions of the Spots they are on
		final int currentSpotRow = currentSpot.getRowPos();
		final int finalSpotRow = finalSpot.getRowPos();
		final int currentSpotCol = currentSpot.getColumnPos();
		final int finalSpotCol = finalSpot.getColumnPos();

		int[] changes = checkMoveValidity(currentSpot, finalSpot);
		if (changes == null) return false;

		int horizontalChange = changes[0];
		int verticalChange = changes[1];


		// This next part checks if any other pieces is blocking the movement of the Runner //

		// As a Runner it only moves Diagonally, this means that the Horizontal Change should be equal to the Vertical Change
		// If the Horizontal Change is not equal the Vertical Change we return false
		if (horizontalChange == verticalChange) {
			// We Calculate the gradient of the movement of the Runner
			// the gradient of a line = (y2 - y1) / (x2 - x1) = Vertical Cahnge/Horizontal Change = rise/run
			int gradient =  (finalSpotRow - currentSpotRow) / (finalSpotCol - currentSpotCol);

			// We check if the movement of the Runner has a positive gradient
			if (gradient > 0) {
				// We check if the Runner is moving up or down
				// If the Final Spot Row is larger than the Current Spot Row then the Runner is moving Up
				Boolean movingUp = finalSpotRow > currentSpotRow;

				// Here if the Runner is moving up or if the Runner is moving down we check
				// all the Diagonal Spots between the Current Spot and the Final Spot If any of those spots
				// contain a Chess Piece then the Runner cannot move
				if (movingUp) {
					int rowIndex = currentSpotRow + 1;
					int colIndex = currentSpotCol + 1;

					while (rowIndex < finalSpotRow) {
						if (board.getSpot(rowIndex, colIndex).getPiece() != null) return false;

						// Both rowIndes & colIndex both are incremented by one, because it is a diagonal move
						// and because the Runner is moving up, and the movement has a positive gradient
						rowIndex++;
						colIndex++;
					}

				} else {

					int rowIndex = currentSpotRow - 1;
					int colIndex = currentSpotCol - 1;

					while (rowIndex > finalSpotRow) {
						if (board.getSpot(rowIndex, colIndex).getPiece() != null) return false;

						// Both rowIndes & colIndex both are decremented by one, because it is a diagonal move
						// and because the Runner is moving down, and the movement has a positive gradient
						rowIndex--;
						colIndex--;
					}

				}

				return true;

			} else {
				// We check if the Runner is moving up or down
				// If the Final Spot Row is larger than the Current Spot Row then the Runner is moving Up
				Boolean movingUp = finalSpotRow > currentSpotRow;

				// Here if the Runner is moving up or if the Runner is moving down we check
				// all the Diagonal Spots between the Current Spot and the Final Spot If any of those spots
				// contain a Chess Piece then the Runner cannot move
				if (movingUp) {
					int rowIndex = currentSpotRow + 1;
					int colIndex = currentSpotCol - 1;

					while (rowIndex < finalSpotRow) {
						if (board.getSpot(rowIndex, colIndex).getPiece() != null) return false;

						// We increment rowIndex by one & decrement colIndex by 1, because it is a diagonal move
						// and because the Runner is moving up so the rowIndex is increasing
						// and the movement has a negative gradient, so the colIndex is decreasing
						rowIndex++;
						colIndex--;
					}
				} else {
					int rowIndex = currentSpotRow - 1;
					int colIndex = currentSpotCol + 1;

					while (rowIndex > finalSpotRow) {
						if (board.getSpot(rowIndex, colIndex).getPiece() != null) return false;

						// We decrement rowIndex by one & increment colIndex by 1, because it is a diagonal move
						// and because the Runner is moving down so the rowIndex is decreasing
						// and the movement has a negative gradient, so the colIndex is increasing
						rowIndex--;
						colIndex++;
					}
				}

				return true;

			}

		}


		return false;

	}

}