package nl.oose.blackpool.dataAcces;

import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.Services.IChildDao;
import nl.oose.blackpool.Services.IGroupDao;
import nl.oose.blackpool.domain.Group;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupDaoImpl extends  AbstractBaseDao<Group> implements IGroupDao {
    private final String SQL_GET_GROUP_BY_ID = "SELECT * FROM class WHERE class_id = ?";
    private final String SQL_GET_ALL_GROUPS ="SELECT * FROM class";
    private final String SQL_INSERT_GROUP = "INSERT INTO class(class_name) VALUES(?)";
    private final String SQL_UPDATE_GROUP = "UPDATE class SET class_name = ? WHERE class_id = ?";
    private final String SQL_DELETE_GROUP = "DELETE FROM class WHERE class_id = ?";

    private final String ID_COLUMN = "class_id";
    private final String NAME_COLUMN = "class_name";

    private IChildDao childDao;

    @Override
    public Optional<Group> get(int id) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GET_GROUP_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.first()) {
                Group group = createGroupFromResultSet(resultSet);
                statement.close();
                return Optional.of(group);
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
    public List<Group> getAll() throws DataException {
        PreparedStatement statement;
        List<Group> groups = new ArrayList<>();
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GET_ALL_GROUPS);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                groups.add(createGroupFromResultSet(resultSet));
            }
            if(!groups.isEmpty()) {
                statement.close();
                return groups;
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
    public void save(Group group) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_INSERT_GROUP);
            statement.setString(1, group.getGroupName());
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
    public void update(Group group) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_UPDATE_GROUP);
            statement.setString(1, group.getGroupName());
            statement.setInt(2, group.getGroupId());
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
            statement = this.dbConnector.getConnection().prepareStatement(SQL_DELETE_GROUP);
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

    @Inject
    public void setChildDao(IChildDao childDao) {
        this.childDao = childDao;
    }

    private Group createGroupFromResultSet(ResultSet resultSet) throws SQLException, DataException {
        Group group = new Group(resultSet.getInt(ID_COLUMN), resultSet.getString(NAME_COLUMN));
        childDao.getByGroupId(group.getGroupId()).ifPresent(group::setChildren);
        return group;
    }
}
