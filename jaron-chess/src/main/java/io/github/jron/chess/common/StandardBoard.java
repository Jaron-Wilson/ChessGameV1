package io.github.jron.chess.common;

import io.github.jron.chess.common.piece.Color;
import io.github.jron.chess.common.piece.Piece;

import java.util.stream.Stream;

public class StandardBoard implements Board {

    private final int width = 8, height = 8;
    private final Piece[][] board = new Piece[8][8];
    private final Incrementer<Color> incrementer;

    public StandardBoard() {
        this.incrementer = new Incrementer<>(Color.WHITE, Color.BLACK);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    @Override
    public Piece removePiece(int x, int y) {
        Piece p = board[x][y];
        board[x][y] = null;
        return p;
    }


    public Stream<Piece> getAllPieces() {
        Stream.Builder<Piece> builder = Stream.builder();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[x][y] != null) {
                    builder.add(board[x][y]);
                }
            }
        }

        return builder.build();
    }

    public Piece setPiece(Position c, Piece piece) {
        return setPiece(c.getX(), c.getY(), piece);
    }

    public Piece setPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
        return piece;
    }

    @Override
    public Incrementer<Color> getTurn() {
        return incrementer;
    }

}
