package nl.oose.blackpool.Services;


import nl.oose.blackpool.DTO.ImageDTO;
import nl.oose.blackpool.DTO.ListOfFacesDTO;
import nl.oose.blackpool.Exceptions.CouldNotLoadFileException;
import nl.oose.blackpool.Exceptions.IncorrectBase64Exception;
import nl.oose.blackpool.Services.adapters.IFaceRecognition;
import nl.oose.blackpool.Services.adapters.OpenCVAdapter;

public class BlurService implements IBlurService{

    private IFaceRecognition faceRecognition = new OpenCVAdapter();

    @Override
    public ImageDTO blur(String uri, ListOfFacesDTO listOfFaces) throws IncorrectBase64Exception, CouldNotLoadFileException {
        return faceRecognition.executeBlur(uri,listOfFaces);
    }
}
