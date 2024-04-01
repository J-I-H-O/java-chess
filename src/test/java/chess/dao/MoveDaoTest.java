package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.Position;
import chess.dto.MoveRequest;
import chess.dto.MoveResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoveDaoTest {

    private final MoveDao moveDao = new MoveDao();

    @BeforeEach
    void clean() {
        moveDao.deleteAll();
    }

    @Test
    @DisplayName("DB에 저장된 값들을 모두 조회할 수 있다.")
    void findAll() {
        moveDao.save(MoveRequest.of(Position.from("a2"), Position.from("a4")));
        moveDao.save(MoveRequest.of(Position.from("a8"), Position.from("a7")));

        List<MoveResponse> allMoves = moveDao.findAllMoves();

        assertThat(allMoves.size()).isEqualTo(2);
        MoveResponse moveResponse1 = allMoves.get(0);
        assertThat(moveResponse1.getSource()).isEqualTo(Position.from("a2"));
        assertThat(moveResponse1.getTarget()).isEqualTo(Position.from("a4"));
        MoveResponse moveResponse2 = allMoves.get(1);
        assertThat(moveResponse2.getSource()).isEqualTo(Position.from("a8"));
        assertThat(moveResponse2.getTarget()).isEqualTo(Position.from("a7"));
    }
}
