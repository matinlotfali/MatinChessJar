package MatinChessLib;

class ChessSquare extends Location{

    Piece piece = null;

    ChessSquare(final byte file, final byte rank)
    {
        super(file,rank);
    }
}
