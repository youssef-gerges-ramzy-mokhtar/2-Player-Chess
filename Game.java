import java.util.*;
import javax.swing.*;
import java.awt.*;

// The Game Class controls all the Logic that goes behind the Chess Game //

class Game{
	// Data Members //
	private Board chessBoard;
	private Board simulationBoard;
	private Player[] players;
	private Player currentPlayer;
	private LinkedList<Piece> killedPieces = new LinkedList<Piece>();
	private int gameStatus;
	private int fiftyMoveRule;

	public Game(Board board) {
		/* 
		Setting the Game Status
		0: Game Continuing
		1: Player 1 Won
		2: Player 2 Won
		3: Draw
		*/
		gameStatus = 0;

		// Setting Board & Simulated Chess Board
		// The Simulated Board is used to check for different possibilities of movements that might cause Checks
		// or Checkmates
		chessBoard = board;
		simulationBoard = (Board) new ChessBoard();

		// Setting Players
		players = new Player[2];
		players[0] = new Player(true);
		players[1] = new Player(false);
		currentPlayer = players[0];

		fiftyMoveRule = 0;
	}

	// validPosition() method is used to validate a given Position values
	private Spot validPosition(int x, int y) {
		// Check if Coordinates are on the Chess Board
		if (x < 0 || y < 0) return null;

		int numRows = chessBoard.getNumRows();
		int numCols = chessBoard.getNumColumns();
		if (x >= numRows || y >= numCols) return null;

		// Get the Spots from the ChessBoard
		return chessBoard.getSpot(x, y);
	}

	// The makeMove() method takes for integers
	// and if all the conditions is met the move is done and the Board is updated, and true is returned
	// else if there is a problem in the move false is returned
	public boolean makeMove(int initialX, int initialY, int finalX, int finalY) {
		// First we check that the Game status is equal to zero
		if (gameStatus != 0) return false;

		// We Generate a Spot Object from the given Positions, using the validPosition() method
		// We check that the spots are not null, becuase if they were null then there is a problem
		// with the positions provided
		Spot currentSpot = validPosition(initialX, initialY);
		Spot finalSpot = validPosition(finalX, finalY);
		if(currentSpot == null || finalSpot == null) return false;

		// We check that the current spot is not equal the final spot
		if (initialX == finalX && initialY == finalY) return false;

		// We get the Spot from the simulation Board at the provided positions
		Spot currentSpotSimulation = simulationBoard.getSpot(initialX, initialY);
		Spot finalSpotSimulation = simulationBoard.getSpot(finalX, finalY);

		// We get the Pieces at the Current Spot and the Final Spot
		Piece currentSpotPiece = currentSpot.getPiece();
		Piece finalSpotPiece = finalSpot.getPiece();
		
		// Check if the Current Spot contains a Chess Piece, else the Player didn't choose a chess Piece to Move
		if (currentSpotPiece == null) return false;
		
		// Check if the Current Player Choose the Correct Chess Piece Color
		if (currentSpotPiece.isWhite() != currentPlayer.isWhite()) return false;

		// Check if the Chess Piece Can Move from the Current Spot to the Final Spot
		if (currentSpotPiece.canMove(chessBoard, currentSpot, finalSpot) == false) return false;


		// If the Chess Piece can move, we update the simulationBoard, by moving the Chess Piece
		// From the Current Spot to the Final Spot
		finalSpotSimulation.setPiece(currentSpotSimulation.getPiece());
		currentSpotSimulation.setPiece(null);

		
		// We Check if this movement will lead to the King being in Check using the isCheck() method
		// If that is the case we reset the simulation board
		if (isCheck()) {
			resetSimulationBoard();
			return false;
		}

		// If the King was not in Check //

		// We Check if there was a Piece at Final Spot, this means that this piece was captured
		// and we add this piece to the killedPieces LinkedList
		// And we set the firtyMoveRule to 0, because a piece was captured
		// Else we increment the fityMoveRule by 1
		if (finalSpotPiece != null) {
			killedPieces.add(finalSpot.getPiece());
			fiftyMoveRule = 0;
		} else {
			fiftyMoveRule++;
		}

		// We Check the Fifty Moves Rule, to end the Game in Draw
		checkFiftyMovesRule();

		// If the Chess Piece can move and there was no Check, we update the chessBoard, by moving the Chess Piece
		// From the Current Spot to the Final Spot
		finalSpot.setPiece(currentSpot.getPiece());
		currentSpot.setPiece(null);

		// Then we Change the Player Turn
		if (currentPlayer == players[0]) {
			currentPlayer = players[1];
		} else {
			currentPlayer = players[0];
		}

		// We Check if there is a CheckMate or a StaleMate by using the isCheckMate() function
		isCheckMate();

		// Finally we check the InsufficientMaterial() rule to change the Game Status To Draw
		checkInsufficientMaterial();
		return true;
	}

	// The resetSimulationBoard() method is used to copy all the pieces from the chessBoard to the simulationBoard
	public void resetSimulationBoard() {
		for (int i = 0; i < chessBoard.getNumRows(); i++) {
			for (int j = 0; j < chessBoard.getNumColumns(); j++) {
				simulationBoard.getSpot(i, j).setPiece(chessBoard.getSpot(i, j).getPiece());
			}
		}
	}


	// isCheck() method retunrs true if the King is under Check on the simulationBoard
	private Boolean isCheck() {

		// First we locate the position of the Current Player King Piece
		// By Scaning the simulationBoard and checking each piece on every spot on the simulationBoard
		Spot currentPlayerKingSpot = null;
		int boardNumRows = simulationBoard.getNumRows();
		int boardNumColumns = simulationBoard.getNumColumns();

		for (int i = 0; i < boardNumRows; i++) {
			for (int j = 0; j < boardNumColumns; j++) {
				Spot currentSpot = simulationBoard.getSpot(i, j);
				Piece currentPiece = currentSpot.getPiece();
				
				if (currentPiece != null && currentPiece.isWhite() == currentPlayer.isWhite() && currentPiece instanceof King) {
					currentPlayerKingSpot = currentSpot;
				}

			}
		}


		// This is just to make the java code compile
		// If this if statment evaluates to true under any case that means there is an error in the Game Logic
		if (currentPlayerKingSpot == null) {
			return false;
		}


		// Second we check if there is any piece from the other player can move to the King Piece Spot
		for (int i = 0; i < boardNumRows; i++) {
			for (int j = 0; j < boardNumColumns; j++) {
				Spot currentSpot = simulationBoard.getSpot(i, j);
				Piece currentPiece = currentSpot.getPiece();

				if (currentPiece != null && currentPlayer.isWhite() != currentPiece.isWhite()) {
					if (currentPiece.canMove(simulationBoard, currentSpot, currentPlayerKingSpot)) return true;
				}

			}
		}

		return false;
	}

	// isCheckMate() method is used to check for CheckMates & StaleMates
	private void isCheckMate() {
		// everyPossibleMove is a LinkedList containing all the possible moves of all pieces of the Current Player
		// initialPositions is a LinkedList to associate with the everyPosibleMove LinkedList the
		// Initial Spot of every piece of the Current Player
		LinkedList<LinkedList<Spot>> everyPossibleMove = new LinkedList<LinkedList<Spot>>();
		LinkedList<Spot> finalPositions;
		LinkedList<Spot> initialPositions = new LinkedList<Spot>();


		// We Check if the Current Player is in Check, to determine if the Current Player is in StaleMate or not
		Boolean staleMate = true;
		if (isCheck()) staleMate = false;

		// We Loop over the simulationBoard
		// First we check if the currentSpot on the simulationBoard has a Chess Piece
		// Second we check if the Piece on that Spot has the same Color as the Current Player
		// Then we get all the possible Spots these piece can move to and store it to finalPositions
		// We add the finalPositions to everyPossibleMove
		// We add the current Spot of the Piece to initialPositions
		for (int i = 0; i < simulationBoard.getNumRows(); i++) {
			for (int j = 0; j < simulationBoard.getNumColumns(); j++) {
				if (simulationBoard.getSpot(i, j).getPiece() != null) {
					if (simulationBoard.getSpot(i, j).getPiece().isWhite() == currentPlayer.isWhite()) {
						finalPositions = getMovesCanMake(simulationBoard, simulationBoard.getSpot(i, j).getRowPos(), simulationBoard.getSpot(i, j).getColumnPos());
						if (finalPositions != null) {
							everyPossibleMove.add(finalPositions);
							initialPositions.add(simulationBoard.getSpot(i, j));
						}
					}
				}
			}
		}


		// We Loop over the everyPossibleMove LinkedList
		for (int i = 0; i < everyPossibleMove.size(); i++) {
			// finalPositions is a Linked List contined Every Possible Move of a Certain Chess Piece
			// currentSpot containes the Spot that holds the current Chess Piece associated with finalPositions
			finalPositions = everyPossibleMove.get(i);
			Spot currentSpot = initialPositions.get(i);

			// We Loop over the finalPositions LinkedList
			for (int j = 0; j < finalPositions.size(); j++) {
				// We move the current Chess Piece on the Current Spot
				// To every possible Spot that the Current Piece can move to
				finalPositions.get(j).setPiece(currentSpot.getPiece());
				currentSpot.setPiece(null);
			
				// If there is a possible move that will not result in check
				// Then it is not checkMate and we reset the simulationBoard and exit the function
				if (!isCheck()) {
					resetSimulationBoard();
					return;
				}

				// After each move of we reset the simulationBoard, to check the next possible move
				resetSimulationBoard();
			}
		}


		// If after checking every possible move, and it is always a check //

		// We check if it is a staleMate, and if that is the case we set the gameStatus to 3
		if (staleMate) {
			gameStatus = 3;
			return;
		}

		// If it is not satleMate then it is a CheckMate
		// We check if the CurrentPlayer is White then the Black Player won and the gameStatus is changed to 2
		// If the CurrentPlayer is Black then the White Player won and the gameStatus is changed to 1
		if (currentPlayer.isWhite()) {
			gameStatus = 2;
		} else {
			gameStatus = 1;
		}
	}



	// getMovesCanMake() returns a LinkedList of all the Spots that a Piece at a Crrent Spot can move to
	public LinkedList<Spot> getMovesCanMake(Board board, int initialX, int initialY) {
		// First we use the validPosition() method to check the Positions value, and get the Spot at the specified Position
		Spot currentSpot = validPosition(initialX, initialY);
		if (currentSpot == null) return null;

		// Check if the Player didn't choose a chess Piece
		if (currentSpot.getPiece() == null) return null;
		
		// Check if the Current Player Choose the Correct Color
		if (currentSpot.getPiece().isWhite() != currentPlayer.isWhite()) return null;


		// Scan the board and run the canMove() method on the CurrentPiece
		// And Pass to the canMove() method the board, and the currentSpot and every Spot on the board
		LinkedList<Spot> movesCanMake = new LinkedList<Spot>();
		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumColumns(); j++) {
				if (currentSpot.getPiece().canMove(board, currentSpot, board.getSpot(i, j)) == true) {
					movesCanMake.add(board.getSpot(i, j));
				}
			}
		}

		return movesCanMake;

	}

	
	// pawnPromotion() returns true if the Pawn of the Specified Color needs to be promoted
	public Boolean pawnPromotion(Boolean isWhite) {
		final int firstRow = 0;
		final int lastRow = chessBoard.getNumRows() - 1;

		// If the Pawn Color is White, then we need to check if the Pawn is at the Last Row
		// If the Pawn Color is Black, then we need to check if the Pawn is at the First Row
		int rowCheck;
		if (isWhite) {
			rowCheck = lastRow;
		} else {
			rowCheck = firstRow;
		}

		// Loop Over the Row that needs to be checked and see if there is a Pawn at that row
		for (int col = 0; col < chessBoard.getNumColumns(); col++) {
			if (chessBoard.getSpot(rowCheck, col).getPiece() instanceof Pawn) return true;
		}

		return false;
	}

	// restGame() is used to reset the Game
	public void resetGame() {
		gameStatus = 0;
		currentPlayer = players[0];
		fiftyMoveRule = 0;
		killedPieces.clear();
		resetSimulationBoard();
	}


	// Draw Match Methods //

	// oferDraw() method is used to check if a Draw was Offered
	public void offerDraw(Boolean acceptance) {
		// If an Offer for a Draw was Offered, and it was accepted the gameStatus is changed to 3
		if (acceptance == false) return;

		gameStatus = 3;
	}

	// checkFiftyMovesRule() checks for the fiftyMoveRule
	private void checkFiftyMovesRule() {
		// If fifty successive moves was made without any capturing the gameStatus is changed to 3
		if (fiftyMoveRule >= 50) gameStatus = 3;
	}

	// checkInsufficientMaterial() is used to check for the Insufficient Material Rule in Chess
	public void checkInsufficientMaterial() {
		int rows = chessBoard.getNumRows();
		int columns = chessBoard.getNumColumns();

		// Case 1: Checking if there is only 2 kings in the Board
		int countPieces = 0;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (chessBoard.getSpot(i, j).getPiece() != null) {
					countPieces++;
				}
			}
		}

		if (countPieces <= 2) {
			gameStatus = 3;
			return;
		}

		// Case 2: Checking if there is a King and a Runner VS. a King
		int countRunners = 0;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Piece currentPiece = chessBoard.getSpot(i, j).getPiece();
				if (currentPiece != null && currentPiece instanceof Runner) {
					countRunners++;
				}
			}
		}

		if (countPieces == 3 && countRunners == 1) {
			gameStatus = 3;
			return;
		}

	}

	// Getters // 
	public LinkedList<Piece> getDeadPieces() {
		return killedPieces;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public int getGameStatus() {
		return gameStatus;
	}
}