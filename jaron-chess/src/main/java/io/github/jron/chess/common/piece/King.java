package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    public List<Coordinate> canMoveTo(Board board, Coordinate current) {
        List<Coordinate> moves = new ArrayList<>(8);

        //dx = delta x
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int x = current.getX() + dx;
                int y = current.getY() + dy;
                if (x >= 0 && y >= 0 && x < 8 && y < 8) {
                    addIfEmptyAndMoreThanThat(moves, board, x, y);
                }
            }
        }
        return moves;
    }

    @Override
    public int getPieceId() {
        return 1;
    }
}