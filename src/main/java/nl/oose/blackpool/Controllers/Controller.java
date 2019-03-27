package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.ImageDTO;
import nl.oose.blackpool.DTO.ListOfFacesDTO;
import nl.oose.blackpool.DTO.ScannedImageDTO;
import nl.oose.blackpool.Exceptions.CouldNotLoadFileException;
import nl.oose.blackpool.Exceptions.IncorrectBase64Exception;
import nl.oose.blackpool.Services.BlurService;
import nl.oose.blackpool.Services.RecognizeService;
import org.apache.commons.httpclient.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import java.util.logging.Level;

@Path("/faceRecognition")
public class Controller {

    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    @Inject
    private RecognizeService recognizeService;

    @Inject
    private BlurService blurService;


    @Path("/recognize")
    @POST
    public Response recognizeFaces (ImageDTO image) {
        try {
            ListOfFacesDTO listOfFacesDTO = recognizeService.recognize(image.getImg());
            if (listOfFacesDTO != null) {
                return Response.ok(listOfFacesDTO).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        } catch (IncorrectBase64Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        } catch (CouldNotLoadFileException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/edit")
    @POST
    public Response editFaces (ScannedImageDTO scannedImage) {
        try {
        return Response.ok(blurService.blur(scannedImage.getImg(), scannedImage.getListOfFaces())).build();
        } catch (IncorrectBase64Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        } catch (CouldNotLoadFileException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }
}