package Console;

import java.util.Scanner;

import Model.User;

import java.util.Arrays;
import java.util.List;

import Processing.Users;

public class UserSelection {
	
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		
		System.out.println("Iniciar sesión o registrar nuevo usuario.");
		System.out.println("1. Iniciar Sesión\n2. Registrar\n3. Cerrar");
		boolean loop = true;
		while (loop) {
			switch (scan.nextInt()) {
				case 1:
					login(); break;
				case 2:
					register(); break;
				case 3:
					loop = false; break;
				default:
					System.out.println("Ingrese un número entre 1 y 3."); break;
			}
		}
		System.out.println("Gracias por usar la aplicación.");
		
	}
	
	private static void login() {
		
		System.out.print("Ingrese su nombre de usuario: ");
		String username = scan.nextLine();
		System.out.print("Ingrese su contraseña: ");
		String password = scan.nextLine();
		User current = Users.loadUser(username, password);
		if (current.equals(null)) System.out.println("Ingreso incorrecto!");
		else initializeView(current);
		
	}
	
	private static void register() {
		
		boolean user = true;
		String username = "";
		List<String> usernames = Arrays.asList(Users.getUsernames());
		while (user) {
			System.out.print("Ingrese un nombre de usuario: ");
			username = scan.nextLine();
			if ((!usernames.contains(username))) user = false;
			else System.out.println("Este nombre de usuario ya existe!");
		}
		
		System.out.print("Ingrese una contraseña para su cuenta: ");
		String password = scan.nextLine();
		User current = Users.registerNewUser(username, password, 0, null, null);
		initializeView(current);
		
	}
	
	private static void initializeView(User current) {
		
		View initialized = new View(current.getAccess(), current.getUsername(), current.getPassword());
		initialized.optionSelection();
		
	}

}