package Structures;

enum PlayState
{
    State_None,
    State_WhiteCheck,
    State_BlackCheck,
    State_WhiteCheckmate,
    State_BlackCheckmate,
    State_Draw,
};

enum PieceMoveResult
{
    MoveResult_NoPieceThere, MoveResult_CanNotMoveThere, MoveResult_OK
};
