package chess.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static final String CONFIG_FILE_PATH = "src/main/resources/db-config.yml";

    private DbConnection() {
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(CONFIG_FILE_PATH));

            String server = properties.getProperty("server");
            String database = properties.getProperty("database");
            String option = properties.getProperty("option");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            String url = "jdbc:mysql://" + server + "/" + database + option;
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException | IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
