package chess.dao;

import chess.database.DbConnection;
import chess.domain.Move;
import chess.dto.MoveRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveDao {

    public void save(final MoveRequest moveRequest) {
        String query = "INSERT INTO move (source, target) VALUES (?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, moveRequest.getSource());
            preparedStatement.setString(2, moveRequest.getTarget());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Move> findAllMoves() {
        String query = "SELECT * FROM move";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Move> moves = new ArrayList<>();
            while (resultSet.next()) {
                moves.add(Move.of(
                        resultSet.getString("source"),
                        resultSet.getString("target")));
            }
            return moves;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM move";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
