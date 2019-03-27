package nl.oose.blackpool.dataAcces;

import nl.oose.blackpool.DTO.LoginDTO;
import nl.oose.blackpool.Services.ILoginDao;
import nl.oose.blackpool.domain.User;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginDaoImpl extends AbstractBaseDao<User> implements ILoginDao {
    private final String SQL_GETUSER = "SELECT * FROM User WHERE id = ?";
    private final String SQL_GETALLUSERS = "SELECT * FROM User";
    private final String SQL_SAVEUSER = "INSERT INTO User(username, password) VALUES (?, ?)";
    private final String SQL_UPDATEUSER = "UPDATE User SET username = ? WHERE id = ?";
    private final String SQL_DELETEUSER = "DELETE FROM User WHERE id = ?";
    private final String SQL_LOGIN = "SELECT * FROM User WHERE username = ? and password = ?";

    private final String USERNAME_COLUMN = "username";
    private final String ID_COLUMN = "id";

    @Override
    public Optional<User> get(int id) {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GETUSER);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                User user = createUserFromResultSet(resultSet);
                statement.close();
                return Optional.of(user);
            }
            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            this.dbConnector.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        PreparedStatement statement;
        List<User> users = new ArrayList<>();
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GETALLUSERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createUserFromResultSet(resultSet));
            }
            statement.close();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.dbConnector.closeConnection();
        }
        return users;
    }

    @Override
    public void save(User user) {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_SAVEUSER);
            statement.setString(1, user.getUsername());
            statement.setString(2, "12345");
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.dbConnector.closeConnection();
        }
    }

    @Override
    public void update(User user) {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_UPDATEUSER);
            statement.setString(1, user.getUsername());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.dbConnector.closeConnection();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_DELETEUSER);
            statement.setInt(1, id);
            statement.executeQuery();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.dbConnector.closeConnection();
        }
    }

    public Optional<User> login(LoginDTO loginDTO) {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_LOGIN);
            statement.setString(1, loginDTO.getUsername());
            statement.setString(2, loginDTO.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                User user = createUserFromResultSet(resultSet);
                statement.close();
                return Optional.of(user);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.dbConnector.closeConnection();
        }
       return Optional.empty();
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt(ID_COLUMN), resultSet.getString(USERNAME_COLUMN));
    }
}