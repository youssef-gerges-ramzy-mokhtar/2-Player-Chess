import javax.swing.*;

class Runner extends Piece {

	// Constructor
	public Runner(Boolean white) {
		super(white);
		
		if (white == true) {
			symbol = new ImageIcon(getClass().getResource("Images 1/WR.gif"), "wr");
		} else {
			symbol = new ImageIcon(getClass().getResource("Images 1/BR.gif"), "br");
		}
	}

	// the canMove() method checks if a Piece can move from the currentSpot to the finalSpot
	public Boolean canMove(Board board, Spot currentSpot, Spot finalSpot) {
		int[] changes = checkMoveValidity(currentSpot, finalSpot);
		if (changes == null) return false;

		// We get the positions of the Chess Pieces on the Board, by getting the positions of the Spots they are on
		final int currentSpotRow = currentSpot.getRowPos();
		final int finalSpotRow = finalSpot.getRowPos();
		final int currentSpotCol = currentSpot.getColumnPos();
		final int finalSpotCol = finalSpot.getColumnPos();

		int horizontalChange = changes[0];
		int verticalChange = changes[1];


		// This next part checks if any other pieces is blocking the movement of the Runner //

		// As a Runner it only moves Diagonally, this means that the Horizontal Change should be equal to the Vertical Change
		// If the Horizontal Change is not equal the Vertical Change we return false
		if (horizontalChange != verticalChange) return false;


		// We Calculate the gradient of the movement of the Runner
		// the gradient of a line = (y2 - y1) / (x2 - x1) = Vertical Cahnge/Horizontal Change = rise/run
		int gradient =  (finalSpotRow - currentSpotRow) / (finalSpotCol - currentSpotCol);

		// We always start from the lower row and move to the higher and keep checking if there is any pieces between the lower and the higher row
		int rowIndex = Math.min(currentSpotRow, finalSpotRow) + 1;
		int colIndex, col_incrementer;

		// If the gradient is positive we choose the smaller column, because we are moving from the lower row to the upper row and the gradient is positive so as the row increase the column will increase
		// If the gradient is negative we choose the higher column, because we are moving from the lower row to the upper row and the gradient is negative so as the row increase the column will decrease
		// the col_incrementer is used to change the colIndex val based on the gradient
		if (gradient > 0) {
			col_incrementer = 1;
			colIndex = Math.min(currentSpotCol, finalSpotCol) + 1;
		} else {
			col_incrementer = -1;
			colIndex = Math.max(currentSpotCol, finalSpotCol) - 1;
		}

		// Here if the Runner is moving up or if the Runner is moving down we check
		// all the Diagonal Spots between the Current Spot and the Final Spot If any of those spots
		// contain a Chess Piece then the Runner cannot move
		while (rowIndex < Math.max(currentSpotRow, finalSpotRow)) {
			if (board.getSpot(rowIndex, colIndex).getPiece() != null) return false;

			rowIndex++;
			colIndex += col_incrementer;
		}

		return true;


	}

}