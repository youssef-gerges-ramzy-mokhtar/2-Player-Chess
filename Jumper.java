import javax.swing.*;

class Jumper extends Piece {

	// Constructor
	public Jumper(Boolean white) {
		super(white);
		
		if (white == true) {
			symbol = new ImageIcon(getClass().getResource("Images 1/WJ.gif"), "wj");
		} else {
			symbol = new ImageIcon(getClass().getResource("Images 1/BJ.gif"), "bj");
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

		// We Check if the Vertical Change is 2 and the Horizontal Change is 1 then the Jumper can Move
		// We Check if the Horizontal Change is 2 and the Vertical Change is 1 then the Jumper can Move
		if (verticalChange == 2 && horizontalChange == 1) return true;
		if (horizontalChange == 2 && verticalChange == 1) return true;

		return false;

	}

}