package nl.oose.blackpool.DTO;

import java.util.List;

public class AllGroupsDTO {
    private List<GroupDTO> groupDTOList;

    public AllGroupsDTO(List<GroupDTO> groupDTOList) {
        this.groupDTOList = groupDTOList;
    }

    public List<GroupDTO> getGroupDTOList() {
        return groupDTOList;
    }

    public void setGroupDTOList(List<GroupDTO> groupDTOList) {
        this.groupDTOList = groupDTOList;
    }
}
