package nl.oose.blackpool.DTO;

import java.util.List;

public class GroupDTO {
    private int id;
    private String groupName;
    private List<ChildDTO> children;

    public GroupDTO(int id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public GroupDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ChildDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ChildDTO> children) {
        this.children = children;
    }
}
