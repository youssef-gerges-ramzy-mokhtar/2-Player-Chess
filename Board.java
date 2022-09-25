interface Board {
	// Getters

	// getNumRows() returns the number of rows in a Chess Board
	public int getNumRows();

	// getNumColumns() returns the number of columns in a Chess Board
	public int getNumColumns();

	// getSpot() retunrs a Spot Object a row x, and column y
	public Spot getSpot(int x, int y);

}