package chess.view;

import static org.junit.jupiter.api.Assertions.*;

import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.Team;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardExpressionTest {

    @Test
    @DisplayName("현재 체스판의 상태를 문자열로 반환한다.")
    void toChessBoardExpression() {
        Piece piece = Piece.of(PieceType.KING, Team.WHITE);
        List<Piece> pieces = Collections.nCopies(64, piece);

        String actualExpression = ChessBoardExpression.toExpression(pieces);

        String expectedExpression = "kkkkkkkk" + System.lineSeparator()
                                    + "kkkkkkkk" + System.lineSeparator()
                                    + "kkkkkkkk" + System.lineSeparator()
                                    + "kkkkkkkk" + System.lineSeparator()
                                    + "kkkkkkkk" + System.lineSeparator()
                                    + "kkkkkkkk" + System.lineSeparator()
                                    + "kkkkkkkk" + System.lineSeparator()
                                    + "kkkkkkkk" + System.lineSeparator();
        Assertions.assertThat(actualExpression).isEqualTo(expectedExpression);
    }
}
