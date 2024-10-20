package account; 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.Console;
import java.util.*;
			
public class AccountSystem { 
	public LoginStatus login(String uname, String passwd) {
		try {
			this.uname = uname;
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
				if (this.uname.equals(name)) { found = true; break; }	
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
			
			String oldInfo = uname + "," + passwd + "," + role; 
			String newInfo = uname + "," + newPass1 + "," + role;

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

	public boolean register() {
		// requires dependency of personal information		
    // param take in personal info

		// ====[ TODO ]===
		// requires the patient to fill in personal information first 
    // personal info is gonna be a struct with all the required member fields
		// --> it should not matter that the exact name blood type etc occurs multiple times
		// --> uniqueness of patients is by the hospitalID
		// **** we will just increase the ID number by 1 each time a new user is created ****
		// read and return the last line of the account for the last hospitalID
		// password is defaulted to "password"
		// leave a message to tell user about this default and to change password 

    try {
      File accounts = new File("../data/AccountDB/accounts.csv");
      Scanner read = new Scanner(accounts);
      read.nextLine();
      String[] lastLine = null;
      while(read.hasNextLine()) {
        lastLine = read.nextLine().split(",");
      } 
      read.close(); 
      int hospitalID = Integer.parseInt(lastLine[0]) + 1;
      uname = String.valueOf(hospitalID);
		  return true;

    } catch(FileNotFoundException e) {
      e.printStackTrace();
      return false;
    }

}

	public String getRole() { return role; }
  public String getUname() { return uname; }
		
	private String uname;
	private String passwd;
	private String role;
}
