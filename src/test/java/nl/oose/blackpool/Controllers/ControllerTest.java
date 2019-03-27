package nl.oose.blackpool.Controllers;


import nl.oose.blackpool.DTO.Face;
import nl.oose.blackpool.DTO.ImageDTO;
import nl.oose.blackpool.DTO.ListOfFacesDTO;
import nl.oose.blackpool.DTO.ScannedImageDTO;
import nl.oose.blackpool.Exceptions.CouldNotLoadFileException;
import nl.oose.blackpool.Exceptions.IncorrectBase64Exception;
import nl.oose.blackpool.Services.BlurService;
import nl.oose.blackpool.Services.RecognizeService;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    private RecognizeService recognizeService = mock(RecognizeService.class);

    @Mock
    private BlurService blurService = mock(BlurService.class);

    @InjectMocks
    private Controller sut;


    private ImageDTO imageDTO;

    private ScannedImageDTO scannedImageDTO;

    private ListOfFacesDTO listOfFaces;

    @Before
    public void setup (){
        Face f1 = new Face(1,1,5,5,true);
        Face f2 = new Face(2,2,10,10,true);
        listOfFaces = new ListOfFacesDTO();
        listOfFaces.addFaceToListOfFaces(f1);
        listOfFaces.addFaceToListOfFaces(f2);

        imageDTO = new ImageDTO("");
        scannedImageDTO = new ScannedImageDTO("",listOfFaces);
    }

    @Test
    public void recognizeFacesTestEmptyHaarcascadeReturnsStatusNO_CONTENT() throws IncorrectBase64Exception, CouldNotLoadFileException {
        //Arrange
        when(recognizeService.recognize(imageDTO.getImg())).thenReturn(null);
        int expected = Response.Status.NO_CONTENT.getStatusCode();

        //Act
        int actual = sut.recognizeFaces(imageDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void recognizeFacesTestResponseContext () throws IncorrectBase64Exception, CouldNotLoadFileException {
        //Arrange
        when(recognizeService.recognize(imageDTO.getImg())).thenReturn(listOfFaces);

        //Act
        Response response = sut.recognizeFaces(imageDTO);
        Object entity = response.getEntity();

        //Assert
        assertEquals(listOfFaces, entity);
    }

    @Test
    public void recognizeFacesTestIncorrectBase64Exception () throws IncorrectBase64Exception, CouldNotLoadFileException {
        //Arrange
        when(recognizeService.recognize(imageDTO.getImg())).thenThrow(new IncorrectBase64Exception());
        int expected = HttpStatus.SC_EXPECTATION_FAILED;

        //Act
        int actual = sut.recognizeFaces(imageDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void recognizeFacesTestCouldNotLoadFileException () throws IncorrectBase64Exception, CouldNotLoadFileException {
        //Arrange
        when(recognizeService.recognize(imageDTO.getImg())).thenThrow(new CouldNotLoadFileException());
        int expected = HttpStatus.SC_INTERNAL_SERVER_ERROR;

        //Act
        int actual = sut.recognizeFaces(imageDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void editFacesTestStatusOk() throws IncorrectBase64Exception, CouldNotLoadFileException {
        //Arrange
        when(blurService.blur(scannedImageDTO.getImg(),scannedImageDTO.getListOfFaces())).thenReturn(null);
        int expected = Response.Status.OK.getStatusCode();

        //Act
        int actual = sut.editFaces(scannedImageDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void editFacesTestResponseContext () throws IncorrectBase64Exception, CouldNotLoadFileException {
        //Arrange
        ImageDTO img = new ImageDTO();
        img.setImg(scannedImageDTO.getImg());
        when(blurService.blur(scannedImageDTO.getImg(),scannedImageDTO.getListOfFaces())).thenReturn(img);

        //Act
        Response response = sut.editFaces(scannedImageDTO);
        Object entity = response.getEntity();

        //Assert
        assertEquals(img, entity);
    }

    @Test
    public void editFacesTestIncorrectBase64Exception () throws IncorrectBase64Exception, CouldNotLoadFileException {
        //Arrange
        when(blurService.blur(scannedImageDTO.getImg(),scannedImageDTO.getListOfFaces())).thenThrow(new IncorrectBase64Exception());
        int expected = HttpStatus.SC_EXPECTATION_FAILED;

        //Act
        int actual = sut.editFaces(scannedImageDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void editFacesTestCouldNotLoadFileException () throws IncorrectBase64Exception, CouldNotLoadFileException {
        //Arrange
        when(blurService.blur(scannedImageDTO.getImg(),scannedImageDTO.getListOfFaces())).thenThrow(new CouldNotLoadFileException());
        int expected = HttpStatus.SC_INTERNAL_SERVER_ERROR;

        //Act
        int actual = sut.editFaces(scannedImageDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

}