/**
 * This is class Knight which extends Piece. It has the two methods (which is overridden) isLegalMove and getName from Piece so that it is easier to setup and manipulate the board without having to specify what type of Piece it is.
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 *
 */

package com.example.android75;

public class Knight extends Piece{
	
	/**
	 * Constructor for Knight. Calls on the super constructor (from Piece)
	 * @param color: Takes in a boolean. True will result in the creation of a white Knight and False will result in the creation of a Black Knight
	 */
	public Knight(boolean color)
	{
		super(color);
	}
	
	//Returns true if Knight can physically make this move
	/**
	 * This method returns true or false based on if a given Knight can move from source to destination while not accounting for the rest of the board
	 * @param startX: The source Rank from the user input
	 * @param startY: The source File from the user input
	 * @param endX: The destination Rank from the user input
	 * @param endY: The destination File from the user input
	 * @return Returns true if the move can be made by a Knight (not confirming that the move can be made). Returns False if a Knight cannot move from source to destination
	 */
	@Override
	public boolean isLegalMove(int startX, int startY, int endX, int endY)
	{
			if((Math.abs(startX-endX)>=1) && (Math.abs(startY-endY)>=1) && (Math.abs(startY-endY)+Math.abs(startX-endX)==3))
			{
				return true;
			}
			return false;
	}
	
	/**
	 * This method returns the name of the piece. In this case wN or bN
	 * @return Returns the name of the piece.
	 */
	@Override
	public String getName()
	{
		//This method returns the name of the piece in 'wp' or 'bp' format for pawn for ease of printing
		if(this.isWhite()==true)
		{
			return "wN";
		}
		else
		{
			return "bN";
		}
	}
}
