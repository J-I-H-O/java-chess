package chess.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static final String SERVER = "localhost:13306";
    public static final String DATABASE = "chess";
    public static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    private DbConnection() {
    }

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
