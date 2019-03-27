package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.LoginDTO;
import nl.oose.blackpool.DTO.LoginResponseDTO;
import nl.oose.blackpool.DTO.TokenRequestDTO;
import nl.oose.blackpool.DTO.VerifiedTokenResponseDTO;
import nl.oose.blackpool.Services.ILoginService;
import nl.oose.blackpool.Services.LoginService;
import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import java.security.NoSuchAlgorithmException;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CMSControllerTest {

    @Mock
    private ILoginService loginService = mock(LoginService.class);

    @InjectMocks
    private CMSController cmsController;

    private LoginDTO loginDTO;
    private TokenRequestDTO tokenRequestDTO;
    private String token;

    @Before
    public void setup() {
        loginDTO = new LoginDTO();
        loginDTO.setUsername("TestUser");
        loginDTO.setPassword("12345");
        token = randomUUID().toString();
        tokenRequestDTO = new TokenRequestDTO(token);
    }

    @Test
    public void loginTestSuccesResponse() throws NoSuchAlgorithmException {
        //Setup
        when(loginService.login(loginDTO)).thenReturn(new LoginResponseDTO(randomUUID().toString()));
        int expected = Response.Status.OK.getStatusCode();

        //Actual
        int actual = cmsController.login(loginDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void loginTestFailResponse() throws NoSuchAlgorithmException {
        //Setup
        when(loginService.login(loginDTO)).thenReturn(null);
        int expected = Response.Status.BAD_REQUEST.getStatusCode();

        //Actual
        int actual = cmsController.login(loginDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void loginTestThrowsNoSuchAlgorithmException() throws NoSuchAlgorithmException {
        //Setup
        when(loginService.login(loginDTO)).thenThrow(new NoSuchAlgorithmException());
        int expected = HttpStatus.SC_EXPECTATION_FAILED;

        //Actual
        int actual = cmsController.login(loginDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void verifyTokenSuccess() {
        //Setup
        when(loginService.verifyToken(tokenRequestDTO.getToken())).thenReturn(true);
        int expected = Response.Status.OK.getStatusCode();

        //Actual
        int actual = cmsController.verifyToken(tokenRequestDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void verifyTokenFail() {
        //Setup
        tokenRequestDTO.setToken("");
        int expected = Response.Status.NOT_FOUND.getStatusCode();

        //Actual
        int actual = cmsController.verifyToken(tokenRequestDTO).getStatus();

        //Assert
        assertEquals(expected, actual);
    }
}