package AccountSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.Console;
import java.util.*;
			
public class AccountSystem { 
	public boolean login(String uname, String passwd) {
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
					return true;
				}
				else {
					System.out.println("[-] Wrong password");
					return false;
				}
			}
			else {
				System.out.println("[-] User not found");
				return false;
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return false;
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

	public String getRole() { return role; }
		
	private String uname;
	private String passwd;
	private String role;
}
