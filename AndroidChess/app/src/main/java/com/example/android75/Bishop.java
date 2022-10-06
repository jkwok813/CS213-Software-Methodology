package com.example.android75;

public class Bishop extends Piece {

    /**
     * Constructor for Bishop. Calls on the super constructor (from Piece)
     *
     * @param color: Takes in a boolean. True will result in the creation of a white Bishop and False will result in the creation of a Black Bishop
     */
    public Bishop(boolean color) {
        super(color);
    }

    //Returns true if Bishop can physically make this move

    /**
     * This method returns true or false based on if a given Bishop can move from source to destination while not accounting for the rest of the board
     *
     * @param startX: The source Rank from the user input
     * @param startY: The source File from the user input
     * @param endX:   The destination Rank from the user input
     * @param endY:   The destination File from the user input
     * @return Returns true if the move can be made by a Bishop (not confirming that the move can be made). Returns False if a Bishop cannot move from source to destination
     */
    @Override
    public boolean isLegalMove(int startX, int startY, int endX, int endY) {
        //If the amount of change in |x|==|y|, then the Bishop moved diagonally
        if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
            return true;
        }
        return false;
    }


    /**
     * This method returns the name of the piece. In this case wB or bB
     *
     * @return Returns the name of the piece.
     */
    @Override
    public String getName() {
        //This method returns the name of the piece in 'wp' or 'bp' format for pawn for ease of printing
        if (this.isWhite() == true) {
            return "wB";
        } else {
            return "bB";
        }
    }
}
