package io.jron.piece;

import io.jron.Board;
import io.jron.Coordanate;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<Coordanate> canMoveTo(Board board, Coordanate current) {
        List<Coordanate> moves = new ArrayList<>(8);

        int x = current.getX();


        if (getColor() == Color.BLACK) {
            int maxY = current.getY()  + (current.getY() == 1 ? 2 : 1 );
            for (int y = current.getY() + 1; y <= maxY; y++) {
                Piece spot = board.getPiece(x, y);
                if (spot == null) {
                    moves.add(new Coordanate(x, y));
                } else {
                    break;
                }
            }

            if( x>0) {
                Piece capture = board.getPiece(x - 1, current.getY() + 1);
                if (capture != null && capture.getColor() == Color.WHITE) {
                    moves.add(new Coordanate(x - 1, current.getY() + 1));
                }
            }

            if( x < board.getWidth()-1) {
                Piece capture = board.getPiece(x + 1, current.getY() + 1);
                if (capture != null && capture.getColor() == Color.WHITE) {
                    moves.add(new Coordanate(x + 1, current.getY() + 1));
                }
            }
        }
        if (getColor() == Color.WHITE) {
            int maxY = current.getY() - (current.getY() == 6 ? 2 : 1);
            for (int y = current.getY() - 1; y >= maxY; y--) {
                Piece spot = board.getPiece(x, y);
                if (spot == null) {
                    moves.add(new Coordanate(x, y));
                } else {
                    break;
                }
            }
            if( x > 0) {

                Piece capture = board.getPiece(x - 1, current.getY() - 1);

                if (capture != null && capture.getColor() == Color.BLACK) {
                    moves.add(new Coordanate(x - 1, current.getY() - 1));
                }
            }

            if( x < board.getWidth()+1) {
                Piece capture = board.getPiece(x + 1, current.getY() - 1);
                if (capture != null && capture.getColor() == Color.BLACK) {
                    moves.add(new Coordanate(x + 1, current.getY() - 1));
                }
            }
        }


        return moves;
    }
}
