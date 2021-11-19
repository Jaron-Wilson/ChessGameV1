package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.FenParser;
import io.github.jron.chess.common.StandardBoard;
import io.github.jron.chess.common.piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class uses a builder patter to launch a swing UI to
 * test your AI.
 */
public class SwingSupport {

    private Piece.Color turn = Piece.Color.WHITE;

    private StandardBoard board;
    private BoardPanel boardPanel;
    private MoveListener moveListener;

    /**
     * You can pass in your own board. This allows you to:
     * <ol>
     *     <li>Set up the state of the board</li>
     *     <li>To configure the size of the board</li>
     *     <li>To configure other settings suck as the goal</li>
     * </ol>
     *
     * @param board
     * @return this to keep the builder going
     */
    public SwingSupport setBoard(StandardBoard board) {
        this.board = board;
        return this;
    }

    public SwingSupport setBoardPanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
        return this;
    }

    public SwingSupport setMoveListener(MoveListener moveListener) {
        this.moveListener = moveListener;
        return this;
    }

    /**
     * Launches the JFrame that contains the io.jron.BoardPanel to display the game.
     */
    public void run() {
        FenParser fen = new FenParser();

        JPanel statusPanel = new JPanel(new BorderLayout());
        final JTextField status = new JTextField(fen.format(board));
        status.setEditable(false);
        statusPanel.add(new JLabel("FEN: "), BorderLayout.WEST);
        statusPanel.add(status, BorderLayout.CENTER);
        JButton undo = new JButton("<");
        //undo.addActionListener((ActionEvent e) -> SwingUtilities.invokeLater(() -> board.undo()));
        statusPanel.add(undo, BorderLayout.EAST);

        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        frame.add(boardPanel.getComponent(), BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.pack();

        StandardBoardPanel boardPanel = (StandardBoardPanel) this.boardPanel;

        /* Register a listener to capture when a piece is to be played. */
        boardPanel.getComponent().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                moveListener.selected(boardPanel.convert(e));
                SwingUtilities.invokeLater(() -> {
                    status.setText(fen.format(board));
                    boardPanel.repaint();
                });
            }
        });

        frame.setVisible(true);
    }
}