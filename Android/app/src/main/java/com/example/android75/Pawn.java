/**
 * This is class Pawn which extends Piece. It has the two methods (which is overridden) isLegalMove and getName from Piece so that it is easier to setup and manipulate the board without having to specify what type of Piece it is.
 * Pawn also has the method isLegalMove2 since pawn is different from the special pieces in that it has more rules in regards to its movement.
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 *
 */

package com.example.android75;

public class Pawn extends Piece{
	
	//White is true and Black is False
	/**
	 * Constructor for Pawn. Calls on the super constructor (from Piece)
	 * @param color: Takes in a boolean. True will result in the creation of a white Pawn and False will result in the creation of a Black Pawn
	 */
	public Pawn(boolean color)
	{
		super(color);
	}
	
	//Returns true if the move is legal
	/**
	 * This method returns true or false based on if a given Pawn can move from source to destination while not accounting for the rest of the board
	 * @param startX: The source Rank from the user input
	 * @param startY: The source File from the user input
	 * @param endX: The destination Rank from the user input
	 * @param endY: The destination File from the user input
	 * @return Returns true if the move can be made by a Pawn (not confirming that the move can be made). Returns False if a Pawn cannot move from source to destination
	 */
	@Override
	public boolean isLegalMove(int startX, int startY, int endX, int endY)
	{
		//White pawn moving
		if(this.isWhite()==true)
		{
			if(startX==2)
			{
				//If its the pawn's first move, it is allowed to move 1 or 2 steps forward
				if(endX==3 || endX==4)
				{
					return true;
				}
			}
			//Pawn just moves forward one step
			else if(endX-startX==1)
			{
				return true;
			}
			//Check if the pawn moved diagonally (assuming it is capturing something)
			else if(endX-startX==1 && Math.abs(endY-startY)==1)
			{
				return true;
			}
			
			return false;
		}
		else
		{
			if(startX==7)
			{
				//If its the pawn's first move, it is allowed to move 1 or 2 steps forward
				if(endX==6 || endX==5)
				{
					return true;
				}
			}
			//Pawn just moves forward one step
			else if(endX-startX==-1)
			{
				return true;
			}
			//Check if the pawn moved diagonally (assuming it is capturing something)
			else if(endX-startX==-1 && Math.abs(endY-startY)==1)
			{
				return true;
			}
			
			return false;
		}
		
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
	@Override	
	public boolean isLegalMove2(int startX, int startY, int endX, int endY, boolean enpassant, int enpx, int enpy, boolean color, Board b)
	{
		/*System.out.println("enpx = " + enpx);
		System.out.println("enpy = " + enpy);
		System.out.println("endx = " + endX);
		System.out.println("endY = " + endY);*/
		//White pawn moving
		Piece s = b.getPiece(enpy, enpx);
		if(this.isWhite()==true)
		{
			if(startX==2)
			{
				//If its the pawn's first move, it is allowed to move 1 or 2 steps forward
				if(endX==3 || endX==4)
				{
					//System.out.println("First move");
					return true;
				}
			}
			//Pawn just moves forward one step
			else if(endX-startX==-1 && endY == startY)
			{
				//System.out.println("Moved one step");
				return true;
			}
			else if(endX == enpy && endY == enpx){//check to see if pawn is moving onto enpx enpy, and make sure it is not the same color. En passant.
				//System.out.println("Enpassant enabled.");
				return true;
			}
			//Check if the pawn moved diagonally (assuming it is capturing something)
			else if(endX-startX==1 && Math.abs(endY-startY)==1)
			{
				return true;
			}
			
			return false;
		}
		else
		{
			if(startX==7)
			{
				//If its the pawn's first move, it is allowed to move 1 or 2 steps forward
				if(endX==6 || endX==5)
				{
					//System.out.println("check1");
					return true;
				}
			}
			//Pawn just moves forward one step
			else if(endX-startX==-1 && endY == startY)
			{
				//System.out.println("check2");
				return true;
			}
			else if((endX == enpy && endY == enpx) && s == null){//check to see if pawn is moving onto enpx enpy, and make sure it is not the same color. En passant.
				//System.out.println("enpassant enbaled");
				return true;
			}
			//Check if the pawn moved diagonally (assuming it is capturing something)
			else if(endX-startX==-1 && Math.abs(endY-startY)==1)
			{
				return true;
			}
			return false;
		}
		
	}
	/**
+	 * This method returns the name of the piece. In this case wp or bp
+	 * @return Returns the name of the piece.
+	 */
	@Override
	public String getName()
	{
		//This method returns the name of the piece in 'wp' or 'bp' format for pawn for ease of printing
		if(this.isWhite()==true)
		{
			return "wp";
		}
		else
		{
			return "bp";
		}
	}

}
