package nl.oose.blackpool.util;

import nl.oose.blackpool.DTO.PermissionsDTO;
import nl.oose.blackpool.domain.Permissions;

import java.util.ArrayList;
import java.util.List;

public class PermissionsMapper extends Mapper<Permissions, PermissionsDTO> {
    @Override
    public PermissionsDTO domainToDTO(Permissions permissions) {
        PermissionsDTO permissionsDTOToReturn = new PermissionsDTO();
        permissionsDTOToReturn.setId(permissions.getChildId());
        permissionsDTOToReturn.setEnclosedEnvironmentPermission(permissions.isEnclosedEnvironmentPermission());
        permissionsDTOToReturn.setSchoolPaperPermission(permissions.isSchoolPaperPermission());
        permissionsDTOToReturn.setSocialMediaPermission(permissions.isSocialMediaPermission());
        return permissionsDTOToReturn;
    }

    @Override
    public Permissions DTOToDomain(PermissionsDTO permissionsDTO) {
        Permissions permissionsToReturn = new Permissions();
        permissionsToReturn.setSocialMediaPermission(permissionsDTO.isSocialMediaPermission());
        permissionsToReturn.setEnclosedEnvironmentPermission(permissionsDTO.isSchoolPaperPermission());
        permissionsToReturn.setSchoolPaperPermission(permissionsDTO.isSchoolPaperPermission());
        permissionsToReturn.setChildId(permissionsDTO.getId());
        return permissionsToReturn;
    }

    @Override
    public List<PermissionsDTO> domainToDTO(List<Permissions> t) {
        List<PermissionsDTO> permissionsDTOSToReturn = new ArrayList<>();
        for(Permissions permissions:t) {
            permissionsDTOSToReturn.add(domainToDTO(permissions));
        }
        return permissionsDTOSToReturn;
    }

    @Override
    public List<Permissions> DTOToDomain(List<PermissionsDTO> permissionsDTOS) {
        List<Permissions> permissionsToReturn = new ArrayList<>();
        for(PermissionsDTO permissionsDTO:permissionsDTOS) {
            permissionsToReturn.add(DTOToDomain(permissionsDTO));
        }
        return permissionsToReturn;
    }
}
