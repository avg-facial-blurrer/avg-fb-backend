package nl.oose.blackpool.domain;

public class Permissions {
    private int childId;
    private boolean socialMediaPermission;
    private boolean schoolPaperPermission;
    private boolean enclosedEnvironmentPermission;

    public Permissions(Boolean socialMediaPermission, Boolean schoolPaperPermission, Boolean enclosedEnvironmentPermission, int childId) {
        this.socialMediaPermission = socialMediaPermission;
        this.schoolPaperPermission = schoolPaperPermission;
        this.enclosedEnvironmentPermission = enclosedEnvironmentPermission;
        this.childId = childId;
    }

    public Permissions() {

    }

    public boolean isSocialMediaPermission() {
        return socialMediaPermission;
    }

    public void setSocialMediaPermission(boolean socialMediaPermission) {
        this.socialMediaPermission = socialMediaPermission;
    }

    public boolean isSchoolPaperPermission() {
        return schoolPaperPermission;
    }

    public void setSchoolPaperPermission(boolean schoolPaperPermission) {
        this.schoolPaperPermission = schoolPaperPermission;
    }

    public boolean isEnclosedEnvironmentPermission() {
        return enclosedEnvironmentPermission;
    }

    public void setEnclosedEnvironmentPermission(boolean enclosedEnvironmentPermission) {
        this.enclosedEnvironmentPermission = enclosedEnvironmentPermission;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }
}
