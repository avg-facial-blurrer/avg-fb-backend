package nl.oose.blackpool.util;


import nl.oose.blackpool.DTO.PermissionsDTO;
import nl.oose.blackpool.domain.Permissions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PermissionsMapperTest {
    private PermissionsMapper sut = new PermissionsMapper();

    private Permissions permissions;
    private PermissionsDTO permissionsDTO;

    private List<Permissions> permissionsList = new ArrayList<>();
    private List<PermissionsDTO> permissionsDTOList = new ArrayList<>();

    @Before
    public void setup() {
        permissions = new Permissions();
        permissions.setChildId(1);
        permissions.setSocialMediaPermission(true);
        permissions.setEnclosedEnvironmentPermission(true);
        permissions.setSchoolPaperPermission(true);

        permissionsDTO = new PermissionsDTO();
        permissionsDTO.setId(1);
        permissionsDTO.setEnclosedEnvironmentPermission(true);
        permissionsDTO.setSchoolPaperPermission(true);
        permissionsDTO.setSocialMediaPermission(true);

        permissionsList.add(permissions);
        permissionsDTOList.add(permissionsDTO);
    }


    @Test
    public void domainToDTOShouldReturnDTO() {
        //Arrange
        PermissionsDTO expectedPermissionsDTO = permissionsDTO;

        //Act
        PermissionsDTO actualPermissionsDTO = sut.domainToDTO(permissions);

        //Assert
        Assert.assertEquals(expectedPermissionsDTO.getId(),expectedPermissionsDTO.getId());
        Assert.assertEquals(expectedPermissionsDTO.isSchoolPaperPermission(), actualPermissionsDTO.isSchoolPaperPermission());
        Assert.assertEquals(expectedPermissionsDTO.isSocialMediaPermission(), actualPermissionsDTO.isSocialMediaPermission());
        Assert.assertEquals(expectedPermissionsDTO.isEnclosedEnvironmentPermission(), actualPermissionsDTO.isEnclosedEnvironmentPermission());
    }

    @Test
    public void listDomainToDTOShouldReturnListDTO() {
        //Arrange
        List<PermissionsDTO> expectedPermissionsDTOList = permissionsDTOList;

        //Act
        List<PermissionsDTO> actualPermissionsDTOList = sut.domainToDTO(permissionsList);

        //Assert
        Assert.assertEquals(expectedPermissionsDTOList.size(),actualPermissionsDTOList.size());
    }

    @Test
    public void DTOToDomainShouldReturnDomain() {
        //Arrange
        Permissions expectedPermissions = permissions;

        //Act
        Permissions actualPermissions = sut.DTOToDomain(permissionsDTO);

        //Assert
        Assert.assertEquals(expectedPermissions.getChildId(),actualPermissions.getChildId());
        Assert.assertEquals(expectedPermissions.isSocialMediaPermission(),actualPermissions.isSocialMediaPermission());
        Assert.assertEquals(expectedPermissions.isEnclosedEnvironmentPermission(),actualPermissions.isEnclosedEnvironmentPermission());
        Assert.assertEquals(expectedPermissions.isSchoolPaperPermission(),actualPermissions.isSchoolPaperPermission());
    }

    @Test
    public void listDTOtoDomainShouldReturnListDomain() {
        //Arrange
        List<Permissions> expectedPermissionsList = permissionsList;

        //Act
        List<Permissions> actualPermissionsList = sut.DTOToDomain(permissionsDTOList);

        //Assert
        Assert.assertEquals(expectedPermissionsList.size(),actualPermissionsList.size());

    }
}
