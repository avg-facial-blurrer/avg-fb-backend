package nl.oose.blackpool.dataAcces;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public  class DbTestConnector {
    private final String databaseProperties = "/database.properties";
    private String connectionString;
    private Properties properties;

    private final String SQL_RELOAD_DB = "{CALL pr_reload_testdb()}";

    private Connection connection;

    public DbTestConnector() throws IOException, ClassNotFoundException {
        properties = new Properties();
        properties.load(DbTestConnector.class.getResourceAsStream(databaseProperties));
        connectionString = createConnectionString();
        loadDriver(properties.getProperty("driver"));
    }

    private String createConnectionString() {
        return properties.getProperty("connectionString").concat(properties.getProperty("testdatabase") + "?user=" + properties.getProperty("user") + "&password=" + properties.getProperty("password"));
    }

    private void loadDriver(String driver) throws ClassNotFoundException {
        Class.forName(driver);
    }

    public Connection getConnection() throws SQLException {
        closeConnection();
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

    private void closeConnection()  {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void reloadTestDatabase() throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement(SQL_RELOAD_DB);
        statement.execute();
        statement.close();
    }

}
