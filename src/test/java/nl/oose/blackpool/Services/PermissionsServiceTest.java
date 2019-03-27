package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.PermissionsDTO;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.dataAcces.PermissionsDaoImpl;
import nl.oose.blackpool.domain.Permissions;
import nl.oose.blackpool.util.PermissionsMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PermissionsServiceTest {

    @Mock
    private IPermissionsDao permissionsDaoMock = mock(PermissionsDaoImpl.class);

    @Mock
    private PermissionsMapper permissionsMapperMock = mock(PermissionsMapper.class);

    @InjectMocks
    private PermissionsService sut = new PermissionsService();



    private PermissionsDTO permissionsDTO;
    private Permissions permissions;
    private List<PermissionsDTO> permissionsDTOList = new ArrayList<>();
    private List<Permissions> permissionsList = new ArrayList<>();

    @Before
    public void setup() {
        permissionsDTO = new PermissionsDTO();
        permissionsDTO.setId(1);
        permissionsDTO.setSocialMediaPermission(true);
        permissionsDTO.setSchoolPaperPermission(true);
        permissionsDTO.setEnclosedEnvironmentPermission(true);

        permissions = new Permissions();
        permissions.setChildId(1);
        permissions.setSchoolPaperPermission(true);
        permissions.setEnclosedEnvironmentPermission(true);
        permissions.setSocialMediaPermission(true);

        permissionsDTOList.add(permissionsDTO);

        permissionsList.add(permissions);

        sut.setPermissionsDao(permissionsDaoMock);
        sut.setPermissionsMapper(permissionsMapperMock);
    }

    @Test
    public void getPermissionsShouldReturnDTO() throws DataException {
        //Arrange
        when(permissionsDaoMock.get(1)).thenReturn(Optional.of(permissions));
        when(permissionsMapperMock.domainToDTO(permissions)).thenReturn(permissionsDTO);
        PermissionsDTO expectedPermissionsDTO = permissionsDTO;

        //Act
        PermissionsDTO actualPermissionsDTO = sut.getPermission(1);

        //Assert
        Assert.assertEquals(expectedPermissionsDTO,actualPermissionsDTO);
    }

    @Test(expected = DataException.class)
    public void getPermissionsShouldThrowDataException() throws DataException {
        //Arrange
        when(permissionsDaoMock.get(1)).thenThrow(new DataException());

        //Act
        sut.getPermission(1);

    }

    @Test
    public void getAllPermissionsShouldReturnListDTO() throws DataException {
        //Arrange
        when(permissionsDaoMock.getAll()).thenReturn(permissionsList);
        when(permissionsMapperMock.domainToDTO(permissionsList)).thenReturn(permissionsDTOList);
        List<PermissionsDTO> expectedPermissionsDTOList = permissionsDTOList;

        //Act
        List<PermissionsDTO> actualPermissionsDTOList = sut.getAllPermissions();

        //Assert
        Assert.assertEquals(expectedPermissionsDTOList,actualPermissionsDTOList);
    }

    @Test(expected = DataException.class)
    public void getAllPermissionsShouldThrowDataException() throws DataException {
        //Arrange
        when(permissionsDaoMock.getAll()).thenThrow(new DataException());

        //Act
        sut.getAllPermissions();
    }

    @Test
    public void addPermissionShouldThrowNoException() throws DataException {
        //Act
        sut.addPermission(permissionsDTO);
    }

    @Test(expected = DataException.class)
    public void addPermissionShouldThrowDataException() throws DataException {
        //Arrange
        when(permissionsMapperMock.DTOToDomain(permissionsDTO)).thenReturn(permissions);
        doThrow(new DataException()).when(permissionsDaoMock).save(permissions);

        //Act
        sut.addPermission(permissionsDTO);
    }

    @Test
    public void updatePermissionShouldThrowNoException() throws DataException {
        //Act
        sut.updatePermission(permissionsDTO);
    }

    @Test(expected = DataException.class)
    public void updatePermissionShouldThrowDataException() throws DataException {
        //Arrange
        when(permissionsMapperMock.DTOToDomain(permissionsDTO)).thenReturn(permissions);
        doThrow(new DataException()).when(permissionsDaoMock).update(permissions);

        //Act
        sut.updatePermission(permissionsDTO);
    }

    @Test
    public void deletePermissionShouldThrowNoException() throws DataException {
        //Act
        sut.deletePermission(permissionsDTO.getId());
    }

    @Test(expected = DataException.class)
    public void deletePermissionShouldThrowDataException() throws DataException {
        //Arrange
        when(permissionsMapperMock.DTOToDomain(permissionsDTO)).thenReturn(permissions);
        doThrow(new DataException()).when(permissionsDaoMock).delete(permissions.getChildId());

        //Act
        sut.deletePermission(permissionsDTO.getId());
    }
}
