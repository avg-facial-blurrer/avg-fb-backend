package nl.oose.blackpool.Services.adapters;

import nl.oose.blackpool.DTO.ImageDTO;
import nl.oose.blackpool.Exceptions.CouldNotLoadFileException;
import org.bytedeco.javacpp.opencv_java;
import nl.oose.blackpool.DTO.Face;
import nl.oose.blackpool.DTO.ListOfFacesDTO;
import nl.oose.blackpool.Exceptions.IncorrectBase64Exception;
import org.bytedeco.javacpp.Loader;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import javax.xml.bind.DatatypeConverter;

import static org.opencv.imgproc.Imgproc.GaussianBlur;

public class OpenCVAdapter implements IFaceRecognition {

    static {
        Loader.load(opencv_java.class);
    }

    private static final String CASCADE_NAME = "/haarcascade_frontalface_alt.xml";
    private static final String HAARCASCADE = OpenCVAdapter.class.getResource(CASCADE_NAME).getPath();
    // If you're using windows replace the line above with the following: private final static String HAARCASCADE = OpenCVAdapter.class.getResource(CASCADE_NAME).getPath().substring(1);
    private static final String BASE64_PRESET = "data:image/jpeg;base64,";
    private CascadeClassifier faceCascade = new CascadeClassifier();

    @Override
    public ListOfFacesDTO executeRecognition(String uri) throws IncorrectBase64Exception, CouldNotLoadFileException {
        checkIfOpenCVIsLoaded();
        Mat image = uriToImage(uri);
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(image, faces, 1.1, 2,
                Objdetect.CASCADE_SCALE_IMAGE, new Size(0, 0), new Size());
        return matOfRectToListOfFacesDTO(faces);

    }

    @Override
    public ImageDTO executeBlur(String uri, ListOfFacesDTO listOfFaces) throws IncorrectBase64Exception, CouldNotLoadFileException {
        checkIfOpenCVIsLoaded();
        Mat img = uriToImage(uri);
        for (Face face : listOfFaces.getListOfFaces()) {
            if (face.isToBlur()) {
                Rect roi;
                float width = getOddNumber(face.getWidth());
                float height = getOddNumber(face.getHeight());
                roi = new Rect(new Point(face.getX(), face.getY()), new Point(face.getX() + face.getWidth(), face.getY() + face.getHeight()));
                Mat submat = img.submat(roi);
                GaussianBlur(submat, submat, new Size(width, height), 55);
                img.copyTo(submat);
            }
        }
        ImageDTO image = new ImageDTO();
        image.setImg(imageToUri(img));
        return image;
    }

    private float getOddNumber(float number) {
        if (number % 2 == 0) {
            return number + 1;
        } else {
            return number;
        }
    }

    private String imageToUri(Mat img) {
        MatOfByte cvData = new MatOfByte();
        Imgcodecs.imencode(".jpeg", img, cvData);
        byte[] data = cvData.toArray();
        String base64 = DatatypeConverter.printBase64Binary(data);
        return BASE64_PRESET + base64;
    }

    private Mat uriToImage(String uri) throws IncorrectBase64Exception {
        if (!uri.startsWith(BASE64_PRESET)) {
            throw new IncorrectBase64Exception("The image is not formatted correctly.");
        }
        byte[] data = DatatypeConverter.parseBase64Binary(uri.split(",")[1]);
        return Imgcodecs.imdecode(new MatOfByte(data), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
    }


    private ListOfFacesDTO matOfRectToListOfFacesDTO(MatOfRect faces) {
        Rect[] facesArray = faces.toArray();
        ListOfFacesDTO listOfFaces = new ListOfFacesDTO();
        for (Rect detectedFace : facesArray) {
            listOfFaces.addFaceToListOfFaces(new Face(detectedFace.x, detectedFace.y, detectedFace.width, detectedFace.height, true));
        }
        return listOfFaces;
    }

    private void checkIfOpenCVIsLoaded() throws CouldNotLoadFileException {
        if (!faceCascade.load(HAARCASCADE)) {
            throw new CouldNotLoadFileException("Unable to load OpenCV file(s)");
        }
    }
}