package io.github.jron.chess.common;

import io.github.jron.chess.common.piece.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncrementerTest {

    @Test
    void increment() {
        Incrementer<Color> i = new Incrementer<>(Color.WHITE, Color.BLACK);

        assertEquals(Color.WHITE, i.get());
        assertEquals(Color.WHITE, i.get());
        assertEquals(Color.BLACK, i.increment().get());
        assertEquals(Color.WHITE, i.increment().get());
    }
}