package nl.oose.blackpool.DTO;

public class CreateChildAndAddToGroupRequest {
    private int groupId;
    private ChildDTO childDTO;

    public CreateChildAndAddToGroupRequest(int groupId, ChildDTO childDTO) {
        this.groupId = groupId;
        this.childDTO = childDTO;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public ChildDTO getChildDTO() {
        return childDTO;
    }

    public void setChildDTO(ChildDTO childDTO) {
        this.childDTO = childDTO;
    }
}
