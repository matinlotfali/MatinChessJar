import Pieces.King;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Structures.*;

import java.util.*;
import java.util.stream.Collectors;

import static Structures.PieceColor.Black;
import static Structures.PieceColor.White;
import static Structures.PieceMoveResult.CanNotMoveThere;
import static Structures.PieceMoveResult.NoPieceThere;
import static Structures.PieceMoveResult.OK;
import static Structures.PlayState.*;

public class MatinChessLib {
    private PieceColor turn = White;
    public PieceColor GetTurn()
    {
        return turn;
    }

    private byte MaxDepth = 2;
    public void SetMaxDepth(final byte max)
    {
        if(max > 0)
            MaxDepth = max;
        else
            throw new IllegalArgumentException("Can not set zero to depth.");
    }

    public Board board = new Board();
    private Random random = new Random();
    private boolean isPlayingAI = false;
    private static MatinChessLib instance = null;
    public static MatinChessLib GetInstance()
    {
        return instance;
    }

    private Movement movementResult;
    private byte currentDepth;

    private MatinChessLib()
    {
        instance = this;
    }

    private int AlphaBetaPurning(int alpha, int beta)
    {
        if(board.IsThreefold() || board.IsFiftyMove())
            return 0;

        if(currentDepth >= MaxDepth)
            return board.GetScore();

        final HashMap<Piece, List<Square>> nextMovesList = new HashMap<>();

        for(Square[] sq : board.squares)
            for(Square s : sq)
            {
                final Piece piece = s.piece;
                if(piece != null)
                    nextMovesList.put(piece, piece.GetNextMoves(false));
            }
//        final int count = nextMovesList.size();

//        nextMovesList = nextMovesList.entrySet().stream()
//                .sorted(new Comparator<Map.Entry<Piece, List<Square>>>() {
//                    @Override
//                    public int compare(Map.Entry<Piece, List<Square>> pieceListEntry, Map.Entry<Piece, List<Square>> t1) {
//                        if (pieceListEntry.getValue().size() > t1.getValue().size())
//                            return 1;
//                        else (pieceListEntry.getValue().size() < t1.getValue().size())
//                                return -1;
//                        return 0;
//                    }
//                })
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//
//        final HashMap<Piece, List<Square>> nextMovesList2 = (HashMap<Piece, List<Square>>)nextMovesList.clone();
//
//        nextMovesList.clear();
//        while(!nextMovesList2.isEmpty())
//        {
//            int maxValue = nextMovesList2.get(0).size();
//            if(maxValue > 0)
//            {
//                int maxCount = 1;
//                final int len = nextMovesList2.size();
//                while(maxCount < len && nextMovesList2.get(maxCount).size() == maxValue)
//                maxCount++;
//                while(maxCount > 0)
//                {
//                    int rand =  random.nextInt(maxCount);
//                    nextMovesList.put(nextMovesList2.get(rand));
//                    nextMovesList2.remove(rand);
//                    maxCount--;
//                }
//            }
//            else
//            {
//                nextMovesList.addAll(nextMovesList2);
//                nextMovesList2.clear();
//                break;
//            }
//        }


        int moveCount = 0;
        for(final Piece nextMovePieces : nextMovesList.keySet())
        {
            for(final Square locationTo : nextMovesList.get(nextMovePieces))
            {
                moveCount++;
                final Square locationFrom = nextMovePieces.GetLocation();
                final Piece pieceRemoved = locationTo.piece;
                nextMovePieces.MovePiece(locationTo);

                if((turn==White && board.BlackKing.GetThreatCount() > 0) ||
                        (turn==Black && board.WhiteKing.GetThreatCount() > 0))
                {
                    moveCount--;
                    nextMovePieces.MoveBack(locationFrom);
                    locationTo.piece = pieceRemoved;
                    continue;
                }

                currentDepth++;
                int result = AlphaBetaPurning(alpha, beta);
                currentDepth--;
                nextMovePieces.MoveBack(locationFrom);
                locationTo.piece = pieceRemoved;

                if(currentDepth % 2 == 0)
                {
                    if(alpha < result)
                    {
                        if(currentDepth == 0)
                            movementResult = new Movement(locationFrom, locationTo);
                        alpha = result;
                    }
                }
                else
                if(beta > result)
                    beta = result;

                if(alpha >= beta)
                    return (currentDepth % 2 == 0)? alpha: beta;
            }
        }

        if(moveCount == 0)
            return board.GetScore();

        return (currentDepth % 2 == 0)? alpha: beta;
    }

    private void MovePiece(final Square from, final Square to, final MoveResult result)
    {
        result.result = OK;
        result.secondaryMove = new Movement(from, to);
        result.hasSecondaryMove = false;
        result.hasPromoted = false;
        result.hasKickedAPiece = false;

        final Piece fromPieceBeforeMove = from.piece;
        final Piece toPieceBeforeMove = to.piece;

        if(toPieceBeforeMove != null)
        {
            result.hasKickedAPiece = true;
            result.kickedPiece = result.primaryMove.to;
        }

        from.piece.MovePiece(to);

        if(to.piece instanceof King) {
            if (((King) to.piece).GetRookMoved()) {
                result.hasSecondaryMove = true;
                result.secondaryMove = ((King) to.piece).GetRookMovement();
            }
        }
        else if (to.piece instanceof Pawn) {
                if (((Pawn) to.piece).GetHasDeletedPiece()) {
                    result.hasKickedAPiece = true;
                    result.kickedPiece = ((Pawn) to.piece).GetDeletedPiece();
                }
        }
        else if (to.piece instanceof Queen) {
            if(fromPieceBeforeMove instanceof Pawn)
                result.hasPromoted = true;
        }
    }

    private void ToggleTurn()
    {
        turn = turn == White? Black: White;
    }

    public MoveResult PlayAI()
    {
        isPlayingAI = true;
        currentDepth = 0;
        MoveResult result = new MoveResult();
        movementResult = result.primaryMove;
        AlphaBetaPurning(Integer.MIN_VALUE, Integer.MAX_VALUE);

        Piece piece = board.squares[result.primaryMove.from.file][result.primaryMove.from.rank].piece;
        Square squareTo = board.squares[result.primaryMove.to.file][result.primaryMove.to.rank];

        MovePiece(piece.GetLocation(), squareTo, result);

        if(piece instanceof Pawn || squareTo.piece != null)
            board.ClearHistory();
        isPlayingAI = false;

        return result;
    }

    public MoveResult MovePiece(final Movement movement)
    {
        MoveResult result = new MoveResult();

        final Square from = board.squares[movement.from.file][movement.from.rank];
        if(from.piece == null)
        {
            result.result = NoPieceThere;
            return result;
        }

        final Square to = board.squares[movement.to.file][movement.to.rank];

        List<Square> list = from.piece.GetNextMoves();
        if(!list.contains(to)) {
            result.result = CanNotMoveThere;
            return result;
        }

        MovePiece(from, to, result);
        return result;
    }

    public List<Square> GetNextMoves(final Square square)
    {
        if(isPlayingAI)
            return null;

        final Square from = board.squares[square.file][square.rank];
        if(from.piece == null)
            return null;

        return from.piece.GetNextMoves();
    }

    public PlayState CheckState()
    {
        if(board.IsThreefold() || board.IsFiftyMove())
            return Draw;
        else if(board.GetNextMovesCount() == 0)
            if(board.WhiteKing.GetThreatCount() > 0)
                return WhiteCheckmate;
            else if(board.BlackKing.GetThreatCount() > 0)
                return BlackCheckmate;
            else
                return Draw;
        else if(board.BlackKing.GetThreatCount() > 0)
            return BlackCheck;
        else if(board.WhiteKing.GetThreatCount() > 0)
            return WhiteCheck;
        else
            return None;
    }
}
