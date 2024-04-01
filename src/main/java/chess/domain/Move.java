package chess.domain;

public class Move {

    private final Position source;
    private final Position target;

    public Move(final Position source, final Position target) {
        this.source = source;
        this.target = target;
    }

    public static Move of(final String source, final String target) {
        return new Move(Position.from(source), Position.from(target));
    }

    public Position getSource() {
        return this.source;
    }

    public Position getTarget() {
        return this.target;
    }
}
