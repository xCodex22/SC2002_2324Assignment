package HospitalManagementSystem.src.records;

/**
	A class for storing patient medical records.
*/
public class MedicalRecord {
	public MedicalRecord(){}

	void view() {}

	private long patientID;
	private String name;
	private unsigned int dateOfBirth;
	private Gender gender;
	private BloodType bloodType;

	private PatientInfo patientInfo;
	private ContactInfo contactInfo;
}
