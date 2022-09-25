class Spot {
	// Data Members //
	private Piece piece;
	private int rowPos;
	private int columnPos;

	// Constructor
	public Spot(Piece piece, int row, int col) {
		this.piece = piece;
		rowPos = row;
		columnPos = col;
	}

	// Setters
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	// Getters
	public Piece getPiece() {
		return piece;
	}

	public int getRowPos() {
		return rowPos;
	}

	public int getColumnPos() {
		return columnPos;
	}
}