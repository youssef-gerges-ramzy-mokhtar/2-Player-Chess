import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// The ChessGame Class is responsible for The GUI //
// The ChessGame Class uses the Game Class, and based on the Logic provided by the Game Class it adjusts the GUI //

class ChessGame extends JFrame implements MouseListener, ActionListener {
	// Data Members //
	private Container contentPane;
	private ChessBoard chessBoard;
	private Game game;
	private SpotPanel initialSpot;
	private SpotPanel finalSpot;
	private JPanel gamePanel;
	private JPanel boardPanel;
	private DeadPieces deadPieces;
	private Text title;
	private Text currentPlayer;
	private JPanel pawnPromotion;

	// Main Method to create an Instance of the ChessGame Class
	// And set the Window Frame Visible
	public static void main(String[] args) {
		ChessGame chess = new ChessGame();
		chess.setVisible(true);
	}

	// Constructor //
	public ChessGame() {
		// contentPane Holds all the GUI Panels & Elements
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout()); // Setting the Layout of the Content Pane
		
		// Initializing the Data Members //
		chessBoard = new ChessBoard();
		game = new Game(chessBoard);
		initialSpot = null;
		finalSpot = null;
		gamePanel = new JPanel(new BorderLayout());
		boardPanel = new JPanel(new BorderLayout());
		deadPieces = new DeadPieces();
		title = new Text("Chess Game");
		currentPlayer = new Text("", Color.black, Color.white, 30, 130, 0, 0, 0);

		// Set the Window Frame Properties
		setExtendedState(MAXIMIZED_BOTH);
		setResizable(true);
		setTitle("Chess Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Setting the Text of the currentPlayer Data Member based on the Player Turn 
		if (game.getCurrentPlayer().isWhite()) {
			currentPlayer.setText("White Player Turn");
		} else {
			currentPlayer.setText("Black Player Turn");
		}

		// Adding the chessBoard and the currentPlayer JPanels to the boardPanel
		boardPanel.add(chessBoard, BorderLayout.CENTER);
		boardPanel.add(currentPlayer, BorderLayout.NORTH);

		// Adding the boardPanel and the deadPieces to the gamePanel
		gamePanel.add(boardPanel, BorderLayout.CENTER);
		gamePanel.add(deadPieces, BorderLayout.EAST);

		// Adding the title and the gamePanel to the contentPane
		contentPane.add(title, BorderLayout.NORTH);
		contentPane.add(gamePanel, BorderLayout.CENTER);
		
		addBoardListener();
		addButtonsListener();
	}

	// Methods //

	// addBoardListener() is used to add a Mouse Listener to the Spot Panel in the chessBoard
	private void addBoardListener() {
		for (int i = 0; i < chessBoard.getNumRows(); i++) {
			for (int j = 0; j < chessBoard.getNumColumns(); j++) {
				chessBoard.getSpotPanel(i, j).addMouseListener(this);
			}
		}	
	}

	// addButtonsListener() is used to add an Action Listener to the Restart Game & Offer Draw Buttons
	private void addButtonsListener() {
		JButton buttons[] = deadPieces.getButtonsPanel().getButtons();
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].getText().equals("Offer Draw") || buttons[i].getText().equals("Restart Game")) {
				buttons[i].addActionListener(this);
			}
		}
	}

	// removeBoardListener() is used to remove the Mouse Listener that is attached to the Spot Panels in the chessBoard
	private void removeBoardListener() {
		for (int i = 0; i < chessBoard.getNumRows(); i++) {
			for (int j = 0; j < chessBoard.getNumColumns(); j++) {
				chessBoard.getSpotPanel(i, j).removeMouseListener(this);
			}
		}
	}

	


	// mouseClicked() method is used to handle what will happen in case a Spot Panel was clicked
	public void mouseClicked(MouseEvent event) {
		// This is only occurs in case a Pawn is Promoted //
		if (event.getSource() instanceof JLabel) {
			// Getting the Clicked JLabel, from that JLable we get the Image from the JLabel
			// Then we get the Description from the Image
			JLabel label = (JLabel) event.getSource();
			ImageIcon pieceImage = (ImageIcon) label.getIcon();
			String imageDescription = pieceImage.getDescription();

			// We Promote the Pawn based on the imageDescription
			promtePawn(imageDescription);

			// After Promoting the Pawn we remove the pawn Promotion Panel
			// And then add the Listener to all Spot Panels
			// And then reset the simulation board in the Game Object
			// And then update the UI of the chessBoard Panel
			removePawnPromotionPanel();
			addBoardListener();
			game.resetSimulationBoard();
			chessBoard.updateUI();
			return;
		}


		// This is to handle User Clicking on the Spot Panels on the Chess Board //

		// Get the Spot Panel Clicked
		SpotPanel spot = (SpotPanel) event.getSource();

		// Check if initialSpot is not set, this means that the Clicked Spot Panel is the First Choise
		// If initialSpot is set, this means that the Clicked Spot Panel is the Final Choice
		if (initialSpot == null) {
			// Get the Position of this Spot Panel and Display the Available Moves the Player can Make
			initialSpot = spot;
			int x1 = initialSpot.getSpot().getRowPos();
			int y1 = initialSpot.getSpot().getColumnPos();

			displayBoardAvailableMoves(game.getMovesCanMake(chessBoard, x1, y1));
		} else {
			// If the Player choose the Final Spot Panel we reset the color of the Chess Board, and call the makeMove() method
			finalSpot = spot;
			chessBoard.resetColor();
			makeMove(initialSpot, finalSpot);
		}

		// After Making the Move we check the game status
		checkGameStatus();
	}


	// Methods to Handle User Mouse Clicks //

	// createPawnPromotionPanel() is used to Create a JPanel to Promote the Pawn
	private JPanel createPawnPromotionPanel(Boolean isWhite) {
		String str = "";

		// According to the Pawn Color set str accordingly
		if (isWhite) {
			str = "Please Choose a Piece to Promote the White Pawn: ";
		} else {
			str = "Please Choose a Piece to Promote the Black Pawn: ";
		}

		Text promotionTitle = new Text(str, Color.black, Color.white, 20, 0, 0, 0, 0);
		JPanel promotionPanel = new JPanel(); 

		promotionPanel.add(promotionTitle);

		// Create 3 new JLabels showing the Options that can be choose to Promote the pawn
		JLabel runner = new JLabel((new Runner(isWhite).getSymbol()));
		JLabel jumper = new JLabel((new Jumper(isWhite).getSymbol()));
		JLabel tower = new JLabel((new Tower(isWhite).getSymbol()));
		JLabel queen = new JLabel((new Queen(isWhite).getSymbol()));

		// Add the 3 JLabels to the promotionPanel
		promotionPanel.add(runner);
		promotionPanel.add(jumper);
		promotionPanel.add(tower);
		promotionPanel.add(queen);
		
		// Add Mouse Listener to the 3 JLabels
		runner.addMouseListener(this);
		jumper.addMouseListener(this);
		tower.addMouseListener(this);
		queen.addMouseListener(this);

		return promotionPanel;
	}


	// promotePawn() is used to change the Pawn Piece to Another Piece based on a String 
	private void promtePawn(String piecePromote) {
		// Based on the piecePromote String, the Pawn is promoted to a new Chess Piece
		// By updating the Piece on the finalSpot and by updating the Image on the finalSpot

		switch(piecePromote) {
			case "wr":
				finalSpot.getSpot().setPiece(new Runner(true));
				break;

			case "wj":
				finalSpot.getSpot().setPiece(new Jumper(true));
				break;

			case "wt":
				finalSpot.getSpot().setPiece(new Tower(true));
				break;

			case "wq":
				finalSpot.getSpot().setPiece(new Queen(true));
				break;

			case "br":
				finalSpot.getSpot().setPiece(new Runner(false));
				break;

			case "bj":
				finalSpot.getSpot().setPiece(new Jumper(false));
				break;

			case "bt":
				finalSpot.getSpot().setPiece(new Tower(false));
				break;

			case "bq":
				finalSpot.getSpot().setPiece(new Queen(false));
		}

		finalSpot.setContent(finalSpot.getSpot().getPiece().getSymbol());
	}


	// removePawnPromotionPanel() is used to remove the pawnPromotion Panel
	private void removePawnPromotionPanel() {
		if (pawnPromotion == null) return;
		boardPanel.remove(pawnPromotion);
		boardPanel.repaint();
	}

	// makeMove() takes 2 Spot Panels and makes a move
	private void makeMove(SpotPanel startSpot, SpotPanel endSpot) {
		// Gets the Positions of the Spot Spot Panels on the Board
		int initialX = startSpot.getSpot().getRowPos();
		int initialY = startSpot.getSpot().getColumnPos();
		int finalX = endSpot.getSpot().getRowPos();
		int finalY = endSpot.getSpot().getColumnPos();

		// We Pass the Positions to the makeMove() method of the Game Object
		Boolean successMove = game.makeMove(initialX, initialY, finalX, finalY);
	
		// If the move was made successful
		if (successMove) {
			// We Change the Images on the Spot Panels
			chessBoard.getSpotPanel(initialX, initialY).setContent(null);
			chessBoard.getSpotPanel(finalX, finalY).setContent(endSpot.getSpot().getPiece().getSymbol());

			// We Change the currentPlayer Text to display the Other Player Turn
			if (game.getCurrentPlayer().isWhite()) {
				currentPlayer.setText("White Player Turn");
			} else {
				currentPlayer.setText("Black Player Turn");
			}


			// We Check if the White or the Black Pawn are Promoted
			Boolean whitePawn = true;
			for (int i = 1; i <= 2; i++) {
				if (game.pawnPromotion(whitePawn)) {
					// remove the Mouse Listener from all Spot Panels
					// Create the Pawn Promotion Panel, and add it to the boardPanel
					removeBoardListener();
					pawnPromotion = createPawnPromotionPanel(whitePawn);
					boardPanel.add(pawnPromotion, BorderLayout.SOUTH);
				}
				whitePawn = !whitePawn;
			}

			deadPieces.addDeadPieces(game.getDeadPieces());
		}

		// Set the initialSpot to null, so that the User when Clicking a Spot Panel becomes his First Choice
		initialSpot = null;
	}

	// checkGameStatus() is used to check the Game Status
	private void checkGameStatus() {
		// If the Game is not continuing the currentPlayer text is adjusted based on the Game Status
		// and we remove the Mouse Listener from all the Spot Panels using the removeBoardListener() method
		if (game.getGameStatus() != 0) {
			if (game.getGameStatus() == 1) {
				currentPlayer.setText("Congratulations: White Player Won");
			} else if (game.getGameStatus() == 2) {
				currentPlayer.setText("Congratulations: Black Player Won");
			} else {
				currentPlayer.setText("Draw Match");
			}

			removeBoardListener();
		}
	}


	// displayBoardAvailableMoves() displays the Available Moves on the Chess Board
	private void displayBoardAvailableMoves(LinkedList<Spot> movesCanMake) {
		// If the LinkedList of Spots is null we reset the color of the chessBoard and exit the method
		if (movesCanMake == null) {
			chessBoard.resetColor();
			return;
		}

		// Else we loop over the moveCanMkae LinkedList and get the Spot Panel at the
		// position of the Spot and set its color to green
		for (int i = 0; i < movesCanMake.size(); i++) {
			int x1 = movesCanMake.get(i).getRowPos();
			int y1 = movesCanMake.get(i).getColumnPos();
			chessBoard.getSpotPanel(x1, y1).setColor(Color.green);
		}
	}



	//////////////////////////////////////// Handling Clicking Button Events /////////////////////////////////
	
	// actionPerformed() method is used to handle what will happen in case a Button click occurs
	public void actionPerformed(ActionEvent e) {
		JButton buttonClicked = (JButton) e.getSource(); // getting the Clicked Button
		String bottonClickedText = buttonClicked.getText(); // getting the text of that Clicked Button

		// If the Button is the Restart Game Button, we reset the Game using the resetGame() method and exit the Method
		if (bottonClickedText.equals("Restart Game")) {
			resetGame();
			return;
		}

		// If the Button is the OfferDraw Button
		if (bottonClickedText.equals("Offer Draw"))  {
			removeBoardListener();

			// We add the Action Listener to the Accept Draw and Reject Draw Buttons
			// And Exit the Method
			JButton buttons[] = deadPieces.getButtonsPanel().getButtons();
			for (int i = 0; i < buttons.length; i++) {
				if (buttons[i].getText().equals("Accept Draw") || buttons[i].getText().equals("Reject Draw")) {
					buttons[i].addActionListener(this);
				}
			}
			return;
		}

		// According to the Accept/Reject Draw button we call the offerDraw() method on the Game Object
		if (bottonClickedText.equals("Reject Draw")) game.offerDraw(false);
		if (bottonClickedText.equals("Accept Draw")) game.offerDraw(true);

		// After Clicking the Accept/Reject Draw Button we add the Mouse Listener to the Spot Panels
		// Then we Check the Game Status
		// Then we Remove the Action Listener from the Reject/Accept Draw Buttons
		addBoardListener();
		checkGameStatus();
		JButton buttons[] = deadPieces.getButtonsPanel().getButtons();
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].getText().equals("Accept Draw") || buttons[i].getText().equals("Reject Draw")) {
				buttons[i].removeActionListener(this);
			}
		}
	}

	// Methods That Handle User Clicking on Buttons //

	// resetGame() method is used to reset the Chess Game
	private void resetGame() {
		chessBoard.resetBoard();
		game.resetGame();
		addBoardListener();
		deadPieces.resetDeadPieces();
		initialSpot = null;
		currentPlayer.setText("White Player Turn");
		removePawnPromotionPanel();
	}

	
	
	// This Methods is just added to implement the MouseListener //
	public void mouseEntered (MouseEvent event) {}
	public void mouseExited (MouseEvent event) {}
	public void mousePressed (MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
}