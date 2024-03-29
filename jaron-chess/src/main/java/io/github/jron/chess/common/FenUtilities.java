package io.github.jron.chess.common;

import io.github.jron.chess.common.piece.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
public class FenUtilities<B extends StandardBoard> {

    // Lowercase is BLACK, upper case is WHITE
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
            case 'r' -> new Rook(Color.BLACK);
            case 'n' -> new Knight(Color.BLACK);
            case 'q' -> new Queen(Color.BLACK);
            case 'k' -> new King(Color.BLACK);
            case 'p' -> new Pawn(Color.BLACK);
            case 'b' -> new Bishop(Color.BLACK);
            case 'R' -> new Rook(Color.WHITE);
            case 'N' -> new Knight(Color.WHITE);
            case 'Q' -> new Queen(Color.WHITE);
            case 'K' -> new King(Color.WHITE);
            case 'P' -> new Pawn(Color.WHITE);
            case 'B' -> new Bishop(Color.WHITE);
            default -> null;
        };
    }

    public static String getNotation(Piece p) {
        return switch (p.getClass().getSimpleName()) {
            case "Rook" -> p.getColor() == Color.WHITE ? "R" : "r";
            case "Knight" -> p.getColor() == Color.WHITE ? "N" : "n";
            case "Bishop" -> p.getColor() == Color.WHITE ? "B" : "b";
            case "King" -> p.getColor() == Color.WHITE ? "K" : "k";
            case "Queen" -> p.getColor() == Color.WHITE ? "Q" : "q";
            case "Pawn" -> p.getColor() == Color.WHITE ? "P" : "p";
            default -> "?";
        };
    }

    public static String getNotation(Color color) {
        return switch (color) {
            case WHITE -> "w";
            case BLACK -> "b";
        };
    }

    public static Color getColor(String c) {
        return switch (c) {
            case "w" -> Color.WHITE;
            case "b" -> Color.BLACK;
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
        return parse(fen, (B) new StandardBoard());
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
    public B parse(String fen, B board) {
        int step = 0;
        String[] sections = fen.trim().split("[/ ]");

        //Parse Piece placement
        for (int length = sections.length; step < length && step < 8; step++) {
            String line = sections[step];
            int x = 0;
            for (char c : line.toCharArray()) {
                if (Character.isLetter(c)) {
                    Piece piece = getPiece(c);
                    if (piece != null) {
                        board.setPiece(x++, step, piece);
                    }
                } else if (Character.isDigit(c)) {
                    int steps = Character.getNumericValue(c);
                    x += steps;
                }
            }
        }

        //Parse Active color
        String color = sections[step++];
        Color turn = getColor(color);
        while (!board.getTurn().get().equals(turn)) {
            board.getTurn().increment();
        }

        //Parse Castling availability
        String castlingString = sections[step++];

        Map<Color, King> kings = board.getAllPieces()
                .filter(King.class::isInstance)
                .map(King.class::cast).collect(Collectors.toMap(Piece::getColor, Function.identity()));

        King white = kings.get(Color.WHITE);
        if (white != null) {
            white.setCanCastleQueenSide(false);
            white.setCanCastleKingSide(false);
        }

        King black = kings.get(Color.BLACK);
        if (black != null) {
            black.setCanCastleQueenSide(false);
            black.setCanCastleKingSide(false);
        }

        if (!castlingString.equals("-")) {
            for (char c : castlingString.toCharArray()) {
                switch (c) {
                    case 'K':
                        if (white != null) white.setCanCastleKingSide(true);
                        break;
                    case 'Q':
                        if (white != null) white.setCanCastleQueenSide(true);
                        break;
                    case 'k':
                        if (black != null) black.setCanCastleKingSide(true);
                        break;
                    case 'q':
                        if (black != null) black.setCanCastleQueenSide(true);
                        break;
                }
            }
        }

        //Parse En passant
        String enPassant = sections[step++];

        //Parse Halfmove clock
        String halfClock = sections[step++];

        //Parse Fullmove number
        String fullMove = sections[step];

        return board;
    }

    public String format(Board board) {

        StringBuilder buffer = new StringBuilder();

        for (int y = 0, spacing = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece p = board.getPiece(x, y);
                if (p != null) {
                    if (spacing > 0) {
                        buffer.append(spacing);
                        spacing = 0;
                    }
                    buffer.append(getNotation(p));
                } else {
                    spacing++;
                }
            }

            if (spacing > 0) {
                buffer.append(spacing);
                spacing = 0;
            }

            if( y<7) {
                buffer.append("/");
            }
        }

        //Format Active color
        buffer.append(" ").append(getNotation(board.getTurn().get()));

        //Format Castling availability
        Map<Color, King> kings = board.getAllPieces()
                .filter(King.class::isInstance)
                .map(King.class::cast).collect(Collectors.toMap(Piece::getColor, Function.identity()));

        StringBuilder castling = new StringBuilder();
        King white = kings.get(Color.WHITE);
        if( white != null) {
            if( white.canCastleKingSide() ) castling.append("K");
            if( white.canCastleQueenSide() ) castling.append("Q");
        }
        King black = kings.get(Color.BLACK);
        if( black != null) {
            if( black.canCastleKingSide() ) castling.append("k");
            if( black.canCastleQueenSide() ) castling.append("q");
        }
        buffer.append(" ").append( castling.length() > 0 ? castling.toString() : "-");

        //todo: Finish the formatting
        buffer.append( " - 0 1");

        return buffer.toString();
    }
}
