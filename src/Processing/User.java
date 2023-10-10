package Processing;

import Model.Store;

public class User {

	private String username;
	private String password;
	private Store workplace;
	private int access;
	
	User (String username, String password, Store workplace, int access) {
		
		this.username = username;
		this.password = password;
		this.workplace = workplace;
		this.access = access;
		
	}
	
	public String getPassword() {
		
		return this.password;
		
	}
	
	public String getUsername() {
		
		return this.username;
		
	}
	
	public Store getWorkplace() {
		
		return this.workplace;
		
	}
	
	public int getAccess() {
		
		return this.access;
		
	}
	
}
