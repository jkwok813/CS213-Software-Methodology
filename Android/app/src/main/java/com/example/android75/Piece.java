/**
 * This is the Piece class which is the basis for all the pieces that are used in the board class.
 * This class allows for certain methods- constructors, isWhite, getName, isLegalMove, etc to be streamlined for all kinds of pieces
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 *
 */

package com.example.android75;

public class Piece
{
	//By default a piece is alive
	/**
	 * Variable that keeps track of if a piece is alive or not
	 * True means alive, False means dead
	 */
	private boolean alive=true;
	
	/**
	 * Variable that keeps track of what color the piece is. True means the color is white. False means the color of the piece is black.
	 */
	private boolean color_white;
	
	//White is true and Black is False
	/**
	 * This is the constructor of the Piece class. This method decides what the color of the Piece that is getting generated is.
	 * @param color: Takes in a boolean. True will result in the creation of a white Piece and False will result in the creation of a Black Piece
	 */
	public Piece(boolean color)
	{
		color_white=color;
	}
	
	//set piece to Black
	public void setBlack()
	{
		color_white=false;
	}
	
	//Check if a given piece is White
	public boolean isWhite()
	{
		return color_white;
	}
	
	//Kill a given piece
	public void killPiece()
	{
		alive=false;
	}
	
	//Check if a given piece is Alive
	public boolean isAlive()
	{
		return alive;
	}
	
	//gets the name of the piece: bR, bN, etc
	/**
	 * This method returns the name of the piece. Ex.: wp or bp for white and black pawn
	 * @return Returns the name of the piece.
	 */
	public String getName()
	{
		return "";
	}
	
	/**
	 * This method returns true or false based on if a given Piece can move from source to destination while not accounting for the rest of the board.
	 * A return of true from this method does not guarantee that the piece in question can actually make that move on the board.
	 * @param startX: The source Rank from the user input
	 * @param startY: The source File from the user input
	 * @param endX: The destination Rank from the user input
	 * @param endY: The destination File from the user input
	 * @return Returns true if the move can be made by a Piece (not confirming that the move can be made). Returns False if a Piece cannot move from source to destination
	 */
	public boolean isLegalMove(int startX, int startY, int endX, int endY)
	{
		return false;
	}
	
	/**
	 * This method confirms if the pawn can move, but it takes in further inputs to allow for enpassant.
	 * @param startX Source Rank
	 * @param startY Source File
	 * @param endX destination Rank
	 * @param endY destination File
	 * @param enpassant checks if enpassant is enabled
	 * @param enpx the rank of the place the pawn will go
	 * @param enpy the file of the place the pawn will go
	 * @param color Color of the piece (black or white)
	 * @param b The board itself to call methods.
	 */
	public boolean isLegalMove2(int startX, int startY, int endX, int endY, boolean enpassant, int enpx, int enpy, boolean color, Board b)
	{
		return false;
	}

}
