package nl.oose.blackpool.DTO;

public class ChildDTO {
    private int id;
    private String firstName;
    private String lastName;
    private PermissionsDTO permissions;

    public ChildDTO(){

    }


    public ChildDTO(int id, String firstName, String lastName, PermissionsDTO permissions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public PermissionsDTO getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionsDTO permissions) {
        this.permissions = permissions;
    }

}
