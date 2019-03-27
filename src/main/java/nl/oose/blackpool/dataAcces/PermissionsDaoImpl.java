package nl.oose.blackpool.dataAcces;

import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.Services.IPermissionsDao;
import nl.oose.blackpool.domain.Permissions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermissionsDaoImpl extends AbstractBaseDao<Permissions> implements IPermissionsDao {

    private final String SQL_GET_PERMISSONS_BY_ID = "SELECT * FROM permissions WHERE child_id = ? LIMIT 1 ";
    private final String SQL_GET_ALL_PERMISSONS = "SELECT * FROM permissions";
    private final String SQL_INSERT_PERMISSIONS = "INSERT INTO permissions VALUES(?, ?, ?, ?)";
    private final String SQL_UPDATE_PERMISSIONS = "UPDATE permissions SET social_media = ?, school_paper =?, enclosed_environment = ? WHERE child_id = ?";
    private final String SQL_DELETE_PERMISSIONS = "DELETE FROM permissions WHERE child_id = ?";

    private final String ENCLOSED_COLUMN = "enclosed_environment";
    private final String SCHOOLPAPER_COLUMN = "school_paper";
    private final String SOCIALMEDIA_COLUMN = "social_media";
    private final String CHILD_ID_COLUMN = "child_id";


    @Override
    public Optional<Permissions> get(int id) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GET_PERMISSONS_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.first()) {
                Permissions permissions = createPermissionsFromResultSet(resultSet);
                statement.close();
                return Optional.of(permissions);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException();
        } finally {
            this.dbConnector.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public List<Permissions> getAll() throws DataException {
        PreparedStatement statement;
        List<Permissions> permissionsList = new ArrayList<>();
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GET_ALL_PERMISSONS);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                permissionsList.add(createPermissionsFromResultSet(resultSet));
            }
            if(!permissionsList.isEmpty()) {
                statement.close();
                return permissionsList;
            }
            statement.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException();
        } finally {
            this.dbConnector.closeConnection();
        }
    }


    @Override
    public void save(Permissions permissions) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_INSERT_PERMISSIONS);
            statement.setInt(1, permissions.getChildId());
            statement.setBoolean(2, permissions.isSocialMediaPermission());
            statement.setBoolean(3, permissions.isSchoolPaperPermission());
            statement.setBoolean(4, permissions.isEnclosedEnvironmentPermission());
            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException();
        } finally {
            this.dbConnector.closeConnection();
        }

    }

    @Override
    public void update(Permissions permissions) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_UPDATE_PERMISSIONS);
            statement.setBoolean(1, permissions.isSocialMediaPermission());
            statement.setBoolean(2, permissions.isSchoolPaperPermission());
            statement.setBoolean(3, permissions.isEnclosedEnvironmentPermission());
            statement.setInt(4, permissions.getChildId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException();
        } finally {
            this.dbConnector.closeConnection();
        }

    }

    @Override
    public void delete(int id) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_DELETE_PERMISSIONS);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException();
        } finally {
            this.dbConnector.closeConnection();
        }
    }

    private Permissions createPermissionsFromResultSet(ResultSet resultSet) throws SQLException {
        return new Permissions(resultSet.getBoolean(SOCIALMEDIA_COLUMN), resultSet.getBoolean(SCHOOLPAPER_COLUMN), resultSet.getBoolean(ENCLOSED_COLUMN), resultSet.getInt(CHILD_ID_COLUMN));
    }
}
