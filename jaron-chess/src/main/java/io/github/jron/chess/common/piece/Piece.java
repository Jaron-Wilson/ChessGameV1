package io.github.jron.chess.common.piece;


import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Coordinate;

import java.util.Collections;
import java.util.List;

public abstract class Piece {

    public static boolean CAN_CAPTURE_OWN = false;

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public abstract int getPieceId();

    public Color getColor() {
        return color;
    }

    public boolean addIfEmptyAndMoreThanThat(List<Coordinate> moves, Board board, int x, int y) {
        if (x < 0 || y < 0 || x > 7 || y > 7) return false;
        System.out.printf("x = %d, y = %d%n", x, y);

        Piece p = board.getPiece(x, y);
        if (p == null) {
            moves.add(new Coordinate(x, y));
            return true;
        } else {
            if (CAN_CAPTURE_OWN || p.getColor() != color) {
                moves.add(new Coordinate(x, y));
                return false;
            }
        }

        return false;
    }

    public List<Coordinate> canMoveTo(Board board, Coordinate current) {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return color + " " + getClass().getSimpleName();
    }

    public enum Color {
        WHITE, BLACK
    }
}
