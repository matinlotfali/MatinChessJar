package Structures;

import Pieces.King;
import Pieces.Piece;
import Pieces.Rook;

import java.util.List;
import java.util.Stack;

import static Structures.PieceColor.Black;
import static Structures.PieceColor.White;

public class Board {
    public final static byte
            A=0,   B=1,    C=2,    D=3,    E=4,    F=5,    G=6,    H=7,
            R1=0,  R2=1,   R3=2,   R4=3,   R5=4,   R6=5,   R7=6,   R8=7;

    public final Square[][] squares = new Square[8][8];
    public King WhiteKing = null;
    public King BlackKing = null;
    public int moveCount = 0;
    private final Stack<BoardHistory> boardHistory = new Stack<BoardHistory>();

    public Board()
    {
        for(byte file = A; file<8; file++)
            for(byte rank = R1; rank<8; rank++)
                squares[file][rank] = new Square(file,rank);

        new King(squares[4][7],White,this);
//        new WhitePawn(squares[0][6],this);
//        new WhitePawn(squares[1][6],this);
//        new WhitePawn(squares[2][6],this);
//        new WhitePawn(squares[3][6],this);
//        new WhitePawn(squares[4][6],this);
//        new WhitePawn(squares[5][6],this);
//        new WhitePawn(squares[6][6],this);
//        new WhitePawn(squares[7][6],this);
        new Rook(squares[0][7],White,this);
//        new Knight(squares[1][7],White,this);
//        new Bishop(squares[2][7],White,this);
//        new Queen(squares[3][7],White,this);
//        new Bishop(squares[5][7],White,this);
//        new Knight(squares[6][7],White,this);
        new Rook(squares[7][7],White,this);

        new King(squares[4][0],Black,this);
//        new BlackPawn(squares[0][1],this);
//        new BlackPawn(squares[1][1],this);
//        new BlackPawn(squares[2][1],this);
//        new BlackPawn(squares[3][1],this);
//        new BlackPawn(squares[4][1],this);
//        new BlackPawn(squares[5][1],this);
//        new BlackPawn(squares[6][1],this);
//        new BlackPawn(squares[7][1],this);
        new Rook(squares[0][0],Black,this);
//        new Knight(squares[1][0],Black,this);
//        new Bishop(squares[2][0],Black,this);
//        new Queen(squares[3][0],Black,this);
//        new Bishop(squares[5][0],Black,this);
//        new Knight(squares[6][0],Black,this);
        new Rook(squares[7][0],Black,this);
    }

    public int GetScore()
    {
        int score = 0;
        for (Square[] u : squares)
            for(Square s : u)
            {
                Piece piece = s.piece;
                if(piece != null)
                {
                    int sc = piece.GetScore();
                    score += (piece.color == Black)? sc: -sc;
                }
            }
        return score;
    }

    public int GetNextMovesCount()
    {
        int result = 0;
        for (Square[] u : squares)
            for(Square s : u)
            {
                Piece piece = s.piece;
                if(piece != null)
                    result += piece.GetNextMovesCount();
            }
        return result;
    }

    public int GetThreatCount(final Square square, final PieceColor color)
    {
        int result = 0;
        for (Square[] u : squares)
            for(Square s : u) {
                Piece piece = s.piece;
                if (piece != null && piece.color != color) {
                    List<Square> list = piece.GetNextMoves(true);
                    for(Square sc : list)
                        if(sc == square)
                            result++;
                }
            }
        return result;
    }

    public void AddToHistory(final Piece piece)
    {
        BoardHistory history = new BoardHistory(this);
        history.movedPiece = piece;
        boardHistory.push(history);
    }

    public void RemoveLastFromHistory()
    {
        boardHistory.pop();
    }

    public boolean IsThreefold()
    {
        int count = 0;
        for(BoardHistory b : boardHistory)
            if(b.isEqual(this))
                count++;
        return count >= 3;
    }

    public boolean IsFiftyMove()
    {
        return boardHistory.size() > 50;
    }
}
