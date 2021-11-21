package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Coordinate;
import io.github.jron.chess.common.piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

/**
 * This panel draws the connect four board given a io.jron.Board
 */
public class StandardBoardPanel extends JPanel implements BoardPanel {

    public static final int PIECE_SIZE = 70;
    private final Board board;
    private final Color[] COLORS = {Color.GRAY, Color.GRAY, Color.RED, Color.BLACK};
    protected Dimension defaultDimension;
    private List<Coordinate> CanMoveToList;
    private Coordinate selectedPiece = null;

    private ImageFactory images;

    public StandardBoardPanel(Board board) throws IOException {
        this(board, new StandardImageFactory());
    }

    public StandardBoardPanel(Board board, ImageFactory imageFactory) {
        this.setOpaque(false);
        this.board = board;
        this.defaultDimension = new Dimension(board.getWidth() * PIECE_SIZE - 5, board.getHeight() * PIECE_SIZE - 5);
        this.images = imageFactory;
    }


    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draw the pieces on the board
        for (int x = 0, width = board.getWidth(); x < width; x++) {
            for (int y = 0, height = board.getHeight(); y < height; y++) {
                g.setColor(((x + (y % 2)) % 2 == 0) ? Color.WHITE.darker() : Color.GRAY);
                g.fillRoundRect(x * PIECE_SIZE, y * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5, 3, 3);
                Piece piece = board.getPiece(x, y);
                if ((piece != null)) {
                    g.drawImage(images.getImage(piece, this), x * PIECE_SIZE, y * PIECE_SIZE, this);
                }
            }
        }

        if (CanMoveToList != null) {
            g.setColor(Color.RED);
            for (Coordinate c : CanMoveToList) {
                g.drawRect(c.getX() * PIECE_SIZE, c.getY() * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5);
            }
        }
    }

    public void setCanMoveToList(Coordinate selectedPiece, List<Coordinate> canMoveToList) {
        this.selectedPiece = selectedPiece;
        CanMoveToList = canMoveToList;
    }

    public Coordinate getSelectedPiece() {
        return selectedPiece;
    }

    public List<Coordinate> getCanMoveToList() {
        return CanMoveToList;
    }

    @Override
    public Coordinate convert(MouseEvent e) {
        int x = e.getX() / StandardBoardPanel.PIECE_SIZE;
        int y = e.getY() / StandardBoardPanel.PIECE_SIZE;
        return new Coordinate(x, y);
    }

    @Override
    public Component getComponent() {
        return this;
    }
}