package io.github.jron.chess.variants.plus.swing;

import io.github.jron.chess.common.Coordinate;
import io.github.jron.chess.common.FenParser;
import io.github.jron.chess.common.StandardBoard;
import io.github.jron.chess.common.piece.Piece;
import io.github.jron.chess.common.swing.MoveListener;
import io.github.jron.chess.common.swing.StandardBoardPanel;
import io.github.jron.chess.common.swing.SwingSupport;
import io.github.jron.chess.variants.plus.Combination;

import java.io.IOException;
import java.util.List;

public class ChessPlus implements MoveListener {

    private Piece.Color turn = Piece.Color.WHITE;
    private Piece selectedPiece;
    private Coordinate selectedCoordanate;

    private List<Coordinate> canMoveToList;

    private StandardBoard board;
    private StandardBoardPanel boardPanel;

    public static void main(String[] args) throws IOException {

        Piece.CAN_CAPTURE_OWN = true;

        ChessPlus chess = new ChessPlus();
        chess.board = (StandardBoard) new FenParser().parse(FenParser.STARTING_POSITION, new StandardBoard());
        chess.boardPanel = new StandardBoardPanel(chess.board, new CombinationImageFactory());

        new SwingSupport()
                .setBoard(chess.board)
                .setBoardPanel(chess.boardPanel)
                .setMoveListener(chess)
                .run();
    }

    @Override
    public void selected(Coordinate coordanate) {

        if (selectedPiece != null && selectedPiece.getColor() == turn) {

            if (canMoveToList != null && canMoveToList.contains(coordanate)) {
                board.setPiece(selectedCoordanate, null);

                Piece captured = board.getPiece(coordanate.getX(), coordanate.getY());

                if (captured != null && captured.getColor() == turn
                        && !(captured instanceof Combination) && !(selectedPiece instanceof Combination)) {
                    board.setPiece(coordanate.getX(), coordanate.getY(), new Combination(selectedPiece, captured));
                } else {
                    board.setPiece(coordanate.getX(), coordanate.getY(), selectedPiece);
                }

                turn = turn == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;

                setSelected(null, null, null);
                return;
            } else {
                System.out.println("MORON!");
            }
        } else {
            System.out.println("Not your TURN DUMMY");
        }

        Piece clickedPiece = board.getPiece(coordanate.getX(), coordanate.getY());
        if (clickedPiece != null) {
            //this.selectedPiece = coordanate;
            System.out.println(clickedPiece.getColor() + " " + clickedPiece.getClass().getSimpleName());
            setSelected(clickedPiece, coordanate, clickedPiece.canMoveTo(board, coordanate));
            return;
        }

        setSelected(null, null, null);
    }

    private void setSelected(Piece piece, Coordinate selectedCoordanate, List<Coordinate> canMoveToList) {
        this.selectedPiece = piece;
        this.selectedCoordanate = selectedCoordanate;
        this.canMoveToList = canMoveToList;
        this.boardPanel.setCanMoveToList(selectedCoordanate, canMoveToList);
    }
}
