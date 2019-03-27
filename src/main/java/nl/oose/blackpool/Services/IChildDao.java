package nl.oose.blackpool.Services;

import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.domain.Child;

import java.util.List;
import java.util.Optional;

public interface IChildDao extends IDao<Child> {
    Optional<List<Child>> getByGroupId(int id) throws DataException;

    void addToGroup(int groupId, Child child) throws DataException;

    void removeFromGroup(int groupId, Child child) throws DataException;

    void createAndAddToGroup(int groupId, Child child) throws DataException;
}
