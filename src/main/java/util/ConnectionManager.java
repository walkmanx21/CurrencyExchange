package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    public static final String URL_KEY = "db.url";

    private ConnectionManager () {
    }

    public static Connection get() {
        try {
            return DriverManager.getConnection(PropertiesUtil.getProperty(URL_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

