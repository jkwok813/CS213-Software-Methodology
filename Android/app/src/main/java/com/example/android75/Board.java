package com.example.android75;

public class Board {

    /**
     * the constructor for the Board class. This method initializes the board by calling on the make method.
     * The make method makes a brand new board from scratch.
     */
    public Board() {
        this.make();
    }

    /**
     * chess_board is the 2d Piece array where the game is being played, where the piece location is being tracked and where the board is printed from.
     */
    Piece[][] chess_board = new Piece[9][9];

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
    public void make() {
        //Create White pieces
        // Create board

        //sets all the starting positions without a piece to be null
        for (int i = 3; i < 7; i++) {
            for (int o = 1; o < 9; o++) {
                chess_board[i][o] = null;
            }
        }

        // board with x=0 and/or y=0 will be ignored since in Chess there is no '0' position
        // a1 will be [1][1], b1 will be [1][2] and h8 will be [8][8]. So, [][] is [number/row][alphabet/column]. a=1, b=2,...h=8

        chess_board[1][1] = new Rook(true);
        chess_board[1][2] = new Knight(true);
        chess_board[1][3] = new Bishop(true);
        chess_board[1][4] = new Queen(true);
        chess_board[1][5] = new King(true);
        whiteR = 1;
        whiteF = 5;
        chess_board[1][6] = new Bishop(true);
        chess_board[1][7] = new Knight(true);
        chess_board[1][8] = new Rook(true);

        chess_board[2][1] = new Pawn(true);
        chess_board[2][2] = new Pawn(true);
        chess_board[2][3] = new Pawn(true);
        chess_board[2][4] = new Pawn(true);
        chess_board[2][5] = new Pawn(true);
        chess_board[2][6] = new Pawn(true);
        chess_board[2][7] = new Pawn(true);
        chess_board[2][8] = new Pawn(true);

        //Create Black pieces
        chess_board[8][1] = new Rook(false);
        chess_board[8][2] = new Knight(false);
        chess_board[8][3] = new Bishop(false);
        chess_board[8][4] = new Queen(false);
        chess_board[8][5] = new King(false);
        blackR = 8;
        blackF = 5;
        chess_board[8][6] = new Bishop(false);
        chess_board[8][7] = new Knight(false);
        chess_board[8][8] = new Rook(false);

        chess_board[7][1] = new Pawn(false);
        chess_board[7][2] = new Pawn(false);
        chess_board[7][3] = new Pawn(false);
        chess_board[7][4] = new Pawn(false);
        chess_board[7][5] = new Pawn(false);
        chess_board[7][6] = new Pawn(false);
        chess_board[7][7] = new Pawn(false);
        chess_board[7][8] = new Pawn(false);

        //chess_board[4][2] = new Pawn(false);

    }

    /**
     * This method deletes the Piece at the given Rank and File
     *
     * @param x: The File value of the Piece that needs to be deleted
     * @param y: The Rank value of the file that needs to be deleted
     */
    public void deletePiece(int x, int y) {
        chess_board[y][x] = null;
        //System.out.println("Piece at " + x + " and " + y + " deleted");
    }

    /**
     * @param x
     * @param y
     * @param p
     * @param color
     */
    public void addPiece(int x, int y, char p, boolean color) {
        if (!color) {
            if (p == 'N') {
                chess_board[x][y] = new Knight(false);
            } else if (p == 'B') {
                chess_board[x][y] = new Bishop(false);
            } else if (p == 'R') {
                chess_board[x][y] = new Rook(false);
            } else if (p == 'p') {
                chess_board[x][y] = new Pawn(false);
            } else if (p == 'K') {
                chess_board[x][y] = new King(false);
            } else {
                chess_board[x][y] = new Queen(false);
            }
        } else {
            if (p == 'N') {
                chess_board[x][y] = new Knight(true);
            } else if (p == 'B') {
                chess_board[x][y] = new Bishop(true);
            } else if (p == 'R') {
                chess_board[x][y] = new Rook(true);
            } else if (p == 'p') {
                chess_board[x][y] = new Pawn(true);
            } else if (p == 'K') {
                chess_board[x][y] = new King(true);
            } else {
                chess_board[x][y] = new Queen(true);
            }
        }
    }

    /**
     * This method prints the board by calling upon chess_board and printing it from black side to white side.
     */
    public void print() {
        //Initial print board
        for (int r = 8; r > 0; r--) {
            for (int f = 1; f < 9; f++) {
                Piece s = chess_board[r][f];
                if (s != null) {
                    String ans = s.getName();
                    System.out.print(ans + " ");
                } else {
                    if (r % 2 == 0) {
                        if (f % 2 != 0)
                            System.out.print("   ");
                        else
                            System.out.print("## ");

                    } else {
                        if (f % 2 != 0)
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

    /**
     * This method returns the Piece at a given rank and file
     *
     * @param startR: The Rank of the Piece in question
     * @param startF: The File of the Piece in question
     * @return Returns the Piece in question from the given Rank and File
     */
    public Piece getPiece(int startR, int startF) {
        Piece ans = chess_board[startR][startF];
        return ans;
    }

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
                if (colour == true) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name == "wR" || name == "wQ") {
                        //System.out.println("black checked by rook or queen from the left");
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
                if (colour == true) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name == "wR" || name == "wQ") {
                        //System.out.println("black checked by rook or queen from the left");
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
                if (colour == true) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name == "wR" || name == "wQ") {
                        //System.out.println("black checked by rook or queen from the top");
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
                if (colour == true) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name == "wR" || name == "wQ") {
                        //System.out.println("black checked by rook or queen from the bottom");
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
                if (colour == true) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name == "wB" || name == "wQ") {
                        //System.out.println("black checked by bishop or queen from the bottom left");
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
                if (colour == true) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name == "wB" || name == "wQ") {
                        //System.out.println("black checked by bishop or queen from the top left");
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
                if (colour == true) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name == "wB" || name == "wQ") {
                        //System.out.println("black checked by bishop or queen from the bottom right");
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
                if (colour == true) {
                    Piece t = chess_board[rank][file];
                    String name = t.getName();
                    if (name == "wB" || name == "wQ") {
                        //System.out.println("black checked by bishop or queen from the top right");
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

        //----------------------------------------------------------------------------------------------------------------
        //Knight check

        Piece u;
        String kc;
        boolean color;

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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "wN") {
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "wN") {
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "wN") {
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "wN") {
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "wN") {
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "wN") {
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "wN") {
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "wN") {
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
        //System.out.println("BlackR = " + blackR);
        //System.out.println("BlackF = " + blackF);
        //System.out.println("file = " + file);
        //System.out.println("rank = " + rank);

        while (file > 1) {//Check if the white king is in check (horizontal), from the left
            file--;
            //System.out.println("Left");
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name == "bR" || name == "bQ") {
                    //System.out.println("White checked by rook or queen from the left");
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
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name == "bR" || name == "bQ") {
                    //System.out.println("White checked by rook or queen from the left");
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
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name == "bR" || name == "bQ") {
                    //System.out.println("White checked by rook or queen from the left");
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
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name == "bR" || name == "bQ") {
                    //System.out.println("White checked by rook or queen from the left");
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
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name == "bR" || name == "bQ") {
                    //System.out.println("White checked by rook or queen from the left");
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
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name == "bR" || name == "bQ") {
                    //System.out.println("White checked by rook or queen from the left");
                    return true;
                } else {
                    break;
                }
            }
        }

        file = whiteF;
        rank = whiteR;


        while (file < 8 && rank > 1) {//Checking if checked from the bottom right
            file++;
            rank--;
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name == "bR" || name == "bQ") {
                    //System.out.println("White checked by rook or queen from the left");
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
            //System.out.println("file = " + file);
            //System.out.println("rank = " + rank);
            if (chess_board[rank][file] != null) {
                Piece s = chess_board[rank][file];
                boolean colour = s.isWhite();
                Piece t = chess_board[rank][file];
                String name = t.getName();
                if (name == "bR" || name == "bQ") {
                    //System.out.println("White checked by rook or queen from the left");
                    return true;
                } else {
                    break;
                }
            }
        }

        file = whiteF;
        rank = whiteR;

        //----------------------------------------------------------------------------------------------------------------
        //Knight check

        Piece u;
        String kc;
        boolean color;

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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "bN") {
                    //System.out.println("Checked by a knight");
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "bN") {
                    //System.out.println("Checked by a knight");
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "bN") {
                    //System.out.println("Checked by a knight");
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "bN") {
                    //System.out.println("Checked by a knight");
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "bN") {
                    //	System.out.println("Checked by a knight");
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "bN") {
                    //	System.out.println("Checked by a knight");
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "bN") {
                    //System.out.println("Checked by a knight");
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
                color = u.isWhite();
                //System.out.println("Piece found, it is a " + kc);
                if (kc == "bN") {
                    //System.out.println("Checked by a knight");
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * This methods moves a piece from the starting Rank and File to the ending Rank and File
     *
     * @param startR Source Rank
     * @param startF Source Rank
     * @param endR   Destination Rank
     * @param endF   Destination File
     */
    public void movePiece(int startR, int startF, int endR, int endF) {
        chess_board[endR][endF] = chess_board[startR][startF];
        chess_board[startR][startF] = null;
    }

    /**
     * This methods returns a boolean on if the King can actually move to the destination spot based on the board.
     *
     * @param startR Source Rank
     * @param startF Source Rank
     * @param endR   Destination Rank
     * @param endF   Destination File
     * @return This methods returns a boolean on if the King can actually move to the destination spot based on the board. True=Yes and False=No.
     */
    public boolean canKingMove(int startR, int startF, int endR, int endF) {
        Piece tk = chess_board[startR][startF];
        //King k=(King)tk;
        boolean bk = tk.isLegalMove(startR, startF, endR, endF);
        if (bk) {
            boolean ansE = false;
            Piece pieceAnsE = chess_board[endR][endF];
            if (pieceAnsE == null) {
                Piece temp1 = chess_board[startR][startF];
                King temp2 = (King) temp1;
                //System.out.println("First Move: "+temp2.getFirstMove());
                temp2.completeFirstMove();
                //System.out.println("First Move after: "+temp2.getFirstMove());

                if (temp2.isWhite())//If the King is white
                {
                    whiteR = endR;
                    whiteF = endF;
                } else {
                    blackR = endR;
                    blackF = endF;
                }

                return true; //End destination is null

            } else {
                ansE = chess_board[endR][endF].isWhite();
            }
            boolean ansS = chess_board[startR][startF].isWhite();

            if (ansS = ansE)//Checking if the start piece and end piece are allies or not
            {
                return false; //False if allies
            } else {
                Piece temp1 = chess_board[startR][startF];
                King temp2 = (King) temp1;
                //System.out.println("First Move: "+temp2.getFirstMove());
                temp2.completeFirstMove();
                //System.out.println("First Move after: "+temp2.getFirstMove());

                if (temp2.isWhite())//If the King is white
                {
                    whiteR = endR;
                    whiteF = endF;
                } else {
                    blackR = endR;
                    blackF = endF;
                }

                return true; //True id end piece is not allies
            }
        } else {
            return false;
        }


    }

    /**
     * This method confirms if the Knight can move from source to destination on the given board
     *
     * @param startR Source Rank
     * @param startF Source File
     * @param endR   Destination Rank
     * @param endF   Destination File
     * @return Returns true if the Knight can move to destination and false otherwise.
     */
    public boolean canKnightMove(int startR, int startF, int endR, int endF) {
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

    /**
     * Method that checks for checkmate
     * @param whiteturn A boolean that reflects which color player's turn it is. Check if black is in checkmate during white turn and vice versa
     * @return True if the Opposing King is in Checkmate
     */
    /*public boolean checkmate(boolean whiteturn) {
        //check all squares around the king to see if it can move anywhere
        if(whiteturn == true) {
            //System.out.println("We're trying to see if black is in checkmate");
            Piece s;
            if(blackR < 8) {
                s = chess_board[blackR+1][blackF]; //Checking space one above
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if black
                    if(whiteturn == true) {//If it's white's turn, we check everything around the black king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            int temp1 = blackR;
                            blackR++;
                            if(bCheck() == false) {
                                blackR--;
                                return false;
                            }
                            blackR = temp1;
                        }
                    }
                }
                else {
                    blackR++;
                    if(bCheck() == false) {
                        blackR--;
                        return false;
                    }
                    blackR--;
                }
            }

            if(blackR < 8 && blackF < 8) {
                s = chess_board[blackR+1][blackF+1]; //Checking space one above, one right
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if black
                    if(whiteturn == true) {//If it's white's turn, we check everything around the black king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            blackR++;
                            blackF++;
                            if(bCheck() == false) {
                                blackR--;
                                blackF--;
                                return false;
                            }
                            blackR--;
                            blackF--;
                        }
                    }
                }
                else {
                    blackR++;
                    blackF++;
                    if(bCheck() == false) {
                        blackR--;
                        blackF++;
                        return false;
                    }
                    blackF++;
                    blackR--;
                }
            }

            if(blackF < 8) {
                s = chess_board[blackR][blackF+1]; //Checking space one right
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if black
                    if(whiteturn == true) {//If it's white's turn, we check everything around the black king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            blackF++;
                            if(bCheck() == false) {
                                blackF--;
                                return false;
                            }
                            blackF--;
                        }
                    }
                }
                else {
                    blackF++;
                    if(bCheck() == false) {
                        blackF--;
                        return false;
                    }
                    blackF--;
                }
            }
            if(blackF < 8 && blackR > 1) {
                s = chess_board[blackR-1][blackF+1]; //Checking one right one down
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if black
                    if(whiteturn == true) {//If it's white's turn, we check everything around the black king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            blackR--;
                            blackF++;
                            if(bCheck() == false) {
                                blackR++;
                                blackF--;
                                return false;
                            }
                            blackR++;
                            blackF--;
                        }
                    }
                }
                else {
                    blackR--;
                    blackF++;
                    if(bCheck() == false) {
                        blackR++;
                        blackF--;
                        return false;
                    }
                    blackR++;
                    blackF--;
                }
            }
            if(blackR > 1) {
                s = chess_board[blackR-1][blackF]; //Checking space one down
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if black
                    if(whiteturn == true) {//If it's white's turn, we check everything around the black king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            blackR--;
                            if(bCheck() == false) {
                                blackR++;
                                return false;
                            }
                            blackR++;
                        }
                    }
                }
                else {
                    blackR--;
                    if(bCheck() == false) {
                        blackR++;
                        return false;
                    }
                    blackR++;
                }
            }
            if(blackR > 1 && blackF > 1) {
                s = chess_board[blackR-1][blackF-1]; //Checking space one down, one left
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if black
                    if(whiteturn == true) {//If it's white's turn, we check everything around the black king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            blackR--;
                            blackF--;
                            if(bCheck() == false) {
                                blackR++;
                                blackF++;
                                return false;
                            }
                            blackR++;
                            blackF++;
                        }
                    }
                }
                else {
                    blackR--;
                    blackF--;
                    if(bCheck() == false) {
                        blackR++;
                        blackF++;
                        return false;
                    }
                    blackR++;
                    blackF++;
                }
            }
            if(blackF > 1) {
                s = chess_board[blackR][blackF-1]; //Checking space one left
                if(s != null) {
                    //System.out.println("Found something on the left sire");
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if black
                    if(whiteturn == true) {//If it's white's turn, we check everything around the black king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            blackF--;
                            if(bCheck() == false) {
                                blackF++;
                                return false;
                            }
                            blackF++;
                        }
                    }
                }
                else {
                    blackF--;
                    if(bCheck() == false) {
                        blackF++;
                        return false;
                    }
                    blackF++;
                }
            }
            if(blackF > 1 && blackR < 8) {
                s = chess_board[blackR+1][blackF-1]; //Checking space one left one up
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if black
                    if(whiteturn == true) {//If it's white's turn, we check everything around the black king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            blackR++;
                            blackF--;
                            if(bCheck() == false) {
                                blackR--;
                                blackF++;
                                return false;
                            }
                            blackR--;
                            blackF++;
                        }
                    }
                }
                else {
                    blackR++;
                    blackF--;
                    if(bCheck() == false) {
                        blackR--;
                        blackF++;
                        return false;
                    }
                    blackR--;
                    blackF++;
                }
            }
            return true;
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        else {
            //System.out.println("Checking to see if white is in checkmate....");
            Piece s;
            if(whiteR < 8) {
                s = chess_board[whiteR+1][whiteF]; //Checking space one above
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if white
                    if(whiteturn == true) {//If it's white's turn, we check everything around the white king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            int temp1 = whiteR;
                            whiteR++;
                            if(wCheck() == false) {
                                whiteR--;
                                return false;
                            }
                            whiteR = temp1;
                        }
                    }
                }
                else {
                    whiteR++;
                    if(wCheck() == false) {
                        whiteR--;
                        return false;
                    }
                    whiteR--;
                }
            }

            if(whiteR < 8 && whiteF < 8) {
                s = chess_board[whiteR+1][whiteF+1]; //Checking space one above, one right
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if white
                    if(whiteturn == true) {//If it's white's turn, we check everything around the white king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            whiteR++;
                            whiteF++;
                            if(wCheck() == false) {
                                whiteR--;
                                whiteF--;
                                return false;
                            }
                            whiteR--;
                            whiteF--;
                        }
                    }
                }
                else {
                    whiteR++;
                    whiteF++;
                    if(wCheck() == false) {
                        whiteR--;
                        whiteF++;
                        return false;
                    }
                    whiteF++;
                    whiteR--;
                }
            }

            if(whiteF < 8) {
                s = chess_board[whiteR][whiteF+1]; //Checking space one right
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if white
                    if(whiteturn == true) {//If it's white's turn, we check everything around the white king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            whiteF++;
                            if(wCheck() == false) {
                                whiteF--;
                                return false;
                            }
                            whiteF--;
                        }
                    }
                }
                else {
                    whiteF++;
                    if(wCheck() == false) {
                        whiteF--;
                        return false;
                    }
                    whiteF--;
                }
            }
            if(whiteF < 8 && whiteR > 1) {
                s = chess_board[whiteR-1][whiteF+1]; //Checking one right one down
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if white
                    if(whiteturn == true) {//If it's white's turn, we check everything around the white king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            whiteR--;
                            whiteF++;
                            if(wCheck() == false) {
                                whiteR++;
                                whiteF--;
                                return false;
                            }
                            whiteR++;
                            whiteF--;
                        }
                    }
                }
                else {
                    whiteR--;
                    whiteF++;
                    if(wCheck() == false) {
                        whiteR++;
                        whiteF--;
                        return false;
                    }
                    whiteR++;
                    whiteF--;
                }
            }
            if(whiteR > 1) {
                s = chess_board[whiteR-1][whiteF]; //Checking space one down
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if white
                    if(whiteturn == true) {//If it's white's turn, we check everything around the white king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            whiteR--;
                            if(wCheck() == false) {
                                whiteR++;
                                return false;
                            }
                            whiteR++;
                        }
                    }
                }
                else {
                    whiteR--;
                    if(wCheck() == false) {
                        whiteR++;
                        return false;
                    }
                    whiteR++;
                }
            }
            if(whiteR > 1 && whiteF > 1) {
                s = chess_board[whiteR-1][whiteF-1]; //Checking space one down, one left
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if white
                    if(whiteturn == true) {//If it's white's turn, we check everything around the white king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            whiteR--;
                            whiteF--;
                            if(wCheck() == false) {
                                whiteR++;
                                whiteF++;
                                return false;
                            }
                            whiteR++;
                            whiteF++;
                        }
                    }
                }
                else {
                    whiteR--;
                    whiteF--;
                    if(wCheck() == false) {
                        whiteR++;
                        whiteF++;
                        return false;
                    }
                    whiteR++;
                    whiteF++;
                }
            }
            if(whiteF > 1) {
                s = chess_board[whiteR][whiteF-1]; //Checking space one left
                if(s != null) {
                    //System.out.println("Found something on the left sire");
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if white
                    if(whiteturn == true) {//If it's white's turn, we check everything around the white king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            whiteF--;
                            if(wCheck() == false) {
                                whiteF++;
                                return false;
                            }
                            whiteF++;
                        }
                    }
                }
                else {
                    whiteF--;
                    if(wCheck() == false) {
                        whiteF++;
                        return false;
                    }
                    whiteF++;
                }
            }
            if(whiteF > 1 && whiteR < 8) {
                s = chess_board[whiteR+1][whiteF-1]; //Checking space one left one up
                if(s != null) {
                    String name = s.getName();
                    boolean color = s.isWhite(); //true if white, false if white
                    if(whiteturn == true) {//If it's white's turn, we check everything around the black king.
                        if(color == true) {//If there's an adjacent white piece, check to see if you can take it
                            whiteR++;
                            whiteF--;
                            if(wCheck() == false) {
                                whiteR--;
                                whiteF++;
                                return false;
                            }
                            whiteR--;
                            whiteF++;
                        }
                    }
                }
                else {
                    whiteR++;
                    whiteF--;
                    if(wCheck() == false) {
                        whiteR--;
                        whiteF++;
                        return false;
                    }
                    whiteR--;
                    whiteF++;
                }
            }
            return true;
        }
    }*/

}
