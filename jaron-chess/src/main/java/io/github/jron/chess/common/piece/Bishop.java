package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public List<Position> canMoveTo(Board board, Position current) {
        List<Position> moves = new ArrayList<>(14);

        for (int x = current.getX() + 1, y = current.getY() - 1; x < 8 && y >= 0; x++, y--) {
            boolean canMove = addIfEmptyAndMoreThanThat(moves, board, x, y);
            if (!canMove) break;
        }

        for (int x = current.getX() + 1, y = current.getY() + 1; x < 8 && y < 8; x++, y++) {
            boolean canMove = addIfEmptyAndMoreThanThat(moves, board, x, y);
            if (!canMove) break;
        }

        for (int x = current.getX() - 1, y = current.getY() + 1; x >+ 0 && y < 8; x--, y++) {
            boolean canMove = addIfEmptyAndMoreThanThat(moves, board, x, y);
            if (!canMove) break;
        }

        for (int x = current.getX() - 1, y = current.getY() - 1; x >= 0 && y >= 0; x--, y--) {
            boolean canMove = addIfEmptyAndMoreThanThat(moves, board, x, y);
            if (!canMove) break;
        }

        return moves;
    }


    @Override
    public int getPieceId() {
        return 4;
    }
}
