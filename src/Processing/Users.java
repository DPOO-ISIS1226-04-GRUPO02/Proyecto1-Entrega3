package Processing;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;

import Model.User;

public class Users {

	private static HashMap<String, User> logins;

	public static void loadUsers() throws IOException, ParseException {

		logins = RentalLoader.usersInformation();

	}
	
	public static User registerNewUser(String login, String password, int access, String workplace) {

		User created = new User(login, password, access, workplace);
		logins.put(password, created);
		return created;

	}

	public static User registerNewUser(String login, String password, int access, String login2, String password2) {
		
		Scanner scan = new Scanner(System.in);
		User user = loadUser(login2, password2);
		String workplace = user.getWorkplace();
		if (workplace.equals(null)) {
			System.out.println("Ingrese la tienda en la que va a trabajar esta persona: ");
			while (CarRental.getStore(workplace).equals(null)) {
				System.out.println("Esta tienda no se ha encontrado. Ingrese el nombre nuevamente: ");
				workplace = scan.nextLine();
			}
		}
		User created = new User(login, password, access, workplace);
		logins.put(login, created);
		scan.close();
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
