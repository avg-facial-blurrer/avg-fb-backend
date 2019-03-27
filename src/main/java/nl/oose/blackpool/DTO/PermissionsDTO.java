package nl.oose.blackpool.DTO;

public class PermissionsDTO {
    private int id;
    private boolean socialMediaPermission;
    private boolean schoolPaperPermission;
    private boolean enclosedEnvironmentPermission;

    public PermissionsDTO(int id,boolean socialMediaPermission, boolean schoolPaperPermission, boolean enclosedEnvironmentPermission) {
        this.id = id;
        this.socialMediaPermission = socialMediaPermission;
        this.schoolPaperPermission = schoolPaperPermission;
        this.enclosedEnvironmentPermission = enclosedEnvironmentPermission;
    }

    public PermissionsDTO() {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
