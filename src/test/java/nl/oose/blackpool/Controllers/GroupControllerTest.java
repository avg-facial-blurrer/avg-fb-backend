package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.DTO.CreateChildAndAddToGroupRequest;
import nl.oose.blackpool.DTO.GroupDTO;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.Services.GroupService;
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
public class GroupControllerTest {
    @Mock
    private IGroupService groupServiceMock = mock(GroupService.class);

    @InjectMocks
    private GroupController sut;

    private GroupDTO groupDTO;

    private ChildDTO childDTO;

    private CreateChildAndAddToGroupRequest createChildAndAddToGroupRequest;

    private List<GroupDTO> groupDTOList = new ArrayList<>();

    @Before
    public void setup() {
        groupDTO = new GroupDTO(1,"groupname");

        groupDTOList.add(groupDTO);

        childDTO = new ChildDTO();

        childDTO.setFirstName("Frans");
        childDTO.setLastName("Kaas");

        createChildAndAddToGroupRequest = new CreateChildAndAddToGroupRequest(1, childDTO);
    }

    @Test
    public void getShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;
        when(groupServiceMock.getGroup(1)).thenReturn(groupDTO);

        //Act
        int actualStatus = sut.getGroup(1).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        when(groupServiceMock.getGroup(1)).thenThrow(new DataException());

        //Act
        int actualStatus = sut.getGroup(1).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getAllShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;
        when(groupServiceMock.getAllGroups()).thenReturn(groupDTOList);

        //Act
        int actualStatus = sut.getAllGroups().getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getAllShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        when(groupServiceMock.getAllGroups()).thenThrow(new DataException());

        //Act
        int actualStatus = sut.getAllGroups().getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void addGroupShouldReturnStatusCreated() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_CREATED;

        //Act
        int actualStatus = sut.addGroup(groupDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void addGroupShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(groupServiceMock).addGroup(groupDTO);
        //Act
        int actualStatus = sut.addGroup(groupDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void deleteShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;

        //Act
        int actualStatus = sut.deleteGroup(groupDTO.getId()).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void deleteShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(groupServiceMock).deleteGroup(groupDTO.getId());

        //Act
        int actualStatus = sut.deleteGroup(groupDTO.getId()).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void updateShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;

        //Act
        int actualStatus = sut.updateGroup(groupDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void updateShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(groupServiceMock).updateGroup(groupDTO);

        //Act
        int actualStatus = sut.updateGroup(groupDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void createChildShouldReturnStatusCreated() {
        //Arrange
        int expectedStatus = HttpStatus.SC_CREATED;

        //Act
        int actualStatus = sut.createChildAndAddToGroup(createChildAndAddToGroupRequest).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void createChildShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(groupServiceMock).createChild(createChildAndAddToGroupRequest);

        //Act
        int actualStatus = sut.createChildAndAddToGroup(createChildAndAddToGroupRequest).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }
}
