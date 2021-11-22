package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public List<Position> canMoveTo(Board board, Position current) {
        List<Position> moves = new ArrayList<>(14);

        for (int y = current.getY(), x = current.getX() - 1; x >= 0; x--) {
            if (!addIfEmptyAndMoreThanThat(moves, board, x, y)) {
                break;
            }
        }
        for (int y = current.getY(), x = current.getX() + 1; x < board.getWidth(); x++) {
            if (!addIfEmptyAndMoreThanThat(moves, board, x, y)) {
                break;
            }
        }

        for (int x = current.getX(), y = current.getY() + 1; y < board.getWidth(); y++) {
            if (!addIfEmptyAndMoreThanThat(moves, board, x, y)) {
                break;
            }
        }
        for (int x = current.getX(), y = current.getY() - 1; y >= 0; y--) {
            if (!addIfEmptyAndMoreThanThat(moves, board, x, y)) {
                break;
            }
        }

        return moves;
    }

    @Override
    public int getPieceId() {
        return 2;
    }
}
