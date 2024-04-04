package chess.dao;

import chess.database.DbConnection;
import chess.domain.piece.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChessGameDao {

    public void saveTurnColor(final Color turnColor) {
        String query = "INSERT INTO chess_game (turn_color) VALUES (?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turnColor.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Color findCurrentTurnColor() {
        String query = "SELECT turn_color FROM chess_game ORDER BY id DESC LIMIT 1";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String rawTurnColor = resultSet.getString("turn_color");
                return Color.valueOf(rawTurnColor);
            }
            return Color.WHITE;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM chess_game";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
