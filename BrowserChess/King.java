/**
 * This is class King which extends Piece. It has the two methods (which is overridden) isLegalMove and getName from Piece so that it is easier to setup and manipulate the board without having to specify what type of Piece it is.
 * King also has boolean variable firstMove which keeps track of if the Rook has taken a first move or not. This is to assist with Castling.
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 *
 */
public class King extends Piece{
	
	//White is true and Black is False
	/**
	 * Constructor for King. Calls on the super constructor (from Piece)
	 * @param color: Takes in a boolean. True will result in the creation of a White King and False will result in the creation of a Black King
	 */
	public King(boolean color)
	{
		super(color);
	}
	
	//Returns true if the move is legal
	/**
	 * This method returns true or false based on if a given King can move from source to destination while not accounting for the rest of the board
	 * @param startX: The source Rank from the user input
	 * @param startY: The source File from the user input
	 * @param endX: The destination Rank from the user input
	 * @param endY: The destination File from the user input
	 * @return Returns true if the move can be made by a King (not confirming that the move can be made). Returns False if a King cannot move from source to destination
	 */
	@Override
	public boolean isLegalMove(int startX, int startY, int endX, int endY) {		
		if(Math.abs(startX-endX)==0 && Math.abs(startY-endY) == 1 || Math.abs(startX-endX)==1 && Math.abs(startY-endY) == 0|| Math.abs(startX-endX)==1 && Math.abs(startY-endY) == 1 ) {//|| endX==1 && endY==7 || endX==1 && endY==3
			return true;
		}
		return false;
	}
	
	/**
	 * This method returns the name of the piece. In this case wK or bK
	 * @return Returns the name of the piece.
	 */
	@Override
	public String getName()
	{
		//This method returns the name of the piece in 'wp' or 'bp' format for pawn for ease of printing
		if(this.isWhite()==true)
		{
			return "wK";
		}
		else
		{
			return "bK";
		}
	}
	
	/**
	 * Boolean variable that keeps track of whether the Rook has made its first move or not. True= no first move. False= First move already made.
	 */
	boolean firstMove=true;
	
	/**
	 * Returns if the King is about to make its first move.
	 * @return Returns true if the King has not moved before. Returns false if the King has moved before
	 */
	public boolean getFirstMove()
	{
		return firstMove;
	}
	
	/**
	 * Sets firstMove to false since the King in questions has made a move.
	 */
	public void completeFirstMove()
	{
		firstMove=false;
	}

}