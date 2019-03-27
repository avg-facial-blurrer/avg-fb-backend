package nl.oose.blackpool.util;


import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.domain.Child;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChildMapperTest {
    private ChildMapper sut = new ChildMapper();

    private Child child;
    private ChildDTO childDTO;

    private List<Child> childList = new ArrayList<>();
    private List<ChildDTO> childDTOList = new ArrayList<>();

    @Before
    public void setup() {
        child = new Child();
        child.setChildId(1);
        child.setLastName("LName");
        child.setFirstName("FName");

        childDTO = new ChildDTO();
        childDTO.setId(1);
        childDTO.setLastName("LName");
        childDTO.setFirstName("FName");

        childList.add(child);
        childDTOList.add(childDTO);
    }


    @Test
    public void domainToDTOShouldReturnDTO() {
        //Arrange
        ChildDTO expectedChildDTO = childDTO;

        //Act
        ChildDTO actualChildDTO = sut.domainToDTO(child);

        //Assert
        Assert.assertEquals(expectedChildDTO.getId(),actualChildDTO.getId());
        Assert.assertEquals(expectedChildDTO.getFirstName(), actualChildDTO.getFirstName());
        Assert.assertEquals(expectedChildDTO.getLastName(), actualChildDTO.getLastName());
    }

    @Test
    public void listDomainToDTOShouldReturnListDTO() {
        //Arrange
        List<ChildDTO> expectedChildDTOList = childDTOList;

        //Act
        List<ChildDTO> actualChildDTOList = sut.domainToDTO(childList);

        //Assert
        Assert.assertEquals(expectedChildDTOList.size(),actualChildDTOList.size());
    }

    @Test
    public void DTOToDomainShouldReturnDomain() {
        //Arrange
        Child expectedChild = child;

        //Act
        Child actualChild = sut.DTOToDomain(childDTO);

        //Assert
        Assert.assertEquals(expectedChild.getChildId(),actualChild.getChildId());
        Assert.assertEquals(expectedChild.getFirstName(),actualChild.getFirstName());
        Assert.assertEquals(expectedChild.getLastName(),actualChild.getLastName());

    }

    @Test
    public void listDTOtoDomainShouldReturnListDomain() {
        //Arrange
        List<Child> expectedChildList = childList;

        //Act
        List<Child> actualChildList = sut.DTOToDomain(childDTOList);

        //Assert
        Assert.assertEquals(expectedChildList.size(),actualChildList.size());

    }
}
