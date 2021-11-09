package io.jron;

import io.jron.piece.Piece;

public class MutableBoard implements Board {

    private final Piece[][] board = new Piece[8][8];

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

    public Piece setPiece(Coordanate c, Piece piece) {
        return setPiece(c.getX(), c.getY(), piece);
    }

    public Piece setPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
        return piece;
    }
}
