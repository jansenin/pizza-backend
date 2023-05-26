package by.fpmibsu.pizza_site.database;

import org.postgresql.ds.PGConnectionPoolDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionCreator {
    private static final PGConnectionPoolDataSource dataSource;
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/database.properties"));
            dataSource = new PGConnectionPoolDataSource();
            dataSource.setURL((String) properties.get("db.url"));
            dataSource.setServerNames(new String[] {(String) properties.get("db.host")});
            dataSource.setDatabaseName((String) properties.get("db.name"));
            dataSource.setUser((String) properties.get("user"));
            dataSource.setPassword((String) properties.get("password"));
            dataSource.setPortNumbers(new int[]{Integer.parseInt((String) properties.get("db.port"))});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection createConnection() throws SQLException {
        return dataSource.getPooledConnection().getConnection();
    }
}