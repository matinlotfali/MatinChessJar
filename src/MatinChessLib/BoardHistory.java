package MatinChessLib;

class BoardHistory {
    private Piece[][] pieces = new Piece[8][8];
    Piece movedPiece;

    BoardHistory(Board board)
    {
        for(int i=0; i<8; i++)
            for(int j=0; j<8; j++)
                pieces[i][j] = board.squares[i][j].piece;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Board)
            return isEqual((Board)obj);
        return false;
    }

    boolean isEqual(Board board)
    {
        for(byte m = 0; m<8; m++)
            for(byte n=0; n<8; n++)
            {
                Piece p = pieces[m][n];
                Piece h = board.squares[m][n].piece;
                if (p != null) {
                    if (h != p)
                        return false;
                } else
                    if(h != null)
                        return false;
            }
        return true;
    }
}
