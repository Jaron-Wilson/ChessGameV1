package io.github.jron.chess.common;

import io.github.jron.chess.common.piece.Piece;

public class StandardBoard implements Board {

    private final Piece[][] board = new Piece[8][8];

    //private Piece.Color turn = Piece.Color.WHITE;

    private final Incrementor<Piece.Color> incrementor;

    public StandardBoard(){
       this.incrementor = new Incrementor<>(0,  Piece.Color.WHITE, Piece.Color.BLACK);
    }
    @Override
    public int getWidth() {
        return 8;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    public Piece setPiece(Position c, Piece piece) {
        return setPiece(c.getX(), c.getY(), piece);
    }

    public Piece setPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
        return piece;
    }

    @Override
    public Incrementor<Piece.Color> getTurn() {
        return incrementor;
    }

    //public void setTurn(Piece.Color turn) {
    //    this.turn = turn;
    //}
}
