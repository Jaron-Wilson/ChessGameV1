package io.github.jron.chess;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Coordanate;
import io.github.jron.chess.common.FenParser;
import io.github.jron.chess.common.piece.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FenParserTest {

    private static List<Object> provideOpeningBoard() {

        List<Object> arguments = new ArrayList<>();

        //Add the black non-pawns
        Collections.addAll(arguments,
                Arguments.of(new Coordanate(0, 0), new Rook(Piece.Color.BLACK)),
                Arguments.of(new Coordanate(1, 0), new Knight(Piece.Color.BLACK)),
                Arguments.of(new Coordanate(2, 0), new Bishop(Piece.Color.BLACK)),
                Arguments.of(new Coordanate(3, 0), new Queen(Piece.Color.BLACK)),
                Arguments.of(new Coordanate(4, 0), new King(Piece.Color.BLACK)),
                Arguments.of(new Coordanate(5, 0), new Bishop(Piece.Color.BLACK)),
                Arguments.of(new Coordanate(6, 0), new Knight(Piece.Color.BLACK)),
                Arguments.of(new Coordanate(7, 0), new Rook(Piece.Color.BLACK))
        );

        //Add the black pawns
        arguments.addAll(IntStream.range(0, 8).mapToObj(x ->
                Arguments.of(new Coordanate(x, 1), new Pawn(Piece.Color.BLACK))).collect(Collectors.toList())
        );

        //Add the white pawns
        arguments.addAll(IntStream.range(0, 8).mapToObj(x ->
                Arguments.of(new Coordanate(x, 6), new Pawn(Piece.Color.WHITE))).collect(Collectors.toList())
        );

        //Add the white non-pawns
        Collections.addAll(arguments,
                Arguments.of(new Coordanate(0, 7), new Rook(Piece.Color.WHITE)),
                Arguments.of(new Coordanate(1, 7), new Knight(Piece.Color.WHITE)),
                Arguments.of(new Coordanate(2, 7), new Bishop(Piece.Color.WHITE)),
                Arguments.of(new Coordanate(3, 7), new Queen(Piece.Color.WHITE)),
                Arguments.of(new Coordanate(4, 7), new King(Piece.Color.WHITE)),
                Arguments.of(new Coordanate(5, 7), new Bishop(Piece.Color.WHITE)),
                Arguments.of(new Coordanate(6, 7), new Knight(Piece.Color.WHITE)),
                Arguments.of(new Coordanate(7, 7), new Rook(Piece.Color.WHITE))
        );

        return arguments;
    }

    /**
     * This test will verify that the starting board is correct. The <code>provideOpeningBoard</code> method
     * is used as a Parameterized MethodSource to check each position one at a time.
     *
     * @param c        The expected position on the board
     * @param expected The expected piece at the given position
     * @see FenParserTest#provideOpeningBoard()
     */
    @ParameterizedTest
    @MethodSource("provideOpeningBoard")
    public void testStartingPosition(Coordanate c, Piece expected) {
        Board board = new FenParser().parse(FenParser.STARTING_POSITION);

        Piece piece = board.getPiece(c.getX(), c.getY());

        assertNotNull(piece, "null not expected");
        assertEquals(expected.getClass().getSimpleName(), piece.getClass().getSimpleName());
        assertEquals(expected.getColor(), piece.getColor());
    }

    @Test
    public void testStartFromOpeningBoardMoveTo_1e4() {
        Board board = new FenParser().parse("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");

        //Check if the starting pawn spot on the e rank is empty
        Piece empty = board.getPiece(4, 6);
        assertNull(empty);

        //Check to see if the white pawn on the e rank is moved up two
        Piece piece = board.getPiece(4, 4);
        assertNotNull(piece);
        assertEquals("Pawn", piece.getClass().getSimpleName());
        assertEquals(Piece.Color.WHITE, piece.getColor());
    }

    @Test
    public void testFormat() {
        Board board = new FenParser().parse("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");

        String fen = new FenParser().format(board);
        System.out.println(fen);
    }
}