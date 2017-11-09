package MatinChessLib;

import java.util.List;

abstract class Piece {
    private int _moveCount;
    private ChessSquare _location;
    ChessSquare GetLocation() { return _location; }

    King myKing;
    int twoStepMoveIndexOnBoard;
    public final Board board;
    public final PieceColor color;

    Piece(final ChessSquare location, final PieceColor color, final Board board)
    {
        this.color = color;
        _location = location;
        this.board = board;
        _location.piece = this;
        this.myKing = (color == PieceColor.White)?
                board.WhiteKing:
                board.BlackKing;
    }

    boolean AppendSquare(final int i, final int j, final List<ChessSquare> nextMoves, final Boolean checkKing)
    {
        ChessSquare square = board.squares[i][j];
        if(square.piece == null)
        {
            if(checkKing)
            {
                ChessSquare from = _location;
                MovePiece(square);
                if(myKing.GetThreatCount() == 0)
                    nextMoves.add(square);
                MoveBack(from);
            }
            else
                nextMoves.add(square);

            return false;
        }
        else
        {
            if(square.piece.color != color)
            {
                if(checkKing)
                {
                    ChessSquare from = _location;
                    Piece deletedPiece = square.piece;
                    MovePiece(square);
                    if(myKing.GetThreatCount() == 0)
                        nextMoves.add(square);
                    MoveBack(from);
                    square.piece = deletedPiece;
                }
                else
                    nextMoves.add(square);
            }
            return true;
        }
    }

    int GetTwoStepMoveIndexOnBoard() { return twoStepMoveIndexOnBoard; }
    int GetMoveCount() { return _moveCount; }

    void MovePiece(final ChessSquare square)
    {
        _location.piece = null;
        square.piece = this;
        _location = square;
        _moveCount++;

        board.moveCount++;
        board.AddToHistory(this);
    }

    void MoveBack(final ChessSquare square)
    {
        _location.piece = null;
        square.piece = this;
        _location = square;
        _moveCount--;

        board.moveCount--;
        board.RemoveLastFromHistory();
    }

    int GetScore() { return GetScore(true); }
    int GetScore(final boolean nextMoves)
    {
        int score;

        PieceColor opponentColor =
                color == PieceColor.White?
                        PieceColor.Black:
                        PieceColor.White;
        score = board.GetThreatCount(_location,opponentColor, true);
        score -= GetThreatCount();
        score *= 10;

        if(nextMoves)
            score += GetNextMovesCount();

        //    const BoardFile file = GetLocation()->file;
        //    const BoardRank rank = GetLocation()->rank;
        //    if(file >= C && file <= E && rank >= R3 && rank <= R6)
        //        score += 10;

        return score;
    }

    int GetThreatCount()
    {
        return board.GetThreatCount(_location,color, !(this instanceof King));
    }

    List<ChessSquare> GetNextMoves() { return GetNextMoves(true);}
    abstract List<ChessSquare> GetNextMoves(final boolean checkKing);
    abstract char GetChar();
    int GetNextMovesCount()
    {
        List<ChessSquare> moves = GetNextMoves();
        return moves.size();
    }
}
