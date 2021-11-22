package io.github.jron.chess.common;

import io.github.jron.chess.common.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncrementorTest {

    @Test
    void increment() {
        Incrementor<Piece.Color> i = new Incrementor<>(0, Piece.Color.WHITE, Piece.Color.BLACK);

        assertEquals(Piece.Color.WHITE, i.get());
        assertEquals(Piece.Color.WHITE, i.get());
        assertEquals(Piece.Color.BLACK, i.increment().get());
        assertEquals(Piece.Color.WHITE, i.increment().get());
    }
}