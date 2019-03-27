package nl.oose.blackpool.Services.adapters;

import nl.oose.blackpool.DTO.ImageDTO;
import nl.oose.blackpool.DTO.ListOfFacesDTO;
import nl.oose.blackpool.Exceptions.CouldNotLoadFileException;
import nl.oose.blackpool.Exceptions.IncorrectBase64Exception;

public interface IFaceRecognition {
    ListOfFacesDTO executeRecognition (String uri) throws IncorrectBase64Exception, CouldNotLoadFileException;

    ImageDTO executeBlur(String uri, ListOfFacesDTO listOfFaces) throws IncorrectBase64Exception, CouldNotLoadFileException;
}
