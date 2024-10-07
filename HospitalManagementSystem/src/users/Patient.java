package HospitalManagementSystem.src.users;

import java.time.LocalDate;

public class Patient extends User{
    // Attributes
    private int patientID;
    private String name;
    private LocalDate dateOfBirth;
    private boolean gender;
    private int contactNum;
    private String contactEmail;
    private String bloodType;
    private String medicalRecord;

    // Constructor
    public Patient(int hospitalID, String password, int patientID, String name,
    LocalDate dateOfBirth,
    boolean gender,
    int contactNum,
    String contactEmail,
    String bloodType,
    String medicalRecord) {
        super(hospitalID);
        this.patientID = patientID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNum = contactNum;
        this.contactEmail = contactEmail;
        this.bloodType = bloodType;
        this.medicalRecord = medicalRecord;
    }

    // Methods:
    // 1. View Medical Records:
    public void viewMedicalRecord() {
        System.out.println("Medical Record for: " + name);
        System.out.println("PatientID: " + patientID);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.print("Gender: ");
        System.out.println("Contact Number: " + contactNum);
        System.out.println("Contact Email: " + contactEmail);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Medical Record: " + medicalRecord);
    }

    // 2. Update Contact Number & Email

    // 3. Set Appointment

    // Setter/ Getter Methods
    public int getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean getGender() {
        return gender;
    }

    public int getContactNum() {
        return contactNum;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getMedicalReport() {
        return medicalRecord;
    }
}
