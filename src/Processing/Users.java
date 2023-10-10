package Processing;

import java.util.HashMap;

import Model.Store;

public class Users {

	private HashMap<String, User> logins = new HashMap<String, User>();
	private CarRental rental;
	
	public Users() {
		
		loadUsers();
		
	}
	
	private void loadUsers() {
		
		// TODO: Use RentalLoader to get all users with their respective information
		
	}
	
	public User registerNewUser(String username, String password, Store workplace, int access) {
		
		User created = new User(username, password, workplace, access);
		logins.put(username, created);
		return created;
		
	}
	
	public User loadUser(String username, String password) {
		
		User possibility = logins.get(username);
		if (possibility.getPassword().equals(password)) return possibility;
		else return null;
		
	}
	
	public String[] getUsernames() {
		
		return (String[]) logins.keySet().toArray();
		
	}
	
}
