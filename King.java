import javax.swing.*;

class King extends Piece {

	// Constructor
	public King(Boolean white) {
		super(white);

		if (white == true) {
			symbol = new ImageIcon("Images 1/WK.gif");
		} else {
			symbol = new ImageIcon("Images 1/BK.gif");
		}
	}

	// the canMove() method checks if a Piece can move from the currentSpot to the finalSpot
	public Boolean canMove(Board board, Spot currentSpot, Spot finalSpot) {
		// We Check the Validity of the Spots and the Pieces on the Spots using the checkMoveValidity Method
		// If the checkMoveValidity() returned null then there is a problem
		// Else we store the Horizontal Change & Vertical Change that are returned from the checkMoveValidity() method
		int[] changes = checkMoveValidity(currentSpot, finalSpot);
		if (changes == null) return false;
		
		int horizontalChange = changes[0];
		int verticalChange = changes[1];

		// We Check if the Horizontal Change is 1 and the Vertical Change is 0 then the King can Move (This is Horizontal Movement)
		// We Check if the Horizontal Change is 0 and the Vertical Change is 1 then the King can Move (This is Vertical Movement)
		// We Check if the Horizontal Change is 1 and the Vertical Change is 1 then the King can Move (This is Diagonal Movement)
		if (horizontalChange == 1 && verticalChange == 0) return true;
		if (horizontalChange == 0 && verticalChange == 1) return true;
		if (horizontalChange == 1 && verticalChange == 1) return true;

		return false;

	}

}