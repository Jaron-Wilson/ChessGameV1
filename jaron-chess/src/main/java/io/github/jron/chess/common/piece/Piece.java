package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;

import java.util.Collections;
import java.util.List;

public abstract class Piece {

    protected int moveCount = 0;
    public static boolean CAN_CAPTURE_OWN = false;

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public abstract int getPieceId();

    public Color getColor() {
        return color;
    }

    public boolean addIfEmptyAndMoreThanThat(List<Position> moves, Board board, int x, int y) {
        if (x < 0 || y < 0 || x > 7 || y > 7) return false;
        System.out.printf("x = %d, y = %d%n", x, y);

        Piece p = board.getPiece(x, y);
        if (p == null) {
            moves.add(new Position(x, y));
            return true;
        } else {
            if (CAN_CAPTURE_OWN || p.getColor() != color) {
                moves.add(new Position(x, y));
                return false;
            }
        }

        return false;
    }

    public List<Position> canMoveTo(Board board, Position current) {
        return Collections.emptyList();
    }

    /**
     * The piece is responsible for moving itself on the board. This allows for
     * special situations such as castling.
     *
     * @param board The state of the game
     * @param p1 Starting Position
     * @param p2 Ending Position
     * @return Captured piece, null if no piece was captured
     */
    public Piece move(StandardBoard board, Position p1, Position p2) {
        moveCount++;
        Piece capture = board.removePiece(p2.getX(), p2.getY());
        board.setPiece(p2.getX(), p2.getY(), board.removePiece(p1.getX(), p1.getY()));
        board.setEligibleEnPassant(null);
        return capture;
    }

    @Override
    public String toString() {
        return color + " " + getClass().getSimpleName();
    }

}
