package nl.oose.blackpool.Services;

import nl.oose.blackpool.Controllers.CMSController;
import nl.oose.blackpool.DTO.LoginDTO;
import nl.oose.blackpool.DTO.LoginResponseDTO;
import nl.oose.blackpool.domain.Cache;
import nl.oose.blackpool.domain.User;

import javax.inject.Inject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.util.UUID.randomUUID;

public class LoginService implements ILoginService {

    @Inject
    private ILoginDao loginDao;

    public LoginResponseDTO login(LoginDTO loginDTO) throws NoSuchAlgorithmException {
        String hashPassword = hashPassword(loginDTO.getPassword());
        loginDTO.setPassword(hashPassword);
        User user = loginDao.login(loginDTO).orElse(null);
        String token = "";
        if (user != null) {
            token = randomUUID().toString();
            CMSController.getCache().addToken(user.getId(), token);
        }
        return new LoginResponseDTO(token);
    }

    public void logout(String token) {
        Cache cache = CMSController.getCache();
        int id = cache.getUserId(token);
        cache.revokeToken(id);
    }

    public boolean verifyToken(String token) {
        return CMSController.getCache().verifyToken(token);
    }

    private String hashPassword(String currentPassword) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        byte[] passwordBytes = messageDigest.digest(currentPassword.getBytes());

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < passwordBytes.length; i++) {
            stringBuilder.append(Integer.toString((passwordBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }
}