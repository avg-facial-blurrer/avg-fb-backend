package nl.oose.blackpool.dataAcces;

import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.Services.IDao;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBaseDao<T> implements IDao<T> {
    protected DbConnector dbConnector;

    @Inject
    public void setDbConnector(DbConnector dbconnector) {
        this.dbConnector = dbconnector;
    }

    public abstract Optional<T> get(int id) throws DataException;

    public abstract List<T> getAll() throws DataException;

    public abstract void save(T t) throws DataException;

    public abstract void update(T t) throws DataException;

    public abstract void delete(int id) throws DataException;

}

