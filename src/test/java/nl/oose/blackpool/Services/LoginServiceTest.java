package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.LoginDTO;
import nl.oose.blackpool.DTO.LoginResponseDTO;
import nl.oose.blackpool.DTO.TokenRequestDTO;
import nl.oose.blackpool.domain.Cache;
import nl.oose.blackpool.domain.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    private ILoginService loginService = mock(LoginService.class);

    private LoginDTO loginDTO;
    private User user;
    private LoginResponseDTO loginResponseDTO;
    private TokenRequestDTO tokenRequestDTO;
    private Cache cache;
    private String token;

    @Before
    public void setup() {
        cache = new Cache();

        token = randomUUID().toString();
        loginDTO = new LoginDTO();
        loginDTO.setUsername("TestUser");
        loginDTO.setPassword("12345");
        loginResponseDTO = new LoginResponseDTO(token);
        tokenRequestDTO = new TokenRequestDTO(token);
        cache.addToken(1, token);
        user = new User(1, "TestUser");
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Test
    public void loginTestThrowsNoSuchAlgorithmException() throws NoSuchAlgorithmException {
        //Setup
        when(loginService.login(loginDTO)).thenThrow(NoSuchAlgorithmException.class);

        //Actual
        exception.expect(NoSuchAlgorithmException.class);

        //Assert
        loginService.login(loginDTO);
    }

    @Test
    public void loginTestReturnTokenSucces() throws NoSuchAlgorithmException {
        //Setup
        when(loginService.login(loginDTO)).thenReturn(loginResponseDTO);
        boolean expected = true;

        //Actual
        boolean actual = loginService.login(loginDTO).getToken() != "";

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void loginTestReturnTokenFail() throws NoSuchAlgorithmException {
        //Setup
        when(loginService.login(loginDTO)).thenReturn(new LoginResponseDTO(""));
        boolean expected = true;

        //Actual
        boolean actual = loginService.login(loginDTO).getToken() == "";

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void verifyTokenSucces() {
        //Setup
        when(loginService.verifyToken(token)).thenReturn(true);
        boolean expected = true;

        //Actual
        boolean actual = loginService.verifyToken(token);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void verifyTokenFail() {
        //Setup
        when(loginService.verifyToken(token)).thenReturn(false);
        boolean expected = false;

        //Actual
        boolean actual = loginService.verifyToken(token);

        //Assert
        assertEquals(expected, actual);
    }
}