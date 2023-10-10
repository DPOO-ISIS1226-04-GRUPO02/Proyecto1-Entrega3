package View;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

import Processing.Users;
import Processing.User;

public class UserSelection {
	
	static Users users = new Users();
	static Scanner scan = new Scanner(System.in);

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
	
	public static void login() {
		
		System.out.print("Ingrese su nombre de usuario: ");
		String username = scan.nextLine();
		System.out.print("Ingrese su contraseña: ");
		String password = scan.nextLine();
		User log = users.loadUser(username, password);
		if (log.equals(null)) System.out.println("Ingreso incorrecto!");
		else /*TODO: Inicializar la vista de usuario */;
		
	}
	
	public static void register() {
		
		boolean user = true;
		String username = "";
		List<String> usernames = Arrays.asList(users.getUsernames());
		while (user) {
			System.out.print("Ingrese un nombre de usuario: ");
			username = scan.nextLine();
			if ((!usernames.contains(username))) user = false;
			else System.out.println("Este nombre de usuario ya existe!");
		}
		
		System.out.print("Ingrese una contraseña para su cuenta: ");
		String password = scan.nextLine();
		User log = users.registerNewUser(username, password, null, (char) 0);
		
		// TODO: Inicializar la vista de los usuarios
		
	}

}