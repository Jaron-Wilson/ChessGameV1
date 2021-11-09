package io.jron;

import io.jron.piece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * This class uses a builder patter to launch a swing UI to
 * test your AI.
 *
 */
public class SwingSupport {

    private Piece.Color turn = Piece.Color.WHITE;

    private MutableBoard board;

    public static void main(String[] args) {
        new SwingSupport().run();
    }

//    /**
//     * You can pass in your own board. This allows you to:
//     * <ol>
//     *     <li>Set up the state of the board</li>
//     *     <li>To configure the size of the board</li>
//     *     <li>To configure other settings suck as the goal</li>
//     * </ol>
//     *
//     * @param board
//     * @return this to keep the builder going
//     */
//    public SwingSupport setBoard(MutableBoard board) {
//        this.board = board;
//        return this;
//    }


    /**
     * Launches the JFrame that contains the io.jron.BoardPanel to display the game.
     */
    public void run() {
        FenParser fen = new FenParser();
        if (board == null) {
            board = new MutableBoard();
            //Black
            fen.parse(FenParser.STARTING_POSITION, board);

            BoardPanel boardPanel = new BoardPanel(board);
            //board.addBoardChangedListener(coordinate -> SwingUtilities.invokeLater(() -> boardPanel.repaint()));

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
            frame.add(boardPanel, BorderLayout.CENTER);
            frame.add(statusPanel, BorderLayout.SOUTH);
            frame.pack();


            //Register a listener to capture when a io.jron.piece is to be played.
            boardPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    int x = e.getX() / BoardPanel.PIECE_SIZE;
                    int y = e.getY() / BoardPanel.PIECE_SIZE;

                    Coordanate selectedPiece = boardPanel.getSelectedPiece();
                    if (selectedPiece != null) {
                        System.out.println("MOVING A PIECE!");

                        Piece pieceToMove = board.getPiece(selectedPiece.getX(), selectedPiece.getY());
                        if (pieceToMove != null && pieceToMove.getColor() == turn) {
                            if (boardPanel.getCanMoveToList().contains(new Coordanate(x, y))) {
                                board.setPiece(selectedPiece, null);

                                Piece capture = board.getPiece(x, y);
                                if( capture!= null && capture.getColor() == turn
                                        && !(capture instanceof Combination) && !(pieceToMove instanceof Combination)) {
                                    board.setPiece(x, y, new Combination(pieceToMove,capture));
                                }
                                else {
                                    board.setPiece(x, y, pieceToMove);
                                }
                                turn = turn == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
                                status.setText( new FenParser().format(board));
                                boardPanel.setCanMoveToList(null, null);
                                new Thread(boardPanel::repaint).start();
                                return;
                            } else {
                                System.out.println("MORON!");
                                boardPanel.setCanMoveToList(null, null);
                                new Thread(boardPanel::repaint).start();
                                return;
                            }
                        } else {
                            System.out.println("Not your TURN DUMMY");
                        }
                    }

                    Piece p = board.getPiece(x, y);
                    if (p != null) {
                        Coordanate c = new Coordanate(x, y);
                        System.out.println(p.getColor() + " " + p.getClass().getSimpleName());
                        List<Coordanate> cordanateList = p.canMoveTo(board, c);
                        boardPanel.setCanMoveToList(c, cordanateList);
                    } else {
                        boardPanel.setCanMoveToList(null, null);
                    }

                    new Thread(boardPanel::repaint).start();

                }
            });

            frame.setVisible(true);
        }
    }
}