package chess.domain;

import chess.domain.piece.Piece;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreCalculator {

    public static final int SINGLE_PAWN_COUNT = 1;
    public static final double SINGLE_PAWN_BONUS_SCORE = 0.5;

    public double calculateScore(final Map<Position, Piece> piecesByPosition) {
        double initialScore = calculateInitialScore(piecesByPosition);
        double pawnBonusScore = calculateSinglePawnBonus(piecesByPosition);

        return initialScore + pawnBonusScore;
    }

    private double calculateInitialScore(final Map<Position, Piece> pieceByPosition) {
        return pieceByPosition.values()
                .stream()
                .mapToDouble(Piece::getPieceScore)
                .sum();
    }

    private double calculateSinglePawnBonus(final Map<Position, Piece> pieceByPosition) {
        Map<Character, Long> pawnCountByFile = calculatePawnCountByFile(pieceByPosition);
        long singlePawnFileCount = pawnCountByFile.values()
                .stream()
                .filter(count -> count == SINGLE_PAWN_COUNT)
                .count();

        return singlePawnFileCount * SINGLE_PAWN_BONUS_SCORE;
    }

    private Map<Character, Long> calculatePawnCountByFile(Map<Position, Piece> pieceByPosition) {
        return pieceByPosition.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isPawn())
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey().getFile(),
                        Collectors.counting())
                );
    }
}
