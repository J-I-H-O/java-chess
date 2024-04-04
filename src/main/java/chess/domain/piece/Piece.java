package chess.domain.piece;

import chess.domain.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    protected final Set<Direction> directions;
    protected final Color color;

    protected Piece(final Color color) {
        directions = new HashSet<>();
        this.color = color;
    }

    public abstract String getOwnPieceTypeName();

    public abstract double getPieceScore();

    public abstract List<Position> findPath(final Position source, final Position target, final Direction direction);

    public final boolean canMoveInTargetDirection(final Direction targetDirection) {
        return directions.contains(targetDirection);
    }

    public final boolean isAlly(final Piece piece) {
        if (piece.isEmpty()) {
            return false;
        }

        return this.color == piece.color;
    }

    public boolean isSameColorWith(final Color color) {
        return this.color == color;
    }

    public final boolean isEmpty() {
        return PieceType.EMPTY.name().equals(getOwnPieceTypeName());
    }

    public final boolean isPawn() {
        return PieceType.PAWN.name().equals(getOwnPieceTypeName());
    }

    public final boolean isKing() {
        return PieceType.KING.name().equals(getOwnPieceTypeName());
    }

    protected final List<Position> findPathOfSingleMovePiece(final Position source, final Position target,
                                                             final Direction direction) {
        validateDirection(direction);

        List<Position> positions = new ArrayList<>();
        Position currentPosition = source;
        currentPosition = currentPosition.moveTowardDirection(direction);
        positions.add(currentPosition);

        validateReachability(target, currentPosition);

        return positions;
    }

    protected final List<Position> findPathOfMultipleMovePiece(final Position source, final Position target,
                                                               final Direction direction) {
        validateDirection(direction);

        List<Position> positions = new ArrayList<>();
        Position currentPosition = source;
        while (currentPosition != target) {
            currentPosition = currentPosition.moveTowardDirection(direction);
            positions.add(currentPosition);
        }

        return positions;
    }

    protected final void validateDirection(final Direction direction) {
        if (!directions.contains(direction)) {
            throw new IllegalArgumentException("[ERROR] 선택한 기물이 이동할 수 없는 방향입니다.");
        }
    }

    protected final void validateReachability(final Position target, final Position currentPosition) {
        if (!currentPosition.equals(target)) {
            throw new IllegalArgumentException("[ERROR] 선택한 기물은 해당 위치에 도달할 수 없습니다.");
        }
    }

    public Color getColor() {
        return color;
    }
}
