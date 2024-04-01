package chess.dao;

import chess.database.DbConnection;
import chess.dto.MoveRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MoveDao {

    public void save(final MoveRequest moveRequest) {
        String query = "INSERT INTO move (source, target) VALUES (?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, moveRequest.getSource());
            preparedStatement.setString(2, moveRequest.getTarget());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
