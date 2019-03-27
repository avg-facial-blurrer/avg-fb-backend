package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.ImageDTO;
import nl.oose.blackpool.DTO.ListOfFacesDTO;
import nl.oose.blackpool.Exceptions.CouldNotLoadFileException;
import nl.oose.blackpool.Exceptions.IncorrectBase64Exception;

public interface IBlurService {
    ImageDTO blur(String uri, ListOfFacesDTO listOfFaces)throws IncorrectBase64Exception, CouldNotLoadFileException;
}
