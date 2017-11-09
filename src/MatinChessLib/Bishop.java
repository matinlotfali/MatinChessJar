package MatinChessLib;

import java.util.ArrayList;
import java.util.List;

import static MatinChessLib.PieceColor.White;

class Bishop extends Piece {

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

        if (MatinChess.GetInstance().GetTurn() != color)
            return nextMoves;

        final byte x = _location.file;
        final byte y = _location.rank;
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
