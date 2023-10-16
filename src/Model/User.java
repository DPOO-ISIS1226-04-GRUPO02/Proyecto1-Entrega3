package Model;

public class User {

	private String username;
	private String password;
	private String workplace;
	private int access;
	
	public User (String username, String password,  int access, String workplace) {
		
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
	
	public String getWorkplace() {
		
		return this.workplace;
		
	}
	
	public int getAccess() {
		
		return this.access;
		
	}
	
}
