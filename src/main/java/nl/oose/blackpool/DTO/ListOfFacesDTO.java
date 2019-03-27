package nl.oose.blackpool.DTO;

import java.util.ArrayList;
import java.util.List;

public class ListOfFacesDTO {
    private List<Face> listOfFaces;

    public ListOfFacesDTO() {
        listOfFaces = new ArrayList<Face>();
    }

    public ListOfFacesDTO(List<Face> listOfFaces) {
        this.listOfFaces = listOfFaces;
    }

    public List<Face> getListOfFaces() {
        return listOfFaces;
    }

    public void setListOfFaces(List<Face> listOfFaces) {
        this.listOfFaces = listOfFaces;
    }

    public void addFaceToListOfFaces (Face face) {
        this.listOfFaces.add(face);
    }
}
