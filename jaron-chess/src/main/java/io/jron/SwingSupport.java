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
 * @see
 */
public class SwingSupport {

    private Piece.Color turn = Piece.Color.WHITE;

    private MutableBoard board;

    public static void main(String[] args) {
        new SwingSupport().run();
    }

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
    public SwingSupport setBoard(MutableBoard board) {
        this.board = board;
        return this;
    }


    /**
     * Launches the JFrame that contains the io.jron.BoardPanel to display the game.
     */
    public void run() {
        if (board == null) {
            board = new MutableBoard();
            //Black

            board.setPiece(0, 1, new Pawn(Piece.Color.BLACK));
            board.setPiece(1, 1, new Pawn(Piece.Color.BLACK));
            board.setPiece(2, 1, new Pawn(Piece.Color.BLACK));
            board.setPiece(3, 1, new Pawn(Piece.Color.BLACK));
            board.setPiece(4, 1, new Pawn(Piece.Color.BLACK));
            board.setPiece(5, 1, new Pawn(Piece.Color.BLACK));
            board.setPiece(6, 1, new Pawn(Piece.Color.BLACK));
            board.setPiece(7, 1, new Pawn(Piece.Color.BLACK)); // Pawns

            board.setPiece(0, 0, new Rook(Piece.Color.BLACK));
            board.setPiece(1, 0, new Knight(Piece.Color.BLACK));
            board.setPiece(2, 0, new Bishop(Piece.Color.BLACK));
            board.setPiece(3, 0, new King(Piece.Color.BLACK));
            board.setPiece(4, 0, new Queen(Piece.Color.BLACK));
            board.setPiece(5, 0, new Bishop(Piece.Color.BLACK));
            board.setPiece(6, 0, new Knight(Piece.Color.BLACK));
            board.setPiece(7, 0, new Rook(Piece.Color.BLACK));

            board.setPiece(0, 6, new Pawn(Piece.Color.WHITE));
            board.setPiece(1, 6, new Pawn(Piece.Color.WHITE));
            board.setPiece(2, 6, new Pawn(Piece.Color.WHITE));
            board.setPiece(3, 6, new Pawn(Piece.Color.WHITE));
            board.setPiece(4, 6, new Pawn(Piece.Color.WHITE));
            board.setPiece(5, 6, new Pawn(Piece.Color.WHITE));
            board.setPiece(6, 6, new Pawn(Piece.Color.WHITE));
            board.setPiece(7, 6, new Pawn(Piece.Color.WHITE));

            board.setPiece(0, 7, new Rook(Piece.Color.WHITE));
            board.setPiece(1, 7, new Knight(Piece.Color.WHITE));
            board.setPiece(2, 7, new Bishop(Piece.Color.WHITE));
            board.setPiece(3, 7, new King(Piece.Color.WHITE));
            board.setPiece(4, 7, new Queen(Piece.Color.WHITE));
            board.setPiece(5, 7, new Bishop(Piece.Color.WHITE));
            board.setPiece(3, 2, new Knight(Piece.Color.WHITE));
            board.setPiece(7, 7, new Rook(Piece.Color.WHITE));

            BoardPanel boardPanel = new BoardPanel(board);

            //Register a listener to capture when a io.jron.piece is to be played.
            boardPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    int x = e.getX() / BoardPanel.PIECE_SIZE;
                    int y = e.getY() / BoardPanel.PIECE_SIZE;

                    Coordanate selectedPiece = boardPanel.getSelectedPiece();
                    if(selectedPiece != null){
                        System.out.println("MOVING A PIECE!");

                        Piece pieceToMove = board.getPiece(selectedPiece.getX(), selectedPiece.getY());
                        if( pieceToMove != null && pieceToMove.getColor() == turn) {
                            if (boardPanel.getCanMoveToList().contains(new Coordanate(x, y))) {
                                board.setPiece(selectedPiece, null);
                                board.setPiece(x, y, pieceToMove);
                                turn = turn == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
                                boardPanel.setCanMoveToList(null, null);
                                new Thread(() -> boardPanel.repaint()).start();
                                return;
                            } else {
                                System.out.println("MORON!");
                                boardPanel.setCanMoveToList(null, null);
                                new Thread(() -> boardPanel.repaint()).start();
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
                        boardPanel.setCanMoveToList(null,null);
                    }

                    new Thread(() -> boardPanel.repaint()).start();

                }
            });

            //board.addBoardChangedListener(coordinate -> SwingUtilities.invokeLater(() -> boardPanel.repaint()));

            JPanel statusPanel = new JPanel(new BorderLayout());
            JTextField status = new JTextField("");
            status.setEditable(false);
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
            frame.setVisible(true);
        }
    }
}