package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Color color) {
        super(color);
    }

    @Override
    public List<Coordinate> canMoveTo(Board board, Coordinate current) {
        List<Coordinate> moves = new ArrayList<>(14);

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

    @Override
    public int getPieceId() {
        return 0;
    }

}
