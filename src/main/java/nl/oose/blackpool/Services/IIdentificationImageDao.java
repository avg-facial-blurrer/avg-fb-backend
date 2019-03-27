package nl.oose.blackpool.Services;

import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.domain.IdentificationImage;

import java.util.List;
import java.util.Optional;

public interface IIdentificationImageDao extends IDao<IdentificationImage> {
    Optional<List<IdentificationImage>> getAllByChildId(int childId) throws DataException;
}
