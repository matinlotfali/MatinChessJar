package Pieces;

import Structures.Board;
import Structures.PieceColor;
import Structures.Square;
import java.util.List;

public abstract class Piece {
    int _moveCount;
    Square _location;
    protected King myKing;
    protected int twoStepMoveIndexOnBoard;
    public final Board board;
    public final PieceColor color;
    public Square GetLocation() { return _location; }

    public Piece(final Square location, final PieceColor color, final Board board)
    {
        this.color = color;
        _location = location;
        this.board = board;
        _location.piece = this;
        this.myKing = (color == PieceColor.White)?
                board.WhiteKing:
                board.BlackKing;
    }

    protected boolean AppendSquare(final int i, final int j, final List<Square> nextMoves, final Boolean checkKing)
    {
        Square square = board.squares[i][j];
        if(square.piece == null)
        {
            if(checkKing)
            {
                Square from = _location;
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
                    Square from = _location;
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

    public int GetTwoStepMoveIndexOnBoard() { return twoStepMoveIndexOnBoard; }
    public int GetMoveCount() { return _moveCount; }

    public void MovePiece(final Square square)
    {
        _location.piece = null;
        square.piece = this;
        _location = square;
        _moveCount++;

        board.moveCount++;
        board.AddToHistory(this);
        //TODO
        //Game::GetInstance()->ToggleTurn();
    }

    public void MoveBack(final Square square)
    {
        _location.piece = null;
        square.piece = this;
        _location = square;
        _moveCount--;

        board.moveCount--;
        board.RemoveLastFromHistory();
        //TODO
        //Game::GetInstance()->ToggleTurn();
    }

    public int GetScore() { return GetScore(true); }
    public int GetScore(final boolean nextMoves)
    {
        int score;

        PieceColor opponentColor =
                color == PieceColor.White?
                        PieceColor.Black:
                        PieceColor.White;
        score = board.GetThreatCount(_location,opponentColor);
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

    public int GetThreatCount()
    {
        return board.GetThreatCount(_location,color);
    }

    public List<Square> GetNextMoves() { return GetNextMoves(true);}
    public abstract List<Square> GetNextMoves(final boolean checkKing);
    public int GetNextMovesCount()
    {
        List<Square> moves = GetNextMoves();
        return moves.size();
    }
}
