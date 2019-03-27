package nl.oose.blackpool.Services;

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

import java.lang.reflect.Array;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecognizeServiceTest {

    @InjectMocks
    private RecognizeService sut;

    @Mock
    private IFaceRecognition faceRecognitionMock;

    private final static String EMPTY_BASE64 = "";


    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Test
    public void recognizeTestEmptyStringThrowsIncorrectBase64Exception() throws IncorrectBase64Exception, CouldNotLoadFileException {
        // Arrange
        when(faceRecognitionMock.executeRecognition(EMPTY_BASE64)).thenThrow(IncorrectBase64Exception.class);
        // Assert
        exception.expect(IncorrectBase64Exception.class);
        // Act
        sut.recognize(EMPTY_BASE64);
    }

    @Test
    public void recognizeTestCouldNotLoadFileException() throws IncorrectBase64Exception, CouldNotLoadFileException {
        // Arrange
        when(faceRecognitionMock.executeRecognition(EMPTY_BASE64)).thenThrow(CouldNotLoadFileException.class);
        // Assert
        exception.expect(CouldNotLoadFileException.class);
        // Act
        sut.recognize(EMPTY_BASE64);
    }

    @Test
    public void recognizeTestMethodReturnsListOfFacesDTO() throws IncorrectBase64Exception, CouldNotLoadFileException {
        // Arrange
        ListOfFacesDTO expected = new ListOfFacesDTO();
        when(faceRecognitionMock.executeRecognition(EMPTY_BASE64)).thenReturn(new ListOfFacesDTO());
        // Act
        ListOfFacesDTO actual = sut.recognize(EMPTY_BASE64);
        // Assert
        assertEquals(expected.getClass(), actual.getClass());
    }

    // verify method call
    @Test
    public void recognizeTestVerifyMethodIsCalledOnce() throws IncorrectBase64Exception, CouldNotLoadFileException {
        // Arrange
        when(faceRecognitionMock.executeRecognition(EMPTY_BASE64)).thenReturn(new ListOfFacesDTO());
        // Act
        sut.recognize(EMPTY_BASE64);
        // Assert
        verify(faceRecognitionMock, times(1)).executeRecognition(EMPTY_BASE64);
    }
}