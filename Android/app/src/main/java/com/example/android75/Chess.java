package com.example.android75;

import java.util.*;

/**
 * Chess.java is the main file/class where everything comes together. This is where the user input is read, the board print requests are sent,
 * and the checking of check and if a move is possible on the board for pieces like Rook, Queen, Bishop, Pawn, King, etc is possible
 * This is also where resign, draw and other inputs are accepted.
 * @author Kishan Sojan (ks1326) and Justin Kwok (jdk179)
 *
 */

public class Chess{
	public static void main(String[] args) {
		
		
		/**
		 * Creates the board for the Chess game
		 */
		Board b=new Board();
	    b.print();
	    System.out.println();
	    
	    /**
	     * Setting variables for input, checking if a draw request is in, if a promotion is up, if it is white turn, if enpassant is up, etc
	     */

	    String input = "";
	    boolean draw = false;
	    boolean promotion = false;
	    boolean whiteturn = true;
	   // int turncounter = 0;
	    boolean enpassant = false;
	    int enpx = -1;
	    int enpy = -1;
	    int enp = 0;
	    
	    /**
	     * Scan's the user's input and initializes the start and end Rank and File
	     */

	    Scanner scanner = new Scanner(System.in);
	    int startR = -1; //second number in first input
	    int startF = -1; //first number in first input
	    int endR = -1; //second number in second input
	    int endF = -1; //first number in second input
	    
	    /**
	     * While loop to keep asking the users for moves. Will stay inside here unless someone resigns, there is a draw, and/or someone wins.
	     */
	    while(true) {
	    	String promo = "";
	    	//System.out.println("Enter move");
	    	
	    	//Printing whose turn it is and accepting their move
	    	if(whiteturn == true) {
	    		System.out.print("White move: ");
	    	}
	    	else {
	    		System.out.print("Black move: ");
	    	}
	    	
	    	input = scanner.nextLine();
	    	String[] words = input.split("\\s+");
	    	int arraylength = words.length;;
	    	
	    	if(words[0].length()<2 || words[1].length()<2)
	    	{
	    		System.out.println("Illegal move, try again");
	    		//System.out.println("Input not sufficient");
	    		continue;
	    	}
	    	
	    	if(input.length() <= 1 || input.length() >= 12) {
	    		System.out.println("Illegal move, try again");
	    		System.out.println();
	    		continue;
	    	}
	    	
	    	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	if(arraylength == 1 && (!words[0].equals("resign") && !words[0].equals("draw"))) {
	    		System.out.println("Illegal move, try again");
	    		System.out.println();
	    		continue;
	    	}
	    	
	    	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	
	    	if(arraylength == 2) {
	    		//System.out.println("Regular move");
	    		char c = words[0].charAt(0);
	    		char d = words[0].charAt(1);
	    		char e = words[1].charAt(0);
	    		char f = words[1].charAt(1);
	    		//System.out.println(f);
	    		if(words[0].length() > 2 || words[1].length() > 2 ) {
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		if(c != 'a' && c != 'b' && c != 'c' && c != 'd' && c != 'e' && c != 'f' && c != 'g' && c != 'h') {
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		if(e != 'a' && e != 'b' && e != 'c' && e != 'd' && e != 'e' && e != 'f' && e != 'g' && e != 'h') {
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		boolean numcheck = Character.isDigit(d);
	    		if (!numcheck){
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		boolean numcheck2 = Character.isDigit(f);
	    		if (!numcheck2){
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		if(c == 'a') {startF = 1;}
	    		if(c == 'b') {startF = 2;}
	    		if(c == 'c') {startF = 3;}
	    		if(c == 'd') {startF = 4;}
	    		if(c == 'e') {startF = 5;}
	    		if(c == 'f') {startF = 6;}
	    		if(c == 'g') {startF = 7;}
	    		if(c == 'h') {startF = 8;}
	    		if(e == 'a') {endF = 1;}
	    		if(e == 'b') {endF = 2;}
	    		if(e == 'c') {endF = 3;}
	    		if(e == 'd') {endF = 4;}
	    		if(e == 'e') {endF = 5;}
	    		if(e == 'f') {endF = 6;}
	    		if(e == 'g') {endF = 7;}
	    		if(e == 'h') {endF = 8;}
	    		startR = d - '0';
	    		endR = f - '0';
	    		
	    		//System.out.println(startR);
	    		//System.out.println(endR);
	    		
	    		if(startR > 9 || endR > 9 || startR == 0 || endR == 0) {
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		//check legality
	    	}
	    	
	    	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	
	    	
	    	if(arraylength == 3) {
	    		//System.out.println("Draw, promotion, resignation");
	    		//System.out.println("Regular move");
	    		char c = words[0].charAt(0);
	    		char d = words[0].charAt(1);
	    		char e = words[1].charAt(0);
	    		char f = words[1].charAt(1);
	    		char g = words[2].charAt(0);
	    		//System.out.println(f);
	    		if(words[0].length() > 2 || words[1].length() > 2 ) {
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		if(c != 'a' && c != 'b' && c != 'c' && c != 'd' && c != 'e' && c != 'f' && c != 'g' && c != 'h') {
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		if(e != 'a' && e != 'b' && e != 'c' && e != 'd' && e != 'e' && e != 'f' && e != 'g' && e != 'h') {
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		boolean numcheck = Character.isDigit(d);
	    		if (!numcheck){
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		boolean numcheck2 = Character.isDigit(f);
	    		if (!numcheck2){
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		if(c == 'a') {startF = 1;}
	    		if(c == 'b') {startF = 2;}
	    		if(c == 'c') {startF = 3;}
	    		if(c == 'd') {startF = 4;}
	    		if(c == 'e') {startF = 5;}
	    		if(c == 'f') {startF = 6;}
	    		if(c == 'g') {startF = 7;}
	    		if(c == 'h') {startF = 8;}
	    		if(e == 'a') {endF = 1;}
	    		if(e == 'b') {endF = 2;}
	    		if(e == 'c') {endF = 3;}
	    		if(e == 'd') {endF = 4;}
	    		if(e == 'e') {endF = 5;}
	    		if(e == 'f') {endF = 6;}
	    		if(e == 'g') {endF = 7;}
	    		if(e == 'h') {endF = 8;}
	    		startR = d - '0';
	    		endR = f - '0';
	    		//System.out.println(startR);
	    		//System.out.println(endR);
	    		if(startR > 9 || endR > 9 || startR == 0 || endR == 0) {
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		//Checking to see if the piece in question that requesting promotion is a pawn or not. If not, the error message below will be thrown.
	    		Piece pIS=b.getPiece(startR,startF);
	    		String pN;
    			if(pIS!=null)
    				pN=pIS.getName(); //gets Name
    			else
    				pN= "   ";
    			
	    		if((g != 'N' && g != 'Q' && g != 'B' && g != 'R') && words[2].length() == 1 || !(pN.substring(1,2).equals("p"))) {
	    			System.out.println("Illegal move, try again");
	    			System.out.println();
	    			continue;
	    		}
	    		if(words[2].equals("draw?")) {
	    			draw = true;
	    		}
	    		//check legality
	    	}
	    	
	    	
	    	if(input.equals("draw") && draw == true ){
	    		System.out.println("It's a draw");
	    		break;
	    	}
	    	if(input.equals("resign")) {
	    		break;
	    	}
	    	
	    	
	    	
	    	
	    	//Checking if the chosen start position/piece is legal
	    	Piece pieceInStart=b.getPiece(startR,startF); //gets wp, bR, etc so we know what piece is in the start position
	    	
	    	if(pieceInStart==null)//Checking if the selected piece is empty/blank/null aka checking if there is a piece there
	    	{
	    		//System.out.println("Starting position is blank");
	    		System.out.println("Illegal move, try again");
	    		continue;
	    	}
	    	
	    	boolean isPieceWhiteStart=pieceInStart.isWhite();
	    	
	    	
	    	if(isPieceWhiteStart!=whiteturn) //Checking if the selected piece is the same color as the current player. If it is not, it is an illegal move
	    	{
	    		//System.out.println("You have selected an enemy piece");
	    		System.out.println("Illegal move, try again");
	    		continue;
	    	}
	    	
	    	//Checking if the chosen end position/piece is legal
	    	Piece pieceInEnd=b.getPiece(endR,endF); //gets wp, bR, etc so we know what piece is currently in the end position
	    	if(pieceInEnd!=null)
	    	{
	    		boolean isPieceWhiteEnd=pieceInEnd.isWhite();
	    		if(isPieceWhiteEnd==whiteturn)//Checking if the end position has a piece that is the same color as the current player
		    	{
	    			//System.out.println("An allay piece is at the end position");
		    		System.out.println("Illegal move, try again");
		    		continue;
		    	}
	    	}		
	    	else
	    	{
	    		
	    	}
	    	
	    	
	    	
	    	String pieceName=pieceInStart.getName(); //gets Name
	    	boolean isLegal;
	    	if(!enpassant || !(pieceName.substring(1,2).equals("p"))) {
	    		isLegal=pieceInStart.isLegalMove(startR, startF, endR, endF);
	    		//System.out.println("Piece Name: "+pieceName);
	    		
	    		
	    		
	    		if(pieceName.substring(1,2).equals("K")) //For castling purposes
	    		{
	    			isLegal=true;
	    		}
	    			
	    		
	    		
	    	}
	    	else {
	    		boolean color = whiteturn;
	    		isLegal = pieceInStart.isLegalMove2(startR, startF, endR, endF, enpassant, enpx, enpy, color, b);
	    	}
	    	
	    	 //Checking if the given move is physically possible for the piece
	    	
	    	if(isLegal==false)//If the move is not legally allowed for the piece
	    	{
	    		//System.out.println("This move is not legal for the selected piece");
	    		System.out.println("Illegal move, try again"); //FLAG
	    		continue;
	    	}
	    	
	    	//Now checking if there are any obstacles for the piece when going from start to end point
	    	
	    	boolean physicallyMove=false; //Can the piece physically move there
	    	
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//Pawn
	    	/**
	    	 * The part of the main file where we check if the pawn move is legal based on board, enpassant, promotion, etc
	    	 */
	    	if(pieceName.substring(1,2).equals("p"))//If the piece is a pawn
	    	{
	    		//System.out.println("Piece is a pawn");
	    		Piece temp = b.getPiece(startR, startF);
	    		boolean color = temp.isWhite();
	    		boolean first = false;
	    		//System.out.println(color);
	    		//System.out.println(startF);
	    		if(startR == 2 && color == true) { //Check to see if it's in starting pos for white
	    			first = true;
	    		}
	    		if(startR == 7 && !color) {//check to see if it's in starting pos for black
	    			first = true;
	    		}
	    		if(Math.abs(endR-startR) > 2) {//Too big a gap
	    			System.out.println("Illegal move, try again"); //FLAG
	    			physicallyMove = false;
	    			continue;
	    		}
	    		if(Math.abs(endR-startR) == 2) {//Trying to move 2 spaces on a non starting space
	    			//System.out.println(first);
	    			if(!first) {
	    				System.out.println("Illegal move, try again"); //FLAG
	    				physicallyMove = false;
	    				continue;
	    			}
	    		}
	    		if(endR-startR == 1 && !color) {//Black is trying to move backwards
    				System.out.println("Illegal move, try again");
    				physicallyMove = false;
    				continue;
	    		}
	    		if(startR-endR == 1 && color == true) {//White is trying to move backwards
	   				System.out.println("Illegal move, try again");
    				physicallyMove = false;
    				continue;
	    		}
	    		if(endF != startF) {//attempting to take a piece
	    			Piece temp2 = b.getPiece(endR, endF);
	    			if(enpassant == true) {
	    				//System.out.println("ENPASSANT");
	    				Piece temp3; 
	    				if(!whiteturn) {
	    					b.getPiece(endR+1, endF);
	    					int tempf = endR+1;
		    				//System.out.println("black turn endR = " + tempf + " endF " + endF);
	    					temp3 = b.getPiece(endR+1, endF);
	    				}
	    				else {
	    					b.getPiece(endR-1, endF);
	    					int tempf = endR-1;
		    				//System.out.println("white turn endR = " + tempf + " endF " + endF);
	    					temp3 = b.getPiece(endR-1, endF);
	    				}
	    				if(temp3 == null) {//enpassant isn't activated... :(
		    				if(color == true) {//White turn
		    					Piece temp4 = b.getPiece(enpx, enpy);
		    					if(temp4 == null) {
		    						physicallyMove = true;
		    					}
		    					else {
				    				//System.out.println("Attempting to move to a null space diagonally, enp enabled.");
				    				physicallyMove = false;
				    				continue;
		    					}
		    				}
		    				else {
		    					Piece temp4 = b.getPiece(enpx, enpy);
		    					if(temp4 == null) {
			    					physicallyMove = true;				
		    					}
		    					else {
				    				//System.out.println("Attempting to move to a null space diagonally, enp enabled.");
				    				physicallyMove = false;
				    				continue;
		    					}
		    				}
	    				}
	    				//System.out.println("ENPASSANT ACTIVATED");
	    				if(color == true) {//White turn
	    					Piece temp4 = b.getPiece(enpx, enpy-1);
	    					if(temp4 == null) {
	    						b.deletePiece(enpx, enpy-1);
	    					}
	    				}
	    				else {
	    					Piece temp4 = b.getPiece(enpx, enpy+1);
	    					if(temp4 == null) {
		    					b.deletePiece(enpx, enpy+1);						
	    					}
	    				}
	    			}
	    			else if(temp2 == null) {
	    				//System.out.println("Attempting to move to a null space diagonally");
	    				physicallyMove = false;
	    				continue;
	    			}
	    			else if(temp2 != null) {//Attempting to move a piece to a tile of the same color
	    				if(temp2.isWhite() == whiteturn) {
	    					//System.out.println("Attempting to move piece to a tile with piece of same color");
	    					physicallyMove = false;
	    					continue;
	    				}
	    			}
	    		}
	    		if(arraylength == 3 && (endR == 8) || (endR == 1)) {
	    			promotion = true;
	    		}
	    		if(endR ==  8 || endR == 1) {
	    			promotion = true;
	    			promo = "Q";
	    		}
	    		if(Math.abs(endR-startR) == 2) {
	    			//System.out.println("enpassant enabled");
	    			enpassant = true;
	    			if(!whiteturn) {
		    			enpx = endF;
		    			enpy = endR+1;
	    			}
	    			else {
	    				enpx = endF;
	    				enpy = endR-1;
	    			}
	    		}
	    		physicallyMove = true;
	    	}
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//Rook
	    	/**
	    	 * The part of the code that checks if the given move is possible based on the board given that the source piece is Rook.
	    	 */
	    	else if(pieceName.substring(1,2).equals("R"))//If the piece is a Rook
	    	{
	    		int tempStartR=startR;
	    		int tempEndR=endR;
	    		
	    		int tempStartF= startF;
	    		int tempEndF=endF;
	    		if(startR-endR==0) //Which means it moving across file/ moving horizontally
	    		{
	    			while(Math.abs(tempEndF-tempStartF)!=0)
	    			{
	    				//System.out.println("Inside F while loop- End:"+tempEndF+" Start:"+tempStartF );
	    				if(tempEndF>tempStartF) //If the piece is moving from left to right
	    				{
	    					//System.out.println("Inside tempEndF>tempStartF");
	    					Piece temp1=b.getPiece(endR,tempStartF+1);
	    			    	if(temp1==null)
	    			    	{
	    			    		tempStartF++;
	    			    		if(tempStartF==tempEndF) //No piece in between the start and end position
	    			    		{
	    			    			//System.out.println("Inside tempStartF==tempEndF");
	    			    			
	    			    			physicallyMove=true;
	    			    			break;
	    			    		}
	    			    	}
	    			    	else //There is a piece in the way
	    			    	{
	    			    		//System.out.println("Inside else statement for temp1==null");
	    			    		if(temp1.isWhite()!=whiteturn && tempStartF+1==tempEndF)
	    						{
	    						//	System.out.println("temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
	    							physicallyMove=true;
	    			    			break;
	    						}
	    						else
	    						{
	    							//System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
		    						physicallyMove=false;
		    						break;
	    						}
	    			    	}
	    				}
	    				else //If the piece is moving from right to left
	    				{
	    					
	    					//System.out.println("Inside else statement of tempEndF>tempStartF");
	    					Piece temp1=b.getPiece(endR,tempStartF-1);
	    			    	if(temp1==null)
	    			    	{
	    			    		tempStartF--;
	    			    		if(tempStartF==tempEndF) //No piece in between the start and end position
	    			    		{
	    			    			//System.out.println("Inside tempStartF==tempEndF");
	    			    			physicallyMove=true;
	    			    			break;
	    			    		}
	    			    	}
	    			    	else //There is a piece in the way
	    			    	{
	    			    		//System.out.println("Inside else statement for temp1==null");
	    			    		if(temp1.isWhite()!=whiteturn && tempStartF-1==tempEndF)
	    						{
	    							//System.out.println("temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
	    							physicallyMove=true;
	    			    			break;
	    						}
	    						else
	    						{
	    							//System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
		    						physicallyMove=false;
		    						break;
	    						}
	    			    	}
	    				}
	    			}
	    		}
	    		else//Rook is moving across Rank/ moving vertically
	    		{
	    			while(Math.abs(tempEndR-tempStartR)!=0)
	    			{
	    				//System.out.println("Inside R while loop- End:"+tempEndR+" Start:"+tempStartR );
	    				if(tempEndR>tempStartR) //if the piece is moving from bottom to top
	    				{
	    					//System.out.println("Inside tempEndR>tempStartR");
	    					Piece temp1=b.getPiece(tempStartR+1, endF);
	    					if(temp1==null)
	    					{
	    						//System.out.println("Inside temp1==null");
	    						tempStartR++;
	    						if(tempStartR==tempEndR) // No pieces in between the start and end position
	    						{
	    							//System.out.println("Inside tempStartR==tempEndR");
	    							physicallyMove=true;
	    			    			break;
	    						}
	    						/*else //There is a piece in the way
		    			    	{
	    							System.out.println("Inside else statement for tempStartR==tempEndR");
		    			    		physicallyMove=false;
		    			    		break;
		    			    	}*/
	    					}
	    					else
	    					{
	    						//System.out.println("Inside else statement for temp1==null");
	    						if(temp1.isWhite()!=whiteturn && tempStartR+1==tempEndR)
	    						{
	    							//System.out.println("temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
	    							physicallyMove=true;
	    			    			break;
	    						}
	    						else
	    						{
	    							//System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
		    						physicallyMove=false;
		    						break;
	    						}
	    						
	    					}
	    				}
	    				else //if the piece is moving from top to bottom
	    				{
	    					//System.out.println("Inside tempEndR<tempStartR");
	    					Piece temp1=b.getPiece(tempStartR-1, endF);
	    					if(temp1==null)
	    					{
	    						//System.out.println("Inside temp1==null");
	    						tempStartR--;
	    						if(tempStartR==tempEndR) // No pieces in between the start and end position
	    						{
	    							//System.out.println("Inside tempStartR==tempEndR");
	    							physicallyMove=true;
	    			    			break;
	    						}
	    						/*else //There is a piece in the way
		    			    	{
	    							System.out.println("Inside else statement for tempStartR==tempEndR");
		    			    		physicallyMove=false;
		    			    		break;
		    			    	}*/
	    					}
	    					else
	    					{
	    						//System.out.println("Inside else statement for temp1==null");
	    						if(temp1.isWhite()!=whiteturn && tempStartR-1==tempEndR)
	    						{
	    							//System.out.println("temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
	    							physicallyMove=true;
	    			    			break;
	    						}
	    						else
	    						{
	    							//System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
		    						physicallyMove=false;
		    						break;
	    						}
	    						
	    					}
	    				}
	    			}
	    		}
	    		
	    		Piece t_1= b.getPiece(startR, startF);
				Rook t_2=(Rook)t_1;
				//System.out.println("First Move: "+temp2.getFirstMove());
				t_2.completeFirstMove();
				//System.out.println("First Move after: "+t_2.getFirstMove());
	    	}
	    	
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//Bishop
	    	/**
	    	 * The part of the code that checks if the given move is possible based on the board given that the source piece is Bishop.
	    	 */
	    	else if(pieceName.substring(1,2).equals("B"))//If the piece is a Bishop
	    	{
	    		int tempbsr = startR;
	    		int tempbsf = startF;
	    		int tempber = endR;
	    		int tempbef = endF;
	    		boolean left;
	    		boolean down;
				if(tempbsr == tempber) {
					System.out.println("Illegal move, try again");
					continue;
				}
				if(tempbsf == tempbef) {
					System.out.println("Illegal move, try again");
					continue;
				}
	    		if(tempbsr > tempber) {
	    			left = true;
	    		}
	    		else {
	    			left = false;
	    		}
	    		if(tempbsf > tempbef) {
	    			down = false;
	    		}
	    		else {
	    			down = true;
	    		}
	    		
	    		if(left == true & down == true) {// Moving diagonally down left
	    			while(tempbsr > tempber+1 && tempbsf > tempbef+1) {//check if there are any pieces in the way
	    				tempbsr--; tempbsf--;
	    				if(tempbsr == tempber) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    				if(tempbsf == tempbef) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    				Piece temp1=b.getPiece(tempbsr, tempbsf);
	    				if(temp1!=null) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    			}
	    			Piece temp2 = b.getPiece(tempber, tempbef);
	    			if(temp2 != null) {
	    				if(temp2.isWhite() == whiteturn) {
	    					System.out.println("Illegal move, try again");
	    				}
	    			}
	    			physicallyMove = true;
	    		}
	    		if(left == true & down == false) {// Moving diagonally up left
	    			while(tempbsr > tempber+1 && tempbsf < tempbef-1) {//check if there are any pieces in the way
	    				tempbsr--; tempbsf++;
	    				if(tempbsr == tempber) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    				if(tempbsf == tempbef) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    				Piece temp1=b.getPiece(tempbsr, tempbsf);
	    				if(temp1!=null) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    			}
	    			Piece temp2 = b.getPiece(tempber, tempbef);
	    			if(temp2 != null) {
	    				if(temp2.isWhite() == whiteturn) {
	    					System.out.println("Illegal move, try again");
	    				}
	    			}
	    			physicallyMove = true;
	    		}
	    		if(left == false & down == false) {// Moving diagonally up right 
	    			while(tempbsr < tempber-1 && tempbsf < tempbef-1) {//check if there are any pieces in the way
	    				tempbsr++; tempbsf++;
	    				if(tempbsr == tempber) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    				if(tempbsf == tempbef) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    				Piece temp1=b.getPiece(tempbsr, tempbsf);
	    				if(temp1!=null) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    			}
	    			Piece temp2 = b.getPiece(tempber, tempbef);
	    			if(temp2 != null) {
	    				if(temp2.isWhite() == whiteturn) {
	    					System.out.println("Illegal move, try again");
	    				}
	    			}
	    			physicallyMove = true;
	    		}
	    		if(left == false & down == true) {// Moving diagonally down right
	    			while(tempbsr < tempber-1 && tempbsf > tempbef+1) {//check if there are any pieces in the way
	    				tempbsr++; tempbsf--;
	    				if(tempbsr == tempber) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    				if(tempbsf == tempbef) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    				Piece temp1=b.getPiece(tempbsr, tempbsf);
	    				if(temp1!=null) {
	    					System.out.println("Illegal move, try again");
	    					physicallyMove = false;
	    					break;
	    				}
	    			}
	    			Piece temp2 = b.getPiece(tempber, tempbef);
	    			if(temp2 != null) {
	    				if(temp2.isWhite() == whiteturn) {
	    					System.out.println("Illegal move, try again");
	    				}
	    			}
	    			physicallyMove = true;
	    		}
	    	}
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//King
	    	/**
	    	 * The part of the code that checks if the given move is possible based on the board given that the source piece is King.
	    	 */
	    	else if(pieceName.substring(1,2).equals("K"))//If the piece is a King
	    	{
	    		//Checking for castling first
	    		boolean castling=false; //true if can castle
	    		Piece ck= b.getPiece(startR,startF);
		    	King k=(King)ck;
		    	
		    	
		    	 if(startR-endR==0 && k.getFirstMove()==true && Math.abs(endF-startF)==2)//If the movement is horizontal and the movement request is of 2 horizontal movements
	    		 {
		    		boolean leftTOright;
				    if(endF>startF)
				    {
				    	leftTOright=true;
				    }
				    else
				    {
				    	leftTOright=false;
				    }
				    
				    Piece t1;
				    Rook t2;
				    if(leftTOright) //Moving left to Right
				    {
				    	//System.out.println("Moving left To Right");
				    	t1=b.getPiece(startR,8);
				    	t2=(Rook)t1;
				    	
				    	if(b.getPiece(startR,6)==null && b.getPiece(startR,7)==null && t2.getFirstMove()==true)
				    	{
				    		castling=true;
				    	}
				    	else
				    	{
				    		castling=false;
				    	}
				    }
				    else //moving right to left
				    {
				    	//System.out.println("Moving right To left");
				    	t1=b.getPiece(startR,1);
				    	t2=(Rook)t1;
				    	
				    	if(b.getPiece(startR,2)==null && b.getPiece(startR,3)==null && b.getPiece(startR,4)==null && t2.getFirstMove()==true)
				    	{
				    		castling=true;
				    	}
				    	else
				    	{
				    		castling=false;
				    	}
				    }
	    		 }
		    	 else //If movement involves vertical movement
		    	 {
		    		 castling=false;
		    	 }
		    	
		    	 if(castling==false)
		    	 {
		    		 physicallyMove= b.canKingMove(startR, startF, endR, endF);
		    	 }
		    	 else
		    	 {
		    		 physicallyMove=true;
		    		 if(endF>startF)
		    		 {
		    			 b.movePiece(startR, 8, startR, 6);
		    		 }
		    		 else
		    		 {
		    			 b.movePiece(startR, 1, startR, 4);
		    		 }
		    		 
		    		 
		    	 }
		    	

	    	}

	    	
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//Knight
	    	/**
	    	 * The part of the code that checks if the given move is possible based on the board given that the source piece is Knight.
	    	 */
	    	else if(pieceName.substring(1,2).equals("N"))//If the piece is a Knight
	    	{
	    		physicallyMove= b.canKnightMove(startR, startF, endR, endF);
	    	}
	    	
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//___________________________________________________________________________________________________________________________________________________________________________________________________
	    	//Queen
	    	/**
	    	 * The part of the code that checks if the given move is possible based on the board given that the source piece is Queen.
	    	 */
	    	else if(pieceName.substring(1,2).equals("Q"))//If the piece is a Queen
	    	{
	    		boolean straight=false;
	    		if(startR==endR || startF==endF)
	    		{
	    			straight=true; //if Rank of File is the same, the queen is moving straight
	    		}
	    		
	    		if(straight) //Use Rook code
	    		{
		    		int tempStartR=startR;
		    		int tempEndR=endR;
		    		
		    		int tempStartF= startF;
		    		int tempEndF=endF;
		    		if(startR-endR==0) //Which means it moving across file/ moving horizontally
		    		{
		    			while(Math.abs(tempEndF-tempStartF)!=0)
		    			{
		    				//System.out.println("Inside F while loop- End:"+tempEndF+" Start:"+tempStartF );
		    				if(tempEndF>tempStartF) //If the piece is moving from left to right
		    				{
		    					//System.out.println("Inside tempEndF>tempStartF");
		    					Piece temp1=b.getPiece(endR,tempStartF+1);
		    			    	if(temp1==null)
		    			    	{
		    			    		tempStartF++;
		    			    		if(tempStartF==tempEndF) //No piece in between the start and end position
		    			    		{
		    			    		//	System.out.println("Inside tempStartF==tempEndF");
		    			    			physicallyMove=true;
		    			    			break;
		    			    		}
		    			    	}
		    			    	else //There is a piece in the way
		    			    	{
		    			    		//System.out.println("Inside else statement for temp1==null");
		    			    		if(temp1.isWhite()!=whiteturn && tempStartF+1==tempEndF)
		    						{
		    							//System.out.println("temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
		    							physicallyMove=true;
		    			    			break;
		    						}
		    						else
		    						{
		    							//System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
			    						physicallyMove=false;
			    						break;
		    						}
		    			    	}
		    				}
		    				else //If the piece is moving from right to left
		    				{
		    					
		    					//System.out.println("Inside else statement of tempEndF>tempStartF");
		    					Piece temp1=b.getPiece(endR,tempStartF-1);
		    			    	if(temp1==null)
		    			    	{
		    			    		tempStartF--;
		    			    		if(tempStartF==tempEndF) //No piece in between the start and end position
		    			    		{
		    			    			//System.out.println("Inside tempStartF==tempEndF");
		    			    			physicallyMove=true;
		    			    			break;
		    			    		}
		    			    	}
		    			    	else //There is a piece in the way
		    			    	{
		    			    		//System.out.println("Inside else statement for temp1==null");
		    			    		if(temp1.isWhite()!=whiteturn && tempStartF-1==tempEndF)
		    						{
		    							//System.out.println("temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
		    							physicallyMove=true;
		    			    			break;
		    						}
		    						else
		    						{
		    							//System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
			    						physicallyMove=false;
			    						break;
		    						}
		    			    	}
		    				}
		    			}
		    		}
		    		else//Rook is moving across Rank/ moving vertically
		    		{
		    			while(Math.abs(tempEndR-tempStartR)!=0)
		    			{
		    				//System.out.println("Inside R while loop- End:"+tempEndR+" Start:"+tempStartR );
		    				if(tempEndR>tempStartR) //if the piece is moving from bottom to top
		    				{
		    					//System.out.println("Inside tempEndR>tempStartR");
		    					Piece temp1=b.getPiece(tempStartR+1, endF);
		    					if(temp1==null)
		    					{
		    					//	System.out.println("Inside temp1==null");
		    						tempStartR++;
		    						if(tempStartR==tempEndR) // No pieces in between the start and end position
		    						{
		    							//System.out.println("Inside tempStartR==tempEndR");
		    							physicallyMove=true;
		    			    			break;
		    						}
		    						/*else //There is a piece in the way
			    			    	{
		    							System.out.println("Inside else statement for tempStartR==tempEndR");
			    			    		physicallyMove=false;
			    			    		break;
			    			    	}*/
		    					}
		    					else
		    					{
		    						//System.out.println("Inside else statement for temp1==null");
		    						if(temp1.isWhite()!=whiteturn && tempStartR+1==tempEndR)
		    						{
		    							//System.out.println("temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
		    							physicallyMove=true;
		    			    			break;
		    						}
		    						else
		    						{
		    							//System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
			    						physicallyMove=false;
			    						break;
		    						}
		    						
		    					}
		    				}
		    				else //if the piece is moving from top to bottom
		    				{
		    					//System.out.println("Inside tempEndR<tempStartR");
		    					Piece temp1=b.getPiece(tempStartR-1, endF);
		    					if(temp1==null)
		    					{
		    						//System.out.println("Inside temp1==null");
		    						tempStartR--;
		    						if(tempStartR==tempEndR) // No pieces in between the start and end position
		    						{
		    							//System.out.println("Inside tempStartR==tempEndR");
		    							physicallyMove=true;
		    			    			break;
		    						}
		    						/*else //There is a piece in the way
			    			    	{
		    							System.out.println("Inside else statement for tempStartR==tempEndR");
			    			    		physicallyMove=false;
			    			    		break;
			    			    	}*/
		    					}
		    					else
		    					{
		    						//System.out.println("Inside else statement for temp1==null");
		    						if(temp1.isWhite()!=whiteturn && tempStartR-1==tempEndR)
		    						{
		    							//System.out.println("temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
		    							physicallyMove=true;
		    			    			break;
		    						}
		    						else
		    						{
		    							//System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
			    						physicallyMove=false;
			    						break;
		    						}
		    						
		    					}
		    				}
		    			}
		    		}
		    	}
	    		else //Use Bishop code
	    		{
		    		int tempbsr = startR;
		    		int tempbsf = startF;
		    		int tempber = endR;
		    		int tempbef = endF;
		    		boolean left;
		    		boolean down;
					if(tempbsr == tempber) {
						System.out.println("Illegal move, try again");
						continue;
					}
					if(tempbsf == tempbef) {
						System.out.println("Illegal move, try again");
						continue;
					}
		    		if(tempbsr > tempber) {
		    			left = true;
		    		}
		    		else {
		    			left = false;
		    		}
		    		if(tempbsf > tempbef) {
		    			down = false;
		    		}
		    		else {
		    			down = true;
		    		}
		    		
		    		if(left == true & down == true) {// Moving diagonally down left
		    			while(tempbsr > tempber+1 && tempbsf > tempbef+1) {//check if there are any pieces in the way
		    				tempbsr--; tempbsf--;
		    				if(tempbsr == tempber) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    				if(tempbsf == tempbef) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    				Piece temp1=b.getPiece(tempbsr, tempbsf);
		    				if(temp1!=null) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    			}
		    			Piece temp2 = b.getPiece(tempber, tempbef);
		    			if(temp2 != null) {
		    				if(temp2.isWhite() == whiteturn) {
		    					System.out.println("Illegal move, try again");
		    				}
		    			}
		    			physicallyMove = true;
		    		}
		    		if(left == true & down == false) {// Moving diagonally up left
		    			while(tempbsr > tempber+1 && tempbsf < tempbef-1) {//check if there are any pieces in the way
		    				tempbsr--; tempbsf++;
		    				if(tempbsr == tempber) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    				if(tempbsf == tempbef) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    				Piece temp1=b.getPiece(tempbsr, tempbsf);
		    				if(temp1!=null) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    			}
		    			Piece temp2 = b.getPiece(tempber, tempbef);
		    			if(temp2 != null) {
		    				if(temp2.isWhite() == whiteturn) {
		    					System.out.println("Illegal move, try again");
		    				}
		    			}
		    			physicallyMove = true;
		    		}
		    		if(left == false & down == false) {// Moving diagonally up right 
		    			while(tempbsr < tempber-1 && tempbsf < tempbef-1) {//check if there are any pieces in the way
		    				tempbsr++; tempbsf++;
		    				if(tempbsr == tempber) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    				if(tempbsf == tempbef) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    				Piece temp1=b.getPiece(tempbsr, tempbsf);
		    				if(temp1!=null) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    			}
		    			Piece temp2 = b.getPiece(tempber, tempbef);
		    			if(temp2 != null) {
		    				if(temp2.isWhite() == whiteturn) {
		    					System.out.println("Illegal move, try again");
		    				}
		    			}
		    			physicallyMove = true;
		    		}
		    		if(left == false & down == true) {// Moving diagonally down right
		    			while(tempbsr < tempber-1 && tempbsf > tempbef+1) {//check if there are any pieces in the way
		    				tempbsr++; tempbsf--;
		    				if(tempbsr == tempber) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    				if(tempbsf == tempbef) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    				Piece temp1=b.getPiece(tempbsr, tempbsf);
		    				if(temp1!=null) {
		    					System.out.println("Illegal move, try again");
		    					physicallyMove = false;
		    					break;
		    				}
		    			}
		    			Piece temp2 = b.getPiece(tempber, tempbef);
		    			if(temp2 != null) {
		    				if(temp2.isWhite() == whiteturn) {
		    					System.out.println("Illegal move, try again");
		    				}
		    			}
		    			physicallyMove = true;
		    		}
		    	}
	    	}
	    	
	    	
	    	/**
	    	 * If the move is not physically possible based on board, Illegal move
	    	 */
	    	if(physicallyMove==false)
	    	{
	    		System.out.println("Illegal move, try again");
	    		continue;
	    	}
	    	
	    	/**
	    	 * Moving piece based on it being completely legal
	    	 * Pawn promotion calls
	    	 */
	    	Piece c = b.getPiece(endR, endF);
	    	String cname = "";
	    	boolean ccolor = true;
	    	
	    	if(physicallyMove==true && isLegal==true)
	    	{
	    		b.movePiece(startR, startF, endR, endF);
	    	}
	    	if(promotion == true) {
	    		if(arraylength == 3) {
	    			b.addPiece(endR, endF, words[2].charAt(0), whiteturn);	 
	    		}
	    		else {
	    			b.addPiece(endR, endF, promo.charAt(0), whiteturn);
	    		}
	    	}
	    	//System.out.println("whiteturn is " + whiteturn);
	    	/**
	    	 * Checking for check and checkmate
	    	 */
	    	boolean wcheck = b.wCheck();
	    	boolean bcheck = b.bCheck();
	    	
	    	//System.out.println("whitecheck = " + wcheck + " blackcheck = " + bcheck);
	    	if(whiteturn == true) {//white turn
	    		//System.out.println("Checking during white turn...");
	    		//System.out.println("wcheck is " + wcheck);
		    	if(wcheck == true) {
		    		System.out.println("Illegal move, try again"); //Illegal white move
			    	Piece d = b.getPiece(endR, endF);
			    	boolean dcolor = true;
			    	String dname = "";
			    	if(d != null) {
				    	dcolor = d.isWhite();
				    	dname = d.getName();
			    	}
			    	//System.out.println("cname is " + cname);
			    	if(c != null) {
			    		b.addPiece(endR, endF, cname.charAt(1), ccolor);
			    	}
			    	else {
			    		b.deletePiece(endF, endR);
			    	}
			    	b.addPiece(startR, startF, dname.charAt(1), dcolor);
			    	b.print();
			    	continue;
			    }
			    /*if(bcheck == true) {
		    		System.out.println("Black in check"); //Black in check
			    }*/
	    	}   
	    	else {//black turn
	    		//System.out.println("Checking during black turn...");
	    		//System.out.println("bcheck is " + bcheck);
	    		//b.wCheck();
	    		//b.bCheck();
		    	if(bcheck == true) {
		    		System.out.println("Illegal move, try again"); //Black in check, illegal move.
			    	Piece d = b.getPiece(endR, endF);
			    	boolean dcolor = true;
			    	String dname = "";
			    	if(d != null) {
				    	dcolor = d.isWhite();
				    	dname = d.getName();
			    	}
			    	if(c != null) {
				    	//System.out.println("cname is " + cname);
			    		b.addPiece(endR, endF, cname.charAt(1), ccolor);
			    	}
			    	else {
			    		b.deletePiece(endF, endR);
			    	}
			    	b.addPiece(startR, startF, dname.charAt(1), dcolor);
			    	b.print();
			    	continue;
			    }
			    /*if(wcheck == true) {
			    	System.out.println("White in check");
			    }*/
	    	}
		    if(enpassant == true) {
		    	if(enp == 0) {
		    		enp = 1;
		    	}
		    	else {
		    		enpassant = false;
		    		enp = 0;
		    	}
		    }
		    
		    /**
		     * Printing out the new board
		     */
	    	System.out.println();
	    	b.print(); //Print out the new board
	    	System.out.println();
	    	
	    	if(wcheck == true) {
	    		System.out.println("Check");
	    	}
	    	if(bcheck == true) {
	    		System.out.println("Check");
	    	}
	    	
	    	whiteturn = !whiteturn; //Flipping the whiteturn boolean value since it is the other player's turn
	    	//System.out.println(input);
	    }
	    
	   
	    //Print out which player won based on who resigned
	    /**
		    * If someone resigned, print out the winner
		    */
	    if(input.equals("resign")) {
	    	//System.out.println(whiteturn);
	    	if(whiteturn == true){
	    		System.out.println("Black wins");
	    	}
	    	else {
	    		System.out.println("Wins wins");
	    	}
	    }
	    /**
	     * Close the scanner and stop taking inputs
	     */

	    scanner.close();
	}

}
