package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.ListOfFacesDTO;
import nl.oose.blackpool.Exceptions.CouldNotLoadFileException;
import nl.oose.blackpool.Exceptions.IncorrectBase64Exception;

public interface IRecognizeService {
    ListOfFacesDTO recognize(String fullBase64String) throws IncorrectBase64Exception, CouldNotLoadFileException;
}
