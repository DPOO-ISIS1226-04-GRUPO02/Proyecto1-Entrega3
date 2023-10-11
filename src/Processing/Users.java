package Processing;

import java.util.HashMap;

import Model.Store;

public class Users {

	private static HashMap<String, User> logins = RentalLoader.usersInformation();
	
	public static User registerNewUser(String username, String password, Store workplace, int access) {
		
		User created = new User(username, password, workplace, access);
		logins.put(username, created);
		return created;
		
	}
	
	public static User loadUser(String username, String password) {
		
		User possibility = logins.get(username);
		if (possibility.getPassword().equals(password)) return possibility;
		else return null;
		
	}
	
	public static String[] getUsernames() {
		
		return (String[]) logins.keySet().toArray();
		
	}

	public static HashMap<String, User> getUsers() {

		return logins;

	}
	
}
