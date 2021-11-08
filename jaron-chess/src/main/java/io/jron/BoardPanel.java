package io.jron;

import io.jron.piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This panel draws the connect four board given a io.jron.Board
 */
public class BoardPanel extends JPanel {

    public static final int PIECE_SIZE = 75;
    private final Board board;
    private final Color[] COLORS = {Color.GRAY, Color.GRAY, Color.RED, Color.BLACK};
    protected Dimension defaultDimension;
    private List<Coordanate> CanMoveToList;
    private Coordanate selectedPiece = null;

    public BoardPanel(Board board) {
        this.board = board;
        this.defaultDimension = new Dimension(board.getWidth() * PIECE_SIZE - 5, board.getHeight() * PIECE_SIZE - 5);
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

                g.setColor(((x + (y % 2)) % 2 == 0) ? Color.GRAY : Color.WHITE.darker());

                g.fillRoundRect(x * PIECE_SIZE, y * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5, 3, 3);
                Piece piece = board.getPiece(x, y);

                if ((piece != null)) {
                    g.setColor(piece.getColor() == Piece.Color.BLACK ? Color.BLACK : Color.WHITE);
                    g.drawString(piece.getClass().getSimpleName(), x * PIECE_SIZE + PIECE_SIZE / 2, y * PIECE_SIZE + PIECE_SIZE / 2);
                }
            }
        }

        if (CanMoveToList != null) {
            g.setColor(Color.RED);
            for (Coordanate c : CanMoveToList) {
                g.drawRect(c.getX() * PIECE_SIZE, c.getY() * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5);
            }
        }
    }

    public void setCanMoveToList(Coordanate selectedPiece, List<Coordanate> canMoveToList) {
        this.selectedPiece = selectedPiece;
        CanMoveToList = canMoveToList;
    }

    public Coordanate getSelectedPiece() {
        return selectedPiece;
    }

    public List<Coordanate> getCanMoveToList() {
        return CanMoveToList;
    }
}