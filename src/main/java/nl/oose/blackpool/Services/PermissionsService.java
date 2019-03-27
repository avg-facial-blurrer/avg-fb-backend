package nl.oose.blackpool.Services;

import nl.oose.blackpool.Controllers.IPermissionsService;
import nl.oose.blackpool.DTO.PermissionsDTO;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.util.PermissionsMapper;

import javax.inject.Inject;
import java.util.List;

public class PermissionsService implements IPermissionsService {

    private PermissionsMapper permissionsMapper;
    private IPermissionsDao permissionsDao;

    @Override
    public PermissionsDTO getPermission(int id) throws DataException {
        return permissionsMapper.domainToDTO(permissionsDao.get(id).orElseThrow(DataException::new));
    }

    @Override
    public List<PermissionsDTO> getAllPermissions() throws DataException {
        return permissionsMapper.domainToDTO(permissionsDao.getAll());
    }

    @Override
    public void addPermission(PermissionsDTO permissionsDTO) throws DataException {
        permissionsDao.save(permissionsMapper.DTOToDomain(permissionsDTO));
    }

    @Override
    public void updatePermission(PermissionsDTO permissionsDTO) throws DataException {
        permissionsDao.update(permissionsMapper.DTOToDomain(permissionsDTO));
    }

    @Override
    public void deletePermission(int id) throws DataException {
        permissionsDao.delete(id);
    }

    @Inject
    public void setPermissionsMapper(PermissionsMapper permissionsMapper) {
        this.permissionsMapper = permissionsMapper;
    }

    @Inject
    public void setPermissionsDao(IPermissionsDao permissionsDao) {
        this.permissionsDao = permissionsDao;
    }
}
