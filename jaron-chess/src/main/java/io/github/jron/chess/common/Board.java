package io.github.jron.chess.common;


import io.github.jron.chess.common.piece.Color;
import io.github.jron.chess.common.piece.Piece;

import java.util.Set;
import java.util.stream.Stream;

/**
 * A board holds the state of the game in a two-dimensional array.
 * A zero '0' represents an empty spot, any other number represents
 * a players io.jron.piece.
 */
public interface Board {

    /**
     * A getter for width
     *
     * @return The number of pieces across the board.
     */
    int getWidth();

    /**
     * A getter for height
     *
     * @return The number of pieces high that make up the board.
     */
    int getHeight();

    /**
     * Gets the io.jron.piece at [x,y]
     *
     * @param x The X Position
     * @param y The y Position
     * @return The io.jron.piece at [x,y]
     */
    Piece getPiece(int x, int y);
    Piece getPiece(Position p);

    Piece removePiece(int x, int y);

    Stream<Piece> getAllPieces();

    Set<Position> getThreatenedPositions(Color color);

    /**
     * Getter for the current turn
     *
     * @return The color whos turn it is
     */
    Incrementer<Color> getTurn();

    Position getEligibleEnPassant();
}