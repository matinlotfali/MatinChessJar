package MatinChessLib;

import java.util.List;
import java.util.Stack;

abstract class Pawn extends Piece {

    private ChessSquare deletedPiece;
    private boolean hasDeletedPiece;
    boolean GetHasDeletedPiece() {return hasDeletedPiece;}

    private Stack<Piece> deletedPieces = new Stack<>();
    ChessSquare GetDeletedPiece() {return deletedPiece;}

    Pawn(final ChessSquare location, final PieceColor color, final Board board)
    {
        super(location,color,board);
    }

    @Override
    void MovePiece(ChessSquare square) {
        hasDeletedPiece = false;
        super.MovePiece(square);
        switch (square.rank)
        {
            case Board.R6:
            {
                final Piece piece = board.squares[_location.file][Board.R5].piece;
                if(piece != null && piece.GetTwoStepMoveIndexOnBoard() == board.moveCount - 1)
                {
                    hasDeletedPiece = true;
                    deletedPiece = piece._location;
                    deletedPieces.push(piece);
                    piece._location.piece = null;
                }
                break;
            }

            case Board.R3:
            {
                final Piece piece = board.squares[_location.file][Board.R4].piece;
                if(piece != null && piece.GetTwoStepMoveIndexOnBoard() == board.moveCount - 1)
                {
                    hasDeletedPiece = true;
                    deletedPiece = piece._location;
                    deletedPieces.push(piece);
                    piece._location.piece = null;
                }
                break;
            }

            case Board.R4:
            case Board.R5:
                if(GetMoveCount() == 1)
                    twoStepMoveIndexOnBoard = board.moveCount;
                break;

            case Board.R8:
            case Board.R1:
                new Queen(_location,color,board);
                break;

            default:
                break;
        }
    }

    @Override
    void MoveBack(ChessSquare square) {
        hasDeletedPiece = false;
        switch (_location.rank)
        {
            case Board.R6:
            case Board.R3:
            {
                if(!deletedPieces.empty())
                {
                    Piece piece = deletedPieces.pop();
                    if(piece != null)
                        piece._location.piece = piece;
                }
                break;
            }

            case Board.R4:
            case Board.R5:
                if(GetMoveCount() == 1)
                    twoStepMoveIndexOnBoard = 0;
                break;

            case Board.R8:
            case Board.R1:
                _location.piece = this;
                break;

            default:
                break;
        }
        super.MoveBack(square);
    }

    protected void Append(final ChessSquare square, List<ChessSquare> nextMoves, final boolean checkKing)
    {
        if(checkKing)
        {
            final ChessSquare from = _location;
            final Piece deletedPiece = square.piece;
            MovePiece(square);
            if(myKing.GetThreatCount() == 0)
                nextMoves.add(square);
            MoveBack(from);
            square.piece = deletedPiece;
        }
        else
            nextMoves.add(square);
    }
}
