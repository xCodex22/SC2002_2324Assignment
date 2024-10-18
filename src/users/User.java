package HospitalManagementSystem.src.users;

public class User {
    // Attributes
    private int hospitalID;
    private String password;

    // Constructor
    public User(int hospitalID) {
        this.hospitalID = hospitalID;
        this.password = "password";
    }

    // Methods
    public void login() {
        
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Getter/ Setter Methods
    public int getHospitalID() {
        return hospitalID;
    }

    public String getPassword() {
        return password;
    }
}
