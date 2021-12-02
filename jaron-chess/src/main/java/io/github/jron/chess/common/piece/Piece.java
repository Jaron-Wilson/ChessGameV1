package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

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
        //System.out.printf("x = %d, y = %d%n", x, y);

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

    public List<Position> getThreatenedPositions(Board board, Position current) {
        return canMoveTo(board, current);
    }

    public boolean isKingThreatened(Board board, Position current, Color turn) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece  p = board.getPiece(x,y);
                if (p != null && p.getColor().equals(turn) && p instanceof King) {
                    Set<Position> positions = board.getThreatenedPositions(turn);
                    return positions.contains(new Position(x,y));
                }
            }
        }
        return false;
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

        if (isKingThreatened(board, p2, board.getTurn().get())) {
                board.getTurn().increment();


                Piece undoMove = board.removePiece(p2.getX(), p2.getY());
                board.setPiece(p2.getX(), p2.getY(), capture);
                board.setPiece(p1.getX(), p1.getY(), undoMove);
                moveCount--;
                board.setEligibleEnPassant(null);
                return undoMove;
            }

//        board.setEligibleEnPassant(null);
//        if (isKingThreatened(board, p2, board.getTurn().get())) {
//            System.out.println("!in check!");
//        }
//
        return capture;
//        return null;
    }

    @Override
    public String toString() {
        return color + " " + getClass().getSimpleName();
    }

}
