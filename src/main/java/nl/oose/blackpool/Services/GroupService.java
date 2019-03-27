package nl.oose.blackpool.Services;

import nl.oose.blackpool.Controllers.IGroupService;
import nl.oose.blackpool.DTO.CreateChildAndAddToGroupRequest;
import nl.oose.blackpool.DTO.GroupDTO;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.util.ChildMapper;
import nl.oose.blackpool.util.GroupMapper;

import javax.inject.Inject;
import java.util.List;

public class GroupService implements IGroupService {
    private GroupMapper groupMapper;
    private IGroupDao groupDao;
    private IChildDao childDao;
    private ChildMapper childMapper;

    @Override
    public GroupDTO getGroup(int id) throws DataException {
        return groupMapper.domainToDTO(groupDao.get(id).orElseThrow(DataException::new));
    }

    @Override
    public List<GroupDTO> getAllGroups() throws DataException {
        return groupMapper.domainToDTO(groupDao.getAll());
    }

    @Override
    public void addGroup(GroupDTO groupDTO) throws DataException {
        groupDao.save(groupMapper.DTOToDomain(groupDTO));
    }

    @Override
    public void updateGroup(GroupDTO groupDTO) throws DataException {
        groupDao.update(groupMapper.DTOToDomain(groupDTO));
    }

    @Override
    public void deleteGroup(int id) throws DataException {
        groupDao.delete(id);
    }

    @Override
    public void createChild(CreateChildAndAddToGroupRequest createChildAndAddToGroupRequest) throws DataException {
        childDao.createAndAddToGroup(createChildAndAddToGroupRequest.getGroupId(), childMapper.DTOToDomain(createChildAndAddToGroupRequest.getChildDTO()));
    }

    @Inject
    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Inject
    public void setGroupDao(IGroupDao groupDao) {
        this.groupDao = groupDao;
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
