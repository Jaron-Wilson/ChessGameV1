package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Piece move(StandardBoard board, Position p1, Position p2) {

        //Castle logic.
        if (moveCount == 0){
            Optional<King> king = board.getAllPieces()
                    .filter(p -> p.getColor().equals(getColor()))
                    .filter(King.class::isInstance)
                    .map(King.class::cast).findFirst();
            if(king.isPresent()) {
                if (p1.getX() == 0) {
                    king.get().setCanCastleQueenSide(false);
                } else {
                    king.get().setCanCastleKingSide(false);
                }
            }
        }
        return super.move(board, p1, p2);
    }

    @Override
    public int getPieceId() {
        return 2;
    }
}
