package nl.oose.blackpool.DTO;

import java.util.List;

public class AllPermissionsDTO {
    private List<PermissionsDTO> permissionsDTOList;

    public AllPermissionsDTO(List<PermissionsDTO> permissionsDTOList) {
        this.permissionsDTOList = permissionsDTOList;
    }

    public List<PermissionsDTO> getPermissionsDTOList() {
        return permissionsDTOList;
    }

    public void setPermissionsDTOList(List<PermissionsDTO> permissionsDTOList) {
        this.permissionsDTOList = permissionsDTOList;
    }
}
