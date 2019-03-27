package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.LoginDTO;
import nl.oose.blackpool.domain.User;

import java.util.Optional;

public interface ILoginDao extends IDao<User> {
    Optional<User> login(LoginDTO loginDTO);
}