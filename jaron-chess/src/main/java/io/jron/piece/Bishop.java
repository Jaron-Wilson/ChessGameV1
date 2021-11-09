package io.jron.piece;

import io.jron.Board;
import io.jron.Coordanate;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public List<Coordanate> canMoveTo(Board board, Coordanate current) {
        List<Coordanate> moves = new ArrayList<>(14);

        for (int x = current.getX() + 1, y = current.getY() - 1; x < 8 && y >= 0; x++, y--) {
            boolean canMove = addIfEmptyAndMoreThanThat(moves, board, x, y);
            if (!canMove) break;
        }

        for (int x = current.getX() + 1, y = current.getY() + 1; x < 8 && y < 8; x++, y++) {
            boolean canMove = addIfEmptyAndMoreThanThat(moves, board, x, y);
            if (!canMove) break;
        }

        for (int x = current.getX() - 1, y = current.getY() + 1; x > 0 && y < 8; x--, y++) {
            boolean canMove = addIfEmptyAndMoreThanThat(moves, board, x, y);
            if (!canMove) break;
        }

        for (int x = current.getX() - 1, y = current.getY() - 1; x > 0 && y > 0; x--, y--) {
            boolean canMove = addIfEmptyAndMoreThanThat(moves, board, x, y);
            if (!canMove) break;
        }

        return moves;
    }


}
