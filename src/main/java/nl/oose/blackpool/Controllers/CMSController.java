package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.LoginDTO;
import nl.oose.blackpool.DTO.LoginResponseDTO;
import nl.oose.blackpool.DTO.TokenRequestDTO;
import nl.oose.blackpool.DTO.VerifiedTokenResponseDTO;
import nl.oose.blackpool.Services.ILoginService;
import nl.oose.blackpool.domain.Cache;
import org.apache.commons.httpclient.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/cms")
public class CMSController {

    private static final Logger LOGGER = Logger.getLogger(CMSController.class.getName());
    private static Cache CACHE = new Cache();

    @Inject
    private ILoginService loginService;

    @Path("/login")
    @POST
    public Response login(LoginDTO loginDTO) {
        try {
            LoginResponseDTO token = loginService.login(loginDTO);
            if (token != null)
                return Response.ok(token).build();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("/logout")
    @POST
    public Response logout(TokenRequestDTO request) {
        loginService.logout(request.getToken());
        return Response.ok().build();
    }

    @Path("/verifyToken")
    @POST
    public Response verifyToken(TokenRequestDTO request) {
        String token = request.getToken();
        if (token != null && !token.equalsIgnoreCase("")) {
            boolean isVerified = loginService.verifyToken(token);
            return Response.ok(new VerifiedTokenResponseDTO(isVerified)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public static Cache getCache() {
        return CACHE;
    }
}