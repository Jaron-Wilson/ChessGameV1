package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.Coordanate;
import io.github.jron.chess.common.FenParser;
import io.github.jron.chess.common.StandardBoard;
import io.github.jron.chess.common.piece.Piece;

import java.io.IOException;
import java.util.List;

public class StandardChess implements MoveListener {

    private Piece.Color turn = Piece.Color.WHITE;
    private Piece selectedPiece;
    private Coordanate selectedCoordanate;

    private List<Coordanate> canMoveToList;

    private StandardBoard board;
    private StandardBoardPanel boardPanel;

    public static void main(String[] args) throws IOException {

        StandardChess chess = new StandardChess();
        chess.board = (StandardBoard) new FenParser().parse(FenParser.STARTING_POSITION, new StandardBoard());
        chess.boardPanel = new StandardBoardPanel(chess.board);

        new SwingSupport()
                .setBoard(chess.board)
                .setBoardPanel(chess.boardPanel)
                .setMoveListener(chess)
                .run();
    }

    @Override
    public void selected(Coordanate coordanate) {

        if (selectedPiece != null && selectedPiece.getColor() == turn) {
            if (canMoveToList != null && canMoveToList.contains(coordanate)) {
                board.setPiece(selectedCoordanate, null);
                board.setPiece(coordanate.getX(), coordanate.getY(), selectedPiece);
                turn = turn == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;

                setStateOfTheGame(null, null, null);
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
            setStateOfTheGame(clickedPiece, coordanate, clickedPiece.canMoveTo(board, coordanate));
            return;
        }

        setStateOfTheGame(null, null, null);
    }

    private void setStateOfTheGame(Piece piece, Coordanate selectedCoordanate, List<Coordanate> canMoveToList) {
        this.selectedPiece = piece;
        this.selectedCoordanate = selectedCoordanate;
        this.canMoveToList = canMoveToList;
        this.boardPanel.setCanMoveToList(selectedCoordanate, canMoveToList);
    }
}
