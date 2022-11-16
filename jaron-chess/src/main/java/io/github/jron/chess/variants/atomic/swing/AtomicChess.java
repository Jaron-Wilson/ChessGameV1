package io.github.jron.chess.variants.atomic.swing;

import io.github.jron.chess.common.FenUtilities;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;
import io.github.jron.chess.common.piece.Pawn;
import io.github.jron.chess.common.piece.Piece;
import io.github.jron.chess.common.swing.StandardBoardPanel;
import io.github.jron.chess.common.swing.StandardChess;
import io.github.jron.chess.common.swing.SwingSupport;

import java.io.IOException;

public class AtomicChess extends StandardChess {

    public static void main(String[] args) throws IOException {

        Piece.CAN_CAPTURE_OWN = false;

        AtomicChess chess = new AtomicChess();
        chess.board = (StandardBoard) new FenUtilities().parse(FenUtilities.STARTING_POSITION, new StandardBoard());
        chess.boardPanel = new StandardBoardPanel(chess.board);

        new SwingSupport()
                .setBoard(chess.board)
                .setBoardPanel(chess.boardPanel)
                .setMoveListener(chess)
                .run();
    }

    @Override
    public void selected(Position c) {


        if (selectedPiece != null && selectedPiece.getColor() == board.getTurn().get()) {
            if (canMoveToList != null && canMoveToList.contains(c)) {

                Piece captured = board.getPiece(c);
                boolean success = selectedPiece.move(board,selectedPosition, c);

                if (success && captured != null){

                    for (int x=0,y=0,dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            x = c.getX()-dx;
                            y = c.getY()-dy;
                            //TODO: FINISH HERE
                            if (x >= 0 && y >= 0 && x < board.getWidth() && y < board.getHeight()) {
                                Piece p = board.getPiece(x, y);
                                if (p != null && !(p instanceof Pawn)) {
//                                    System.out.println(board.removePiece(x, y) + " -1");

                                    board.removePiece(x, y);
                                }
                            }

                        }
                    }
//                    p = board.getPiece(c.getX()+1, c.getY());
//                    if(p!=null && !(p instanceof Pawn)){
//                        System.out.println(board.removePiece(c.getX()+1, c.getY()) + " +1");
//                        board.removePiece(c.getX()+1, c.getY());
//                    }
                    board.removePiece(c.getX(), c.getY());
                    System.out.println( board.removePiece(c.getX(), c.getY()) + " whats this");
                }else if (selectedPiece instanceof Pawn && (c.getY() == 0 || c.getY() == 7)) {
                    Piece promoted = promote(selectedPiece);
                    board.setPiece(c, promoted);
                }
                if (success) {
//                Change the persons turn
                    board.getTurn().increment();
                    setStateOfTheGame(null, null, null);

                }
                return;
            } else {

                System.out.println("Cant move here!");
            }
        } else {
            System.out.println("Not your TURN DUMMY " + board.getTurn().get());
        }

        Piece clickedPiece = board.getPiece(c.getX(), c.getY());
        if (clickedPiece != null) {
            //this.selectedPiece = c;
            System.out.println(clickedPiece.getColor() + " " + clickedPiece.getClass().getSimpleName());
            setStateOfTheGame(clickedPiece, c, clickedPiece.canMoveTo(board, c));
            return;
        }

        setStateOfTheGame(null, null, null);
    }
}
