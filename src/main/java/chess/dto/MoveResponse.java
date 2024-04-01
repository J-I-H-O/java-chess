package chess.dto;

import chess.domain.Position;

public class MoveResponse {

    private final Position source;
    private final Position target;

    public MoveResponse(final Position source, final Position target) {
        this.source = source;
        this.target = target;
    }

    public static MoveResponse of(final String source, final String target) {
        return new MoveResponse(Position.from(source), Position.from(target));
    }

    public Position getSource() {
        return this.source;
    }

    public Position getTarget() {
        return this.target;
    }
}
