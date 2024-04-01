package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.Move;
import chess.domain.Position;
import chess.dto.MoveRequest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoveDaoTest {

    private final MoveDao moveDao = new MoveDao();

    @BeforeEach
    @AfterEach
    void clean() {
        moveDao.deleteAll();
    }

    @Test
    @DisplayName("DB에 저장된 값들을 모두 조회할 수 있다.")
    void findAll() {
        moveDao.save(MoveRequest.of(Position.from("a2"), Position.from("a4")));
        moveDao.save(MoveRequest.of(Position.from("a7"), Position.from("a6")));

        List<Move> allMoves = moveDao.findAllMoves();

        assertThat(allMoves.size()).isEqualTo(2);
        Move move1 = allMoves.get(0);
        assertThat(move1.getSource()).isEqualTo(Position.from("a2"));
        assertThat(move1.getTarget()).isEqualTo(Position.from("a4"));
        Move move2 = allMoves.get(1);
        assertThat(move2.getSource()).isEqualTo(Position.from("a7"));
        assertThat(move2.getTarget()).isEqualTo(Position.from("a6"));
    }
}
