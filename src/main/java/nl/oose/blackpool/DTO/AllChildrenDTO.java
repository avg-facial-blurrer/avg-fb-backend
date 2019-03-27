package nl.oose.blackpool.DTO;

import java.util.List;

public class AllChildrenDTO {
    private List<ChildDTO> childDTOList;

    public AllChildrenDTO(List<ChildDTO> childDTOList) {
        this.childDTOList = childDTOList;
    }

    public List<ChildDTO> getChildDTOList() {
        return childDTOList;
    }

    public void setChildDTOList(List<ChildDTO> childDTOList) {
        this.childDTOList = childDTOList;
    }
}
