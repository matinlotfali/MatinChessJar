package Structures;

import Pieces.Piece;

public class Square {

    public Piece piece = null;
    public final byte file;
    public final byte rank;

    public Square(final byte file, final byte rank)
    {
        this.file = file;
        this.rank = rank;
    }
}
