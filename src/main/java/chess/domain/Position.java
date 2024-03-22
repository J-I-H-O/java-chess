package chess.domain;

import chess.domain.piece.Direction;
import java.util.LinkedHashMap;
import java.util.Map;

public class Position {

    private static final Map<String, Position> CACHE = new LinkedHashMap<>();

    private final char file;
    private final int rank;

    static {
        for (char i = 'a'; i <= 'h'; i++) {
            for (int j = 1; j <= 8; j++) {
                CACHE.put(toKey(i, j), new Position(i, j));
            }
        }
    }

    private Position(final char file, final int rank) {
        this.file = file;
        this.rank = rank;
    }

    public static Position of(final char file, final int rank) {
        Position position = CACHE.get(toKey(file, rank));
        if (position == null) {
            throw new IllegalArgumentException("[ERROR] 범위를 벗어난 위치입니다.");
        }

        return position;
    }

    private static String toKey(final char file, final int rank) {
        return String.valueOf(file) + rank;
    }

    public Position moveTowardDirection(final Direction direction) {
        char file = (char) direction.calculateNextFile(this.file);
        int rank = direction.calculateNextRank(this.rank);
        return Position.of(file, rank);
    }

    public Direction calculateDirection(final Position target) {
        int fileDifference = target.file - this.file;
        int rankDifference = target.rank - this.rank;

        return Direction.find(fileDifference, rankDifference);
    }
}
