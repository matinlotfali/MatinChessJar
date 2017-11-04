package Structures;

public class MoveResult {
    public PieceMoveResult result;
    public boolean hasPromoted;
    public boolean hasKickedAPiece;
    public boolean hasSecondaryMove;
    public Square kickedPiece;
    public Movement primaryMove;
    public Movement secondaryMove;
}
