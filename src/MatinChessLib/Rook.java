package MatinChessLib;

import java.util.ArrayList;
import java.util.List;

import static MatinChessLib.PieceColor.White;

class Rook extends Piece {
    Rook(final ChessSquare location, final PieceColor color, final Board board)
    {
        super(location,color,board);
    }

    @Override
    final char GetChar() {
        return color == White ? 'r' : 'R';
    }

    @Override
    final int GetScore(boolean nextMoves) {
        return super.GetScore(nextMoves) + 500;
    }

    @Override
    final List<ChessSquare> GetNextMoves(boolean checkKing) {
        final List<ChessSquare> nextMoves = new ArrayList<>(16);

        if (MatinChess.GetInstance().GetTurn() != color)
            return nextMoves;

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

        return nextMoves;
    }
}
