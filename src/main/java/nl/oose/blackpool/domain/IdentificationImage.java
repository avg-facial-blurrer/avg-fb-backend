package nl.oose.blackpool.domain;

public class IdentificationImage {
    private int childId;
    private int imageID;
    private String pathToImage;

    public IdentificationImage(int childId, int imageID, String pathToImage) {
        this.childId = childId;
        this.imageID = imageID;
        this.pathToImage = pathToImage;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
