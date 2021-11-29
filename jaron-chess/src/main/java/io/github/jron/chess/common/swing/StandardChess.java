package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.FenUtilities;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;
import io.github.jron.chess.common.piece.*;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StandardChess implements MoveListener {

    private Piece selectedPiece;
    private Position selectedPosition;
    public ImageFactory imageFactory;

    private List<Position> canMoveToList;

    private StandardBoard board;
    private StandardBoardPanel boardPanel;

    public static void main(String[] args) throws IOException {

        StandardChess chess = new StandardChess();
        chess.board = new FenUtilities().parse(FenUtilities.STARTING_POSITION, new StandardBoard());

        chess.board = new FenUtilities().parse("r3k2r/pppPPPpp/8/8/8/8/PPPpppPP/R3K2R w KQkq - 0 1", new StandardBoard());


        chess.boardPanel = new StandardBoardPanel(chess.board, chess.imageFactory = new StandardImageFactory());

        new SwingSupport()
                .setBoard(chess.board)
                .setBoardPanel(chess.boardPanel)
                .setMoveListener(chess)
                .run();
    }

    @Override
    public void selected(Position coordanate) {

        if (selectedPiece != null && selectedPiece.getColor() == board.getTurn().get()) {
            if (canMoveToList != null && canMoveToList.contains(coordanate)) {
                Piece captured = selectedPiece.move(board,selectedPosition, coordanate );
                if (selectedPiece instanceof Pawn && (coordanate.getY() == 0 || coordanate.getY() == 7)) {
                  Piece promoted = promote(selectedPiece);
                  board.setPiece(coordanate, promoted);
                }
                board.getTurn().increment();
                setStateOfTheGame(null, null, null);
                return;
            } else {
                System.out.println("MORON!");
            }
        } else {
            System.out.println("Not your TURN DUMMY " + board.getTurn().get());
        }

        Piece clickedPiece = board.getPiece(coordanate.getX(), coordanate.getY());
        if (clickedPiece != null) {
            //this.selectedPiece = coordanate;
            System.out.println(clickedPiece.getColor() + " " + clickedPiece.getClass().getSimpleName());
            setStateOfTheGame(clickedPiece, coordanate, clickedPiece.canMoveTo(board, coordanate));
            return;
        }

        setStateOfTheGame(null, null, null);
    }

    private Piece promote(Piece pawn) {

        Piece[] pieces = new Piece[]{
                new Queen(board.getTurn().get()),
                new Rook(board.getTurn().get()),
                new Bishop(board.getTurn().get()),
                new Knight(board.getTurn().get())
        };
//          create a array of icons
        Icon[] options = Arrays.stream(pieces).map(p -> new ImageIcon(imageFactory.getImage(p, boardPanel))).toArray(Icon[]::new);

        int n = JOptionPane.showOptionDialog(boardPanel,
                "Promote pawn to?",
                "Promote pawn to...",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon(imageFactory.getImage(pawn, boardPanel)),
                options,
        options[0]);

        return pieces[n];
    }

    private void setStateOfTheGame(Piece piece, Position selectedCoordanate, List<Position> canMoveToList) {
        this.selectedPiece = piece;
        this.selectedPosition = selectedCoordanate;
        this.canMoveToList = canMoveToList;
        this.boardPanel.setCanMoveToList(selectedCoordanate, canMoveToList);
    }
}
