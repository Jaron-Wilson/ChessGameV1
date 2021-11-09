package io.jron.piece;

import io.jron.Board;
import io.jron.Coordanate;

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
    public List<Coordanate> canMoveTo(Board board, Coordanate current) {
        List<Coordanate> moves = new ArrayList<>(14);
        moves.addAll(piece1.canMoveTo(board, current));
        moves.addAll(piece2.canMoveTo(board, current));

        return moves;
    }

    @Override
    public String toString() {
        return piece1.getClass().getSimpleName().substring(0,2) + "/" + piece2.getClass().getSimpleName().substring(0,2);
    }
}
