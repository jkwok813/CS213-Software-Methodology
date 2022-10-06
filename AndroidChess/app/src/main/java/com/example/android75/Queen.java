/**
 * This is class Queen which extends Piece. It has the two methods (which is overridden) isLegalMove and getName from Piece so that it is easier to setup and manipulate the board without having to specify what type of Piece it is.
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 *
 */

package com.example.android75;

public class Queen extends Piece{
	
	/**
	 * Constructor for Queen. Calls on the super constructor (from Piece)
	 * @param color: Takes in a boolean. True will result in the creation of a white Queen and False will result in the creation of a Black Queen
	 */
	public Queen(boolean color) {
		super(color);
	}
	
	/**
	 * This method returns true or false based on if a given Queen can move from source to destination while not accounting for the rest of the board
	 * @param startX: The source Rank from the user input
	 * @param startY: The source File from the user input
	 * @param endX: The destination Rank from the user input
	 * @param endY: The destination File from the user input
	 * @return Returns true if the move can be made by a Queen (not confirming that the move can be made). Returns False if a Queen cannot move from source to destination
	 */
	@Override
	public boolean isLegalMove(int startX, int startY, int endX, int endY) {
		//Check if move is diagonal
		//If the amount of change in |x|==|y|, then the Queen moved diagonally
        if(Math.abs(startX-endX)==Math.abs(startY-endY))
        {
            return true;
        }
        if(startX != endX || startY != endY) {
        	return true;
        }
		return false;
	}
	
	
	/**
	 * This method returns the name of the piece. In this case wQ or bQ
	 * @return Returns the name of the piece.
	 */
	@Override
	public String getName(){
		if(this.isWhite() == true) {
			String ans = "wQ";
			return ans;
		}
		else {
			String ans = "bQ";
			return ans;
		}
	}
}