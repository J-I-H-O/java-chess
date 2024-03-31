package chess.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import chess.domain.piece.Color;
import chess.domain.piece.Empty;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChessGameTest {

    @Test
    @DisplayName("연속해서 같은 색의 기물을 움직일 수 없다.")
    void cannotMoveSameColorInARow() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of('a', 2);
        Position passThroughPosition = Position.of('a', 3);
        Position targetPosition = Position.of('a', 4);
        positionPiece.put(sourcePosition, Pawn.colorOf(Color.WHITE));
        positionPiece.put(passThroughPosition, Empty.of());
        positionPiece.put(targetPosition, Empty.of());

        ChessBoard chessBoard = new ChessBoard(positionPiece);
        ChessGame chessGame = new ChessGame(chessBoard, Color.WHITE);
        chessGame.move(sourcePosition, passThroughPosition);

        assertThatCode(() -> chessGame.move(passThroughPosition, targetPosition))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
