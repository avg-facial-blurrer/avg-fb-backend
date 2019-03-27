package nl.oose.blackpool.util;

import nl.oose.blackpool.DTO.GroupDTO;
import nl.oose.blackpool.domain.Group;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GroupMapper extends Mapper<Group, GroupDTO> {
    private ChildMapper childMapper;

    @Override
    public GroupDTO domainToDTO(Group group) {
        GroupDTO groupDTOToReturn = new GroupDTO();
        groupDTOToReturn.setId(group.getGroupId());
        groupDTOToReturn.setGroupName(group.getGroupName());
        if(group.getChildren() != null) {
            groupDTOToReturn.setChildren(childMapper.domainToDTO(group.getChildren()));
        }
        return groupDTOToReturn;
    }

    @Override
    public Group DTOToDomain(GroupDTO groupDTO) {
        Group groupToReturn = new Group();
        groupToReturn.setGroupName(groupDTO.getGroupName());
        groupToReturn.setGroupId(groupDTO.getId());
        return groupToReturn;
    }

    @Override
    public List<GroupDTO> domainToDTO(List<Group> t) {
        List<GroupDTO> groupDTOListToReturn = new ArrayList<>();
        for (Group group:t) {
            groupDTOListToReturn.add(domainToDTO(group));
        }
        return groupDTOListToReturn;
    }

    @Override
    public List<Group> DTOToDomain(List<GroupDTO> groupDTOS) {
        List<Group> groupsToReturn = new ArrayList<>();
        for(GroupDTO groupDTO: groupDTOS) {
            groupsToReturn.add(DTOToDomain(groupDTO));
        }
        return groupsToReturn;
    }

    @Inject
    public void setChildMapper(ChildMapper childMapper) {
        this.childMapper = childMapper;
    }
}
