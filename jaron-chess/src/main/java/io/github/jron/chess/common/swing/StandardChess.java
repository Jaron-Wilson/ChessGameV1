package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.FenUtilities;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;
import io.github.jron.chess.common.piece.Piece;

import java.io.IOException;
import java.util.List;

public class StandardChess implements MoveListener {

    private Piece selectedPiece;
    private Position selectedPosition;

    private List<Position> canMoveToList;

    private StandardBoard board;
    private StandardBoardPanel boardPanel;

    public static void main(String[] args) throws IOException {

        StandardChess chess = new StandardChess();
        chess.board = (StandardBoard) new FenUtilities().parse(FenUtilities.STARTING_POSITION, new StandardBoard());
        chess.boardPanel = new StandardBoardPanel(chess.board);

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
                board.setPiece(selectedPosition, null);
                board.setPiece(coordanate.getX(), coordanate.getY(), selectedPiece);
                //Piece.Color turn = board.getTurn() == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
                //board.setTurn(turn);
                board.getTurn().increment();

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

    private void setStateOfTheGame(Piece piece, Position selectedCoordanate, List<Position> canMoveToList) {
        this.selectedPiece = piece;
        this.selectedPosition = selectedCoordanate;
        this.canMoveToList = canMoveToList;
        this.boardPanel.setCanMoveToList(selectedCoordanate, canMoveToList);
    }
}
