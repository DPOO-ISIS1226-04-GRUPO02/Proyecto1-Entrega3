package Console;

import java.util.Scanner;

import Model.User;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import Processing.CarRental;
import Processing.Users;

public class UserSelection {
	
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		
		try {
			Users.loadUsers();
			CarRental.loadCarRental();
			System.out.println("Info cargada exitosamente");
		} catch (ParseException pe) {
			System.out.println("Se ha encontrado un error cargando los usuarios: " + pe);
		} catch (IOException ioe) {
			System.out.println("Se he encontrado un error con el acceso al directorio de usuarios: " + ioe);
		}
		System.out.println("Iniciar sesión o registrar nuevo usuario.");
		System.out.println("1. Iniciar Sesión\n2. Registrar\n3. Cerrar");
		boolean loop = true;
		while (loop) {
			switch (scan.nextInt()) {
				case 1:
					login(scan); break;
				case 2:
					register(scan); break;
				case 3:
					loop = false; break;
				default:
					System.out.println("Ingrese un número entre 1 y 3."); break;
			}
		}
		scan.close();
	}
	
	private static void login(Scanner scan) {
		scan.nextLine();
		System.out.print("Ingrese su nombre de usuario: ");
		String username = scan.nextLine();
		System.out.print("\nIngrese su contraseña: ");
		String password = scan.nextLine();
		User current = Users.loadUser(username, password);
		if (current.equals(null)) System.out.println("Ingreso incorrecto!");
		else initializeView(current, scan);
		
	}
	
	private static void register(Scanner scan) {
		
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
		User current = Users.registerNewUser(username, password, 0, null);
		initializeView(current, scan);
		
	}
	
	private static void initializeView(User current, Scanner scan) {
		
		
		View initialized = new View(current.getAccess(), current.getUsername(), current.getPassword());
		initialized.optionSelection(scan);
		
	}

}