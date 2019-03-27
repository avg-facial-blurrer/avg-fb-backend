package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.DTO.PermissionsDTO;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.Services.ChildService;
import nl.oose.blackpool.Services.PermissionsService;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PermissionsControllerTest {
    @Mock
    private IPermissionsService permissionsServiceMock = mock(PermissionsService.class);

    @InjectMocks
    private PermissionsController sut;

    private PermissionsDTO permissionsDTO;

    private List<PermissionsDTO> permissionsDTOList = new ArrayList<>();

    @Before
    public void setup() {
        permissionsDTO = new PermissionsDTO(1,true, false, true);

        permissionsDTOList.add(permissionsDTO);
    }

    @Test
    public void getShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;
        when(permissionsServiceMock.getPermission(1)).thenReturn(permissionsDTO);

        //Act
        int actualStatus = sut.getPermissionsById(1).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        when(permissionsServiceMock.getPermission(1)).thenThrow(new DataException());

        //Act
        int actualStatus = sut.getPermissionsById(1).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getAllShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;
        when(permissionsServiceMock.getAllPermissions()).thenReturn(permissionsDTOList);

        //Act
        int actualStatus = sut.getAllPermissions().getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getAllShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        when(permissionsServiceMock.getAllPermissions()).thenThrow(new DataException());

        //Act
        int actualStatus = sut.getAllPermissions().getStatus();

        //Assert
        Assert.assertEquals(expectedStatus,actualStatus);
    }

    @Test
    public void addChildShouldReturnStatusCreated() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_CREATED;


        //Act
        int actualStatus = sut.addPermission(permissionsDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void addChildShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(permissionsServiceMock).addPermission(permissionsDTO);


        //Act
        int actualStatus = sut.addPermission(permissionsDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void updateChildShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;

        //Act
        int actualStatus = sut.updatePermission(permissionsDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void updateChildShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(permissionsServiceMock).updatePermission(permissionsDTO);

        //Act
        int actualStatus = sut.updatePermission(permissionsDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void deleteChildShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;

        //Act
        int actualStatus = sut.deletePermission(permissionsDTO.getId()).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void deleteChildShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(permissionsServiceMock).deletePermission(permissionsDTO.getId());

        //Act
        int actualStatus = sut.deletePermission(permissionsDTO.getId()).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }
}
