package Console;

import java.util.Scanner;
import java.util.Set;

import Model.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import Processing.CarRental;
import Processing.Users;

public class View {
	
	private int access;
	private String login;
	private String password;
	
	View(int access, String login, String password) {
		
		this.access = access;
		this.login = login;
		this.password = password;
		
	}
	
	public boolean optionSelection() {
		
		Scanner scan = new Scanner(System.in);
		int selection = 0;
		switch (access) {
			case 0:
				System.out.println("0. Salir de la aplicación");
				System.out.println("1. Agregar o cambiar información personal");
				System.out.println("2. Reservar un carro");
				selection = scan.nextInt();
				scan.close();
				if (0 <= selection && selection <= 2) runOptions(selection);
				break;
			case 1:
				System.out.println("0. Salir de la aplicación");
				System.out.println("1. Agregar o cambiar información personal");
				System.out.println("2. Reservar un carro");
				System.out.println("3. Confirmar recogida de un carro");
				System.out.println("4. Confirmar devolución de un carro");
				selection = scan.nextInt();
				scan.close();
				if (0 <= selection && selection <= 4) runOptions(selection);
				break;
			case 2:
				System.out.println("0. Salir de la aplicación");
				System.out.println("1. Agregar o cambiar información personal");
				System.out.println("2. Reservar un carro");
				System.out.println("3. Confirmar recogida de un carro");
				System.out.println("4. Confirmar devolución de un carro");
				System.out.println("5. Registrar nuevo empleado");
				selection = scan.nextInt();
				scan.close();
				if (0 <= selection && selection <= 5) runOptions(selection);
				break;
			case 3:
				System.out.println("0. Salir de la aplicación");
				System.out.println("1. Agregar o cambiar información personal");
				System.out.println("2. Reservar un carro");
				System.out.println("3. Confirmar recogida de un carro");
				System.out.println("4. Confirmar devolución de un carro");
				System.out.println("5. Registrar nuevo empleado");
				System.out.println("6. Registrar nuevo gerente local");
				System.out.println("7. Registrar un nuevo carro");
				System.out.println("8. Registrar una nueva tienda");
				System.out.println("9. Cambiar un carro de sede");
				System.out.println("10. Inhabilitar renta de un carro");
				System.out.println("11. Cambiar tarifas diarias por categoría");
				System.out.println("12. Añadir seguro");
				System.out.println("13. Habilitar/Inhabilitar un seguro");
				System.out.println("14. Cambiar información de una tienda");
				System.out.println("15. Generar historial de alquileres para un carro");
				selection = scan.nextInt();
				scan.close();
				if (0 <= selection && selection <= 15) runOptions(selection);
				break;
		}
		if (selection == 0) return false;
		else return true;
		
	}

	private void runOptions(int selection) {

		Scanner scan = new Scanner(System.in);
		String clientLogin;
		List<String> usernames = Arrays.asList(Users.getUsernames());
		if (selection == 0) selection = scan.nextInt();
		switch (selection) {	
			case 0:
				System.out.println("Gracias por usar la aplicación.");
				break;
			case 1:
				if (!CarRental.clientExists(login)) {
					System.out.println("Debe crear su usuario...");
					System.out.println("\nIngrese su nombre: ");
					String name = scan.nextLine();
					System.out.println("Ingrese su número de telefono: ");
					long phone = scan.nextLong();
					System.out.println("Ingrese su email: ");
					String email = scan.nextLine();
					while (!email.contains("@")) {
						System.out.println("Ingrese un email válido");
						email = scan.nextLine();
					}
					System.out.println("Ingrese su fecha de nacimiento (dd/mm/aaaa): ");
					String dateBirthString = scan.nextLine();
					byte i = 0;
					int[] calendarValues = {0, 0, 0};
					for (String value: dateBirthString.split("/")) {
						calendarValues[i] = Integer.parseInt(value);
						i += 1;
					}
					Calendar dateBirth = Calendar.getInstance();
					dateBirth.set(calendarValues[0], calendarValues[1], calendarValues[2], 0, 0, 0);
					System.out.println("Ingrese su país de nacimiento: ");
					String nationality = scan.nextLine();
					System.out.println("Ingrese la ubicación de la foto de su identificación (en el computador): ");
					String idPhotoPath = scan.nextLine(); 

					System.out.println("-- DATOS DE LA TARJETA DE CREDITO PARA EL PAGO --");
					System.out.println("Ingrese el número de su tarjeta de crédito: ");
					long cardNumber = scan.nextLong();
					System.out.println("Ingrese la fecha de expiración de su tarjeta (dd/mm/aaaa): ");
					String cardExpiratioString = scan.nextLine();
					i = 0;
					for (String value: cardExpiratioString.split("/")) {
						calendarValues[i] = Integer.parseInt(value);
						i += 1;
					}
					Calendar cardExpiration = Calendar.getInstance();
					cardExpiration.set(calendarValues[0], calendarValues[1], calendarValues[2], 0, 0, 0);
					System.out.println("Ingrese el código trasero de la tarjeta de crédito: ");
					short cardCode = scan.nextShort();
					System.out.println("Ingrese el nombre del dueño de la tarjeta de crédito: ");
					String cardOwner = scan.nextLine();
					System.out.println("Ingrese la dirección de facturación de la tarjeta: ");
					String cardAddress = scan.nextLine();

					System.out.println("-- DATOS DE LA LICENCIA DE CONDUCCION --");
					System.out.println("Ingrese el número de la licencia: ");
					long licenceNumber = scan.nextLong();
					System.out.println("Ingrese el país en que se expidió la licencia: ");
					String licenceCountry = scan.nextLine();
					System.out.println("Ingrese la fecha de expiración de su licencia (dd/mm/aaaa): ");
					String licenceExpiratioString = scan.nextLine();
					i = 0;
					for (String value: licenceExpiratioString.split("/")) {
						calendarValues[i] = Integer.parseInt(value);
						i += 1;
					}
					Calendar licenceExpiration = Calendar.getInstance();
					licenceExpiration.set(calendarValues[0], calendarValues[1], calendarValues[2], 0, 0, 0);
					System.out.println("Ingrese la ubicación de la foto de su licencia (en el computador): ");
					String licencePhotoPath = scan.nextLine(); 

					CarRental.registerNewClient(name, phone, email, dateBirth, nationality, idPhotoPath, cardNumber, 
						cardExpiration, cardCode, cardOwner, cardAddress, login, licenceNumber, licenceCountry, 
						licenceExpiration, licencePhotoPath);
				} else {		
					CarRental.modifyClient(login);
				}
				break;
			case 2:
				// TODO: Call implemented option to reserve a car
				System.out.println("Ingrese su nombre de usuario: ");
				clientLogin = scan.nextLine();
				Client client = CarRental.getClient(clientLogin);
				ArrayList<String> categories = new ArrayList<>(CarRental.getCategories());
				System.out.println("Ingrese el número de la categoría de vehículo que desea alquilar: ");
				for (String elemento : categories){
					int i = 1;
					System.out.println(String.valueOf(i) + ". " + elemento);
					i += 1;
				}
				int categoriaSelect = scan.nextInt();
				String categoria = categories.get(categoriaSelect -1);
				System.out.println("Ingrese el número de la sede en la que desea recoger su vehículo: ");
				ArrayList<String> tiendas = new ArrayList<>(CarRental.getStores());
				for (String tienda : tiendas){
					int i = 1;
					System.out.println(String.valueOf(i) + ". " + tienda);
					i += 1;
				}
				int storeOriginSelect = scan.nextInt();
				String storeOrigin = tiendas.get(storeOriginSelect -1);
				System.out.println("Ingrese el número de la sede en la que desea devolver su vehículo: ");
				for (String tienda : tiendas){
					int i = 1;
					System.out.println(String.valueOf(i) + ". " + tienda);
					i += 1;
				}
				int storeDestinySelect = scan.nextInt();
				String storeDestiny = tiendas.get(storeDestinySelect -1);
				Calendar fechaInicio = null;
				Calendar fechaFin = null;
				try {
					System.out.println("Ingrese la fecha y hora aproximada en la que desea recoger su vehículo en formato yy-MM-dd:HH-mm (ej: 2023-10-15:09-30): ");
					String fechaI = scan.nextLine();
					System.out.println("Ingrese la fecha y hora aproximada en la que desea devolver su vehículo en formato yy-MM-dd:HH-mm (ej: 2023-10-21:21-30): ");
					String fechaF = scan.nextLine();
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd:HH-mm");
		
					// Convertir la fecha de recogida en un objeto Calendar
					fechaInicio = Calendar.getInstance();
					fechaInicio.setTime(sdf.parse(fechaI));
		
					// Convertir la fecha de devolución en un objeto Calendar
					fechaFin = Calendar.getInstance();
					fechaFin.setTime(sdf.parse(fechaF));
		
				} catch (java.text.ParseException e) {
					System.out.println("Error: Formato de fecha incorrecto.");
					fechaInicio = null;
					fechaFin = null;
				}
			
				if (fechaInicio != null && fechaFin != null){
					CarRental.reserveCar(client.getName(), categoria, storeOrigin, storeDestiny, fechaInicio, fechaFin);

				}
				
				break;
			case 3:
				System.out.println("Ingrese el nombre de usuario del cliente: ");
				clientLogin = scan.nextLine();
				try {
				CarRental.confirmPickUp(clientLogin, Users.loadUser(login, password).getWorkplace());
				} catch (ParseException pe) {
					System.out.println("Se ha encontrado un error con el formato de la fecha " + pe);
				}
				break;
			case 4:
				System.out.println("Ingrese el nombre de usuario del cliente: ");
				clientLogin = scan.nextLine();
				System.out.println("¿En cuánto tiempo estará el vehículo listo para volver a ser" +
					"alquilado? (días): ");
				int days = scan.nextInt();
				System.out.println("¿Existen cargos extra por algún motivo? Descríbalos. Esto incluye si" +
					"el carro está en mal estado, hubo un parte, etc: ");
				String comments = scan.nextLine();
				System.out.println("¿Cuánto es el recargo de?: ");
				int extraCharges = scan.nextInt();
				CarRental.confirmReturn(clientLogin, days, comments, extraCharges, login, password);
				break;
			case 5:
				System.out.println("Ingrese el nombre de usuario para el nuevo empleado: ");
				String employeeLogin = scan.nextLine();
				while (usernames.contains(employeeLogin)) {
					System.out.println("¡Este nombre de usuario ya existe!");
					System.out.println("Ingrese un nuevo nombre de usuario: ");
					employeeLogin = scan.nextLine();
				}
				System.out.println("Ingrese la contraseña para el nuevo empleado: ");
				String employeePassword = scan.nextLine();
				Users.registerNewUser(employeeLogin, employeePassword, 1, login, password);
				break;
			case 6:
				System.out.println("Ingrese un nombre de usuario para la cuenta del gerente: ");
				String managerLogin = scan.nextLine();
				while (usernames.contains(managerLogin)) {
					System.out.println("¡Este nombre de usuario ya existe!");
					System.out.println("Ingrese un nuevo nombre de usuario: ");
					managerLogin = scan.nextLine();
				}
				System.out.println("Ingrese una contraseña para la cuenta del gerente: ");
				String managerPassword = scan.nextLine();
				System.out.println("Ingrese el nombre de la tienda al que va a pertenecer este gerente: ");
				String storeName = scan.nextLine();
				while (CarRental.getStore(storeName).equals(null)) {
					System.out.println("Esta tienda no se ha encontrado. Ingrese el nombre nuevamente: ");
					storeName = scan.nextLine();
				}
				Users.registerNewUser(managerLogin, managerPassword, 2, storeName);
				break;
			case 7:
				
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
				// TODO: Add the rest of the general manger options
			default:
				System.out.println("Option not found.");
				break;
		}
		scan.close();

	}

}
