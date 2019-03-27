package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.Exceptions.DataException;
import nl.oose.blackpool.Services.ChildService;
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
public class ChildControllerTest {
    @Mock
    private IChildService childServiceMock = mock(ChildService.class);

    @InjectMocks
    private ChildController sut;

    private ChildDTO childDTO;

    private List<ChildDTO> childDTOList = new ArrayList<>();

    @Before
    public void setup() {
        childDTO = new ChildDTO(1,"Fred", "Frans", null);

        childDTOList.add(childDTO);
    }

    @Test
    public void getShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;
        when(childServiceMock.getChild(1)).thenReturn(childDTO);

        //Act
        int actualStatus = sut.getChild(1).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        when(childServiceMock.getChild(1)).thenThrow(new DataException());

        //Act
        int actualStatus = sut.getChild(1).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getAllShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;
        when(childServiceMock.getAllChildren()).thenReturn(childDTOList);

        //Act
        int actualStatus = sut.getAllChildren().getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getAllShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        when(childServiceMock.getAllChildren()).thenThrow(new DataException());

        //Act
        int actualStatus = sut.getAllChildren().getStatus();

        //Assert
        Assert.assertEquals(expectedStatus,actualStatus);
    }

    @Test
    public void addChildShouldReturnStatusCreated() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_CREATED;


        //Act
        int actualStatus = sut.addChild(childDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void addChildShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(childServiceMock).addChild(childDTO);
        

        //Act
        int actualStatus = sut.addChild(childDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void updateChildShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;

        //Act
        int actualStatus = sut.updateChild(childDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void updateChildShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(childServiceMock).updateChild(childDTO);

        //Act
        int actualStatus = sut.updateChild(childDTO).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void deleteChildShouldReturnStatusOk() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_OK;

        //Act
        int actualStatus = sut.deleteChild(childDTO.getId()).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void deleteChildShouldReturnStatus417() throws DataException {
        //Arrange
        int expectedStatus = HttpStatus.SC_EXPECTATION_FAILED;
        doThrow(new DataException()).when(childServiceMock).deleteChild(childDTO.getId());

        //Act
        int actualStatus = sut.deleteChild(childDTO.getId()).getStatus();

        //Assert
        Assert.assertEquals(expectedStatus, actualStatus);

    }
}
