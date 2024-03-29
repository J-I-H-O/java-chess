package chess.domain.piece;

public enum Color {

    BLACK,
    WHITE,
    ;

    public static Color switchColor(final Color color) {
        if (color == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
