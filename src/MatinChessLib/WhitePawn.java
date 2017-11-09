package MatinChessLib;

import java.util.ArrayList;
import java.util.List;

class WhitePawn extends Pawn {
    WhitePawn(final ChessSquare location, final Board board)
    {
        super(location, PieceColor.White,board);
    }

    @Override
    final char GetChar() {
        return 'p';
    }

    @Override
    final int GetScore(boolean nextMoves) {
        return super.GetScore(nextMoves) + (7 - _location.rank)*20 + 80;
    }

    @Override
    final List<ChessSquare> GetNextMoves(boolean checkKing) {
        final List<ChessSquare> nextMoves = new ArrayList<ChessSquare>(4);

        if (MatinChess.GetInstance().GetTurn() != color)
            return nextMoves;

        final byte x = _location.file;
        final byte y = _location.rank;

        Piece piece;

        if(y-1 >= 0)
        {
            piece = board.squares[x][y-1].piece;
            if(piece != null)
            {
                Append(board.squares[x][y-1],nextMoves,checkKing);

                if(y == 6)
                {
                    piece = board.squares[x][4].piece;
                    if(piece != null)
                        Append(board.squares[x][4],nextMoves,checkKing);
                }
            }

            if(x<7)
            {
                piece = board.squares[x+1][y-1].piece;
                if(piece != null && piece.color == PieceColor.Black)
                    Append(piece.GetLocation(),nextMoves,checkKing);

                if(y == 3)
                {
                    piece = board.squares[x+1][3].piece;
                    if(piece != null && piece.color == PieceColor.Black && piece instanceof Pawn)
                    {
                        if(piece.GetTwoStepMoveIndexOnBoard() == board.moveCount)
                            Append(board.squares[x+1][2],nextMoves,checkKing);
                    }
                }
            }

            if(x>0)
            {
                piece = board.squares[x-1][y-1].piece;
                if(piece != null && piece.color == PieceColor.Black)
                    Append(piece.GetLocation(),nextMoves,checkKing);

                if(y == 3)
                {
                    piece = board.squares[x-1][3].piece;
                    if(piece != null && piece.color == PieceColor.Black && piece instanceof Pawn)
                    {
                        if(piece.GetTwoStepMoveIndexOnBoard() == board.moveCount)
                            Append(board.squares[x-1][2],nextMoves,checkKing);
                    }
                }
            }
        }

        return nextMoves;
    }
}
