package nl.oose.blackpool.domain;

import java.util.List;

public class Child {
    private int childId;
    private String firstName;
    private String lastName;
    private Permissions permissions;
    private List<IdentificationImage> identificationImages;

    public Child() {

    }

    public Child(int childId, String firstName, String lastName) {
        this.childId = childId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public List<IdentificationImage> getIdentificationImages() {
        return identificationImages;
    }

    public void setIdentificationImages(List<IdentificationImage> identificationImages) {
        this.identificationImages = identificationImages;
    }
}
