package chess.dto;

import chess.domain.Position;

public class MoveResponse {

    private final long id;
    private final Position source;
    private final Position target;

    public MoveResponse(final long id, final Position source, final Position target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public static MoveResponse of(final long id, final String source, final String target) {
        return new MoveResponse(id, Position.from(source), Position.from(target));
    }

    public Position getSource() {
        return this.source;
    }

    public Position getTarget() {
        return this.target;
    }
}
