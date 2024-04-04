package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.dto.PieceRequest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceDaoTest {

    private final PieceDao pieceDao = new PieceDao();

    @BeforeEach
    @AfterEach
    void clean() {
        pieceDao.deleteAll();
    }

    @Test
    @DisplayName("DB에 저장된 값들을 모두 조회할 수 있다.")
    void findAll() {
        pieceDao.saveAll(List.of(
                PieceRequest.from(Position.from("a2"), Pawn.colorOf(Color.WHITE)),
                PieceRequest.from(Position.from("a7"), Pawn.colorOf(Color.BLACK))));

        Map<Position, Piece> pieces = pieceDao.findAllPieces();

        assertThat(pieces.get(Position.from("a2"))).isEqualTo(Pawn.colorOf(Color.WHITE));
        assertThat(pieces.get(Position.from("a7"))).isEqualTo(Pawn.colorOf(Color.BLACK));
    }
}
