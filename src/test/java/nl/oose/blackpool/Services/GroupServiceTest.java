package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.DTO.CreateChildAndAddToGroupRequest;
import nl.oose.blackpool.DTO.GroupDTO;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.dataAcces.ChildDaoImpl;
import nl.oose.blackpool.dataAcces.GroupDaoImpl;
import nl.oose.blackpool.domain.Child;
import nl.oose.blackpool.domain.Group;
import nl.oose.blackpool.util.ChildMapper;
import nl.oose.blackpool.util.GroupMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private IGroupDao groupDaoMock = mock(GroupDaoImpl.class);

    @Mock
    private GroupMapper groupMapperMock = mock(GroupMapper.class);

    @Mock
    private IChildDao childDaoMock = mock(ChildDaoImpl.class);

    @Mock
    private ChildMapper childMapperMock = mock(ChildMapper.class);

    @InjectMocks
    private GroupService sut = new GroupService();



    private GroupDTO groupDTO;
    private Group group;
    private ChildDTO childDTO;
    private Child child;
    private CreateChildAndAddToGroupRequest createChildAndAddToGroupRequest;
    private List<GroupDTO> groupDTOList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();

    @Before
    public void setup() {
        groupDTO = new GroupDTO();
        groupDTO.setId(1);
        groupDTO.setGroupName("GroupName");

        group = new Group();
        group.setGroupId(1);
        group.setGroupName("GroupName");

        groupDTOList.add(groupDTO);

        groupList.add(group);

        childDTO = new ChildDTO();
        childDTO.setFirstName("Frans");
        childDTO.setLastName("Kaas");

        child = new Child();
        child.setFirstName("Frans");
        childDTO.setLastName("Kaas");

        createChildAndAddToGroupRequest = new CreateChildAndAddToGroupRequest(1, childDTO);

        sut.setGroupDao(groupDaoMock);
        sut.setGroupMapper(groupMapperMock);
        sut.setChildDao(childDaoMock);
        sut.setChildMapper(childMapperMock);
    }

    @Test
    public void getGroupShouldReturnDTO() throws DataException {
        //Arrange
        when(groupDaoMock.get(1)).thenReturn(Optional.of(group));
        when(groupMapperMock.domainToDTO(group)).thenReturn(groupDTO);
        GroupDTO expectedGroupDTO = groupDTO;

        //Act
        GroupDTO actualGroupDTO = sut.getGroup(1);

        //Assert
        Assert.assertEquals(expectedGroupDTO,actualGroupDTO);
    }

    @Test(expected = DataException.class)
    public void getGroupShouldThrowDataException() throws DataException {
        //Arrange
        when(groupDaoMock.get(1)).thenThrow(new DataException());

        //Act
        sut.getGroup(1);

    }

    @Test
    public void getAllGroupsShouldReturnListDTO() throws DataException {
        //Arrange
        when(groupDaoMock.getAll()).thenReturn(groupList);
        when(groupMapperMock.domainToDTO(groupList)).thenReturn(groupDTOList);
        List<GroupDTO> expectedGroupDTOList = groupDTOList;

        //Act
        List<GroupDTO> actualGroupDTOList = sut.getAllGroups();

        //Assert
        Assert.assertEquals(expectedGroupDTOList,actualGroupDTOList);
    }

    @Test(expected = DataException.class)
    public void getAllGroupsShouldThrowDataException() throws DataException {
        //Arrange
        when(groupDaoMock.getAll()).thenThrow(new DataException());

        //Act
        sut.getAllGroups();
    }

    @Test
    public void addGroupShouldThrowNoException() throws DataException {
        //Act
        sut.addGroup(groupDTO);
    }

    @Test(expected = DataException.class)
    public void addGroupShouldThrowDataException() throws DataException {
        //Arrange
        when(groupMapperMock.DTOToDomain(groupDTO)).thenReturn(group);
        doThrow(new DataException()).when(groupDaoMock).save(group);

        //Act
        sut.addGroup(groupDTO);
    }

    @Test
    public void updateGroupShouldThrowNoException() throws DataException {
        //Act
        sut.updateGroup(groupDTO);
    }

    @Test(expected = DataException.class)
    public void updateGroupShouldThrowDataException() throws DataException {
        //Arrange
        when(groupMapperMock.DTOToDomain(groupDTO)).thenReturn(group);
        doThrow(new DataException()).when(groupDaoMock).update(group);

        //Act
        sut.updateGroup(groupDTO);
    }

    @Test
    public void deleteGroupShouldThrowNoException() throws DataException {
        //Act
        sut.deleteGroup(groupDTO.getId());
    }

    @Test(expected = DataException.class)
    public void deleteGroupShouldThrowDataException() throws DataException {
        //Arrange
        when(groupMapperMock.DTOToDomain(groupDTO)).thenReturn(group);
        doThrow(new DataException()).when(groupDaoMock).delete(group.getGroupId());

        //Act
        sut.deleteGroup(groupDTO.getId());
    }

    @Test
    public void createChildShouldThrowNoException() throws DataException {
        //Act
        sut.createChild(createChildAndAddToGroupRequest);
    }

    @Test(expected = DataException.class)
    public void createChildShouldThrowDataException() throws DataException {
        //Arrange
        when(childMapperMock.DTOToDomain(createChildAndAddToGroupRequest.getChildDTO())).thenReturn(child);
        doThrow(new DataException()).when(childDaoMock).createAndAddToGroup(createChildAndAddToGroupRequest.getGroupId(), child);

        //Act
        sut.createChild(createChildAndAddToGroupRequest);
    }
}
