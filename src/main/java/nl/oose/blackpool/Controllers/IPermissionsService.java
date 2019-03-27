package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.PermissionsDTO;
import nl.oose.blackpool.Exceptions.DataException;

import java.util.List;

public interface IPermissionsService {

    PermissionsDTO getPermission(int id) throws DataException;

    List<PermissionsDTO> getAllPermissions() throws DataException;

    void addPermission(PermissionsDTO permissionsDTO) throws DataException;

    void updatePermission(PermissionsDTO permissionsDTO) throws DataException;

    void deletePermission(int id) throws DataException;
}
