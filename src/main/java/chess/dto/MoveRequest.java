package chess.dto;

import chess.domain.Position;

public class MoveRequest {

    private final String source;
    private final String target;

    private MoveRequest(final String source, final String target) {
        this.source = source;
        this.target = target;
    }

    public static MoveRequest of(final Position source, final Position target) {
        return new MoveRequest(source.getValue(), target.getValue());
    }

    public String getSource() {
        return this.source;
    }

    public String getTarget() {
        return this.target;
    }
}
