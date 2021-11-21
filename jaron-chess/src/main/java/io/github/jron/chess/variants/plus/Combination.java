package io.github.jron.chess.variants.plus;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Coordinate;
import io.github.jron.chess.common.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class Combination extends Piece {

    private Piece piece1, piece2;

    public Combination(Piece piece1, Piece piece2) {
        super(piece1.getColor());
        this.piece1 = piece1;
        this.piece2 = piece2;
    }

    @Override
    public List<Coordinate> canMoveTo(Board board, Coordinate current) {
        List<Coordinate> moves = new ArrayList<>(20);
        moves.addAll(piece1.canMoveTo(board, current));
        moves.addAll(piece2.canMoveTo(board, current));

        return moves;
    }

    @Override
    public String toString() {
        return piece1.getClass().getSimpleName().substring(0, 2) + "/" + piece2.getClass().getSimpleName().substring(0, 2);
    }

    public Piece getPiece1() {
        return piece1;
    }

    public Piece getPiece2() {
        return piece2;
    }

    @Override
    public int getPieceId() {
        return ((piece1.getPieceId() * 10) + (piece2.getPieceId() + 1));
    }
}