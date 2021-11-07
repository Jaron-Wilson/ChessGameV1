package io.jron.piece;

import io.jron.Board;
import io.jron.Coordanate;

import java.util.Collections;
import java.util.List;

public abstract class Piece {

    private Color color = null;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean addIfEmptyAndMoreThanThat(List<Coordanate> moves , Board board, int x, int y) {
        if (x < 0 || y < 0 || x > 7 || y > 7) return false;
        System.out.println(String.format("x = %d, y = %d", x, y));

        Piece p = board.getPiece(x, y);
        if (p == null) {
            moves.add(new Coordanate(x, y));
            return true;
        } else {
            if (p.getColor() != color) {
                moves.add(new Coordanate(x, y));
                return false;
            }
        }

        return false;
    }

    public List<Coordanate> canMoveTo(Board board, Coordanate current) {
        return Collections.emptyList();
    }

    public enum Color {
        WHITE, BLACK
    }
}
