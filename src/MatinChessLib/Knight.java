package MatinChessLib;

import java.util.ArrayList;
import java.util.List;

import static MatinChessLib.PieceColor.White;

class Knight extends Piece {

    Knight(final ChessSquare location, final PieceColor color, final Board board)
    {
        super(location,color,board);
    }

    @Override
    char GetChar() {
        return color == White ? 'n' : 'N';
    }

    @Override
    int GetScore(final boolean nextMoves) {
        return super.GetScore(nextMoves) + 300;
    }

    @Override
    List<ChessSquare> GetNextMoves(final boolean checkKing) {
        final List<ChessSquare> nextMoves = new ArrayList<>(8);

        if (MatinChess.GetInstance().GetTurn() != color)
            return nextMoves;

        final byte x = _location.file;
        final byte y = _location.rank;

        if(x<7 && y<6)
            AppendSquare(x+1,y+2,nextMoves,checkKing);

        if(x<6 && y<7)
            AppendSquare(x+2,y+1,nextMoves,checkKing);

        if(x>0 && y<6)
            AppendSquare(x-1,y+2,nextMoves,checkKing);

        if(x<6 && y>0)
            AppendSquare(x+2,y-1,nextMoves,checkKing);

        if(x<7 && y>1)
            AppendSquare(x+1,y-2,nextMoves,checkKing);

        if(x>1 && y<7)
            AppendSquare(x-2,y+1,nextMoves,checkKing);

        if(x>0 && y>1)
            AppendSquare(x-1,y-2,nextMoves,checkKing);

        if(x>1 && y>0)
            AppendSquare(x-2,y-1,nextMoves,checkKing);

        return nextMoves;
    }
}
