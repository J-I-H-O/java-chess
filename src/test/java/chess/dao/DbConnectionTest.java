package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.database.DbConnection;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DbConnectionTest {

    @Test
    public void connection() {
        try (final Connection connection = DbConnection.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
