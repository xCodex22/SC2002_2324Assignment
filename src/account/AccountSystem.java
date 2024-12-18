package account; 

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.Console;
import java.util.*;

/**
 * class for managaing accounts in the data base
 */

public class AccountSystem { 
	 /**
    * Logs in a user with a given hospital ID and password.
    * 
    * @param hospitalID The ID of the hospital account.
    * @param passwd The password for the account.
    * @return The status of the login attempt.
    * @throws FileNotFoundException if the accounts file is not found.
    */

  public LoginStatus login(String hospitalID, String passwd) {
		try {
			this.hospitalID = hospitalID;
			this.passwd = passwd;	
			
			boolean found = false;
			String name, password, role;
			name = password = role = null;
			File accounts = new File("../data/AccountDB/accounts.csv");
			Scanner read = new Scanner(accounts);
			read.nextLine();
			while(read.hasNextLine()) {
				String[] line = read.nextLine().split(",");
				name = line[0]; password = line[1]; role = line[2];
				if (this.hospitalID.equals(name)) { found = true; break; }	
			}
			read.close();
			if (found) {
				if (this.passwd.equals(password)) {
					this.role = role;	
          return LoginStatus.SUCCESS;
				}
				else return LoginStatus.WRONG_PASSWORD;
			}
			else return LoginStatus.USER_NOT_FOUND;
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return LoginStatus.ERROR;
		}	
	} 

  /**
   * Changes the password of the user, ensure 1) user knows the old password 2) new password is different from old password 3) new password needs to be confirmed again
   * @param oldPass The current password.
   * @param newPass1 The new password.
   * @param newPass2 The new password for confirmation.
   * @return true if the password change is successful, false otherwise.
   */

	public boolean changePassword(String oldPass, String newPass1, String newPass2) {
		if (!passwd.equals(oldPass)) {
			System.out.println("[-] Wrong Password");
			return false;
		}
		if (!newPass1.equals(newPass2)) {
			System.out.println("[-] Newly Created Passwords Do Not Match");
			return false;
		}
		else {
			if(newPass1.equals(oldPass)) {
				System.out.println("[-] New Password Must be Different from Old Password");
				return false;
			}
			
			String oldInfo = hospitalID + "," + passwd + "," + role; 
			String newInfo = hospitalID + "," + newPass1 + "," + role;

			try {
				List<String> content = new ArrayList<>(Files.readAllLines(Paths.get("../data/AccountDB/accounts.csv"), StandardCharsets.UTF_8));
				for (int i = 0; i < content.size(); i++) {
					if(content.get(i).equals(oldInfo)) {
						content.set(i, newInfo);
						break;
					}
				}
				Files.write(Paths.get("../data/AccountDB/accounts~.csv"), content, StandardCharsets.UTF_8);
				Files.copy(Paths.get("../data/AccountDB/accounts~.csv"), Paths.get("../data/AccountDB/accounts.csv"), StandardCopyOption.REPLACE_EXISTING);
				Files.delete(Paths.get("../data/AccountDB/accounts~.csv"));

			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
  
  /**
   * Registers a new user with the provided information and role.
   * @param info An array of user details.
   * @param role The role of the user (e.g., patient, doctor, pharmacist).
   * @return true if registration is successful, false otherwise.
   */

	public boolean register(String[] info, String role) {
    try {
      File accounts = new File("../data/AccountDB/accounts.csv");
      Scanner read = new Scanner(accounts);
      String[] lastLine = null;
      while(read.hasNextLine()) 
        lastLine = read.nextLine().split(",");
      read.close(); 
      int new_ID = Integer.parseInt(lastLine[0]) + 1;
      info[0] = String.valueOf(new_ID);
      this.hospitalID = info[0];
      String newEntry = String.join(",", info);
      String fileName = null;
      String dirName = "../data/BasicInfoDB/";
      String accEntry = "password,";
      FileWriter writer = null;
      
      switch(role) {
        case "PATIENT":
          fileName = "patient.csv";
          accEntry += "PATIENT\n";
          break;
        case "DOCTOR":
          fileName = "doctor.csv";
          accEntry += "DOCTOR\n";
          break;
        case "PHARMACIST":
          fileName = "pharmacist.csv";
          accEntry += "PHARMACIST\n";
         break; 
        default:
          System.out.println("[-] Illegal Registration");
          return false;
      }

      writer = new FileWriter(dirName+fileName, true);
      writer.write(newEntry+"\n");
      writer.close();
      writer = new FileWriter("../data/AccountDB/accounts.csv", true);
      newEntry = info[0] + "," + accEntry;
      writer.write(newEntry);
      writer.close();
      if (role == "PATIENT") {
        writer = new FileWriter("../data/MedicalRecordsDB/" + info[0] + ".csv");
        writer.write("hospitalID,bloodType,serviceDate,serviceName,drID,drName,diagnosis,medicationPrescribed,medicationAmount,treatmentPlan,remarks\n");
        writer.close();
        writer = new FileWriter("../data/AppointmentDB/" + info[0] + "request.csv");
        writer.write("status,patientID,appointmentDate,timeSlot,drID,drName\n");
        writer.close();
        writer = new FileWriter("../data/AppointmentDB/" + info[0] + "outcome.csv");
        writer.write("patientID,serviceDate,serviceName,drID,diagnosis,medicationPrescribed,medicationAmount,medicationStatus,treatmentPlan,remarks\n");
        writer.close();
      }

      if (role == "DOCTOR") 
        copyDir("../data/ScheduleDB/init/2024", "../data/ScheduleDB/" + String.valueOf(new_ID) + "/2024", true);

		  return true;
    } catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Deletes an account associated with the given hospital ID.
   * 
   * @param id The ID of the account to delete.
   * @return true if the account is deleted successfully, false otherwise.
   */
  public boolean deleteAccount(String id) {
    try {
				List<String> content = new ArrayList<>(Files.readAllLines(Paths.get("../data/AccountDB/accounts.csv"), StandardCharsets.UTF_8));
        List<String> newContent = new ArrayList<>();
				for (int i = 0; i < content.size(); i++) {
					if(content.get(i).substring(0,id.length()).equals(id)) {
						continue;
					}  
          newContent.add(content.get(i));
				}
				Files.write(Paths.get("../data/AccountDB/accounts~.csv"), newContent, StandardCharsets.UTF_8);
				Files.copy(Paths.get("../data/AccountDB/accounts~.csv"), Paths.get("../data/AccountDB/accounts.csv"), StandardCopyOption.REPLACE_EXISTING);
				Files.delete(Paths.get("../data/AccountDB/accounts~.csv"));
			} catch(IOException e) {
				e.printStackTrace();
        return false;
			}
		return true;
  }

  /**
   * copys the directory, needed for initalisation of doctors 
   *
   * @param src the source directory
   * @param dest the destination directory
   * @param overwrite whether we are going to overwrite files into the desintation directory
   */
  protected void copyDir(String src, String dest, boolean overwrite) {
    if (!Files.exists(Paths.get(dest))) {
      try {
        Files.createDirectories(Paths.get(dest));
      } catch (IOException e) {
        e.printStackTrace();
        return; 
      }
    }

    try {
      Files.walk(Paths.get(src)).forEach(a -> {
        Path b = Paths.get(dest, a.toString().substring(src.length()));
        try {
          if (!a.toString().equals(src))
          Files.copy(a, b, overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[]{});
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * gets the role of the account
   *
   * @return the role of the account
   */
  public String getRole() { return role; }

  /**
   * gets the id of the account
   *
   * @return the id of the account
   */
  public String getID() { return hospitalID; }
		
	private String hospitalID;
	private String passwd;
	private String role;
}
