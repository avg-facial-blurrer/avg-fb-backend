package nl.oose.blackpool.Services;

import nl.oose.blackpool.Controllers.IChildService;
import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.util.ChildMapper;

import javax.inject.Inject;
import java.util.List;

public class ChildService implements IChildService {
    private IChildDao childDao;

    private ChildMapper childMapper;

    @Override
    public ChildDTO getChild(int id) throws DataException {
        return childMapper.domainToDTO(childDao.get(id).orElseThrow(DataException::new));
    }

    @Override
    public List<ChildDTO> getAllChildren() throws DataException {
        return childMapper.domainToDTO(childDao.getAll());
    }

    @Override
    public void addChild(ChildDTO childDTO) throws DataException {
        childDao.save(childMapper.DTOToDomain(childDTO));
    }

    @Override
    public void updateChild(ChildDTO childDTO) throws DataException {
        childDao.update(childMapper.DTOToDomain(childDTO));
    }

    @Override
    public void deleteChild(int id) throws DataException {
        childDao.delete(id);
    }


    @Inject
    public void setChildDao(IChildDao childDao) {
        this.childDao = childDao;
    }

    @Inject
    public void setChildMapper(ChildMapper childMapper) {
        this.childMapper = childMapper;
    }
}
