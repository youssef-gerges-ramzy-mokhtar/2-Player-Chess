1) How is the Chess Game Structured
	a) This is the Following Classes Responsible for the Game Logic:
		- Piece
		- Pawn
		- King
		- Jumper
		- Runner
		- Tower
		- Spot
		- Player
		- Board
		- Game

	b) This is the Following Classes Responsible for the Game Graphical User Interface:
		- Text
		- Button
		- Buttons
		- DeadPieces
		- SpotPanel
		- ChessBoard
		- ChessGame

2) Why didn't I include packages:
	I could have included packages by adjusting the class path on my computer, but
I wasn't sure if these will work on the computer having the Java Files. That is why I didn't
Create packages, but if I would include packages they will be as following

	a) pieces (a package containing the following classes)
		- Piece
		- Pawn
		- King
		- Jumper
		- Runner
		- Tower
	
	b) gameLogic (a package containing the following classes and packages)
		- Spot
		- Player
		- Board
		- Game
		- pieces (package)
	
	c) gui (a package containing the following classes)
		- Text
		- Button
		- Buttons
		- DeadPieces
		- SpotPanel
		- ChessBoard
		- ChessGame

3) Why do I separte GUI Components to separte classes
	- I think in OOP we try to treat Objects as separte entities and we can think 
	  of GUI Components on a Window Frame as Separate entities that we can treat separtely

	- Allows resuability of the Object with different properties that can be provided
	  through the constructor for example the Text Class that extends JPanel is used
	  to set the Title of the Window, also used to set the Title Dead Pieces 
	  also used to show who is the current Player throughout the Game, all of this are
	  instances of the Text Class

	- Third reason is that it is a matter of my coding style. I like to separte the
	  GUI Components into separate classes.
	