import javax.swing.*;

class Queen extends Piece {

	// Constructor
	public Queen(Boolean white) {
		super(white);

		if (white) symbol = new ImageIcon(getClass().getResource("Images 1/WQ.gif"), "wq");
		else symbol = new ImageIcon(getClass().getResource("Images 1/BQ.gif"), "bq");
	}

	// the canMove() method checks if a Piece can move from the currentSpot to the finalSpot
	public Boolean canMove(Board board, Spot currentSpot, Spot finalSpot) {
		if (checkMoveValidity(currentSpot, finalSpot) == null) return false;

		Piece currentQueen = currentSpot.getPiece();

		// A Queen acts exactly the same as the Tower and exactly the same as the Runner
		// Here we create a temporary Tower and we place this temporary tower into the current spot, and then we call the canMove() method on the temp tower
		// If the temporary tower can move to the final spot that means that the Queen also can move to the final spot
		Tower tempTower = new Tower(currentSpot.getPiece().isWhite());
		currentSpot.setPiece(tempTower);

		if (tempTower.canMove(board, currentSpot, finalSpot)) {
			currentSpot.setPiece(currentQueen);
			return true;
		}

		// Here we create a temporary Runner and we place this temporary Runner into the current spot, and then we call the canMove() method on the temp Runner
		// If the temporary Runner can move to the final spot that means that the Queen also can move to the final spot
		Runner tempRunner = new Runner(currentSpot.getPiece().isWhite());
		currentSpot.setPiece(tempRunner);

		if (tempRunner.canMove(board, currentSpot, finalSpot)) {
			currentSpot.setPiece(currentQueen);
			return true;
		}

		currentSpot.setPiece(currentQueen);
		return false;
	}

}