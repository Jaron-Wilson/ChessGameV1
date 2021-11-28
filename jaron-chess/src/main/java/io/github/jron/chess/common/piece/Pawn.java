package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<Position> canMoveTo(Board board, Position current) {
        List<Position> moves = new ArrayList<>(4);

        int x = current.getX();

        if (getColor() == Color.BLACK) {
            int maxY = current.getY() + (current.getY() == 1 ? 2 : 1);
            for (int y = current.getY() + 1; y <= maxY; y++) {
                if (!addIfEmptyAndMoreThanThat(moves, board, x, y)) {
                    break;
                }
            }

            if (x > 0 && current.getY() > 0) {
//                 Checks [ ][P][ ]
//                        [x][ ][ ]
                Position moveTo = new Position(x - 1, current.getY() + 1);
                Piece capture = board.getPiece(moveTo);
                if ((capture != null && capture.getColor() == Color.WHITE)
                ||  moveTo.equals(board.getEligibleEnPassant())) {
                    moves.add(moveTo);
                }
            }

            if (x < board.getWidth() - 1) {
//                 Checks [ ][P][ ]
//                        [ ][ ][x]
                Position moveTo = new Position(x + 1, current.getY() + 1);
                Piece capture = board.getPiece(moveTo);
                if ((capture != null && capture.getColor() == Color.WHITE)
                        ||  moveTo.equals(board.getEligibleEnPassant())) {
                    moves.add(moveTo);
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
            if (x > 0 && current.getY() > 0) {
//                 Checks [x][ ][ ]
//                        [ ][P][ ]
                Position moveTo = new Position(x - 1, current.getY() - 1);
                Piece capture = board.getPiece(moveTo);
                if ((capture != null && capture.getColor() == Color.BLACK)
                        ||  moveTo.equals(board.getEligibleEnPassant())) {
                    moves.add(moveTo);
                }
            }

            if (x < board.getWidth() - 1) {
//                 Checks [ ][ ][x]
//                        [ ][P][ ]
                Position moveTo = new Position(x + 1, current.getY() - 1);
                Piece capture = board.getPiece(moveTo);
                if ((capture != null && capture.getColor() == Color.BLACK)
                        ||  moveTo.equals(board.getEligibleEnPassant())) {
                    moves.add(moveTo);
                }
            }
        }

        return moves;
    }

    @Override
    public List<Position> getThreadedPositions(Board board, Position current) {
        List<Position> moves = new ArrayList<>(4);

        int x = current.getX();

        if (getColor() == Color.BLACK) {
            if (x > 0 && current.getY() > 0) {
//                 Checks [ ][P][ ]
//                        [x][ ][ ]
                moves.add(new Position(x - 1, current.getY() + 1));
            }

            if (x < board.getWidth() - 1) {
//                 Checks [ ][P][ ]
//                        [ ][ ][x]
                moves.add(new Position(x + 1, current.getY() + 1));
            }
        }
        if (getColor() == Color.WHITE) {
            if ((x > 0) && (current.getY() > 0)) {
//                 Checks [x][ ][ ]
//                        [ ][P][ ]
                moves.add(new Position(x - 1, current.getY() - 1));
            }

            if (x < board.getWidth() - 1) {
//                 Checks [ ][ ][x]
//                        [ ][P][ ]
                    moves.add(new Position(x + 1, current.getY() - 1));
            }
        }

        return moves;
    }

    @Override
    public Piece move(StandardBoard board, Position p1, Position p2) {

        Piece enPassantCapture = null;
        if( p2.equals(board.getEligibleEnPassant())){
            enPassantCapture = board.removePiece(p2.getX(), p1.getY());
        }

        Piece capture = super.move(board, p1, p2);
        if( enPassantCapture != null) capture = enPassantCapture;


        if ( p1.getY() - p2.getY() == 2 ) {
            if ( p2.getX()>0 && (board.getPiece(p2.getX()-1, p2.getY()) instanceof Pawn
            || board.getPiece(p2.getX()+1, p2.getY()) instanceof Pawn)){

                board.setEligibleEnPassant(new Position(p2.getX(), p2.getY()+1));
                System.out.println("EligibleEnPassant: " + board.getEligibleEnPassant());
            }
        }

        if ( p1.getY() - p2.getY() == -2 ) {
            if ( p2.getX()>0 && (board.getPiece(p2.getX()+1, p2.getY()) instanceof Pawn
                    || board.getPiece(p2.getX()-1, p2.getY()) instanceof Pawn)){

                board.setEligibleEnPassant(new Position(p2.getX(), p2.getY()-1));
                System.out.println("EligibleEnPassant: " + board.getEligibleEnPassant());
            }
        }

        return capture;
    }

    @Override
    public int getPieceId() {
        return 5;
    }
}
