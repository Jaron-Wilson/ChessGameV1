package io.github.jron.chess;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.FenUtilities;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;
import io.github.jron.chess.common.piece.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("rawtypes")
class FenParserTest {

    private static List<Object> provideOpeningBoard() {

        List<Object> arguments = new ArrayList<>();

        //Add the black non-pawns
        Collections.addAll(arguments,
                Arguments.of(new Position(0, 0), new Rook(Color.BLACK)),
                Arguments.of(new Position(1, 0), new Knight(Color.BLACK)),
                Arguments.of(new Position(2, 0), new Bishop(Color.BLACK)),
                Arguments.of(new Position(3, 0), new Queen(Color.BLACK)),
                Arguments.of(new Position(4, 0), new King(Color.BLACK)),
                Arguments.of(new Position(5, 0), new Bishop(Color.BLACK)),
                Arguments.of(new Position(6, 0), new Knight(Color.BLACK)),
                Arguments.of(new Position(7, 0), new Rook(Color.BLACK))
        );

        //Add the black pawns
        arguments.addAll(IntStream.range(0, 8).mapToObj(x ->
                Arguments.of(new Position(x, 1), new Pawn(Color.BLACK))).collect(Collectors.toList())
        );

        //Add the white pawns
        arguments.addAll(IntStream.range(0, 8).mapToObj(x ->
                Arguments.of(new Position(x, 6), new Pawn(Color.WHITE))).collect(Collectors.toList())
        );

        //Add the white non-pawns
        Collections.addAll(arguments,
                Arguments.of(new Position(0, 7), new Rook(Color.WHITE)),
                Arguments.of(new Position(1, 7), new Knight(Color.WHITE)),
                Arguments.of(new Position(2, 7), new Bishop(Color.WHITE)),
                Arguments.of(new Position(3, 7), new Queen(Color.WHITE)),
                Arguments.of(new Position(4, 7), new King(Color.WHITE)),
                Arguments.of(new Position(5, 7), new Bishop(Color.WHITE)),
                Arguments.of(new Position(6, 7), new Knight(Color.WHITE)),
                Arguments.of(new Position(7, 7), new Rook(Color.WHITE))
        );

        return arguments;
    }

    private static List<Object> provideFenForCastlingAvailability() {
        return Arrays.asList(
                Arguments.of(FenUtilities.STARTING_POSITION, Arrays.asList(true, true, true, true)),
                Arguments.of("rn2kb1r/p3pp1p/2p1bnp1/R5q1/8/3P1Q1P/1pPBBPP1/1N2K1NR b Kkq - 1 15", Arrays.asList(true, false, true, true)),
                Arguments.of("6r1/1ppk1prp/p2bbp2/8/1P6/2P2NP1/P2NRP1P/4RK2 w - - 1 23", Arrays.asList(false, false, false, false)),
                Arguments.of("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", Arrays.asList(true, true, true, true))
        );
    }

    private static List<Object> provideFenForParseThenBackToFormat() {
        return Arrays.asList(
                Arguments.of(FenUtilities.STARTING_POSITION)
                //Arguments.of("rn2kb1r/p3pp1p/2p1bnp1/R5q1/8/3P1Q1P/1pPBBPP1/1N2K1NR b Kkq - 1 15"),
                //Arguments.of("6r1/1ppk1prp/p2bbp2/8/1P6/2P2NP1/P2NRP1P/4RK2 w - - 1 23"),
                //Arguments.of("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1")
        );
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
    public void testStartingPosition(Position c, Piece expected) {
        Board board = new FenUtilities<>().parse(FenUtilities.STARTING_POSITION);

        Piece piece = board.getPiece(c.getX(), c.getY());

        assertNotNull(piece, "null not expected");
        assertEquals(expected.getClass().getSimpleName(), piece.getClass().getSimpleName());
        assertEquals(expected.getColor(), piece.getColor());
    }

    @Test
    public void testStartFromOpeningBoardMoveTo_1e4() {
        Board board = new FenUtilities().parse("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");

        //Check if the starting pawn spot on the e rank is empty
        Piece empty = board.getPiece(4, 6);
        assertNull(empty);

        //Check to see if the white pawn on the e rank is moved up two
        Piece piece = board.getPiece(4, 4);
        assertNotNull(piece);
        assertEquals("Pawn", piece.getClass().getSimpleName());
        assertEquals(Color.WHITE, piece.getColor());
    }

    @ParameterizedTest
    @MethodSource("provideFenForCastlingAvailability")
    public void testCastlingAvailabilityKkq(String fen, List<Boolean> canCastle) {
        StandardBoard board = new FenUtilities().parse(fen, new StandardBoard());

        Map<Color, King> kings = board.getAllPieces()
                .filter(King.class::isInstance)
                .map(King.class::cast).collect(Collectors.toMap(Piece::getColor, Function.identity()));

        if (canCastle.size() > 0) assertEquals(canCastle.get(0), kings.get(Color.WHITE).canCastleKingSide());
        if (canCastle.size() > 1) assertEquals(canCastle.get(1), kings.get(Color.WHITE).canCastleQueenSide());
        if (canCastle.size() > 2) assertEquals(canCastle.get(2), kings.get(Color.BLACK).canCastleKingSide());
        if (canCastle.size() > 3) assertEquals(canCastle.get(3), kings.get(Color.BLACK).canCastleQueenSide());
    }


    @ParameterizedTest
    @MethodSource("provideFenForParseThenBackToFormat")
    public void testParseThenBackToFormat(String expected) {
        Board board = new FenUtilities().parse(expected);

        String fen = new FenUtilities().format(board);
        System.out.println(fen);

        assertEquals(expected, fen);
    }
}