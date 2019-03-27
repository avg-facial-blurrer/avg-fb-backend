package nl.oose.blackpool.dataAcces;

import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.Services.IIdentificationImageDao;
import nl.oose.blackpool.domain.IdentificationImage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class IdentificationImageDaoImpl extends AbstractBaseDao<IdentificationImage> implements IIdentificationImageDao {
    private final String SQL_GET_IDENTIFICATION_IMAGE_BY_IMAGE_ID = "SELECT * FROM identification_images WHERE image_id = ? LIMIT 1";
    private final String SQL_GET_ALL_IDENTIFICATION_IMAGES = "SELECT * FROM identification_images";
    private final String SQL_GET_ALL_IDENTIFICATION_IMAGES_BY_CHILD_ID = "SELECT * FROM identification_images where child_id = ?";
    private final String SQL_INSERT_IDENTIFICATION_IMAGE = "INSERT INTO identification_images(child_id, image_path) VALUES(?, ?)";
    private final String SQL_UPDATE_IDENTIFICATION_IMAGE = "UPDATE identification_images SET child_id = ?, image_path =? WHERE image_id = ?";
    private final String SQL_DELETE_IDENTIFICATION_IMAGE = "DELETE FROM identification_images WHERE image_id = ?";

    private final String CHILD_ID_COLUMN = "child_id";
    private final String IMAGE_ID_COLUMN = "image_id";
    private final String IMAGE_PATH_COLUMN = "image_path";




    @Override
    public Optional<IdentificationImage> get(int id) throws DataException {
        PreparedStatement statement;
        try{
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GET_IDENTIFICATION_IMAGE_BY_IMAGE_ID);
            statement.setInt(1, id);
            ResultSet resultset = statement.executeQuery();
            if(resultset.first()) {
                IdentificationImage identificationImage = createIdentificationFromResultSet(resultset);
                statement.close();
                return Optional.of(identificationImage);
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
    public List<IdentificationImage> getAll() throws DataException {
        PreparedStatement statement;
        List<IdentificationImage> identificationImageList = new ArrayList<>();
        try{
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GET_ALL_IDENTIFICATION_IMAGES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                identificationImageList.add(createIdentificationFromResultSet(resultSet));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataException();
        } finally {
            this.dbConnector.closeConnection();
        }
        return identificationImageList;
    }


    @Override
    public Optional<List<IdentificationImage>> getAllByChildId(int childId) throws DataException {
        PreparedStatement statement;
        List<IdentificationImage> identificationImageList = new ArrayList<>();
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_GET_ALL_IDENTIFICATION_IMAGES_BY_CHILD_ID);
            statement.setInt(1, childId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                identificationImageList.add(createIdentificationFromResultSet(resultSet));
            }
            if(!identificationImageList.isEmpty()) {
                statement.close();
                return Optional.of(identificationImageList);
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
    public void save(IdentificationImage identificationImage) throws DataException {
        PreparedStatement statement;
        try{
            statement = this.dbConnector.getConnection().prepareStatement(SQL_INSERT_IDENTIFICATION_IMAGE);
            statement.setInt(1,identificationImage.getChildId());
            statement.setString(2,identificationImage.getPathToImage());
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
    public void update(IdentificationImage identificationImage) throws DataException {
        PreparedStatement statement;
        try {
            statement = this.dbConnector.getConnection().prepareStatement(SQL_UPDATE_IDENTIFICATION_IMAGE);
            statement.setInt(1, identificationImage.getChildId());
            statement.setString(2, identificationImage.getPathToImage());
            statement.setInt(3, identificationImage.getImageID());
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
            statement = this.dbConnector.getConnection().prepareStatement(SQL_DELETE_IDENTIFICATION_IMAGE);
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

    private IdentificationImage createIdentificationFromResultSet(ResultSet resultSet) throws SQLException {
        return new IdentificationImage(resultSet.getInt(CHILD_ID_COLUMN), resultSet.getInt(IMAGE_ID_COLUMN), resultSet.getString(IMAGE_PATH_COLUMN));
    }
}


