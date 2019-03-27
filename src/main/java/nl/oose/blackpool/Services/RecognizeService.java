package nl.oose.blackpool.Services;

import nl.oose.blackpool.DTO.ListOfFacesDTO;
import nl.oose.blackpool.Exceptions.CouldNotLoadFileException;
import nl.oose.blackpool.Exceptions.IncorrectBase64Exception;
import nl.oose.blackpool.Services.adapters.IFaceRecognition;
import nl.oose.blackpool.Services.adapters.OpenCVAdapter;

public class RecognizeService implements IRecognizeService{

    private IFaceRecognition faceRecognition = new OpenCVAdapter();

    @Override
    public ListOfFacesDTO recognize(String fullBase64String) throws IncorrectBase64Exception, CouldNotLoadFileException {
        return faceRecognition.executeRecognition(fullBase64String);
    }
}
