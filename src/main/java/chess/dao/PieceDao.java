package chess.dao;

import chess.database.DbConnection;
import chess.domain.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.dto.PieceRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceDao {

    public void saveAll(final List<PieceRequest> pieceRequests) {
        String query = "INSERT INTO pieces (color, type, position) VALUES (?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (PieceRequest pieceRequest : pieceRequests) {
                preparedStatement.setString(1, pieceRequest.getColor());
                preparedStatement.setString(2, pieceRequest.getType());
                preparedStatement.setString(3, pieceRequest.getPosition());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Piece> findAllPieces() {
        String query = "SELECT * FROM pieces";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Position, Piece> loadedPieces = new HashMap<>();
            while (resultSet.next()) {
                String rawPosition = resultSet.getString("position");
                String rawPieceColor = resultSet.getString("color");
                String rawPieceType = resultSet.getString("type");

                PieceType pieceType = PieceType.valueOf(rawPieceType);
                Color color = Color.valueOf(rawPieceColor);

                loadedPieces.put(Position.from(rawPosition), pieceType.create(color));
            }
            return loadedPieces;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM pieces";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
