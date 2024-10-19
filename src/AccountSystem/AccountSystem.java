package AccountSystem;

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
			File accounts = new File("../data/Accounts.csv");
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
				List<String> content = new ArrayList<>(Files.readAllLines(Paths.get("../data/Accounts.csv"), StandardCharsets.UTF_8));
				for (int i = 0; i < content.size(); i++) {
					if(content.get(i).equals(oldInfo)) {
						content.set(i, newInfo);
						break;
					}
				}
				Files.write(Paths.get("../data/Accounts~.csv"), content, StandardCharsets.UTF_8);
				Files.copy(Paths.get("../data/Accounts~.csv"), Paths.get("../data/Accounts.csv"), StandardCopyOption.REPLACE_EXISTING);
				Files.delete(Paths.get("../data/Accounts~.csv"));

			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean register() {
		// requires dependency of personal information		

		// ====[ TODO ]===
		// requires the patient to fill in personal information first 
		// --> it should not matter that the exact name blood type etc occurs multiple times
		// --> uniqueness of patients is by the hospitalID
		// **** we will just increase the ID number by 1 each time a new user is created ****
		// read and return the last line of the account for the last hospitalID
		// password is defaulted to "password"
		// leave a message to tell user about this default and to change password 

		long hospitalID = 1000;

		final String message = 	"[+] Account Created Successfully\n\n" +
								"[!!] Please Save the Following Information\n" +
								"[1] Your unique Hospital ID: " + hospitalID + "\n" +
								"[2] Your default password is: password\n" +
								"[3] Please change your password after logging in\n\n" +
								"You may exit this page and log in now.";

		System.out.println(message);
		return true;
	}

	public String getRole() { return role; }
		
	private String uname;
	private String passwd;
	private String role;
}
