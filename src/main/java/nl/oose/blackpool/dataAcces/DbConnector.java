package nl.oose.blackpool.dataAcces;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnector {
    private final String databaseProperties = "/database.properties";
    private String connectionString;
    private Properties properties;

    private Connection connection;

    public DbConnector() throws IOException, ClassNotFoundException {
        properties = new Properties();
        properties.load(DbConnector.class.getResourceAsStream(databaseProperties));
        connectionString = createConnectionString();
        loadDriver(properties.getProperty("driver"));
    }

    private String createConnectionString() {
        return properties.getProperty("connectionString").concat(properties.getProperty("database") + "?user=" + properties.getProperty("user") + "&password=" + properties.getProperty("password"));
    }

    private void loadDriver(String driver) throws ClassNotFoundException {
        Class.forName(driver);
    }

    public Connection getConnection() throws SQLException {
        if(connection != null) {
            if(connection.isClosed()) {
                connection = DriverManager.getConnection(connectionString);
            }
            return  connection;
        }
        else {
            connection = DriverManager.getConnection(connectionString);
            return connection;
        }
    }

    public void closeConnection()  {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
