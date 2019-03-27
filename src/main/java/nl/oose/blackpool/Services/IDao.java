package nl.oose.blackpool.Services;

import nl.oose.blackpool.Exceptions.DataException;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {
    Optional<T> get(int id) throws DataException;

    List<T> getAll() throws DataException;

    void save(T t) throws DataException;

    void update(T t) throws DataException;

    void delete(int id) throws DataException;
}
