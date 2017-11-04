package Pieces;

import Structures.Board;
import Structures.PieceColor;
import Structures.Square;

import java.util.ArrayList;
import java.util.List;

public class WhitePawn extends Pawn {
    public WhitePawn(final Square location, final Board board)
    {
        super(location,PieceColor.White,board);
    }

    @Override
    public int GetScore(boolean nextMoves) {
        return super.GetScore(nextMoves) + (7 - _location.rank)*20 + 80;
    }

    @Override
    public List<Square> GetNextMoves(boolean checkKing) {
        List<Square> nextMoves = new ArrayList<Square>(4);

        //TODO
        //if(Game::GetInstance()->GetTurn() != color)
        //return nextMoves;

        byte x = _location.file;
        byte y = _location.rank;

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
