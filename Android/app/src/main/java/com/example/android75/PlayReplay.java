package com.example.android75;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.android75.databinding.PlayreplayDetailsBinding;

import java.util.ArrayList;

public class PlayReplay extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private PlayreplayDetailsBinding binding;
    Piece[][] chess_board = new Piece[9][9];
    public TextView[][] Display = new TextView[9][9];
    public TextView[][] Back = new TextView[9][9];
    boolean whiteturn = true;
    boolean enpassant;
    String promotion;
    int enp = 0;
    int enpx = -1;
    int enpy = -1;
    String[] todd;
    int turncount;
    boolean blackfirstmove;
    boolean whitefirstmove;
    boolean gameend = false;
    public TextView game_over;
    public TextView game_over_black;
    public TextView game_over_white;
    View next_turn;
    ArrayList<String> gameLog;
    boolean enpassantActivated=false;
    //These keep track of the Rank and File coordinates of (White and Black) King
    /**
     * The variables below keep track of the Rank and File of the White and Black King respectively
     */
    int whiteR = 0;
    int whiteF = 0;
    int blackR = 0;
    int blackF = 0;
    int countingturns = 0;
    /**
     * The make method creates the board. It sets up the board to look like a normal chess board from which we can start the game.
     * It creates a board by creating each Piece manually since the Pieces always start off from the same starting position.
     * This method also sets all the positions without a piece to null.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playreplay_details);
        System.out.println("radadadadadada");
        this.make2();
        promotion = "";
        game_over = findViewById(R.id.game_over);
        game_over.setVisibility(View.INVISIBLE);
        game_over_black = findViewById(R.id.game_over_black);
        game_over_black.setVisibility(View.INVISIBLE);
        game_over_white = findViewById(R.id.game_over_white);
        game_over_white.setVisibility(View.INVISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        next_turn = findViewById(R.id.next_turn);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(replaylist.TITLE_NAME);
        String date = bundle.getString(replaylist.TITLE_DATE);
        String moves = bundle.getString(replaylist.MOVES);
        System.out.println("Title = " + title + " date = " + date + " moves = " + moves);
        todd = new String[10000];
        //Read the contents of MOVE and print them all to an array
        int gar = 0;
        String line2;
        int i = 0;
        int j = 1;
        while(j!= moves.length()){
            line2 = moves.substring(j-1, j);
            //System.out.println("The length of moves is " + moves.length());
            //System.out.println(line2);
            if(line2.equals("|")){
                todd[gar] = moves.substring(i, j-1);
                gar++;
                i=j;
            }
            j++;
        }
        todd[gar] = moves.substring(i, j-1);
        gar++;
    }
    public void make2() {

        //Create White pieces
        // Create board
        //File=Column=Alphabet=first number
        //Rank=Row=Number=second number
        //sets all the starting positions without a piece to be null
        enpassant = false;
        enp = 0;
        enpx = -1;
        enpy = -1;
        blackfirstmove = true;
        whitefirstmove = true;
        for (int i = 3; i < 7; i++) {
            for (int o = 1; o < 9; o++) {
                chess_board[i][o] = null;
            }
        }
// board with x=0 and/or y=0 will be ignored since in Chess there is no '0' position
        // a1 will be [1][1], b1 will be [1][2] and h8 will be [8][8]. So, [][] is [number/row][alphabet/column]. a=1, b=2,...h=8

        chess_board[1][1]=new Rook(true);
        chess_board[1][2]=new Knight(true);
        chess_board[1][3]=new Bishop(true);
        chess_board[1][4]=new Queen(true);
        chess_board[1][5]=new King(true);
        whiteR=1;
        whiteF=5;
        chess_board[1][6]=new Bishop(true);
        chess_board[1][7]=new Knight(true);
        chess_board[1][8]=new Rook(true);

        chess_board[2][1]=new Pawn(true);
        chess_board[2][2]=new Pawn(true);
        chess_board[2][3]=new Pawn(true);
        chess_board[2][4]=new Pawn(true);
        chess_board[2][5]=new Pawn(true);
        chess_board[2][6]=new Pawn(true);
        chess_board[2][7]=new Pawn(true);
        chess_board[2][8]=new Pawn(true);

        //Create Black pieces
        chess_board[8][1]=new Rook(false);
        chess_board[8][2]=new Knight(false);
        chess_board[8][3]=new Bishop(false);
        chess_board[8][4]=new Queen(false);
        chess_board[8][5]=new King(false);
        blackR=8;
        blackF=5;
        chess_board[8][6]=new Bishop(false);
        chess_board[8][7]=new Knight(false);
        chess_board[8][8]=new Rook(false);

        chess_board[7][1]=new Pawn(false);
        chess_board[7][2]=new Pawn(false);
        chess_board[7][3]=new Pawn(false);
        chess_board[7][4]=new Pawn(false);
        chess_board[7][5]=new Pawn(false);
        chess_board[7][6]=new Pawn(false);
        chess_board[7][7]=new Pawn(false);
        chess_board[7][8]=new Pawn(false);

        Display[1][1] = findViewById(R.id.C11);
        Back[1][1] = findViewById(R.id.B11);
        Display[1][2] = findViewById(R.id.C12);
        Back[1][2] = findViewById(R.id.B12);
        Display[1][3] = findViewById(R.id.C13);
        Back[1][3] = findViewById(R.id.B13);
        Display[1][4] = findViewById(R.id.C14);
        Back[1][4] = findViewById(R.id.B14);
        Display[1][5] = findViewById(R.id.C15);
        Back[1][5] = findViewById(R.id.B15);
        Display[1][6] = findViewById(R.id.C16);
        Back[1][6] = findViewById(R.id.B16);
        Display[1][7] = findViewById(R.id.C17);
        Back[1][7] = findViewById(R.id.B17);
        Display[1][8] = findViewById(R.id.C18);
        Back[1][8] = findViewById(R.id.B18);

        Display[2][1] = findViewById(R.id.C21);
        Back[2][1] = findViewById(R.id.B21);
        Display[2][2] = findViewById(R.id.C22);
        Back[2][2] = findViewById(R.id.B22);
        Display[2][3] = findViewById(R.id.C23);
        Back[2][3] = findViewById(R.id.B23);
        Display[2][4] = findViewById(R.id.C24);
        Back[2][4] = findViewById(R.id.B24);
        Display[2][5] = findViewById(R.id.C25);
        Back[2][5] = findViewById(R.id.B25);
        Display[2][6] = findViewById(R.id.C26);
        Back[2][6] = findViewById(R.id.B26);
        Display[2][7] = findViewById(R.id.C27);
        Back[2][7] = findViewById(R.id.B27);
        Display[2][8] = findViewById(R.id.C28);
        Back[2][8] = findViewById(R.id.B28);

        Display[3][1] = findViewById(R.id.C31);
        Back[3][1] = findViewById(R.id.B31);
        Display[3][2] = findViewById(R.id.C32);
        Back[3][2] = findViewById(R.id.B32);
        Display[3][3] = findViewById(R.id.C33);
        Back[3][3] = findViewById(R.id.B33);
        Display[3][4] = findViewById(R.id.C34);
        Back[3][4] = findViewById(R.id.B34);
        Display[3][5] = findViewById(R.id.C35);
        Back[3][5] = findViewById(R.id.B35);
        Display[3][6] = findViewById(R.id.C36);
        Back[3][6] = findViewById(R.id.B36);
        Display[3][7] = findViewById(R.id.C37);
        Back[3][7] = findViewById(R.id.B37);
        Display[3][8] = findViewById(R.id.C38);
        Back[3][8] = findViewById(R.id.B38);

        Display[4][1] = findViewById(R.id.C41);
        Back[4][1] = findViewById(R.id.B41);
        Display[4][2] = findViewById(R.id.C42);
        Back[4][2] = findViewById(R.id.B42);
        Display[4][3] = findViewById(R.id.C43);
        Back[4][3] = findViewById(R.id.B43);
        Display[4][4] = findViewById(R.id.C44);
        Back[4][4] = findViewById(R.id.B44);
        Display[4][5] = findViewById(R.id.C45);
        Back[4][5] = findViewById(R.id.B45);
        Display[4][6] = findViewById(R.id.C46);
        Back[4][6] = findViewById(R.id.B46);
        Display[4][7] = findViewById(R.id.C47);
        Back[4][7] = findViewById(R.id.B47);
        Display[4][8] = findViewById(R.id.C48);
        Back[4][8] = findViewById(R.id.B48);

        Display[5][1] = findViewById(R.id.C51);
        Back[5][1] = findViewById(R.id.B51);
        Display[5][2] = findViewById(R.id.C52);
        Back[5][2] = findViewById(R.id.B52);
        Display[5][3] = findViewById(R.id.C53);
        Back[5][3] = findViewById(R.id.B53);
        Display[5][4] = findViewById(R.id.C54);
        Back[5][4] = findViewById(R.id.B54);
        Display[5][5] = findViewById(R.id.C55);
        Back[5][5] = findViewById(R.id.B55);
        Display[5][6]= findViewById(R.id.C56);
        Back[5][6] = findViewById(R.id.B56);
        Display[5][7] = findViewById(R.id.C57);
        Back[5][7] = findViewById(R.id.B57);
        Display[5][8] = findViewById(R.id.C58);
        Back[5][8] = findViewById(R.id.B58);

        Display[6][1] = findViewById(R.id.C61);
        Back[6][1] = findViewById(R.id.B61);
        Display[6][2] = findViewById(R.id.C62);
        Back[6][2] = findViewById(R.id.B62);
        Display[6][3] = findViewById(R.id.C63);
        Back[6][3] = findViewById(R.id.B63);
        Display[6][4] = findViewById(R.id.C64);
        Back[6][4] = findViewById(R.id.B64);
        Display[6][5] = findViewById(R.id.C65);
        Back[6][5] = findViewById(R.id.B65);
        Display[6][6] = findViewById(R.id.C66);
        Back[6][6] = findViewById(R.id.B66);
        Display[6][7] = findViewById(R.id.C67);
        Back[6][7] = findViewById(R.id.B67);
        Display[6][8] = findViewById(R.id.C68);
        Back[6][8] = findViewById(R.id.B68);

        Display[7][1] = findViewById(R.id.C71);
        Back[7][1] = findViewById(R.id.B71);
        Display[7][2] = findViewById(R.id.C72);
        Back[7][2] = findViewById(R.id.B72);
        Display[7][3]= findViewById(R.id.C73);
        Back[7][3] = findViewById(R.id.B73);
        Display[7][4] = findViewById(R.id.C74);
        Back[7][4] = findViewById(R.id.B74);
        Display[7][5] = findViewById(R.id.C75);
        Back[7][5] = findViewById(R.id.B75);
        Display[7][6] = findViewById(R.id.C76);
        Back[7][6] = findViewById(R.id.B76);
        Display[7][7] = findViewById(R.id.C77);
        Back[7][7] = findViewById(R.id.B77);
        Display[7][8] = findViewById(R.id.C78);
        Back[7][8] = findViewById(R.id.B78);

        Display[8][1] = findViewById(R.id.C81);
        Back[8][1] = findViewById(R.id.B81);
        Display[8][2] = findViewById(R.id.C82);
        Back[8][2] = findViewById(R.id.B82);
        Display[8][3] = findViewById(R.id.C83);
        Back[8][3] = findViewById(R.id.B83);
        Display[8][4] = findViewById(R.id.C84);
        Back[8][4] = findViewById(R.id.B84);
        Display[8][5] = findViewById(R.id.C85);
        Back[8][5] = findViewById(R.id.B85);
        Display[8][6] = findViewById(R.id.C86);
        Back[8][6] = findViewById(R.id.B86);
        Display[8][7] = findViewById(R.id.C87);
        Back[8][7] = findViewById(R.id.B87);
        Display[8][8] = findViewById(R.id.C88);
        Back[8][8] = findViewById(R.id.B88);
        boardDisp();
    }

    public void boardDisp() {
        ////print();
        for (int j = 8; j > 0; j--) {
            for (int i = 1; i < 9; i++) {
                Piece s = chess_board[i][j];
                if (s != null) {
                    if (chess_board[i][j].getName().equals("wp")) {
                        Display[i][j].setBackgroundResource(R.drawable.wpawn);
                    } else if (chess_board[i][j].getName().equals("wQ")) {
                        Display[i][j].setBackgroundResource(R.drawable.wqueen);
                    } else if (chess_board[i][j].getName().equals("wK")) {
                        Display[i][j].setBackgroundResource(R.drawable.wking);
                    } else if (chess_board[i][j].getName().equals("wB")) {
                        Display[i][j].setBackgroundResource(R.drawable.wbshop);
                    } else if (chess_board[i][j].getName().equals("wN")) {
                        Display[i][j].setBackgroundResource(R.drawable.wknight);
                    } else if (chess_board[i][j].getName().equals("wR")) {
                        Display[i][j].setBackgroundResource(R.drawable.wrook);
                    } else if (chess_board[i][j].getName().equals("bQ")) {
                        Display[i][j].setBackgroundResource(R.drawable.bqueen);
                    } else if (chess_board[i][j].getName().equals("bK")) {
                        Display[i][j].setBackgroundResource(R.drawable.bking);
                    } else if (chess_board[i][j].getName().equals("bB")) {
                        Display[i][j].setBackgroundResource(R.drawable.bbishop);
                    } else if (chess_board[i][j].getName().equals("bN"))  {
                        Display[i][j].setBackgroundResource(R.drawable.bknight);
                    } else if (chess_board[i][j].getName().equals("bR"))  {
                        Display[i][j].setBackgroundResource(R.drawable.brook);
                    } else if (chess_board[i][j].getName().equals("bp")) {
                        Display[i][j].setBackgroundResource(R.drawable.bpawn);
                    }
                }
                if(s == null && Display[i][j] != null){
                    Display[i][j].setBackgroundResource(0);
                }
            }
        }
    }
    public void next_turn(View view){
        //call movepiece, move the piece from todd[int]
        if(gameend){
            return;
        }
        countingturns++;
        int startR = 0;
        int endR = 0;
        int startF = 0 ;
        int endF= 0;
        //System.out.println("IT IS CURRENTLY FUC " + todd[turncount]);
        //Check for resign, draw here
        System.out.println(todd[turncount]);
        if(todd[turncount].equals("resign")){
            //Enter a game over here
            if(countingturns%2 == 0) {
                game_over_black.setVisibility(View.VISIBLE);
                gameend = true;
                return;
            }
            else{
                game_over_white.setVisibility(View.VISIBLE);
                gameend = true;
                return;
            }
        }
        if(todd[turncount].equals("draw")){
            //Enter a game over here
            game_over.setVisibility(View.VISIBLE);
            gameend = true;
            return;
        }
        //Check for move here
        if(todd[turncount].charAt(0) == 'a'){
            startF = 1;
        }
        else if(todd[turncount].charAt(0) == 'b'){
            startF = 2;
        }
        else if(todd[turncount].charAt(0) == 'c'){
            startF = 3;
        }
        else if(todd[turncount].charAt(0) == 'd'){
            startF = 4;
        }
        else if(todd[turncount].charAt(0) == 'e'){
            startF = 5;
        }
        else if(todd[turncount].charAt(0) == 'f'){
            startF = 6;
        }
        else if(todd[turncount].charAt(0) == 'g'){
            startF = 7;
        }
        else if(todd[turncount].charAt(0) == 'h'){
            startF = 8;
        }
        if(todd[turncount].charAt(3) == 'a'){
            endF = 1;
        }
        else if(todd[turncount].charAt(3) == 'b'){
            endF = 2;
        }
        else if(todd[turncount].charAt(3) == 'c'){
            endF = 3;
        }
        else if(todd[turncount].charAt(3) == 'd'){
            endF = 4;
        }
        else if(todd[turncount].charAt(3) == 'e'){
            endF = 5;
        }
        else if(todd[turncount].charAt(3) == 'f'){
            endF = 6;
        }
        else if(todd[turncount].charAt(3) == 'g'){
            endF = 7;
        }
        else if(todd[turncount].charAt(3) == 'h'){
            endF = 8;
        }

        startR = Character.getNumericValue(todd[turncount].charAt(1));
        endR = Character.getNumericValue(todd[turncount].charAt(4));
        //Check for enpassant, castling or promotion here
        if(todd[turncount].length() > 6){//enpassant, castling activated.
            ///////////castling
            if(todd[turncount].charAt(6) == 'T'){
                if(startF > endF) {//Moving to the left
                    movePiece(startR, 5, endR, 7);
                }
                else{
                    movePiece(startR, 5, endR, 3);
                }
            }
            /////////enpassant
            if(todd[turncount].charAt(6) == 'S'){
               deletePiece(endF, startR);
            }
        }
        //Call movepiece here
        if(!gameend) {
            System.out.println("startR, F e f " + startR + startR + endR + endF);
            movePiece(startR, startF, endR, endF);
        }
        //Check promotion
        if(todd[turncount].length() > 6) {
            if (todd[turncount].charAt(6) == 'N') {
                for (int x = 1; x < 9; x++) {
                    System.out.println("Checking file " + x);
                    Piece pawncheck = chess_board[1][x];
                    if (pawncheck != null) {
                        //System.out.println("Piece found");
                        String piecename = pawncheck.getName();
                        String piecetype = piecename.substring(1, 2);
                        if (piecetype.equals("p")) {
                            chess_board[1][x] = new Knight(false);
                        } else {
                            System.out.println("Not a pawn." + pawncheck.getName());
                        }
                    }
                    pawncheck = chess_board[8][x];
                    if (pawncheck != null) {
                        String piecename = pawncheck.getName();
                        String piecetype = piecename.substring(1, 2);
                        if (piecetype.equals("p")) {
                            chess_board[8][x] = new Knight(true);
                        } else {
                            System.out.println("Not a pawn." + pawncheck.getName());
                        }
                    }

                }
            }
            if (todd[turncount].charAt(6) == 'R') {
                for (int x = 1; x < 9; x++) {
                    System.out.println("Checking file " + x);
                    Piece pawncheck = chess_board[1][x];
                    if (pawncheck != null) {
                        //System.out.println("Piece found");
                        String piecename = pawncheck.getName();
                        String piecetype = piecename.substring(1, 2);
                        if (piecetype.equals("p")) {
                            chess_board[1][x] = new Rook(false);
                        } else {
                            System.out.println("Not a pawn." + pawncheck.getName());
                        }
                    }
                    pawncheck = chess_board[8][x];
                    if (pawncheck != null) {
                        String piecename = pawncheck.getName();
                        String piecetype = piecename.substring(1, 2);
                        if (piecetype.equals("p")) {
                            chess_board[8][x] = new Rook(true);
                        } else {
                            System.out.println("Not a pawn." + pawncheck.getName());
                        }
                    }

                }
            }
            if (todd[turncount].charAt(6) == 'B') {
                for (int x = 1; x < 9; x++) {
                    System.out.println("Checking file " + x);
                    Piece pawncheck = chess_board[1][x];
                    if (pawncheck != null) {
                        //System.out.println("Piece found");
                        String piecename = pawncheck.getName();
                        String piecetype = piecename.substring(1, 2);
                        if (piecetype.equals("p")) {
                            chess_board[1][x] = new Bishop(false);
                        } else {
                            System.out.println("Not a pawn." + pawncheck.getName());
                        }
                    }
                    pawncheck = chess_board[8][x];
                    if (pawncheck != null) {
                        String piecename = pawncheck.getName();
                        String piecetype = piecename.substring(1, 2);
                        if (piecetype.equals("p")) {
                            chess_board[8][x] = new Bishop(true);
                        } else {
                            System.out.println("Not a pawn." + pawncheck.getName());
                        }
                    }

                }
            }
            if (todd[turncount].charAt(6) == 'Q') {
                for (int x = 1; x < 9; x++) {
                    System.out.println("Checking file " + x);
                    Piece pawncheck = chess_board[1][x];
                    if (pawncheck != null) {
                        //System.out.println("Piece found");
                        String piecename = pawncheck.getName();
                        String piecetype = piecename.substring(1, 2);
                        if (piecetype.equals("p")) {
                            chess_board[1][x] = new Queen(false);
                        } else {
                            System.out.println("Not a pawn." + pawncheck.getName());
                        }
                    }
                    pawncheck = chess_board[8][x];
                    if (pawncheck != null) {
                        String piecename = pawncheck.getName();
                        String piecetype = piecename.substring(1, 2);
                        if (piecetype.equals("p")) {
                            chess_board[8][x] = new Queen(true);
                        } else {
                            System.out.println("Not a pawn." + pawncheck.getName());
                        }
                    }

                }
            }
        }
        boardDisp();
        turncount++;
    }
    public void movePiece(int startR,int startF, int endR, int endF)
    {
        chess_board[endR][endF]=chess_board[startR][startF];
        chess_board[startR][startF]=null;
        boardDisp();
        Display[startR][startF].setBackgroundResource(0);
    }
    public void deletePiece(int x, int y) {
        System.out.println(y + " " + x + "del'd");
        chess_board[y][x] = null;
        boardDisp();
        //System.out.println("Piece at " + x + " and " + y + " deleted");
    }
}