package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Coordanate;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public List<Coordanate> canMoveTo(Board board, Coordanate current) {
        List<Coordanate> moves = new ArrayList<>(8);

        for (int y = current.getY() - 2; y <= current.getY() + 2; y += 4) {
            for (int x = current.getX() - 1; x <= current.getX() + 2; x += 2) {
                addIfEmptyAndMoreThanThat(moves, board, x, y);
            }
        }

        for (int y = current.getY() - 1; y <= current.getY() + 1; y += 2) {
            for (int x = current.getX() - 2; x <= current.getX() + 2; x += 4) {
                addIfEmptyAndMoreThanThat(moves, board, x, y);
            }
        }

        return moves;
    }

    @Override
    public int getPieceId() {
        return 3;
    }
}
