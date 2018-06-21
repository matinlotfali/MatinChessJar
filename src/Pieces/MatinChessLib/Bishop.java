package MatinChessLib;

import java.util.ArrayList;
import java.util.List;

import static MatinChessLib.PieceColor.White;

class Bishop extends Piece {

//hello this is Abhipsa
    Bishop(final ChessSquare location, final PieceColor color, final Board board) {
        super(location, color, board);
    }

    @Override
    char GetChar() {
        return color == White ? 'b' : 'B';
    }

    @Override
    int GetScore(boolean nextMoves) {
        return super.GetScore(nextMoves) + 300;
    }

    @Override
    List<ChessSquare> GetNextMoves(boolean checkKing) {
        List<ChessSquare> nextMoves = new ArrayList<>(64);

        //final byte x = GetLocation().file;
        //final byte y = GetLocation().rank;
	//I did not like them at all
        for (int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++)
            if (AppendSquare(i, j, nextMoves, checkKing))
                break;

        for (int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--)
            if (AppendSquare(i, j, nextMoves, checkKing))
                break;

        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--)
            if (AppendSquare(i, j, nextMoves, checkKing))
                break;

        for (int i = x - 1, j = y + 1; i >= 0 && j < 8; i--, j++)
            if (AppendSquare(i, j, nextMoves, checkKing))
                break;
        return nextMoves;
    }
}
