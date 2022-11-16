package io.github.jron.chess.common.piece;

import io.github.jron.chess.common.Board;
import io.github.jron.chess.common.Position;
import io.github.jron.chess.common.StandardBoard;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    private boolean canCastleKingSide = true, isCanCastleQueenSide = true;

    public King(Color color) {
        super(color);
    }

    @Override
    public List<Position> canMoveTo(Board board, Position current) {
        List<Position> moves = new ArrayList<>(8);

        //dx = delta x
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int x = current.getX() + dx;
                int y = current.getY() + dy;
                if (x >= 0 && y >= 0 && x < 8 && y < 8) {
                    addIfEmptyAndMoreThanThat(moves, board, x, y);
                }
            }
        }

//        Castle
        int x = current.getX(), y = current.getY();
        int numberOfEmptySpacesLeft = 0, numberOfEmptySpacesRight = 0;
        for(int left=x-1, right=x+1; left>=0||right<8; left--, right++){
            if( left >= 0 && board.getPiece(left, y) == null ) numberOfEmptySpacesLeft++;
            if( right < 8 && board.getPiece(right, y) == null ) numberOfEmptySpacesRight++;
        }

        if( getColor().equals(Color.WHITE) ){
            if( canCastleQueenSide() && numberOfEmptySpacesLeft == 3 ){
                addIfEmptyAndMoreThanThat(moves, board, x-2, y);
            }
            if( canCastleKingSide() && numberOfEmptySpacesRight == 2){
                addIfEmptyAndMoreThanThat(moves, board, x+2, y);
            }
        }

        if( getColor().equals(Color.BLACK) ){
            if( canCastleQueenSide() && numberOfEmptySpacesLeft == 3 ){
                addIfEmptyAndMoreThanThat(moves, board, x-2, y);
            }
            if( canCastleKingSide() && numberOfEmptySpacesRight == 2){
                addIfEmptyAndMoreThanThat(moves, board, x+2, y);
            }
        }
//        End of castling

        return moves;
    }

    @Override
    public int getPieceId() {
        return 1;
    }

    public boolean canCastleKingSide() {
        return canCastleKingSide;
    }

    public boolean canCastleQueenSide() {
        return isCanCastleQueenSide;
    }

    public void setCanCastleKingSide(boolean canCastleKingSide) {
        this.canCastleKingSide = canCastleKingSide;
    }

    public void setCanCastleQueenSide(boolean canCastleQueenSide) {
        isCanCastleQueenSide = canCastleQueenSide;
    }

    public boolean move(StandardBoard board, Position oldPosition, Position newPosition) {
        canCastleKingSide = isCanCastleQueenSide = false;
        board.getPiece(newPosition);
        boolean success = super.move(board, oldPosition, newPosition);

        if ( oldPosition.getX() - newPosition.getX() == 2 ) {
            Piece rook = board.removePiece(0, newPosition.getY());
            board.setPiece( newPosition.getX()+1, newPosition.getY(), rook);
        }
        if ( oldPosition.getX() - newPosition.getX() == -2 ) {
            Piece rook = board.removePiece(7, newPosition.getY());
            board.setPiece( newPosition.getX()-1, newPosition.getY(), rook);
        }

        return success;
    }

}