package HospitalManagementSystem.src.users;

public class Administrator extends User {
    // Attributes
    private String contactEmail;

    // Constructor
    public Administrator(int hospitalID, String contactEmail) {
        super(hospitalID);
        this.contactEmail = contactEmail;
    }

    // Setters/ Getters
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
