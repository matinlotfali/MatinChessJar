package MatinChessLib;

import java.util.ArrayList;
import java.util.List;

import static MatinChessLib.PieceColor.White;

class Queen extends Piece {

    Queen(final ChessSquare location, final PieceColor color, final Board board)
    {
        super(location,color,board);
    }

    @Override
    final char GetChar() {
        return color == White ? 'q' : 'Q';
    }

    @Override
    final int GetScore(boolean nextMoves) {
        return super.GetScore(nextMoves) + 800;
    }

    @Override
    final List<ChessSquare> GetNextMoves(boolean checkKing) {
        final List<ChessSquare> nextMoves = new ArrayList<>(64);

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
