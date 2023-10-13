package Processing;

import java.util.HashMap;
import java.util.Scanner;

import Model.Store;
import Model.User;

public class Users {

	private static HashMap<String, User> logins = RentalLoader.usersInformation();
	
	public static User registerNewUser(String login, String password, int access, String login2, String password2) {
		
		Store workplace = loadUser(login2, password2).getWorkplace();
		if (workplace.equals(null)) {
			Scanner scan = new Scanner(System.in);
			System.out.println("¿A qué tienda desea asignar a esta persona?");
			String workplaceName = scan.nextLine();
			workplace = CarRental.getStore(workplaceName);
		}
		User created = new User(login, password, workplace, access);
		logins.put(login, created);
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
