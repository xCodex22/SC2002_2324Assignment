package account; 

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.Console;
import java.util.*;
			
public class AccountSystem { 
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
      }
		  return true;
    } catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }

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

  public String getRole() { return role; }
  public String getID() { return hospitalID; }
		
	private String hospitalID;
	private String passwd;
	private String role;
}
