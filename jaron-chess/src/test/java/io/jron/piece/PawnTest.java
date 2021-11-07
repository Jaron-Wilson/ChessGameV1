package io.jron.piece;

import io.jron.Coordanate;
import io.jron.MutableBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class PawnTest {

    @Test
    public void testFirstMove(){
        MutableBoard b = new MutableBoard();
        Pawn pawn = (Pawn) b.setPiece(4,6, new Pawn(Piece.Color.WHITE));

        List<Coordanate> moves = pawn.canMoveTo(b, new Coordanate(4, 6));
        Assertions.assertEquals(moves.size(), 2);

        System.out.println(moves);
    }
}