package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.ImageDTO;
import nl.oose.blackpool.DTO.ListOfFacesDTO;
import nl.oose.blackpool.Exceptions.CouldNotLoadFileException;
import nl.oose.blackpool.Exceptions.IncorrectBase64Exception;
import nl.oose.blackpool.Services.adapters.IFaceRecognition;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlurServiceTest {

    @InjectMocks
    private BlurService sut;

    @Mock
    private IFaceRecognition faceRecognitionMock;

    private ListOfFacesDTO listOfFaces;
    private final static String EMPTY_BASE64 = "";

    @Before
    public void setup () {
        listOfFaces = new ListOfFacesDTO();
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Test
    public void blurTestEmptyStringThrowsIncorrectBase64Exception() throws IncorrectBase64Exception, CouldNotLoadFileException {
        // Arrange
        when(faceRecognitionMock.executeBlur(EMPTY_BASE64,listOfFaces)).thenThrow(IncorrectBase64Exception.class);
        // Assert
        exception.expect(IncorrectBase64Exception.class);
        // Act
        sut.blur(EMPTY_BASE64, listOfFaces);
    }

    @Test
    public void blurTestThrowsCouldNotLoadFileException() throws IncorrectBase64Exception, CouldNotLoadFileException {
        // Arrange
        when(faceRecognitionMock.executeBlur(EMPTY_BASE64,listOfFaces)).thenThrow(CouldNotLoadFileException.class);
        // Assert
        exception.expect(CouldNotLoadFileException.class);
        // Act
        sut.blur(EMPTY_BASE64, listOfFaces);
    }

    @Test
    public void blurTestMethodReturnsImageDTO() throws IncorrectBase64Exception, CouldNotLoadFileException {
        // Arrange
        ImageDTO expected = new ImageDTO();
        when(faceRecognitionMock.executeBlur(EMPTY_BASE64, listOfFaces)).thenReturn(expected);
        // Act
        ImageDTO actual = sut.blur(EMPTY_BASE64,listOfFaces);
        // Assert
        assertEquals(expected.getClass(), actual.getClass());
    }

    // verify method call
    @Test
    public void blurTestVerifyMethodIsCalledOnce() throws IncorrectBase64Exception, CouldNotLoadFileException {
        // Arrange
        when(faceRecognitionMock.executeBlur(EMPTY_BASE64,listOfFaces)).thenReturn(new ImageDTO());
        // Act
        sut.blur(EMPTY_BASE64,listOfFaces);
        // Assert
        verify(faceRecognitionMock, times(1)).executeBlur(EMPTY_BASE64, listOfFaces);
    }
}