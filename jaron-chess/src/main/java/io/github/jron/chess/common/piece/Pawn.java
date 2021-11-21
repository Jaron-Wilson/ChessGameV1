package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<Coordinate> canMoveTo(Board board, Coordinate current) {
        List<Coordinate> moves = new ArrayList<>(4);

        int x = current.getX();

        if (getColor() == Color.BLACK) {
            int maxY = current.getY() + (current.getY() == 1 ? 2 : 1);
            for (int y = current.getY() + 1; y <= maxY; y++) {
                if (!addIfEmptyAndMoreThanThat(moves, board, x, y)) {
                    break;
                }
            }

            if (x > 0) {
//                 Checks [ ][P][ ]
//                        [x][ ][ ]
                Piece capture = board.getPiece(x - 1, current.getY() + 1);
                if (capture != null && capture.getColor() == Color.WHITE) {
                    moves.add(new Coordinate(x - 1, current.getY() + 1));
                }
            }

            if (x < board.getWidth() - 1) {
//                 Checks [ ][P][ ]
//                        [ ][ ][x]
                Piece capture = board.getPiece(x + 1, current.getY() + 1);
                if (capture != null && capture.getColor() == Color.WHITE) {
                    moves.add(new Coordinate(x + 1, current.getY() + 1));
                }
            }
        }
        if (getColor() == Color.WHITE) {
            int maxY = current.getY() - (current.getY() == 6 ? 2 : 1);
            for (int y = current.getY() - 1; y >= maxY; y--) {
                if (!addIfEmptyAndMoreThanThat(moves, board, x, y)) {
                    break;
                }
            }
            if (x > 0) {
//                 Checks [x][ ][ ]
//                        [ ][P][ ]
                Piece capture = board.getPiece(x - 1, current.getY() - 1);
                if (capture != null && capture.getColor() == Color.BLACK) {
                    moves.add(new Coordinate(x - 1, current.getY() - 1));
                }
            }

            if (x < board.getWidth() - 1) {
//                 Checks [ ][ ][x]
//                        [ ][P][ ]
                Piece capture = board.getPiece(x + 1, current.getY() - 1);
                if (capture != null && capture.getColor() == Color.BLACK) {
                    moves.add(new Coordinate(x + 1, current.getY() - 1));
                }
            }
        }

        return moves;
    }

    @Override
    public int getPieceId() {
        return 5;
    }
}
