package io.jron;

import io.jron.piece.*;

/**
 * <p>Forsyth–Edwards Notation (FEN) is a standard notation for describing a particular board position of a chess game.
 * The purpose of FEN is to provide all the necessary information to restart a game from a particular position.</p>
 *
 * <p>FEN is based on a system developed by Scottish newspaper journalist David Forsyth. Forsyth's system became popular
 * in the 19th century; Steven J. Edwards extended it to support use by computers. FEN is defined[2] in the
 * "Portable Game Notation Specification and Implementation Guide".[1] In the Portable Game Notation for chess games,
 * FEN is used to define initial positions other than the standard one.[3] FEN does not provide sufficient
 * information to decide whether a draw by threefold repetition may be legally claimed or a draw offer may be
 * accepted; for that, a different format such as Extended Position Description is needed.</p>
 *
 * <p>
 * A FEN "record" defines a particular game position, all in one text line and using only the ASCII character set.
 * A text file with only FEN data records should have the file extension ".fen".
 * </p>
 *
 * <p>
 * A FEN record contains six fields. The separator between fields is a space. The fields are:
 * <ol>
 *     <li>
 *         <b>Piece placement</b> (from White's perspective). Each rank is described, starting with rank 8 and ending with
 *         rank 1; within each rank, the contents of each square are described from file "a" through file "h".
 *         Following the Standard Algebraic Notation (SAN), each piece is identified by a single letter taken from
 *         the standard English names (pawn = "P", knight = "N", bishop = "B", rook = "R", queen = "Q" and king = "K").
 *         White pieces are designated using upper-case letters ("PNBRQK") while black pieces use lowercase ("pnbrqk").
 *         Empty squares are noted using digits 1 through 8 (the number of empty squares), and "/" separates ranks.
 *     </li>
 *     <li>
 *         <b>Active color</b>. "w" means White moves next, "b" means Black moves next.
 *     </li>
 *     <li>
 *         <b>Castling availability</b>. If neither side can castle, this is "-". Otherwise, this has one or more
 *         letters: "K" (White can castle kingside), "Q" (White can castle queenside), "k" (Black can castle kingside),
 *         and/or "q" (Black can castle queenside). A move that temporarily prevents castling does not negate this
 *         notation.
 *     </li>
 *     <li>
 *         <b>En passant</b> target square in algebraic notation. If there's no en passant target square, this is "-".
 *         If a pawn has just made a two-square move, this is the position "behind" the pawn. This is recorded
 *         regardless of whether there is a pawn in position to make an en passant capture.[6]
 *     </li>
 *     <li>
 *         <b>Halfmove clock: </b>The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.[7]
 *     </li>
 *     <li>
 *         <b>Fullmove number:</b> The number of the full move. It starts at 1, and is incremented after Black's move.
 *     </li>
 * </ol>
 * </p>
 * <i>- https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation</i>
 */
public class FenParser {

    public static final String STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    /**
     * This factory returns a piece from a char. The mapping is as follows:
     * <ul>
     *     <li><b>R</b> : White Rook</li>
     *     <li><b>N</b> : White Knight</li>
     *     <li><b>Q</b> : White Queen</li>
     *     <li><b>K</b> : White King</li>
     *     <li><b>P</b> : White Pawn</li>
     *     <li><b>B</b> : White Bishop</li>
     * </ul>
     * <ul>
     *     <li><b>r</b> : Black Rook</li>
     *     <li><b>n</b> : Black Knight</li>
     *     <li><b>q</b> : Black Queen</li>
     *     <li><b>k</b> : Black King</li>
     *     <li><b>p</b> : Black Pawn</li>
     *     <li><b>b</b> : Black Bishop</li>
     * </ul>
     * <ul>
     *     <li><b>*</b> : <code>null</code></li>
     * </ul>
     */
    public static Piece getPiece(char c) {
        return switch (c) {
            case 'r' -> new Rook(Piece.Color.BLACK);
            case 'n' -> new Knight(Piece.Color.BLACK);
            case 'q' -> new Queen(Piece.Color.BLACK);
            case 'k' -> new King(Piece.Color.BLACK);
            case 'p' -> new Pawn(Piece.Color.BLACK);
            case 'b' -> new Bishop(Piece.Color.BLACK);
            case 'R' -> new Rook(Piece.Color.WHITE);
            case 'N' -> new Knight(Piece.Color.WHITE);
            case 'Q' -> new Queen(Piece.Color.WHITE);
            case 'K' -> new King(Piece.Color.WHITE);
            case 'P' -> new Pawn(Piece.Color.WHITE);
            case 'B' -> new Bishop(Piece.Color.WHITE);
            default -> null;
        };
    }

    /**
     * Parses the fen notation into a board
     * <p>
     * todo: Only the (1)Piece placement are parsed, everything else after the space is ignored for now.
     *
     * @param fen Portable Game Notation
     * @return A Board containing the state of the loaded game
     */
    public Board parse(String fen) {
        return parse(fen, new MutableBoard());
    }

    /**
     * Parses the fen notation into a board
     * <p>
     * todo: Only the (1)Piece placement are parsed, everything else after the space is ignored for now.
     *
     * @param fen   Portable Game Notation
     * @param board The <code>Board</code> instance to populate
     * @return A Board containing the state of the loaded game
     */
    public Board parse(String fen, MutableBoard board) {
        String[] lines = fen.split("[/ ]");
        for (int y = 0, length = lines.length; y < length && y < 8; y++) {
            String line = lines[y];
            int x = 0;
            for (char c : line.toCharArray()) {
                if (Character.isLetter(c)) {
                    Piece piece = getPiece(c);
                    if (piece != null) {
                        board.setPiece(x++, y, piece);
                    }
                } else if (Character.isDigit(c)) {
                    int steps = Character.getNumericValue(c);
                    x += steps;
                }
            }
        }

        return board;
    }
}
