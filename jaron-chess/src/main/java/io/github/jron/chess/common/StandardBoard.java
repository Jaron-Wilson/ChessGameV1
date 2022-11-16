package io.github.jron.chess.common;

import io.github.jron.chess.common.piece.Color;
import io.github.jron.chess.common.piece.Piece;
import io.github.jron.chess.common.piece.Queen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class StandardBoard implements Board {

    private final int width = 8, height = 8;
    private final Piece[][] board = new Piece[8][8];
    private final Incrementer<Color> incrementer;

    private Position eligibleEnPassant;

    public StandardBoard(StandardBoard board){
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.board[x][y] = board.board[x][y];
            }
        }

        this.incrementer = new Incrementer<>(board.incrementer);
    }

    public StandardBoard() {
        this.incrementer = new Incrementer<>(Color.WHITE, Color.BLACK);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Piece getPiece(int x, int y) {
        return board[x][y];
    }
    public Piece getPiece(Position p) {
        return getPiece(p.getX(), p.getY());
    }


    @Override
    public Piece removePiece(int x, int y) {
        Piece p = board[x][y];
        board[x][y] = null;
        return p;
    }


    public Stream<Piece> getAllPieces() {
        Stream.Builder<Piece> builder = Stream.builder();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[x][y] != null) {
                    builder.add(board[x][y]);
                }
            }
        }

        return builder.build();
    }

    public Set<Position> getThreatenedPositions(Color color) {
        Set<Position> positions = new HashSet<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[x][y] != null) {
                    Piece p = board[x][y];
                    if( p instanceof Queen ){
                        System.out.println("Stink");
                    }
                    if( !p.getColor().equals(color)){
                        List<Position> threatened = p.getThreatenedPositions(this, new Position(x,y));
                        positions.addAll(threatened);
                    }
                }
            }
        }

        return positions;
    }

    public Piece setPiece(Position c, Piece piece) {
        return setPiece(c.getX(), c.getY(), piece);
    }

    public Piece setPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
        return piece;
    }

    public Position getEligibleEnPassant() {
        return eligibleEnPassant;
    }

    public void setEligibleEnPassant(Position eligibleEnPassant) {
        this.eligibleEnPassant = eligibleEnPassant;
    }

    @Override
    public Incrementer<Color> getTurn() {
        return incrementer;
    }

}
