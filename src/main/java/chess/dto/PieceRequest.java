package chess.dto;

import chess.domain.Position;
import chess.domain.piece.Piece;

public class PieceRequest {

    private final String position;
    private final String type;
    private final String color;

    private PieceRequest(final String position, final String type, final String color) {
        this.position = position;
        this.type = type;
        this.color = color;
    }

    public static PieceRequest from(final Position position, final Piece piece) {
        String positionValue = position.getValue();
        String typeValue = piece.getOwnPieceTypeName();
        String colorValue = piece.getColor().name();

        return new PieceRequest(positionValue, typeValue, colorValue);
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getPosition() {
        return position;
    }
}
