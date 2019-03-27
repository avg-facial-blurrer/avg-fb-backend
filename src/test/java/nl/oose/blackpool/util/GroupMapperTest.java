package nl.oose.blackpool.util;


import nl.oose.blackpool.DTO.GroupDTO;
import nl.oose.blackpool.domain.Group;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GroupMapperTest {
    private GroupMapper sut = new GroupMapper();

    private Group group;
    private GroupDTO groupDTO;

    private List<Group> groupList = new ArrayList<>();
    private List<GroupDTO> groupDTOList = new ArrayList<>();

    @Before
    public void setup() {
        group = new Group();
        group.setGroupId(1);
        group.setGroupName("GName");

        groupDTO = new GroupDTO();
        groupDTO.setId(1);
        groupDTO.setGroupName("GName");

        groupList.add(group);
        groupDTOList.add(groupDTO);
    }


    @Test
    public void domainToDTOShouldReturnDTO() {
        //Arrange
        GroupDTO expectedGroupDTO = groupDTO;

        //Act
        GroupDTO actuaGroupDTO = sut.domainToDTO(group);

        //Assert
        Assert.assertEquals(expectedGroupDTO.getId(),expectedGroupDTO.getId());
        Assert.assertEquals(expectedGroupDTO.getGroupName(), expectedGroupDTO.getGroupName());
    }

    @Test
    public void listDomainToDTOShouldReturnListDTO() {
        //Arrange
        List<GroupDTO> expectedGroupDTOList = groupDTOList;

        //Act
        List<GroupDTO> actualGroupDTOList = sut.domainToDTO(groupList);

        //Assert
        Assert.assertEquals(expectedGroupDTOList.size(),actualGroupDTOList.size());
    }

    @Test
    public void DTOToDomainShouldReturnDomain() {
        //Arrange
        Group expectedGroup = group;

        //Act
        Group actualGroup = sut.DTOToDomain(groupDTO);

        //Assert
        Assert.assertEquals(expectedGroup.getGroupId(),actualGroup.getGroupId());
        Assert.assertEquals(expectedGroup.getGroupName(),actualGroup.getGroupName());
    }

    @Test
    public void listDTOtoDomainShouldReturnListDomain() {
        //Arrange
        List<Group> expectedGroupList = groupList;

        //Act
        List<Group> actualGroupList = sut.DTOToDomain(groupDTOList);

        //Assert
        Assert.assertEquals(expectedGroupList.size(),actualGroupList.size());

    }
}
