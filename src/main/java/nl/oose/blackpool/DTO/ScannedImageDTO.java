package nl.oose.blackpool.DTO;

public class ScannedImageDTO {
    private String img;
    ListOfFacesDTO listOfFaces;

    public ScannedImageDTO() {
    }

    public ScannedImageDTO(String img, ListOfFacesDTO listOfFaces) {
        this.img = img;
        this.listOfFaces = listOfFaces;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ListOfFacesDTO getListOfFaces() {
        return listOfFaces;
    }

    public void setListOfFaces(ListOfFacesDTO listOfFaces) {
        this.listOfFaces = listOfFaces;
    }
}
