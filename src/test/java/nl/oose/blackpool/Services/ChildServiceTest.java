package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.dataAcces.ChildDaoImpl;
import nl.oose.blackpool.domain.Child;
import nl.oose.blackpool.util.ChildMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ChildServiceTest {

    @Mock
    private IChildDao childDaoMock = mock(ChildDaoImpl.class);

    @Mock
    private ChildMapper childMapperMock = mock(ChildMapper.class);

    @InjectMocks
    private ChildService sut = new ChildService();



    private ChildDTO childDTO;
    private Child child;
    private List<ChildDTO> childDTOList = new ArrayList<>();
    private List<Child> childList = new ArrayList<>();

    @Before
    public void setup() {
        childDTO = new ChildDTO();
        childDTO.setId(1);
        childDTO.setFirstName("Fname");
        childDTO.setLastName("Lname");

        child = new Child();
        child.setChildId(1);
        child.setFirstName("Fname");
        child.setLastName("Lname");

        childDTOList.add(childDTO);

        childList.add(child);

        sut.setChildDao(childDaoMock);
        sut.setChildMapper(childMapperMock);
    }

    @Test
    public void getChildShouldReturnDTO() throws DataException {
        //Arrange
        when(childDaoMock.get(1)).thenReturn(Optional.of(child));
        when(childMapperMock.domainToDTO(child)).thenReturn(childDTO);
        ChildDTO expectedChildDTO = childDTO;

        //Act
        ChildDTO actualChildDTO = sut.getChild(1);

        //Assert
        Assert.assertEquals(expectedChildDTO,actualChildDTO);
    }

    @Test(expected = DataException.class)
    public void getChildShouldThrowDataException() throws DataException {
        //Arrange
        when(childDaoMock.get(1)).thenThrow(new DataException());

        //Act
        sut.getChild(1);

    }

    @Test
    public void getAllChildrenShouldReturnListDTO() throws DataException {
        //Arrange
        when(childDaoMock.getAll()).thenReturn(childList);
        when(childMapperMock.domainToDTO(childList)).thenReturn(childDTOList);
        List<ChildDTO> expectedChildDTOList = childDTOList;

        //Act
        List<ChildDTO> actualChildDTOList = sut.getAllChildren();

        //Assert
        Assert.assertEquals(expectedChildDTOList,actualChildDTOList);
    }

    @Test(expected = DataException.class)
    public void getAllChildrenShouldThrowDataException() throws DataException {
        //Arrange
        when(childDaoMock.getAll()).thenThrow(new DataException());

        //Act
        sut.getAllChildren();
    }

    @Test
    public void addChildShouldThrowNoException() throws DataException {
        //Act
        sut.addChild(childDTO);
    }

    @Test(expected = DataException.class)
    public void addChildShouldThrowDataException() throws DataException {
        //Arrange
        when(childMapperMock.DTOToDomain(childDTO)).thenReturn(child);
        doThrow(new DataException()).when(childDaoMock).save(child);

        //Act
        sut.addChild(childDTO);
    }

    @Test
    public void updateChildShouldThrowNoException() throws DataException {
        //Act
        sut.updateChild(childDTO);
    }

    @Test(expected = DataException.class)
    public void updateChildShouldThrowDataException() throws DataException {
        //Arrange
        when(childMapperMock.DTOToDomain(childDTO)).thenReturn(child);
        doThrow(new DataException()).when(childDaoMock).update(child);

        //Act
        sut.updateChild(childDTO);
    }

    @Test
    public void deleteChildShouldThrowNoException() throws DataException {
        //Act
        sut.deleteChild(childDTO.getId());
    }

    @Test(expected = DataException.class)
    public void deleteChildShouldThrowDataException() throws DataException {
        //Arrange
        when(childMapperMock.DTOToDomain(childDTO)).thenReturn(child);
        doThrow(new DataException()).when(childDaoMock).delete(child.getChildId());

        //Act
        sut.deleteChild(childDTO.getId());
    }
}
