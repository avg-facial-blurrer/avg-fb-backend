package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.CreateChildAndAddToGroupRequest;
import nl.oose.blackpool.DTO.GroupDTO;
import nl.oose.blackpool.Exceptions.DataException;

import java.util.List;

public interface IGroupService {
    GroupDTO getGroup(int id) throws DataException;

    List<GroupDTO> getAllGroups() throws DataException;

    void addGroup(GroupDTO childDTO) throws DataException;

    void updateGroup(GroupDTO childDTO) throws DataException;

    void deleteGroup(int id) throws DataException;

    void createChild(CreateChildAndAddToGroupRequest createChildAndAddToGroupRequest) throws DataException;
}
