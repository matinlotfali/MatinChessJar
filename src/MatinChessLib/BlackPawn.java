package MatinChessLib;

import java.util.ArrayList;
import java.util.List;

import static MatinChessLib.PieceColor.Black;
import static MatinChessLib.PieceColor.White;

class BlackPawn extends Pawn {

    BlackPawn(final ChessSquare location, final Board board)
    {
        super(location, Black,board);
    }

    @Override
    char GetChar() {
        return 'P';
    }

    @Override
    int GetScore(boolean nextMoves) {
        return super.GetScore(nextMoves) +  _location.rank*20 + 80;
    }

    @Override
    List<ChessSquare> GetNextMoves(boolean checkKing) {
        List<ChessSquare> nextMoves = new ArrayList<>(4);

        if (MatinChess.GetInstance().GetTurn() != color)
            return nextMoves;

        byte x = _location.file;
        byte y = _location.rank;

        Piece piece;

        if(y+1 < 8)
        {
            piece = board.squares[x][y+1].piece;
            if(piece != null)
            {
                Append(board.squares[x][y+1],nextMoves,checkKing);

                if(y == 1)
                {
                    piece = board.squares[x][3].piece;
                    if(piece != null)
                        Append(board.squares[x][3],nextMoves,checkKing);
                }
            }

            if(x<7)
            {
                piece = board.squares[x+1][y+1].piece;
                if(piece != null && piece.color == White)
                    Append(piece.GetLocation(),nextMoves,checkKing);

                if(y == 4)
                {
                    piece = board.squares[x+1][4].piece;
                    if(piece != null && piece.color == White && piece instanceof Pawn)
                    {
                        if(piece.GetTwoStepMoveIndexOnBoard() == board.moveCount)
                        Append(board.squares[x+1][5],nextMoves,checkKing);
                    }
                }
            }

            if(x>0)
            {
                piece = board.squares[x-1][y+1].piece;
                if(piece != null && piece.color == White)
                    Append(piece.GetLocation(),nextMoves,checkKing);

                if(y == 4)
                {
                    piece = board.squares[x-1][4].piece;
                    if(piece != null && piece.color == White && piece instanceof Pawn)
                    {
                        if(piece.GetTwoStepMoveIndexOnBoard() == board.moveCount)
                        Append(board.squares[x-1][5],nextMoves,checkKing);
                    }
                }
            }
        }

        return nextMoves;
    }
}
