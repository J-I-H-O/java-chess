package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreCalculatorTest {

    @Test
    @DisplayName("남아있는 기물들의 점수 합을 계산할 수 있다. 9 + 5 + 3 + 2.5 -> 19.5")
    void calculateRemainingPieceScore() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position position1 = Position.of('a', 1);
        Position position2 = Position.of('a', 2);
        Position position3 = Position.of('a', 3);
        Position position4 = Position.of('a', 4);
        positionPiece.put(position1, Queen.colorOf(Color.WHITE));   // 9
        positionPiece.put(position2, Rook.colorOf(Color.WHITE));    // 5
        positionPiece.put(position3, Bishop.colorOf(Color.WHITE));  // 3
        positionPiece.put(position4, Knight.colorOf(Color.WHITE));  // 2.5

        ScoreCalculator scoreCalculator = new ScoreCalculator();
        double score = scoreCalculator.calculateScore(positionPiece);

        assertThat(score).isEqualTo(19.5);
    }

    @Test
    @DisplayName("여러 개의 폰이 존재할 때 같은 세로줄에 존재하는 경우, 점수는 0.5점으로 계산한다. 0.5 + 0.5 -> 1")
    void calculatePawnsScoreInSameFile() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position position1 = Position.of('a', 1);
        Position position2 = Position.of('a', 2);
        positionPiece.put(position1, Pawn.colorOf(Color.WHITE));   // 0.5
        positionPiece.put(position2, Pawn.colorOf(Color.WHITE));    // 0.5

        ScoreCalculator scoreCalculator = new ScoreCalculator();
        double score = scoreCalculator.calculateScore(positionPiece);

        assertThat(score).isEqualTo(1);
    }

    @Test
    @DisplayName("여러 개의 폰이 존재할 때 같은 세로줄에 존재하지 않는 경우, 점수는 1점으로 계산한다. 1 + 1 -> 2")
    void calculatePawnsScoreInDifferentFile() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position position1 = Position.of('a', 1);
        Position position2 = Position.of('b', 1);
        positionPiece.put(position1, Pawn.colorOf(Color.WHITE));   // 1
        positionPiece.put(position2, Pawn.colorOf(Color.WHITE));    // 1

        ScoreCalculator scoreCalculator = new ScoreCalculator();
        double score = scoreCalculator.calculateScore(positionPiece);

        assertThat(score).isEqualTo(2);
    }
}
