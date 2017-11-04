package Pieces;

import Structures.Board;
import Structures.PieceColor;
import Structures.Square;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(final Square location, final PieceColor color, final Board board)
    {
        super(location,color,board);
    }

    @Override
    public int GetScore(boolean nextMoves) {
        return super.GetScore(nextMoves) + 800;
    }

    @Override
    public List<Square> GetNextMoves(boolean checkKing) {
        List<Square> nextMoves = new ArrayList<>(64);

        //TODO
//        if(Game::GetInstance()->GetTurn() != color)
//        return nextMoves;

        final byte x = _location.file;
        final byte y = _location.rank;

        for(int i=x+1; i<8; i++)
            if(AppendSquare(i,y,nextMoves,checkKing))
                break;

        for(int i=y+1; i<8; i++)
            if(AppendSquare(x,i,nextMoves,checkKing))
                break;

        for(int i=x-1; i>=0; i--)
            if(AppendSquare(i,y,nextMoves,checkKing))
                break;

        for(int i=y-1; i>=0; i--)
            if(AppendSquare(x,i,nextMoves,checkKing))
                break;

        for(int i=x+1, j=y+1; i<8 && j<8; i++,j++)
            if(AppendSquare(i,j,nextMoves,checkKing))
                break;

        for(int i=x+1, j=y-1; i<8 && j>=0; i++,j--)
            if(AppendSquare(i,j,nextMoves,checkKing))
                break;

        for(int i=x-1, j=y-1; i>=0 && j>=0; i--,j--)
            if(AppendSquare(i,j,nextMoves,checkKing))
                break;

        for(int i=x-1, j=y+1; i>=0 && j<8; i--,j++)
            if(AppendSquare(i,j,nextMoves,checkKing))
                break;

        return nextMoves;
    }
}
