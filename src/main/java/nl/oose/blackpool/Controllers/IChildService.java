package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.Exceptions.DataException;

import java.util.List;

public interface IChildService {

    ChildDTO getChild(int id) throws DataException;

    List<ChildDTO> getAllChildren() throws DataException;

    void addChild(ChildDTO childDTO) throws DataException;

    void updateChild(ChildDTO childDTO) throws DataException;

    void deleteChild(int id) throws DataException;

}
