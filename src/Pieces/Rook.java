package Pieces;

import Structures.Board;
import Structures.PieceColor;
import Structures.Square;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(final Square location, final PieceColor color, final Board board)
    {
        super(location,color,board);
    }

    @Override
    public int GetScore(boolean nextMoves) {
        return super.GetScore(nextMoves) + 500;
    }

    @Override
    public List<Square> GetNextMoves(boolean checkKing) {
        List<Square> nextMoves = new ArrayList<Square>(16);

        //TODO
        //if(Game::GetInstance()->GetTurn() != color)
        //return nextMoves;

        byte x = _location.file;
        byte y = _location.rank;
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

        return nextMoves;
    }
}
