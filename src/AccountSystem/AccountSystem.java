package AccountSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.Console;
			
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
			if (found) {
				if (this.passwd.equals(password)) {
					this.role = role;	
					return true;
				}
				else {
					System.out.println("Wrong password");
					return false;
				}
			}
			else {
				System.out.println("User not found");
				return false;
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}	
	} 

	public void changePassowrd() {
		
	}

	public String getRole() { return role; }
		
	private String uname;
	private String passwd;
	private String role;
}
