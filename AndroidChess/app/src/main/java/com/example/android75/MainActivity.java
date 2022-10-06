package com.example.android75;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import com.example.android75.Piece;
import com.example.android75.Queen;
import com.example.android75.Rook;
import com.example.android75.Pawn;
import com.example.android75.Bishop;
import com.example.android75.Knight;
import com.example.android75.King;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements Serializable {
    /**
     * chess_board is the 2d Piece array where the game is being played, where the piece location is being tracked and where the board is printed from.
     */
    Piece[][] chess_board = new Piece[9][9];
    public TextView[][] Display = new TextView[9][9];
    public TextView[][] Back = new TextView[9][9];
    boolean whiteturn = true;
    public TextView game_over_white;
    public TextView game_over_black;
    public TextView game_over;
    public TextView black_turn;
    public TextView white_turn;
    boolean enpassant;
    String promotion;
    int enp = 0;
    int enpx = -1;
    int enpy = -1;
    int undid = 0;
    boolean blackfirstmove;
    boolean whitefirstmove;
    View b;
    View c;
    View d;
    View e;
    View f;
    View h;
    View queenpromo;
    View rookpromo;
    View bishoppromo;
    View knightpromo;
    View replaybutton;
    View test;
    View white_checked;
    View black_checked;
    ArrayList<String> gameLog;
    boolean enpassantActivated=false;
    String readFromFile;
    //These keep track of the Rank and File coordinates of (White and Black) King
    /**
     * The variables below keep track of the Rank and File of the White and Black King respectively
     */
    int whiteR = 0;
    int whiteF = 0;
    int blackR = 0;
    int blackF = 0;

    /**
     * The make method creates the board. It sets up the board to look like a normal chess board from which we can start the game.
     * It creates a board by creating each Piece manually since the Pieces always start off from the same starting position.
     * This method also sets all the positions without a piece to null.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Inside OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.make();
        promotion = "";
        game_over = findViewById(R.id.game_over);
        game_over_white = findViewById(R.id.game_over_white);
        game_over_black = findViewById(R.id.game_over_black);
        black_turn = findViewById(R.id.black_turn);
        black_turn.setVisibility(View.INVISIBLE);
        white_turn = findViewById(R.id.white_turn);
        white_turn.setVisibility(View.VISIBLE);
        game_over_white.setVisibility(View.INVISIBLE);
        game_over_black.setVisibility(View.INVISIBLE);
        game_over.setVisibility(View.INVISIBLE);
        b = findViewById(R.id.new_game_button);
        b.setVisibility(View.GONE);
        c = findViewById(R.id.save_button);
        c.setVisibility(View.GONE);
        d = findViewById(R.id.undo_button);
        d.setVisibility(View.VISIBLE);
        e = findViewById(R.id.resign_button);
        e.setVisibility(View.VISIBLE);
        f = findViewById(R.id.draw_button);
        f.setVisibility(View.VISIBLE);
        h = findViewById(R.id.ai_button);
        h.setVisibility(View.VISIBLE);
        queenpromo = findViewById(R.id.queen_button);
        rookpromo = findViewById(R.id.rook_button);
        bishoppromo = findViewById(R.id.bishop_button);
        knightpromo = findViewById(R.id.knight_button);
        queenpromo.setVisibility(View.GONE);
        bishoppromo.setVisibility(View.GONE);
        rookpromo.setVisibility(View.GONE);
        knightpromo.setVisibility(View.GONE);
        replaybutton = findViewById(R.id.access_recording_button);
        white_checked = findViewById(R.id.white_checked);
        black_checked = findViewById(R.id.black_checked);
        black_checked.setVisibility(View.INVISIBLE);
        white_checked.setVisibility(View.INVISIBLE);
        gameLog= new ArrayList<String>();

        readFromFile= readFromFile();
        System.out.println("From file: "+readFromFile);

    }


    public void make() {

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

    public void print() {
        //Initial print board
        for(int r = 8; r > 0; r--){
            for(int f = 1; f< 9; f++){
                Piece s = chess_board[r][f];
                if(s!=null)
                {
                    String ans=s.getName();
                    System.out.print(ans+" ");
                }
                else
                {
                    if(r%2==0)
                    {
                        if(f%2!=0)
                            System.out.print("   ");
                        else
                            System.out.print("## ");

                    }
                    else
                    {
                        if(f%2!=0)
                            System.out.print("## ");
                        else
                            System.out.print("   ");
                    }
                }


            }
            System.out.println(r);
        }
        System.out.println(" a  b  c  d  e  f  g  h");
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
    String start_pos="empty";
    String end_pos="empty";
    int startR=0;
    int startF=0;
    int endR=0;
    int endF=0;

    public void onClick(View v) {

        String temp="";
        switch (v.getId()) {
            //Row 8
            case R.id.C81:
                temp="a8";
                System.out.println("a8");
                break;
            case R.id.C82:
                temp="b8";
                System.out.println("b8");
                break;
            case R.id.C83:
                temp="c8";
                System.out.println("c8");
                break;
            case R.id.C84:
                temp="d8";
                System.out.println("d8");
                break;
            case R.id.C85:
                temp="e8";
                System.out.println("e8");
                break;
            case R.id.C86:
                temp="f8";
                System.out.println("f8");
                break;
            case R.id.C87:
                temp="g8";
                System.out.println("g8");
                break;
            case R.id.C88:
                temp="h8";
                System.out.println("h8");
                break;

            //Row 7
            case R.id.C71:
                temp="a7";
                System.out.println("a7");
                break;
            case R.id.C72:
                temp="b7";
                System.out.println("b7");
                break;
            case R.id.C73:
                temp="c7";
                System.out.println("c7");
                break;
            case R.id.C74:
                temp="d7";
                System.out.println("d7");
                break;
            case R.id.C75:
                temp="e7";
                System.out.println("e7");
                break;
            case R.id.C76:
                temp="f7";
                System.out.println("f7");
                break;
            case R.id.C77:
                temp="g7";
                System.out.println("g7");
                break;
            case R.id.C78:
                temp="h7";
                System.out.println("h7");
                break;

            //Row 6
            case R.id.C61:
                temp="a6";
                System.out.println("a6");
                break;
            case R.id.C62:
                temp="b6";
                System.out.println("b6");
                break;
            case R.id.C63:
                temp="c6";
                System.out.println("c6");
                break;
            case R.id.C64:
                temp="d6";
                System.out.println("d6");
                break;
            case R.id.C65:
                temp="e6";
                System.out.println("e6");
                break;
            case R.id.C66:
                temp="f6";
                System.out.println("f6");
                break;
            case R.id.C67:
                temp="g6";
                System.out.println("g6");
                break;
            case R.id.C68:
                temp="h6";
                System.out.println("h6");
                break;

            //Row 5
            case R.id.C51:
                temp="a5";
                System.out.println("a5");
                break;
            case R.id.C52:
                temp="b5";
                System.out.println("b5");
                break;
            case R.id.C53:
                temp="c5";
                System.out.println("c5");
                break;
            case R.id.C54:
                temp="d5";
                System.out.println("d5");
                break;
            case R.id.C55:
                temp="e5";
                System.out.println("e5");
                break;
            case R.id.C56:
                temp="f5";
                System.out.println("f5");
                break;
            case R.id.C57:
                temp="g5";
                System.out.println("g5");
                break;
            case R.id.C58:
                temp="h5";
                System.out.println("h5");
                break;

            //Row 4
            case R.id.C41:
                temp="a4";
                System.out.println("a4");
                break;
            case R.id.C42:
                temp="b4";
                System.out.println("b4");
                break;
            case R.id.C43:
                temp="c4";
                System.out.println("c4");
                break;
            case R.id.C44:
                temp="d4";
                System.out.println("d4");
                break;
            case R.id.C45:
                temp="e4";
                System.out.println("e4");
                break;
            case R.id.C46:
                temp="f4";
                System.out.println("f4");
                break;
            case R.id.C47:
                temp="g4";
                System.out.println("g4");
                break;
            case R.id.C48:
                temp="h4";
                System.out.println("h4");
                break;

            //Row 3
            case R.id.C31:
                temp="a3";
                System.out.println("a3");
                break;
            case R.id.C32:
                temp="b3";
                System.out.println("b3");
                break;
            case R.id.C33:
                temp="c3";
                System.out.println("c3");
                break;
            case R.id.C34:
                temp="d3";
                System.out.println("d3");
                break;
            case R.id.C35:
                temp="e3";
                System.out.println("e3");
                break;
            case R.id.C36:
                temp="f3";
                System.out.println("f3");
                break;
            case R.id.C37:
                temp="g3";
                System.out.println("g3");
                break;
            case R.id.C38:
                temp="h3";
                System.out.println("h3");
                break;

            //Row 2
            case R.id.C21:
                temp="a2";
                System.out.println("a2");
                break;
            case R.id.C22:
                temp="b2";
                System.out.println("b2");
                break;
            case R.id.C23:
                temp="c2";
                System.out.println("c2");
                break;
            case R.id.C24:
                temp="d2";
                System.out.println("d2");
                break;
            case R.id.C25:
                temp="e2";
                System.out.println("e2");
                break;
            case R.id.C26:
                temp="f2";
                System.out.println("f2");
                break;
            case R.id.C27:
                temp="g2";
                System.out.println("g2");
                break;
            case R.id.C28:
                temp="h2";
                System.out.println("h2");
                break;

            //Row 1
            case R.id.C11:
                temp="a1";
                System.out.println("a1");
                break;
            case R.id.C12:
                temp="b1";
                System.out.println("b1");
                break;
            case R.id.C13:
                temp="c1";
                System.out.println("c1");
                break;
            case R.id.C14:
                temp="d1";
                System.out.println("d1");
                break;
            case R.id.C15:
                temp="e1";
                System.out.println("e1");
                break;
            case R.id.C16:
                temp="f1";
                System.out.println("f1");
                break;
            case R.id.C17:
                temp="g1";
                System.out.println("g1");
                break;
            case R.id.C18:
                temp="h1";
                System.out.println("h1");
                break;
        }

        int cNumber= convert(temp);
        int numPart2=cNumber%10; //File/column
        int numPart1=cNumber/10; //Rank/row

        String colorTemp="";

        if(chess_board[numPart1][numPart2]!=null)
        {
            if(start_pos.equals("empty"))
            {
                colorTemp= chess_board[numPart1][numPart2].getName();
                //System.out.println("Inside start_pos.equals(empty)");
                //Checking if the selected piece is the same color as the player
                if((colorTemp.substring(0,1).equals("w")&& whiteturn==true) || (colorTemp.substring(0,1).equals("b")&& whiteturn==false))
                {
                    //Confirmed selected piece is the same color as the player
                    //System.out.println("Same color as user check");
                    start_pos=temp;
                    startR=numPart1;
                    startF=numPart2;
                    System.out.println("start_pos: "+start_pos);

                }
                else
                {
                    //System.out.println("Nope, not the same color as the player");
                }

            }
            else
            {
                //System.out.println("Nope, start_pos is already filled");
                System.out.println("start_pos: "+start_pos);
            }
        }

        //System.out.println("______________________________________");

        if(!(start_pos.equals("empty")) && (end_pos.equals("empty")))
        {
            //System.out.println("Inside end_position first if statement");

            if(chess_board[numPart1][numPart2]!=null)
            {
                //Current position is not an empty space
                colorTemp= chess_board[numPart1][numPart2].getName();
                //System.out.println("ColorTemp"+colorTemp);
                if((colorTemp.substring(0,1).equals("w")&& whiteturn==true) || (colorTemp.substring(0,1).equals("b")&& whiteturn==false))
                {
                    //Confirmed selected piece is the same color as the player
                    //System.out.println("Same color as user and will make the current end_position candidate the start position");
                    start_pos=temp;
                    startR=numPart1;
                    startF=numPart2;
                    System.out.println("start_pos: "+start_pos);
                    System.out.println("end_pos: "+end_pos);

                }
                else
                {
                    //Confirmed opposing color as the current player
                    //System.out.println("Confirmed opposing color as the current player");
                    end_pos=temp;
                    endR=numPart1;
                    endF=numPart2;
                    System.out.println("end_pos: "+end_pos);

                }
            }
            else
            {
                //The current position.getName equals null. This means it is an empty space.
                //Thus, end_position can be here
                //System.out.println("Else statement for null check for chess_board.getName()");
                end_pos=temp;
                endR=numPart1;
                endF=numPart2;
                System.out.println("end_pos: "+end_pos);
            }

        }
        //System.out.println("______________________________________");
        //System.out.println("______________________________________");

        if(!(start_pos.equals("empty")) && !(end_pos.equals("empty")))
        {
            System.out.println("Inside the call for movePiece");
            boolean isLegal = checkLegality(startR, startF, endR, endF);
            if(isLegal) {
                movePiece(startR, startF, endR, endF);
                undid = 0;
                boolean illegalmove = false;
                if(bCheck() == true){
                    black_checked.setVisibility(View.VISIBLE);
                }
                else{
                    black_checked.setVisibility(View.INVISIBLE);
                }
                if(wCheck() == true){
                    white_checked.setVisibility(View.VISIBLE);
                }
                else{
                    white_checked.setVisibility(View.INVISIBLE);
                }
                if(!whiteturn){
                    System.out.println("Checking to see if white king is in check...");
                    if (wCheck() == true){
                        System.out.println("White King is still in check");
                        illegalmove = true;
                    }
                }
                else{
                    System.out.println("Checking to see if black king is in check...");
                    if(bCheck() == true){
                        System.out.println("Black King is still in check");
                        illegalmove = true;
                    }
                }
                if(!illegalmove) {
                    if (enpassant == true) {
                        if (enp == 0) {
                            enp = 1;
                        } else {
                            enpassant = false;
                            enp = 0;
                        }
                    }
                }
                else{
                    undo2();
                }
            }
            else{
                System.out.println("Move is not legal");
            }
            System.out.println("WhiteR, whiteF = " + whiteR + " " + whiteF + " BlackR, blackF = " + blackR + " "+ blackF);
            start_pos="empty";
            end_pos="empty";
        }

    }
    public boolean checkLegality(int startR, int startF, int endR, int endF){
        Piece startPiece = getPiece(startR, startF);
        String starter = startPiece.getName();
        String startName = starter.substring(1,2);
        System.out.println(startName);
        if (startName.equals("p")){//is pawn
            Piece temp = getPiece(startR, startF);
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
                return false;
            }
            if(Math.abs(endR-startR) == 2) {//Trying to move 2 spaces on a non starting space
                //System.out.println(first);
                if(!first) {
                    System.out.println("Illegal move, try again"); //FLAG
                    return false;
                }
            }
            if(endR-startR == 1 && !color) {//Black is trying to move backwards
                System.out.println("Illegal move, try again");
                return false;
            }
            if(startR-endR == 1 && color == true) {//White is trying to move backwards
                System.out.println("Illegal move, try again");
                return false;
            }
            if(startR == endR && startF != endF){//attempting to move horizontally
                System.out.println("Piece is moving horizontally");
                return false;
            }
            Piece temporary = getPiece(endR, endF);
            if(temporary != null && endF == startF){
                System.out.println("Attempting to move onto another piece");
                return false;
            }
            if(endF != startF) {//attempting to take a piece
                Piece temp2 = getPiece(endR, endF);
                if(enpassant == true) {
                    System.out.println("ENPASSANT");
                    Piece temp3;
                    if(!whiteturn) {
                        getPiece(endR+1, endF);
                        int tempf = endR+1;
                        //System.out.println("black turn endR = " + tempf + " endF " + endF);
                        temp3 = getPiece(endR+1, endF);
                        if(temp3 != null) {
                            if (temp3.isWhite() == whiteturn) {
                                return false;
                            }
                        }
                    }
                    else {
                        getPiece(endR-1, endF);
                        int tempf = endR-1;
                        //System.out.println("white turn endR = " + tempf + " endF " + endF);
                        temp3 = getPiece(endR-1, endF);
                        System.out.println("temp3 = " + temp3.getName());
                        if(temp3 != null) {
                            if (temp3.isWhite() == whiteturn) {
                                return false;
                            }
                        }
                    }
                    if(temp3 == null) {//enpassant isn't activated... :(
                        if(color == true) {//White turn
                            Piece temp4 = getPiece(enpx, enpy);
                            //System.out.println("temp4  = " + temp4.getName());
                            if(temp4 == null) {
                                return true;
                            }
                            else {
                                //System.out.println("Attempting to move to a null space diagonally, enp enabled.");
                                return false;
                            }
                        }
                        else {
                            Piece temp4 = getPiece(enpx, enpy);
                           // System.out.println("temp 4 = " + temp4.getName());
                            if(temp4 == null) {
                                return true;
                            }
                            else {
                                //System.out.println("Attempting to move to a null space diagonally, enp enabled.");
                                return false;
                            }
                        }
                    }
                    System.out.println("ENPASSANT ACTIVATED");
                    enpassantActivated=true;
                    if(color == true) {//White turn
                        Piece temp4 = getPiece(enpx, enpy-1);
                        //System.out.println("enpx " + enpx + " enpy " + enpy);
                        if(temp4 != null) {
                            deletePiece(enpx, enpy-1);
                        }
                    }
                    else {
                        Piece temp4 = getPiece(enpx, enpy+1);
                        //System.out.println("enpx " + enpx + " enpy " + enpy);
                        if(temp4 != null) {
                            deletePiece(enpx, enpy+1);
                        }
                    }
                    //print();
                }
                else if(temp2 == null) {
                    //System.out.println("Attempting to move to a null space diagonally");
                    return false;
                }
                else if(temp2 != null) {//Attempting to move a piece to a tile of the same color
                    if(temp2.isWhite() == whiteturn) {
                        //System.out.println("Attempting to move piece to a tile with piece of same color");
                        return false;
                    }
                }
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
            return true;
        }
        else if(startName.equals("Q")) {//is queen
            boolean straight=false;
            if(startR==endR || startF==endF)
            {
                straight=true; //if Rank of File is the same, the queen is moving straight
            }
            if(straight){
                {
                    int tempStartR=startR;
                    int tempEndR=endR;

                    int tempStartF= startF;
                    int tempEndF=endF;
                    if((startR-endR != 0) && (startF-endF != 0)){
                        return false;
                    }
                    if(startR-endR==0) //Which means it moving across file/ moving horizontally
                    {
                        System.out.println("Moving horizontally");
                        while(Math.abs(tempEndF-tempStartF)!=0)
                        {
                            //System.out.println("Inside F while loop- End:"+tempEndF+" Start:"+tempStartF );
                            if(tempEndF>tempStartF) //If the piece is moving from left to right
                            {
                                //System.out.println("Inside tempEndF>tempStartF");
                                Piece temp1=getPiece(endR,tempStartF+1);
                                if(temp1==null)
                                {
                                    tempStartF++;
                                    if(tempStartF==tempEndF) //No piece in between the start and end position
                                    {
                                        //System.out.println("Inside tempStartF==tempEndF");

                                        return true;
                                    }
                                }
                                else //There is a piece in the way
                                {
                                    //System.out.println("Inside else statement for temp1==null");
                                    if(temp1.isWhite()!=whiteturn && tempStartF+1==tempEndF)
                                    {
                                        //	System.out.println("temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
                                        return true;
                                    }
                                    else
                                    {
                                        //System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
                                        return false;
                                    }
                                }
                            }
                            else //If the piece is moving from right to left
                            {

                                //System.out.println("Inside else statement of tempEndF>tempStartF");
                                Piece temp1=getPiece(endR,tempStartF-1);
                                if(temp1==null)
                                {
                                    tempStartF--;
                                    if(tempStartF==tempEndF) //No piece in between the start and end position
                                    {
                                        //System.out.println("Inside tempStartF==tempEndF");
                                        return true;
                                    }
                                }
                                else //There is a piece in the way
                                {
                                    //System.out.println("Inside else statement for temp1==null");
                                    if(temp1.isWhite()!=whiteturn && tempStartF-1==tempEndF)
                                    {
                                        //System.out.println("temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
                                        return true;
                                    }
                                    else
                                    {
                                        //System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    else//Rook is moving across Rank/ moving vertically
                    {
                        System.out.println("Moving vertically");
                        while(Math.abs(tempEndR-tempStartR)!=0)
                        {
                            //System.out.println("Inside R while loop- End:"+tempEndR+" Start:"+tempStartR );
                            if(tempEndR>tempStartR) //if the piece is moving from bottom to top
                            {
                                //System.out.println("Inside tempEndR>tempStartR");
                                Piece temp1=getPiece(tempStartR+1, endF);
                                if(temp1==null)
                                {
                                    //System.out.println("Inside temp1==null");
                                    tempStartR++;
                                    if(tempStartR==tempEndR) // No pieces in between the start and end position
                                    {
                                        //System.out.println("Inside tempStartR==tempEndR");
                                        return true;
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
                                        return true;
                                    }
                                    else
                                    {
                                        //System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
                                        return false;
                                    }

                                }
                            }
                            else //if the piece is moving from top to bottom
                            {
                                //System.out.println("Inside tempEndR<tempStartR");
                                Piece temp1=getPiece(tempStartR-1, endF);
                                if(temp1==null)
                                {
                                    //System.out.println("Inside temp1==null");
                                    tempStartR--;
                                    if(tempStartR==tempEndR) // No pieces in between the start and end position
                                    {
                                        //System.out.println("Inside tempStartR==tempEndR");
                                        return true;
                                    }
                                }
                                else
                                {
                                    //System.out.println("Inside else statement for temp1==null");
                                    if(temp1.isWhite()!=whiteturn && tempStartR-1==tempEndR)
                                    {
                                        //System.out.println("temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
                                        return true;
                                    }
                                    else
                                    {
                                        //System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
                                        return false;
                                    }

                                }
                            }
                        }
                    }
                    Piece t_1= getPiece(startR, startF);
                    Rook t_2=(Rook)t_1;
                    t_2.completeFirstMove();
                }
            }
            if(Math.abs(startR-endR)==Math.abs(startF-endF)) // Queen is moving diagonally
            {
                int tempbsr = startR;
                int tempbsf = startF;
                int tempber = endR;
                int tempbef = endF;
                System.out.println("StartR " + startR + " startF " + startF + " endR " + endR + " endF " + endF);
                System.out.println("tempbsr" + tempbsr + " tempbsf " + tempbsf + " tempber " + tempber + " tempbef " + tempbef);
                boolean left;
                boolean down;
                if(tempbsf > tempbef) {
                    left = true;
                }
                else {
                    left = false;
                }
                if(tempbsr < tempber) {
                    down = false;
                }
                else {
                    down = true;
                }

                if(left == true & down == true) {// Moving diagonally down left
                    System.out.println("Moving down left");
                    while(tempbsr > tempber+1 && tempbsf > tempbef+1) {//check if there are any pieces in the way
                        tempbsr--; tempbsf--;
                        Piece temp1=getPiece(tempbsr, tempbsf);
                        if(temp1!=null) { //Has run into a piece
                            System.out.println("You've run into a piece");
                            return false;
                        }
                    }

                    Piece temp2 = getPiece(tempber, tempbef);
                    if(temp2 != null) {//same color
                        if(temp2.isWhite() == whiteturn) {
                            System.out.println("Same color");
                            return false;
                        }
                    }
                    return true;
                }
                if(left == true & down == false) {// Moving diagonally up left
                    System.out.println("Moving up left");
                    while(tempbsr < tempber-1 && tempbsf > tempbef+1) {//check if there are any pieces in the way
                        System.out.println("tempbsr" + tempbsr + " tempbsf " + tempbsf + " tempber " + tempber + " tempbef " + tempbef);
                        tempbsr++; tempbsf--;
                        Piece temp1=getPiece(tempbsr, tempbsf);
                        if(temp1!=null) { //Has run into a piece
                            System.out.println("You've run into a piece");
                            return false;
                        }
                    }

                    Piece temp2 = getPiece(tempber, tempbef);
                    if(temp2 != null) {//same color
                        if(temp2.isWhite() == whiteturn) {
                            System.out.println("Same color");
                            return false;
                        }
                    }
                    return true;
                }
                if(left == false & down == false) {// Moving diagonally up right
                    System.out.println("Moving up right");
                    while(tempbsr < tempber-1 && tempbsf < tempbef-1) {//check if there are any pieces in the way
                        tempbsr++; tempbsf++;
                        Piece temp1=getPiece(tempbsr, tempbsf);
                        if(temp1!=null) { //Has run into a piece
                            System.out.println("You've run into a piece");
                            return false;
                        }
                    }

                    Piece temp2 = getPiece(tempber, tempbef);
                    if(temp2 != null) {//same color
                        if(temp2.isWhite() == whiteturn) {
                            System.out.println("Same color");
                            return false;
                        }
                    }
                    return true;
                }
                if(left == false & down == true) {// Moving diagonally down right
                    System.out.println("Moving Down Right");
                    while(tempbsr > tempber+1 && tempbsf < tempbef-1) {//check if there are any pieces in the way
                        tempbsr--; tempbsf++;
                        Piece temp1=getPiece(tempbsr, tempbsf);
                        if(temp1!=null) { //Has run into a piece
                            System.out.println("You've run into a piece");
                            return false;
                        }
                    }

                    Piece temp2 = getPiece(tempber, tempbef);
                    if(temp2 != null) {//same color
                        if(temp2.isWhite() == whiteturn) {
                            System.out.println("Same color");
                            return false;
                        }
                    }
                    return true;
                }
            }

            return false;
        }
        else if(startName.equals("N")) {//is Knight
            if((Math.abs(startR-endR)>=1) && (Math.abs(startF-endF)>=1) && (Math.abs(startF-endF)+Math.abs(startR-endR)==3))
            {
                boolean ansE = false;
                Piece pieceAnsE = chess_board[endR][endF];
                if (pieceAnsE == null) {
                    return true; //End destination is null
                } else {
                    ansE = chess_board[endR][endF].isWhite();
                }
                boolean ansS = chess_board[startR][startF].isWhite();

                if (ansS == ansE)//Checking if the start piece and end piece are allies or not
                {
                    return false; //False if allies
                } else {
                    return true; //True id end piece is not allies
                }
            }
            return false;
        }
        else if(startName.equals("B")) {//is Bishop
            System.out.println("In Bishop Check");
            if (Math.abs(startR - endR) == Math.abs(startF - endF)) {
                int tempbsr = startR;
                int tempbsf = startF;
                int tempber = endR;
                int tempbef = endF;
                System.out.println("StartR " + startR + " startF " + startF + " endR " + endR + " endF " + endF);
                System.out.println("tempbsr" + tempbsr + " tempbsf " + tempbsf + " tempber " + tempber + " tempbef " + tempbef);
                boolean left;
                boolean down;
                if(tempbsf > tempbef) {
                    left = true;
                }
                else {
                    left = false;
                }
                if(tempbsr < tempber) {
                    down = false;
                }
                else {
                    down = true;
                }

                if(left == true & down == true) {// Moving diagonally down left
                    System.out.println("Moving down left");
                    while(tempbsr > tempber+1 && tempbsf > tempbef+1) {//check if there are any pieces in the way
                        tempbsr--; tempbsf--;
                        Piece temp1=getPiece(tempbsr, tempbsf);
                        if(temp1!=null) { //Has run into a piece
                            System.out.println("You've run into a piece");
                            return false;
                        }
                    }

                    Piece temp2 = getPiece(tempber, tempbef);
                    if(temp2 != null) {//same color
                        if(temp2.isWhite() == whiteturn) {
                            System.out.println("Same color");
                            return false;
                        }
                    }
                    return true;
                }
                if(left == true & down == false) {// Moving diagonally up left
                    System.out.println("Moving up left");
                    while(tempbsr < tempber-1 && tempbsf > tempbef+1) {//check if there are any pieces in the way
                        System.out.println("tempbsr" + tempbsr + " tempbsf " + tempbsf + " tempber " + tempber + " tempbef " + tempbef);
                        tempbsr++; tempbsf--;
                        Piece temp1=getPiece(tempbsr, tempbsf);
                        if(temp1!=null) { //Has run into a piece
                            System.out.println("You've run into a piece");
                            return false;
                        }
                    }

                    Piece temp2 = getPiece(tempber, tempbef);
                    if(temp2 != null) {//same color
                        if(temp2.isWhite() == whiteturn) {
                            System.out.println("Same color");
                            return false;
                        }
                    }
                    return true;
                }
                if(left == false & down == false) {// Moving diagonally up right
                    System.out.println("Moving up right");
                    while(tempbsr < tempber-1 && tempbsf < tempbef-1) {//check if there are any pieces in the way
                        tempbsr++; tempbsf++;
                        Piece temp1=getPiece(tempbsr, tempbsf);
                        if(temp1!=null) { //Has run into a piece
                            System.out.println("You've run into a piece");
                            return false;
                        }
                    }

                    Piece temp2 = getPiece(tempber, tempbef);
                    if(temp2 != null) {//same color
                        if(temp2.isWhite() == whiteturn) {
                            System.out.println("Same color");
                            return false;
                        }
                    }
                    return true;
                }
                if(left == false & down == true) {// Moving diagonally down right
                    System.out.println("Moving Down Right");
                    while(tempbsr > tempber+1 && tempbsf < tempbef-1) {//check if there are any pieces in the way
                        tempbsr--; tempbsf++;
                        Piece temp1=getPiece(tempbsr, tempbsf);
                        if(temp1!=null) { //Has run into a piece
                            System.out.println("You've run into a piece");
                            return false;
                        }
                    }

                    Piece temp2 = getPiece(tempber, tempbef);
                    if(temp2 != null) {//same color
                        if(temp2.isWhite() == whiteturn) {
                            System.out.println("Same color");
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        else if(startName.equals("K")) {//is King
            boolean firstmove = false;
            if(whiteturn){
                firstmove = whitefirstmove;
            }
            else{
                firstmove = blackfirstmove;
            }
            if(startR-endR==0 && firstmove==true && Math.abs(endF-startF)==2)//If the movement is horizontal and the movement request is of 2 horizontal movements
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
                    t1=getPiece(startR,8);
                    t2=(Rook)t1;

                    if(getPiece(startR,6)==null && getPiece(startR,7)==null && t2.getFirstMove()==true)
                    {
                        if(whiteturn){
                            whitefirstmove = false;
                        }
                        else{
                            blackfirstmove = false;
                        }
                        moveCastling(startR, 8, startR, 6, startR,5,startR,7);
                        undid = 0;
                        System.out.println("Castled");
                        //whiteturn=!whiteturn;
                        return false;
                    }
                    else
                    {
                        return false;
                    }
                }
                else //moving right to left
                {
                    //System.out.println("Moving right To left");
                    t1=getPiece(startR,1);
                    t2=(Rook)t1;

                    if(getPiece(startR,2)==null && getPiece(startR,3)==null && getPiece(startR,4)==null && t2.getFirstMove()==true)
                    {
                        if(whiteturn){
                            whitefirstmove = false;
                        }
                        else{
                            blackfirstmove = false;
                        }
                        moveCastling(startR, 1, startR, 4, startR,5,startR,3);
                        undid = 0;
                        System.out.println("Castled");
                        //whiteturn=!whiteturn;
                        return false;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            else if(Math.abs(startR-endR)==0 && Math.abs(startF-endF) == 1 || Math.abs(startR-endR)==1 && Math.abs(startF-endF) == 0|| Math.abs(startR-endR)==1 && Math.abs(startF-endF) == 1 ) {//|| endR==1 && endF==7 || endR==1 && endF==3
                //Put king method here
                if(whiteturn){
                    whitefirstmove = false;
                }
                else{
                    blackfirstmove = false;
                }
                return true;
            }
            return false;
        }
        else if(startName.equals("R")) {//is Rook
            {
                int tempStartR=startR;
                int tempEndR=endR;

                int tempStartF= startF;
                int tempEndF=endF;
                if((startR-endR != 0) && (startF-endF != 0)){
                    return false;
                }
                if(startR-endR==0) //Which means it moving across file/ moving horizontally
                {
                    System.out.println("Moving horizontally");
                    while(Math.abs(tempEndF-tempStartF)!=0)
                    {
                        //System.out.println("Inside F while loop- End:"+tempEndF+" Start:"+tempStartF );
                        if(tempEndF>tempStartF) //If the piece is moving from left to right
                        {
                            //System.out.println("Inside tempEndF>tempStartF");
                            Piece temp1=getPiece(endR,tempStartF+1);
                            if(temp1==null)
                            {
                                tempStartF++;
                                if(tempStartF==tempEndF) //No piece in between the start and end position
                                {
                                    //System.out.println("Inside tempStartF==tempEndF");

                                    return true;
                                }
                            }
                            else //There is a piece in the way
                            {
                                //System.out.println("Inside else statement for temp1==null");
                                if(temp1.isWhite()!=whiteturn && tempStartF+1==tempEndF)
                                {
                                    //	System.out.println("temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
                                    return true;
                                }
                                else
                                {
                                    //System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
                                    return false;
                                }
                            }
                        }
                        else //If the piece is moving from right to left
                        {

                            //System.out.println("Inside else statement of tempEndF>tempStartF");
                            Piece temp1=getPiece(endR,tempStartF-1);
                            if(temp1==null)
                            {
                                tempStartF--;
                                if(tempStartF==tempEndF) //No piece in between the start and end position
                                {
                                    //System.out.println("Inside tempStartF==tempEndF");
                                    return true;
                                }
                            }
                            else //There is a piece in the way
                            {
                                //System.out.println("Inside else statement for temp1==null");
                                if(temp1.isWhite()!=whiteturn && tempStartF-1==tempEndF)
                                {
                                    //System.out.println("temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
                                    return true;
                                }
                                else
                                {
                                    //System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartF==tempEndF");
                                    return false;
                                }
                            }
                        }
                    }
                }
                else//Rook is moving across Rank/ moving vertically
                {
                    System.out.println("Moving vertically");
                    while(Math.abs(tempEndR-tempStartR)!=0)
                    {
                        //System.out.println("Inside R while loop- End:"+tempEndR+" Start:"+tempStartR );
                        if(tempEndR>tempStartR) //if the piece is moving from bottom to top
                        {
                            //System.out.println("Inside tempEndR>tempStartR");
                            Piece temp1=getPiece(tempStartR+1, endF);
                            if(temp1==null)
                            {
                                //System.out.println("Inside temp1==null");
                                tempStartR++;
                                if(tempStartR==tempEndR) // No pieces in between the start and end position
                                {
                                    //System.out.println("Inside tempStartR==tempEndR");
                                    return true;
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
                                    return true;
                                }
                                else
                                {
                                    //System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
                                    return false;
                                }

                            }
                        }
                        else //if the piece is moving from top to bottom
                        {
                            //System.out.println("Inside tempEndR<tempStartR");
                            Piece temp1=getPiece(tempStartR-1, endF);
                            if(temp1==null)
                            {
                                //System.out.println("Inside temp1==null");
                                tempStartR--;
                                if(tempStartR==tempEndR) // No pieces in between the start and end position
                                {
                                    //System.out.println("Inside tempStartR==tempEndR");
                                    return true;
                                }
                            }
                            else
                            {
                                //System.out.println("Inside else statement for temp1==null");
                                if(temp1.isWhite()!=whiteturn && tempStartR-1==tempEndR)
                                {
                                    //System.out.println("temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
                                    return true;
                                }
                                else
                                {
                                    //System.out.println("Inside else statement for temp1.isWhite()!=whiteturn && tempStartR==tempEndR");
                                    return false;
                                }

                            }
                        }
                    }
                }
                Piece t_1= getPiece(startR, startF);
                Rook t_2=(Rook)t_1;
                t_2.completeFirstMove();
            }
        }
        return true;
    }


    public void movePiece(int startR,int startF, int endR, int endF)
    {
        String toadd=reverseConvert(startR,startF, endR, endF);

        if(enpassantActivated==true)
        {
            toadd=toadd+" S";
            enpassantActivated=false;
        }
        gameLog.add(toadd);
        for (int j = 8; j > 0; j--)
        {
            for (int i = 1; i < 9; i++)
            {
                old_chess_board[i][j]=chess_board[i][j];
            }
        }
        chess_board[endR][endF]=chess_board[startR][startF];
        chess_board[startR][startF]=null;
        //print();
        Piece kingcheck = getPiece(endR, endF);
        String kingname = kingcheck.getName();
        String kingtype = kingname.substring(1,2);
        if(kingtype.equals("K")){
            if(whiteturn){
                whiteR = endR; whiteF = endF;
            }
            else{
                blackR = endR; blackF = endF;
            }
        }
        for(int x = 1; x < 9; x++){//Checks all the pieces at the ends to see if promotion can occur.
            Piece pawncheck = chess_board[endR][x];
            if(pawncheck != null && (endR == 8 || endR == 1)){
                String piecename = pawncheck.getName();
                String piecetype = piecename.substring(1,2);
                if(piecetype.equals("p")){
                    //prompt promotion
                    game_over_black.setVisibility(View.INVISIBLE);
                    game_over_white.setVisibility(View.INVISIBLE);
                    game_over.setVisibility(View.INVISIBLE);
                    black_turn.setVisibility(View.INVISIBLE);
                    white_turn.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);
                    d.setVisibility(View.GONE);
                    e.setVisibility(View.GONE);
                    f.setVisibility(View.GONE);
                    h.setVisibility(View.GONE);
                    queenpromo.setVisibility(View.VISIBLE);
                    bishoppromo.setVisibility(View.VISIBLE);
                    rookpromo.setVisibility(View.VISIBLE);
                    knightpromo.setVisibility(View.VISIBLE);
                    replaybutton.setVisibility(View.GONE);
                }

            }
        }
        boardDisp();
        whiteturn=!whiteturn;
        if(whiteturn){
            black_turn.setVisibility(View.INVISIBLE);
            white_turn.setVisibility(View.VISIBLE);
        }
        else{
            black_turn.setVisibility(View.VISIBLE);
            white_turn.setVisibility(View.INVISIBLE);
        }
        Display[startR][startF].setBackgroundResource(0);
        castled=false;
    }

    boolean castled=false;
    public void moveCastling(int startR,int startF, int endR, int endF, int startR2,int startF2, int endR2, int endF2)
    {
        String toadd=reverseConvert(startR,startF, endR, endF);
        toadd=toadd+" T";
        gameLog.add(toadd);
        for (int j = 8; j > 0; j--)
        {
            for (int i = 1; i < 9; i++)
            {
                old_chess_board[i][j]=chess_board[i][j];
            }
        }

        chess_board[endR][endF]=chess_board[startR][startF];
        chess_board[startR][startF]=null;

        chess_board[endR2][endF2]=chess_board[startR2][startF2];
        chess_board[startR2][startF2]=null;

        boardDisp();
        whiteturn=!whiteturn;
        if(whiteturn){
            black_turn.setVisibility(View.INVISIBLE);
            white_turn.setVisibility(View.VISIBLE);
            //Sets black king's new position
            blackR=startR2;
            blackF=endF2;
            System.out.println("blackR: "+blackR+" blackF: "+blackF);
        }
        else{
            black_turn.setVisibility(View.VISIBLE);
            white_turn.setVisibility(View.INVISIBLE);
            //Sets white king's new position
            whiteR=startR2;
            whiteF=endF2;
            System.out.println("whiteR: "+whiteR+" whiteF: "+whiteF);
        }
        Display[startR][startF].setBackgroundResource(0);
        Display[startR2][startF2].setBackgroundResource(0);
        castled=true;
    }

    public int convert(String s)
    {
        //Converts String 'a3' as int: 31
        String s1=s.substring(0,1); //gets the File/column
        String s2=s.substring(1,2); //gets the Rank/row

        int ans=0;
        ans=Integer.parseInt(s2);
        ans=ans*10;

        int secondPart=0;

        if(s1.equals("a"))
        {
            secondPart=1;
        }
        if(s1.equals("b"))
        {
            secondPart=2;
        }
        if(s1.equals("c"))
        {
            secondPart=3;
        }
        if(s1.equals("d"))
        {
            secondPart=4;
        }
        if(s1.equals("e"))
        {
            secondPart=5;
        }
        if(s1.equals("f"))
        {
            secondPart=6;
        }
        if(s1.equals("g"))
        {
            secondPart=7;
        }
        if(s1.equals("h"))
        {
            secondPart=8;
        }

        ans=ans+secondPart;
        //System.out.println("Answer: "+ans);
        return ans;

    }

    public String reverseConvert(int startR,int startF, int endR, int endF)
    {
        String s="";
        String e="";

        if(startF==1)
        {
            s="a";
        }
        if(startF==2)
        {
            s="b";
        }
        if(startF==3)
        {
            s="c";
        }
        if(startF==4)
        {
            s="d";
        }
        if(startF==5)
        {
            s="e";
        }
        if(startF==6)
        {
            s="f";
        }
        if(startF==7)
        {
            s="g";
        }
        if(startF==8)
        {
            s="h";
        }
        s=s+startR;



        if(endF==1)
        {
            e="a";
        }
        if(endF==2)
        {
            e="b";
        }
        if(endF==3)
        {
            e="c";
        }
        if(endF==4)
        {
            e="d";
        }
        if(endF==5)
        {
            e="e";
        }
        if(endF==6)
        {
            e="f";
        }
        if(endF==7)
        {
            e="g";
        }
        if(endF==8)
        {
            e="h";
        }
        e=e+endR;

        String ans=s+" "+e;
        return ans;

    }

    public void resign(View view) {
        if (whiteturn) {
            game_over_black.setVisibility(View.INVISIBLE);
            game_over_white.setVisibility(View.VISIBLE);
            game_over.setVisibility(View.INVISIBLE);
            black_turn.setVisibility(View.INVISIBLE);
            white_turn.setVisibility(View.INVISIBLE);
            b.setVisibility(View.VISIBLE);
            c.setVisibility(View.VISIBLE);
            d.setVisibility(View.GONE);
            e.setVisibility(View.GONE);
            f.setVisibility(View.GONE);
            h.setVisibility(View.GONE);
        } else {
            game_over_white.setVisibility(View.INVISIBLE);
            game_over_black.setVisibility(View.VISIBLE);
            game_over.setVisibility(View.INVISIBLE);
            black_turn.setVisibility(View.INVISIBLE);
            white_turn.setVisibility(View.INVISIBLE);
            b.setVisibility(View.VISIBLE);
            c.setVisibility(View.VISIBLE);
            d.setVisibility(View.GONE);
            e.setVisibility(View.GONE);
            f.setVisibility(View.GONE);
            h.setVisibility(View.GONE);
        }
        gameLog.add("resign");
    }

    public void draw(View view){
        game_over_black.setVisibility(View.INVISIBLE);
        game_over_white.setVisibility(View.INVISIBLE);
        game_over.setVisibility(View.VISIBLE);
        black_turn.setVisibility(View.INVISIBLE);
        white_turn.setVisibility(View.INVISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.GONE);
        e.setVisibility(View.GONE);
        f.setVisibility(View.GONE);
        h.setVisibility(View.GONE);
        gameLog.add("draw");
    }

    Piece[][] old_chess_board = new Piece[9][9];

    public void undo(View view)
    {
        if(gameLog.size()==0)
        {
            return;
        }


        if(castled==true) //Code for castling undo
        {
            if(whiteturn) //Black castled
            {
                blackfirstmove=true;
                blackR=8;
                blackF=5;
                System.out.println("Inside undo- blackR: "+blackR+" blackF: "+blackF);
            }
            else
            {
                whitefirstmove=true;
                whiteR=1;
                whiteF=5;
                System.out.println("inside undo- whiteR: "+whiteR+" whiteF: "+whiteF);
            }
        }
        if(undid == 0) {
            System.out.println("Undo");
            for (int j = 8; j > 0; j--) {
                for (int i = 1; i < 9; i++) {
                    chess_board[i][j] = old_chess_board[i][j];
                }
            }
            whiteturn = !whiteturn;
            if (whiteturn) {
                black_turn.setVisibility(View.INVISIBLE);
                white_turn.setVisibility(View.VISIBLE);
            } else {
                black_turn.setVisibility(View.VISIBLE);
                white_turn.setVisibility(View.INVISIBLE);
            }
            undid = 1;
            //chess_board=old_chess_board;
            //print();
            if(bCheck() == true){
                black_checked.setVisibility(View.VISIBLE);
            }
            else{
                black_checked.setVisibility(View.INVISIBLE);
            }
            if(wCheck() == true){
                white_checked.setVisibility(View.VISIBLE);
            }
            else{
                white_checked.setVisibility(View.INVISIBLE);
            }
            boardDisp();
        }
        else{
            System.out.println("Cannot undo more than one move");
        }

        if((gameLog.get(gameLog.size()-1).contains("S")))
        {
            System.out.println("Inside undo enpassant");
            //Last move was an enpassant
            if(!whiteturn)
            {
                //Black did enpassant. Need to return white pawn
                String[] words=gameLog.get(gameLog.size()-1).split(" ");
                //Add white pawn back to the board. Black pawn row+1
                int newX=Integer.parseInt(words[0].substring(1,2));
                System.out.println("newX white: "+newX);
                int newY=((convert(words[1]))%10);
                System.out.println("The white Y value"+newY);
                chess_board[newX][newY]=new Pawn(true);
                //print();
                boardDisp();
            }
            else
            {
                //White did enpassant. Need to return black pawn
                String[] words=gameLog.get(gameLog.size()-1).split(" ");
                //Add black pawn back to the board. Black pawn row-1
                int newX=Integer.parseInt(words[0].substring(1,2));
                System.out.println("newX Black: "+newX);
                int newY=((convert(words[1]))%10);
                System.out.println("The black Y value"+newY);
                chess_board[newX][newY]=new Pawn(false);
                //print();
                boardDisp();
            }

            enpassant=true;
        }
        print();
        gameLog.remove(gameLog.size()-1);
    }

    public void undo2()
    {
        if(gameLog.size()==0)
        {
            return;
        }
        if(castled==true) //Code for castling undo
        {
            if(whiteturn) //Black castled
            {
                blackfirstmove=true;
                blackR=8;
                blackF=5;
                System.out.println("Inside undo- blackR: "+blackR+" blackF: "+blackF);
            }
            else
            {
                whitefirstmove=true;
                whiteR=1;
                whiteF=5;
                System.out.println("inside undo- whiteR: "+whiteR+" whiteF: "+whiteF);
            }
        }
        if(undid == 0) {
            System.out.println("Undo");
            for (int j = 8; j > 0; j--) {
                for (int i = 1; i < 9; i++) {
                    chess_board[i][j] = old_chess_board[i][j];
                }
            }
            whiteturn = !whiteturn;
            if (whiteturn) {
                black_turn.setVisibility(View.INVISIBLE);
                white_turn.setVisibility(View.VISIBLE);
            } else {
                black_turn.setVisibility(View.VISIBLE);
                white_turn.setVisibility(View.INVISIBLE);
            }
            undid = 1;
            if(bCheck() == true){
                black_checked.setVisibility(View.VISIBLE);
            }
            else{
                black_checked.setVisibility(View.INVISIBLE);
            }
            if(wCheck() == true){
                white_checked.setVisibility(View.VISIBLE);
            }
            else{
                white_checked.setVisibility(View.INVISIBLE);
            }
            //chess_board=old_chess_board;
            //print();
            boardDisp();
        }
        else{
            System.out.println("Cannot undo more than one move");
        }

        if((gameLog.get(gameLog.size()-1).contains("S")))
        {
            System.out.println("Inside undo enpassant");
            //Last move was an enpassant
            if(!whiteturn)
            {
                //Black did enpassant. Need to return white pawn
                String[] words=gameLog.get(gameLog.size()-1).split(" ");
                //Add white pawn back to the board. Black pawn row+1
                int newX=Integer.parseInt(words[0].substring(1,2));
                System.out.println("newX white: "+newX);
                int newY=((convert(words[1]))%10);
                System.out.println("The white Y value"+newY);
                chess_board[newX][newY]=new Pawn(true);
                //print();
                boardDisp();
            }
            else
            {
                //White did enpassant. Need to return black pawn
                String[] words=gameLog.get(gameLog.size()-1).split(" ");
                //Add black pawn back to the board. Black pawn row-1
                int newX=Integer.parseInt(words[0].substring(1,2));
                System.out.println("newX Black: "+newX);
                int newY=((convert(words[1]))%10);
                System.out.println("The black Y value"+newY);
                chess_board[newX][newY]=new Pawn(false);
                //print();
                boardDisp();
            }
        }
        print();

        gameLog.remove(gameLog.size()-1);
    }

    public void new_game(View view){
        chess_board = new Piece[9][9];
        Display = new TextView[9][9];
        Back = new TextView[9][9];
        whiteturn = true;
        gameLog= new ArrayList<String>();
        readFromFile= readFromFile();
        System.out.println("From file in new game: "+readFromFile);

        //These keep track of the Rank and File coordinates of (White and Black) King
        whiteR = 0;
        whiteF = 0;
        blackR = 0;
        blackF = 0;
        game_over_white.setVisibility(View.INVISIBLE);
        game_over_black.setVisibility(View.INVISIBLE);
        game_over.setVisibility(View.INVISIBLE);
        white_turn.setVisibility(View.VISIBLE);
        black_turn.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);
        h.setVisibility(View.VISIBLE);
        black_checked.setVisibility(View.INVISIBLE);
        white_checked.setVisibility(View.INVISIBLE);
        this.make();
    }
    public void access_recording(View view){
        Intent intent = new Intent(this, replaylist.class);
        startActivity(intent);
    }

    public void ai_move(View view)
    {
        boolean foundMove=false;
        String aiPiece="p";
        int pawnMove=1;
        if(whiteturn==true)
        {
            aiPiece="w"+aiPiece;
        }
        else
        {
            aiPiece="b"+aiPiece;
            pawnMove=-1;
        }


        //Trying to find a pawn piece
        for (int j = 8; j > 0; j--) {
            for (int i = 1; i < 9; i++) {
                Piece s = chess_board[i][j];
                if (s != null) {
                    if (chess_board[i][j].getName().equals(aiPiece))
                    {
                        ArrayList<Integer> temp= new ArrayList<Integer>();
                        //All possible moves for pawn
                        if((i+pawnMove)>0 && (i+pawnMove)<9)
                        {
                            if((j+1)<9)
                            {
                                //Moving right Diagonal for white and left diagonal for black
                                temp.add(i+pawnMove);
                                temp.add(j+1);
                            }

                            if((j-1)>0)
                            {
                                //Moving left Diagonal for white and Right diagonal for black
                                temp.add(i+pawnMove);
                                temp.add(j-1);
                            }

                            //Moving Straight forward
                            temp.add(i+pawnMove);
                            temp.add(j);
                        }

                        if(temp!=null)
                        {
                            for(int a=0; a<temp.size(); a=a+2)
                            {
                                if(foundMove==true)
                                    break;
                                System.out.println("I: "+i+" J: "+j+" New I: "+temp.get(a)+" new J: "+temp.get(a+1));
                                boolean isLegal=checkLegality(i,j,temp.get(a),temp.get(a+1));
                                System.out.println("Pawn Legality:"+isLegal);
                                if(isLegal) {
                                    movePiece(i,j,temp.get(a),temp.get(a+1));
                                    //promotion for AI pawn
                                    if(aiPiece.substring(0,1).equals("w"))
                                    {
                                        if(temp.get(a)==8)
                                        {
                                            queenpromo(view);
                                        }
                                    }
                                    else
                                    {
                                        if(temp.get(a)==1)
                                        {
                                            queenpromo(view);
                                        }
                                    }
                                    foundMove=true;
                                    if(enpassant == true) {
                                        if(enp == 0) {
                                            enp = 1;
                                        }
                                        else {
                                            enpassant = false;
                                            enp = 0;
                                        }
                                    }
                                }
                                else{
                                    System.out.println("Move is not legal");
                                }
                            }
                        }

                    }
                }
                if(foundMove==true)
                    break;
            }
            if(foundMove==true)
                break;
        }



        //___________________________________________________________________
        //System.out.println("Before rook foundMove check:"+foundMove);
        //Checking Rook pieces now
        aiPiece="R";
        int rookMove=1;
        if(whiteturn==true)
        {
            aiPiece="w"+aiPiece;
        }
        else
        {
            aiPiece="b"+aiPiece;
            rookMove=-1;
        }

        if(foundMove==false && aiPiece.substring(1,2).equals("R"))
        {
            //System.out.println("Inside Rook AI");
            for (int j = 8; j > 0; j--) {
                for (int i = 1; i < 9; i++) {
                    Piece s = chess_board[i][j];
                    //System.out.println("Before s!=null");
                    if (s != null) {
                        //System.out.println("inside s!=null");
                        //System.out.println("Rook i:"+i+" Rook j:"+j);
                        //System.out.println("Name: "+chess_board[i][j].getName()+" aiPiece: "+aiPiece);
                        if (chess_board[i][j].getName().equals(aiPiece)) {
                            //Piece is Rook
                            //System.out.println("Inside Piece is Rook");
                            ArrayList<Integer> temp = new ArrayList<Integer>();
                            //All possible moves for rook
                            if ((i + rookMove) > 0 && (i + rookMove) < 9) //Moving forward
                            {
                                temp.add(i + rookMove);
                                temp.add(j);
                            }

                            if ((j + 1) > 0 && (j + 1) < 9) //Moving towards the right
                            {
                                temp.add(i);
                                temp.add(j + 1);
                            }

                            if ((j - 1) > 0 && (j - 1) < 9) //Moving towards the left
                            {
                                temp.add(i);
                                temp.add(j - 1);
                            }
                            System.out.print("Rook i:"+i);
                            if ((i - rookMove) > 0 && (i - rookMove) < 9) //Moving backward
                            {
                                temp.add(i - rookMove);
                                temp.add(j);
                            }


                            if (temp != null) {
                                for (int a = 0; a < temp.size(); a = a + 2) {
                                    if (foundMove == true)
                                        break;
                                    //System.out.println("Rook- I: " + i + " J: " + j + " New I: " + temp.get(a) + " new J: " + temp.get(a + 1));
                                    boolean isLegal = checkLegality(i, j, temp.get(a), temp.get(a + 1));
                                    //System.out.println("Rook Legality:"+isLegal);
                                    if (isLegal) {
                                        movePiece(i, j, temp.get(a), temp.get(a + 1));
                                        foundMove = true;
                                        if (enpassant == true) {
                                            if (enp == 0) {
                                                enp = 1;
                                            } else {
                                                enpassant = false;
                                                enp = 0;
                                            }
                                        }
                                    } else {
                                        System.out.println("Move is not legal");

                                    }
                                }
                            }
                        }
                    }
                    if(foundMove==true)
                        break;
                }
                if(foundMove==true)
                    break;
            }
        }

        //___________________________________________________________________
        //System.out.println("Before bishop foundMove check:"+foundMove);
        //Checking Bishop pieces now
        aiPiece="B";
        int move1=1;
        if(whiteturn==true)
        {
            aiPiece="w"+aiPiece;
        }
        else
        {
            aiPiece="b"+aiPiece;
            move1=-1;
        }

        if(foundMove==false && aiPiece.substring(1,2).equals("B"))
        {
            for (int j = 8; j > 0; j--)
            {
                for (int i = 1; i < 9; i++)
                {
                    Piece s = chess_board[i][j];
                    if (s != null)
                    {
                        if (chess_board[i][j].getName().equals(aiPiece))
                        {
                            ArrayList<Integer> temp= new ArrayList<Integer>();
                            //All possible moves for Bishop

                            if((i+move1)>0 && (i+move1)<9 && (j+1)>0 && (j+1)<9)
                            {
                                //Moving towards top right
                                temp.add(i+move1);
                                temp.add(j+1);
                            }

                            if((i+move1)>0 && (i+move1)<9 && (j-1)>0 && (j-1)<9)
                            {
                                //Moving towards top right
                                temp.add(i+move1);
                                temp.add(j-1);
                            }

                            if((i-move1)>0 && (i-move1)<9 && (j+1)>0 && (j+1)<9)
                            {
                                //Moving towards top right
                                temp.add(i-move1);
                                temp.add(j+1);
                            }

                            if((i-move1)>0 && (i-move1)<9 && (j-1)>0 && (j-1)<9)
                            {
                                //Moving towards top right
                                temp.add(i-move1);
                                temp.add(j-1);
                            }

                            if(temp!=null)
                            {
                                for(int a=0; a<temp.size(); a=a+2)
                                {
                                    if (foundMove == true)
                                        break;

                                    System.out.println("Bishop I: "+i+" J: "+j+" New I: "+temp.get(a)+" new J: "+temp.get(a+1));
                                    boolean isLegal=checkLegality(i,j,temp.get(a),temp.get(a+1));
                                    if(isLegal) {
                                        movePiece(i,j,temp.get(a),temp.get(a+1));
                                        foundMove=true;
                                        if(enpassant == true) {
                                            if(enp == 0) {
                                                enp = 1;
                                            }
                                            else {
                                                enpassant = false;
                                                enp = 0;
                                            }
                                        }
                                    }
                                    else{
                                        System.out.println("Move is not legal");
                                    }

                                }


                            }
                        }
                    }
                }
            }
        }

        //___________________________________________________________________
        //System.out.println("Before knight foundMove check:"+foundMove);
        //Checking Knight pieces now
        aiPiece="N";
        move1=1;
        if(whiteturn==true)
        {
            aiPiece="w"+aiPiece;
        }
        else
        {
            aiPiece="b"+aiPiece;
            move1=-1;
        }

        if(foundMove==false && aiPiece.substring(1,2).equals("N")) {
            for (int j = 8; j > 0; j--) {
                for (int i = 1; i < 9; i++) {
                    Piece s = chess_board[i][j];
                    if (s != null) {
                        if (chess_board[i][j].getName().equals(aiPiece))
                        {
                            ArrayList<Integer> temp= new ArrayList<Integer>();
                            //All possible moves for Knight

                            //Two upward vertical movements first and 1 horizontal
                            if((i+2)>0 && (i+2)<9)
                            {
                                if((j+1)>0 && (j+1)<9) //Top right
                                {
                                    temp.add(i+2);
                                    temp.add(j+1);
                                }

                                if((j-1)>0 && (j-1)<9) //Top left
                                {
                                    temp.add(i+2);
                                    temp.add(j-1);
                                }
                            }

                            //One upward vertical and two horizontal
                            if((i+1)>0 && (i+1)<9)
                            {
                                if((j+2)>0 && (j+2)<9) //Top-mid right
                                {
                                    temp.add(i+1);
                                    temp.add(j+2);
                                }

                                if((j-2)>0 && (j-2)<9) //Top-mid left
                                {
                                    temp.add(i+1);
                                    temp.add(j-2);
                                }
                            }

                            //Two downward vertical movements first and 1 horizontal
                            if((i-2)>0 && (i-2)<9)
                            {
                                if((j+1)>0 && (j+1)<9) //Bottom right
                                {
                                    temp.add(i-2);
                                    temp.add(j+1);
                                }

                                if((j-1)>0 && (j-1)<9) //Bottom left
                                {
                                    temp.add(i-2);
                                    temp.add(j-1);
                                }
                            }

                            //One downward vertical and two horizontal
                            if((i-1)>0 && (i-1)<9)
                            {
                                if((j+2)>0 && (j+2)<9) //Bottom-mid right
                                {
                                    temp.add(i-1);
                                    temp.add(j+2);
                                }

                                if((j-2)>0 && (j-2)<9) //Bottom-mid left
                                {
                                    temp.add(i-1);
                                    temp.add(j-2);
                                }
                            }


                            if(temp!=null)
                            {
                                for(int a=0; a<temp.size();a=a+2)
                                {
                                    if(foundMove==true)
                                        break;
                                    System.out.println("Knight: I: "+i+" J: "+j+" New I: "+temp.get(a)+" new J: "+temp.get(a+1));
                                    boolean isLegal=checkLegality(i,j,temp.get(a),temp.get(a+1));
                                    if(isLegal) {
                                        movePiece(i,j,temp.get(a),temp.get(a+1));
                                        foundMove=true;
                                        if(enpassant == true) {
                                            if(enp == 0) {
                                                enp = 1;
                                            }
                                            else {
                                                enpassant = false;
                                                enp = 0;
                                            }
                                        }
                                    }
                                    else{
                                        System.out.println("Move is not legal");
                                    }

                                }
                            }

                        }

                    }
                    if(foundMove==true)
                        break;

                }
                if(foundMove==true)
                    break;

            }
        }


        //___________________________________________________________________
        //System.out.println("Before Queen foundMove check:"+foundMove);
        //Checking Queen pieces now

        aiPiece="Q";
        move1=1;
        if(whiteturn==true)
        {
            aiPiece="w"+aiPiece;
        }
        else
        {
            aiPiece="b"+aiPiece;
            move1=-1;
        }

        if(foundMove==false && aiPiece.substring(1,2).equals("Q"))
        {
            for (int j = 8; j > 0; j--)
            {
                for (int i = 1; i < 9; i++)
                {
                    Piece s = chess_board[i][j];
                    if (s != null)
                    {
                        if (chess_board[i][j].getName().equals(aiPiece))
                        {
                            ArrayList<Integer> temp= new ArrayList<Integer>();
                            //All possible moves for Queen

                            if ((i + rookMove) > 0 && (i + rookMove) < 9) //Moving forward
                            {
                                temp.add(i + rookMove);
                                temp.add(j);
                            }

                            if((i+move1)>0 && (i+move1)<9 && (j+1)>0 && (j+1)<9)
                            {
                                //Moving towards top right
                                temp.add(i+move1);
                                temp.add(j+1);
                            }

                            if ((j - 1) > 0 && (j - 1) < 9) //Moving towards the left
                            {
                                temp.add(i);
                                temp.add(j - 1);
                            }

                            if((i+move1)>0 && (i+move1)<9 && (j-1)>0 && (j-1)<9)
                            {
                                //Moving towards top right
                                temp.add(i+move1);
                                temp.add(j-1);
                            }

                            if((i-move1)>0 && (i-move1)<9 && (j+1)>0 && (j+1)<9)
                            {
                                //Moving towards top right
                                temp.add(i-move1);
                                temp.add(j+1);
                            }

                            if((i-move1)>0 && (i-move1)<9 && (j-1)>0 && (j-1)<9)
                            {
                                //Moving towards top right
                                temp.add(i-move1);
                                temp.add(j-1);
                            }

                            if ((j + 1) > 0 && (j + 1) < 9) //Moving towards the right
                            {
                                temp.add(i);
                                temp.add(j + 1);
                            }


                            System.out.print("Rook i:"+i);
                            if ((i - rookMove) > 0 && (i - rookMove) < 9) //Moving backward
                            {
                                temp.add(i - rookMove);
                                temp.add(j);
                            }

                            if(temp!=null)
                            {
                                for(int a=0; a<temp.size(); a=a+2)
                                {
                                    if (foundMove == true)
                                        break;

                                    System.out.println("Bishop I: "+i+" J: "+j+" New I: "+temp.get(a)+" new J: "+temp.get(a+1));
                                    boolean isLegal=checkLegality(i,j,temp.get(a),temp.get(a+1));
                                    if(isLegal) {
                                        movePiece(i,j,temp.get(a),temp.get(a+1));
                                        foundMove=true;
                                        if(enpassant == true) {
                                            if(enp == 0) {
                                                enp = 1;
                                            }
                                            else {
                                                enpassant = false;
                                                enp = 0;
                                            }
                                        }
                                    }
                                    else{
                                        System.out.println("Move is not legal");
                                    }

                                }


                            }
                        }
                    }
                }
            }
        }

        //___________________________________________________________________
        //System.out.println("Before King foundMove check:"+foundMove);
        //Checking King pieces now
        int i;
        int j;

        if(whiteturn)
        {
            i=whiteR;
            j=whiteF;
        }
        else
        {
            i=blackR;
            j=blackF;
        }

        ArrayList<Integer> kingTemp= new ArrayList<Integer>();
        //All possible moves for King

        if((i+1)>0 && (i+1)<9 && (j-1)>0 && (j-1)<9) //top left
        {
            kingTemp.add(i+1);
            kingTemp.add(j-1);
        }
        if((i+1)>0 && (i+1)<9) //top mid
        {
            kingTemp.add(i+1);
            kingTemp.add(j);
        }
        if((i+1)>0 && (i+1)<9 && (j+1)>0 && (j+1)<9) //top right
        {
            kingTemp.add(i+1);
            kingTemp.add(j+1);
        }

        if((j-1)>0 && (j-1)<9) //mid left
        {
            kingTemp.add(i);
            kingTemp.add(j-1);
        }
        if((j+1)>0 && (j+1)<9) //mid right
        {
            kingTemp.add(i);
            kingTemp.add(j+1);
        }

        if((i-1)>0 && (i-1)<9 && (j-1)>0 && (j-1)<9) //bottom left
        {
            kingTemp.add(i-1);
            kingTemp.add(j-1);
        }
        if((i-1)>0 && (i-1)<9) //bottom mid
        {
            kingTemp.add(i-1);
            kingTemp.add(j);
        }
        if((i-1)>0 && (i-1)<9 && (j+1)>0 && (j+1)<9) //bottom right
        {
            kingTemp.add(i-1);
            kingTemp.add(j+1);
        }

        if(kingTemp!=null)
        {
            for(int a=0; a<kingTemp.size();a=a+2)
            {
                if(foundMove==true)
                    break;
                System.out.println("King: I: "+i+" J: "+j+" New I: "+kingTemp.get(a)+" new J: "+kingTemp.get(a+1));
                boolean isLegal=checkLegality(i,j,kingTemp.get(a),kingTemp.get(a+1));
                if(isLegal) {
                    movePiece(i,j,kingTemp.get(a),kingTemp.get(a+1));
                    if(!whiteturn)
                    {
                        whiteR=kingTemp.get(a);
                        whiteF=kingTemp.get(a+1);
                    }
                    else
                    {
                        blackR=kingTemp.get(a);
                        blackF=kingTemp.get(a+1);
                    }
                    foundMove=true;
                    if(enpassant == true) {
                        if(enp == 0) {
                            enp = 1;
                        }
                        else {
                            enpassant = false;
                            enp = 0;
                        }
                    }
                }
                else{
                    System.out.println("Move is not legal");
                }

            }
        }

        undid = 0;
    }

    private String s_text="Save text:";
    String date_to_String="ha";
    public void save_game(View view) throws IOException {
        for (int i = 0; i < gameLog.size();i++)
        {
            System.out.println(gameLog.get(i));
        }


        AlertDialog.Builder popup = new AlertDialog.Builder(this);
        popup.setTitle("Please enter name for the recording");

        //Getting name of the recording and putting it in s_text
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        popup.setView(input);

        // Setting up the buttons
        popup.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int a) {
                System.out.println("A: "+a);
                s_text = input.getText().toString();
                //Getting the date and text

                //System.out.println("Input:"+s_text);
                //java.util.Date date=new java.util.Date();

                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                date_to_String = dateFormat.format(date);
                System.out.println("Converted String: " + date_to_String);

                gameLog.add(0,s_text);
                System.out.println("Added name "+s_text);
                //date_to_String=date.toString(); //Date format: Sun Dec 12 09:49:06 EST 2021
                gameLog.add(1,date_to_String);
                System.out.println("Added date "+date);
                //System.out.println("Date: "+date_to_String);
                for(String i: gameLog)
                {
                    System.out.println(i+" loop");
                }
                gameLog.add("*");
                writeTOFile(true);
            }
        });
        popup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        //gameLog.add("*********2"); //Ending of this recording
        //gameLog.add("______________________________"); //Ending of the file/arraylist
        //writeTOFile();
        //String listString = String.join("|", gameLog);
        //saveGameLog a=new saveGameLog(listString);
        //a.writeTOFile(listString);
        System.out.println("End text1: "+s_text);
        System.out.println("End text2: "+date_to_String);
        for(String s: gameLog)
            System.out.println("Game Log: "+s);
        //writeTOFile();
        popup.show();

    }

    public void writeTOFile(boolean a)
    {
        Context context=getApplicationContext();
        String data;
        if(a)
        {
            data=  readFromFile+String.join("|", gameLog);
            //data=  String.join("|", gameLog);
        }
        else
        {
            //data =  readFromFile+String.join("|", gameLog);
            data=  String.join("|", gameLog);
        }

        //String data=String.join("|", gameLog);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("gamerecording.dat", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    public String readFromFile()
    {
        Context context=getApplicationContext();
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("gamerecording.dat");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


//    This line is to divide the new methods from the imported chess methods.

    /**
     * This methods returns if the black king is in check or not
     *
     * @return True if Black King is in check. False if the Black King is not in check
     */
    public boolean bCheck() {
        //System.out.println("black check");
        int rank = blackR;
        int file = blackF;
        //System.out.println("BlackR = " + blackR);
        //System.out.println("BlackF = " + blackF);
        //System.out.println("file = " + file);
        //System.out.println("rank = " + rank);

        while (file > 1) {//Check if the black king is in check (horizontal), from the left
            file--;
            //System.out.println("Left");
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                if (colour) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name.equals("wR") || name.equals("wQ")) {
                        System.out.println("black checked by rook or queen from the left");
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        file = blackF;
        rank = blackR;

        while (file < 8) {//Check if the black king is in check (horizontal), from the right
            file++;
            //System.out.println("Right");
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                if (colour) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name.equals("wR") || name.equals("wQ")) {
                        System.out.println("black checked by rook or queen from the left");
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        file = blackF;
        rank = blackR;

        while (rank < 8) {//Check if the black king is in check (horizontal), from the top
            rank++;
            //System.out.println("Top");
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                //System.out.println("Piece found");
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                if (colour) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name.equals("wR") || name.equals("wQ")) {
                        System.out.println("black checked by rook or queen from the top");
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        file = blackF;
        rank = blackR;

        while (rank > 1) {//Check if the black king is in check (horizontal), from the bottom
            rank--;
            //System.out.println("Bottom");
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                if (colour) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name.equals("wR") || name.equals("wQ")) {
                        System.out.println("black checked by rook or queen from the bottom");
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        //System.out.println("Diagonal check");

        file = blackF;
        rank = blackR;


        while (file > 1 && rank > 1) {//Checking if checked from the bottom left
            file--;
            rank--;
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                if (colour) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name.equals("wB") || name.equals("wQ")) {
                        System.out.println("black checked by bishop or queen from the bottom left");
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        file = blackF;
        rank = blackR;


        while (file > 1 && rank < 8) {//Checking if checked from the top left
            file--;
            rank++;
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                if (colour) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name.equals("wB") || name.equals("wQ")) {
                        System.out.println("black checked by bishop or queen from the top left");
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        file = blackF;
        rank = blackR;


        while (file < 8 && rank > 1) {//Checking if checked from the bottom right
            file++;
            rank--;
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                if (colour) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name.equals("wB") || name.equals("wQ")) {
                        System.out.println("black checked by bishop or queen from the bottom right");
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        file = blackF;
        rank = blackR;

        while (file < 8 && rank < 8) {//Checking if checked from the top right
            file++;
            rank++;
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                if (colour) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name.equals("wB") || name.equals("wQ")) {
                        System.out.println("black checked by bishop or queen from the top right");
                        return true;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        file = blackF;
        rank = blackR;
        if(rank > 1 && file > 1) {
            Piece pawnchecker = chess_board[rank - 1][file - 1];
            if (pawnchecker != null) {
                //System.out.println("Not a pawn");
                String piecename = pawnchecker.getName();
                System.out.println(piecename);
                if (piecename.equals("wp")) {
                    return true;
                }
            }
        }

        file = blackF;
        rank = blackR;
        if(rank > 1 && file < 8) {
            Piece pawnchecker2 = chess_board[rank - 1][file + 1];
            //print();
            if (pawnchecker2 != null) {
                //System.out.println("Not a pawn");
                String piecename = pawnchecker2.getName();
                if (piecename.equals("wp")) {
                    return true;
                }
            }
        }

        file = blackF;
        rank = blackR;

        //----------------------------------------------------------------------------------------------------------------
        //Knight check

        Piece u;
        String kc;

        //System.out.println("Knight check");
        if (file < 6 && rank < 7) {
            //System.out.println("right 2 up 1");
            int file2 = file + 2;
            int rank2 = rank + 1;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("wN")) {
                    //System.out.println("Checked by a knight");
                    return true;
                }
            }
        }
        rank = blackR;
        file = blackF;
        if (file < 6 && rank > 2) {
            //System.out.println("right 2 down 1");
            int file2 = file + 2;
            int rank2 = rank - 1;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("wN")) {
                    //System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        rank = blackR;
        file = blackF;

        if (file < 7 && rank > 3) {
            //System.out.println("right 1 down 2");
            int file2 = file + 1;
            int rank2 = rank - 2;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("wN")) {
                    //System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        rank = blackR;
        file = blackF;

        if (file < 7 && rank < 6) {
            //System.out.println("right 1 up 2");
            int file2 = file + 1;
            int rank2 = rank + 2;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("wN")) {
                    //System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        rank = blackR;
        file = blackF;

        if (file > 2 && rank < 6) {
            //System.out.println("left 1 up 2");
            int file2 = file - 1;
            int rank2 = rank + 2;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("wN")) {
                    //	System.out.println("Checked by a knight");
                    return true;
                }
            }
        }
        rank = blackR;
        file = blackF;
        if (file > 2 && rank > 3) {
            //System.out.println("left 1 down 2");
            int file2 = file - 1;
            int rank2 = rank - 2;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("wN")) {
                    //	System.out.println("Checked by a knight");
                    return true;
                }
            }
        }
        rank = blackR;
        file = blackF;
        if (file > 3 && rank < 8) {
            //System.out.println("left 2 up 1");
            int file2 = file - 2;
            int rank2 = rank + 1;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("wN")) {
                    //System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        rank = blackR;
        file = blackF;
        //System.out.println("test rank = " + rank);
        if (file > 3 && rank < 2) {
            //System.out.println("left 2 down 1");
            int file2 = file - 2;
            int rank2 = rank - 1;
            //	System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("wN")) {
                    //System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This methods returns if the white king is in check or not
     *
     * @return True if White King is in check. False if the White King is not in check
     */
    public boolean wCheck() {
        //System.out.println("White check");
        int rank = whiteR;
        int file = whiteF;
        //System.out.println("WhiteR = " + whiteR);
       // System.out.println("WhiteF = " + whiteF);
        //System.out.println("file = " + file);
        //System.out.println("rank = " + rank);

        while (file > 1) {//Check if the white king is in check (horizontal), from the left
            file--;
            //System.out.println("Left");
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name.equals("bR") || name.equals("bQ")) {
                    System.out.println("White checked by rook or queen from the left");
                    return true;
                } else {
                    break;
                }
            }
        }

        file = whiteF;
        rank = whiteR;

        while (file < 8) {//Check if the white king is in check (horizontal), from the right
            file++;
            //System.out.println("Right");
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name.equals("bR") || name.equals("bQ")) {
                    System.out.println("White checked by rook or queen from the right");
                    return true;
                } else {
                    break;
                }
            }
        }

        file = whiteF;
        rank = whiteR;

        while (rank < 8) {//Check if the white king is in check (horizontal), from the top
            rank++;
            //System.out.println("Top");
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name.equals("bR") || name.equals("bQ")) {
                    System.out.println("White checked by rook or queen from the top");
                    return true;
                } else {
                    break;
                }
            }
        }

        file = whiteF;
        rank = whiteR;

        while (rank > 1) {//Check if the white king is in check (horizontal), from the bottom
            rank--;
            //System.out.println("Bottom");
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name.equals("bR") || name.equals("bQ")) {
                    System.out.println("White checked by rook or queen from the bottom");
                    return true;
                } else {
                    break;
                }
            }
        }

        //System.out.println("Diagonal check");

        file = whiteF;
        rank = whiteR;


        while (file > 1 && rank > 1) {//Checking if checked from the bottom left
            file--;
            rank--;
            //System.out.println("checking rank " + rank + " file " + file);
            if (chess_board[rank][file] != null) {
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name.equals("bB") || name.equals("bQ")) {
                    System.out.println("White checked by rook or queen from the bottom left");
                    return true;
                } else {
                    break;
                }
            }
        }

        file = whiteF;
        rank = whiteR;


        while (file > 1 && rank < 8) {//Checking if checked from the top left
            file--;
            rank++;
           // System.out.println("checking rank " + rank + " file " + file);
            if (chess_board[rank][file] != null) {
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name.equals("bB") || name.equals("bQ")) {
                    System.out.println("White checked by rook or queen from the top left");
                    return true;
                } else {
                    System.out.println("Piece is " + name);
                    break;
                }
            }
        }

        file = whiteF;
        rank = whiteR;


        while (file < 8 && rank > 1) {//Checking if checked from the bottom right
            file++;
            rank--;
            //System.out.println("checking rank " + rank + " file " + file);
            if (chess_board[rank][file] != null) {
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name.equals("bB") || name.equals("bQ")) {
                    System.out.println("White checked by rook or queen from the bottom right");
                    return true;
                } else {
                    break;
                }
            }
        }

        file = whiteF;
        rank = whiteR;

        while (file < 8 && rank < 8) {//Checking if checked from the top right
            file++;
            rank++;
            //System.out.println("checking rank " + rank + " file " + file);
            if (chess_board[rank][file] != null) {
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name.equals("bB") || name.equals("bQ")) {
                    System.out.println("White checked by rook or queen from the top right");
                    return true;
                } else {
                    break;
                }
            }
        }

        file = whiteF;
        rank = whiteR;
        if(rank < 8 && file > 1) {
            Piece pawnchecker = chess_board[rank + 1][file - 1];
            if (pawnchecker != null) {
                System.out.println("Not a pawn");
                String piecename = pawnchecker.getName();
                System.out.println(piecename);
                if (piecename.equals("bp")) {
                    return true;
                }
            }
        }

        file = whiteF;
        rank = whiteR;
        if(rank < 8 && file < 8) {
            Piece pawnchecker2 = chess_board[rank + 1][file + 1];
            //print();
            if (pawnchecker2 != null) {
                System.out.println("Not a pawn");
                String piecename = pawnchecker2.getName();
                if (piecename.equals("bp")) {
                    return true;
                }
            }
        }
        file = whiteF;
        rank = whiteR;

        //----------------------------------------------------------------------------------------------------------------
        //Knight check

        Piece u;
        String kc;

        //System.out.println("Knight check");
        if (file < 6 && rank < 7) {
            //System.out.println("right 2 up 1");
            int file2 = file + 2;
            int rank2 = rank + 1;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("bN")) {
                    System.out.println("Checked by a knight");
                    return true;
                }
            }
        }
        rank = whiteR;
        file = whiteF;
        if (file < 6 && rank > 2) {
            //System.out.println("right 2 down 1");
            int file2 = file + 2;
            int rank2 = rank - 1;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("bN")) {
                    System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        rank = whiteR;
        file = whiteF;

        if (file < 7 && rank > 3) {
            //System.out.println("right 1 down 2");
            int file2 = file + 1;
            int rank2 = rank - 2;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("bN")) {
                    System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        rank = whiteR;
        file = whiteF;

        if (file < 7 && rank < 6) {
            //System.out.println("right 1 up 2");
            int file2 = file + 1;
            int rank2 = rank + 2;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("bN")) {
                    System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        rank = whiteR;
        file = whiteF;

        if (file > 2 && rank < 6) {
            //System.out.println("left 1 up 2");
            int file2 = file - 1;
            int rank2 = rank + 2;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("bN")) {
                    System.out.println("Checked by a knight");
                    return true;
                }
            }
        }
        rank = whiteR;
        file = whiteF;
        if (file > 2 && rank > 3) {
            //System.out.println("left 1 down 2");
            int file2 = file - 1;
            int rank2 = rank - 2;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("bN")) {
                    System.out.println("Checked by a knight");
                    return true;
                }
            }
        }
        rank = whiteR;
        file = whiteF;
        if (file > 3 && rank < 8) {
            //System.out.println("left 2 up 1");
            int file2 = file - 2;
            int rank2 = rank + 1;
            //System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("bN")) {
                    System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        rank = whiteR;
        file = whiteF;
        //System.out.println("test rank = " + rank);
        if (file > 3 && rank < 2) {
            //System.out.println("left 2 down 1");
            int file2 = file - 2;
            int rank2 = rank - 1;
            //	System.out.println("file = " + file2);
            //System.out.println("rank = " + rank2);
            u = chess_board[rank2][file2];
            if (u != null) {
                kc = u.getName();
                //System.out.println("Piece found, it is a " + kc);
                if (kc.equals("bN")) {
                    System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        return false;
    }
    public void deletePiece(int x, int y) {
        System.out.println(y + " " + x + "del'd");
        chess_board[y][x] = null;
        //System.out.println("Piece at " + x + " and " + y + " deleted");
    }
    public Piece getPiece(int startR, int startF) {
        Piece ans = chess_board[startR][startF];
        return ans;
    }
    public void queenpromo(View view){
        promotion = "queen";
        queenpromo.setVisibility(View.GONE);
        bishoppromo.setVisibility(View.GONE);
        rookpromo.setVisibility(View.GONE);
        knightpromo.setVisibility(View.GONE);
        replaybutton.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);
        h.setVisibility(View.VISIBLE);
        for(int x = 1; x < 9; x++){
            //System.out.println("Checking file " + x);
            if(whiteturn){
                Piece pawncheck = chess_board[1][x];
                if(pawncheck != null) {
                    //System.out.println("Piece found");
                    String piecename = pawncheck.getName();
                    String piecetype = piecename.substring(1, 2);
                    if (piecetype.equals("p")) {
                        chess_board[1][x] = new Queen(false);
                    }
                    else{
                        System.out.println("Not a pawn." + pawncheck.getName());
                    }
                }
            }
            else{
                Piece pawncheck = chess_board[8][x];
                if(pawncheck != null) {
                    String piecename = pawncheck.getName();
                    String piecetype = piecename.substring(1, 2);
                    if (piecetype.equals("p")) {
                        chess_board[8][x] = new Queen(true);
                    }
                    else{
                        System.out.println("Not a pawn." + pawncheck.getName());
                    }
                }
            }
        }
        boardDisp();
        String last=gameLog.get(gameLog.size()-1);
        gameLog.remove(gameLog.size()-1);
        last=last+" Q";
        gameLog.add(last);
    }
    public void rookpromo(View view){
        promotion = "queen";
        queenpromo.setVisibility(View.GONE);
        bishoppromo.setVisibility(View.GONE);
        rookpromo.setVisibility(View.GONE);
        knightpromo.setVisibility(View.GONE);
        replaybutton.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);
        h.setVisibility(View.VISIBLE);
        for(int x = 1; x < 9; x++){
            //System.out.println("Checking file " + x);
            if(whiteturn){
                Piece pawncheck = chess_board[1][x];
                if(pawncheck != null) {
                    //System.out.println("Piece found");
                    String piecename = pawncheck.getName();
                    String piecetype = piecename.substring(1, 2);
                    if (piecetype.equals("p")) {
                        chess_board[1][x] = new Rook(false);
                    }
                    else{
                        System.out.println("Not a pawn." + pawncheck.getName());
                    }
                }
            }
            else{
                Piece pawncheck = chess_board[8][x];
                if(pawncheck != null) {
                    String piecename = pawncheck.getName();
                    String piecetype = piecename.substring(1, 2);
                    if (piecetype.equals("p")) {
                        chess_board[8][x] = new Rook(true);
                    }
                    else{
                        System.out.println("Not a pawn." + pawncheck.getName());
                    }
                }
            }
        }
        boardDisp();
        String last=gameLog.get(gameLog.size()-1);
        gameLog.remove(gameLog.size()-1);
        last=last+" R";
        gameLog.add(last);
    }
    public void bishoppromo(View view){
        promotion = "bishop";
        queenpromo.setVisibility(View.GONE);
        bishoppromo.setVisibility(View.GONE);
        rookpromo.setVisibility(View.GONE);
        knightpromo.setVisibility(View.GONE);
        replaybutton.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);
        h.setVisibility(View.VISIBLE);
        for(int x = 1; x < 9; x++){
            System.out.println("Checking file " + x);
            if(whiteturn){
                Piece pawncheck = chess_board[1][x];
                if(pawncheck != null) {
                    //System.out.println("Piece found");
                    String piecename = pawncheck.getName();
                    String piecetype = piecename.substring(1, 2);
                    if (piecetype.equals("p")) {
                        chess_board[1][x] = new Bishop(false);
                    }
                    else{
                        System.out.println("Not a pawn." + pawncheck.getName());
                    }
                }
            }
            else{
                Piece pawncheck = chess_board[8][x];
                if(pawncheck != null) {
                    String piecename = pawncheck.getName();
                    String piecetype = piecename.substring(1, 2);
                    if (piecetype.equals("p")) {
                        chess_board[8][x] = new Bishop(true);
                    }
                    else{
                        System.out.println("Not a pawn." + pawncheck.getName());
                    }
                }
            }
        }
        boardDisp();
        String last=gameLog.get(gameLog.size()-1);
        gameLog.remove(gameLog.size()-1);
        last=last+" B";
        gameLog.add(last);
    }
    public void knightpromo(View view){
        promotion = "Knight";
        queenpromo.setVisibility(View.GONE);
        bishoppromo.setVisibility(View.GONE);
        rookpromo.setVisibility(View.GONE);
        knightpromo.setVisibility(View.GONE);
        replaybutton.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);
        h.setVisibility(View.VISIBLE);
        for(int x = 1; x < 9; x++){
            System.out.println("Checking file " + x);
            if(whiteturn){
                Piece pawncheck = chess_board[1][x];
                if(pawncheck != null) {
                    //System.out.println("Piece found");
                    String piecename = pawncheck.getName();
                    String piecetype = piecename.substring(1, 2);
                    if (piecetype.equals("p")) {
                        chess_board[1][x] = new Knight(false);
                    }
                    else{
                        System.out.println("Not a pawn." + pawncheck.getName());
                    }
                }
            }
            else{
                Piece pawncheck = chess_board[8][x];
                if(pawncheck != null) {
                    String piecename = pawncheck.getName();
                    String piecetype = piecename.substring(1, 2);
                    if (piecetype.equals("p")) {
                        chess_board[8][x] = new Knight(true);
                    }
                    else{
                        System.out.println("Not a pawn." + pawncheck.getName());
                    }
                }
            }
        }
        boardDisp();
        String last=gameLog.get(gameLog.size()-1);
        gameLog.remove(gameLog.size()-1);
        last=last+" N";
        gameLog.add(last);
    }
}

