package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;
import io.github.jron.chess.common.piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * This panel draws the connect four board given a io.jron.Board
 */
public class StandardBoardPanel extends JPanel implements BoardPanel {

    public static final int PIECE_SIZE = 70;
    private final Board board;
    private final ImageFactory images;
    protected Dimension defaultDimension;
    private List<Position> CanMoveToList;
    private Position selectedPiece = null;

    public StandardBoardPanel(Board board) throws IOException {
        this(board, new StandardImageFactory());
    }

    public StandardBoardPanel(Board board, ImageFactory imageFactory) {
        setOpaque(false);
        this.board = board;
        images = imageFactory;
        defaultDimension = new Dimension(board.getWidth() * PIECE_SIZE - 5, board.getHeight() * PIECE_SIZE - 5);
    }


    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Set<Position> positions = ((StandardBoard)board).getThreatenedPositions(board.getTurn().get());

        //Draw the pieces on the board
        for (int x = 0, width = board.getWidth(); x < width; x++) {
            for (int y = 0, height = board.getHeight(); y < height; y++) {
                g.setColor(((x + (y % 2)) % 2 == 0) ? Color.WHITE.darker() : Color.GRAY);

//                if(positions.contains(new Position(x,y))) {
//                    if (board.getTurn().get() == io.github.jron.chess.common.piece.Color.BLACK) {
//                        g.setColor(Color.GRAY.brighter().brighter());
//                    }else {
//                        g.setColor(Color.GRAY.darker().darker());
//                    }
//                }

                g.fillRoundRect(x * PIECE_SIZE, y * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5, 3, 3);
                Piece piece = board.getPiece(x, y);
                if ((piece != null)) {
                    g.drawImage(images.getImage(piece, this), x * PIECE_SIZE, y * PIECE_SIZE, this);
                }
            }
        }

        if (CanMoveToList != null) {
            g.setColor(Color.RED);
            for (Position c : CanMoveToList) {
                g.drawRect(c.getX() * PIECE_SIZE, c.getY() * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5);
            }

            g.setColor(Color.GREEN);
            Position c = board.getEligibleEnPassant();
            if (c != null) {
                g.drawRect(c.getX() * PIECE_SIZE, c.getY() * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5);
            }
        }

    }

    public void setCanMoveToList(Position selectedPiece, List<Position> canMoveToList) {
        this.selectedPiece = selectedPiece;
        CanMoveToList = canMoveToList;
    }

    public Position getSelectedPiece() {
        return selectedPiece;
    }

    public List<Position> getCanMoveToList() {
        return CanMoveToList;
    }

    @Override
    public Position convert(MouseEvent e) {
        int x = e.getX() / StandardBoardPanel.PIECE_SIZE;
        int y = e.getY() / StandardBoardPanel.PIECE_SIZE;
        return new Position(x, y);
    }

    @Override
    public Component getComponent() {
        return this;
    }
}