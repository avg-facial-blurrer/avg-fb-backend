package nl.oose.blackpool.dataAcces;

import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.Services.IChildDao;
import nl.oose.blackpool.Services.IIdentificationImageDao;
import nl.oose.blackpool.Services.IPermissionsDao;
import nl.oose.blackpool.domain.Child;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChildDaoImpl extends AbstractBaseDao<Child> implements IChildDao {

    private final String SQL_CHILD_BY_ID = "SELECT * FROM child WHERE child_id = ? LIMIT 1 ";
    private final String SQL_GET_ALL_CHILDREN = "SELECT * FROM child";
    private final String SQL_INSERT_CHILD = "INSERT INTO child(first_name, last_name) VALUES(?, ?)";
    private final String SQL_UPDATE_CHILD = "UPDATE child SET first_name = ?, last_name =? WHERE child_id = ?";
    private final String SQL_DELETE_CHILD =  "DELETE FROM child WHERE child_id = ?";
    private final String SQL_GET_BY_CLASS_ID = "SELECT C.child_id, C.first_name, C.last_name FROM child as C INNER JOIN child_in_class AS CI ON C.child_id = CI.child_id \n" +
            "WHERE CI.class_id = ?";
    private final String SQL_ADD_CHILD_TO_GROUP = "INSERT INTO child_in_class VALUES(?,?)";
    private final String SQL_REMOVE_CHILD_FROM_GROUP = "DELETE FROM child_in_class WHERE class_id = ? AND child_id = ?";
    private final String SQL_CREATE_CHILD_AND_ADD_TO_GROUP = "CALL pr_create_child_and_add_to_group(?, ?, ?)";

    private final String CHILD_ID_COLUMN = "child_id";
    private final String FIRST_NAME_COLUMN = "first_name";
    private final String LAST_NAME_COLUMN = "last_name";

    private IPermissionsDao permissionsDao;
    private IIdentificationImageDao identificationImageDao;


    @Override
    public Optional<Child> get(int id) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_CHILD_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.first()) {
                Child child = createChildFromResultSet(resultSet);
                statement.close();
                return Optional.of(child);
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
    public List<Child> getAll() throws DataException {
        PreparedStatement statement;
        List<Child> childList = new ArrayList<>();
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GET_ALL_CHILDREN);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                childList.add(createChildFromResultSet(resultSet));
            }
            statement.close();
            return childList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException();
        } finally {
            this.dbConnector.closeConnection();
        }
    }


    @Override
    public void save(Child child) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_INSERT_CHILD);
            statement.setString(1,child.getFirstName());
            statement.setString(2,child.getLastName());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException();
        } finally {
            this.dbConnector.closeConnection();
        }

    }

    @Override
    public void update(Child child) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_UPDATE_CHILD);
            statement.setString(1, child.getFirstName());
            statement.setString(2, child.getLastName());
            statement.setInt(3, child.getChildId());
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
            statement = this.dbConnector.getConnection().prepareStatement(SQL_DELETE_CHILD);
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

    @Override
    public Optional<List<Child>> getByGroupId(int id) throws DataException {
        PreparedStatement statement;
        List<Child> children = new ArrayList<>();
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GET_BY_CLASS_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                children.add(createChildFromResultSet(resultSet));
            }
            if(!children.isEmpty()) {
                statement.close();
                return Optional.of(children);
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
    public void addToGroup(int groupId, Child child) throws DataException {
        PreparedStatement statement;
        try{
            statement = this.dbConnector.getConnection().prepareStatement(SQL_ADD_CHILD_TO_GROUP);
            statement.setInt(1, groupId);
            statement.setInt(2,child.getChildId());
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
    public void removeFromGroup(int groupId, Child child) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_REMOVE_CHILD_FROM_GROUP);
            statement.setInt(1, groupId);
            statement.setInt(2, child.getChildId());
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
    public void createAndAddToGroup(int groupId, Child child) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_CREATE_CHILD_AND_ADD_TO_GROUP);
            statement.setInt(1, groupId);
            statement.setString(2, child.getFirstName());
            statement.setString(3, child.getLastName());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException();
        } finally {
            this.dbConnector.closeConnection();
        }
    }

    @Inject
    public void setPermissionsDao(IPermissionsDao permissionsDao) {
        this.permissionsDao = permissionsDao;
    }
    @Inject
    public void setIdentificationImageDao(IIdentificationImageDao identificationImageDao) {
        this.identificationImageDao = identificationImageDao;
    }

    private Child createChildFromResultSet(ResultSet resultSet) throws SQLException, DataException {
        Child child = new Child(resultSet.getInt(CHILD_ID_COLUMN), resultSet.getString(FIRST_NAME_COLUMN), resultSet.getString(LAST_NAME_COLUMN));
        permissionsDao.get(child.getChildId()).ifPresent(child::setPermissions);
        identificationImageDao.getAllByChildId(child.getChildId()).ifPresent(child::setIdentificationImages);
        return child;
    }


}
