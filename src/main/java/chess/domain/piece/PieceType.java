package chess.domain.piece;

import java.util.function.Function;

public enum PieceType {
    
    KING(King::colorOf),
    QUEEN(Queen::colorOf),
    ROOK(Rook::colorOf),
    BISHOP(Bishop::colorOf),
    KNIGHT(Knight::colorOf),
    PAWN(Pawn::colorOf),
    EMPTY(color -> Empty.EMPTY);

    private final Function<Color, Piece> function;

    PieceType(Function<Color, Piece> function) {
        this.function = function;
    }

    public Piece create(Color color) {
        return function.apply(color);
    }
}
