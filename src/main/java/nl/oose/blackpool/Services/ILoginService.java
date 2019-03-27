package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.LoginDTO;
import nl.oose.blackpool.DTO.LoginResponseDTO;
import nl.oose.blackpool.domain.Cache;

import java.security.NoSuchAlgorithmException;

public interface ILoginService {
    LoginResponseDTO login(LoginDTO loginDTO) throws NoSuchAlgorithmException;
    boolean verifyToken(String token);
    void logout(String token);
}