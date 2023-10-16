package Console;

import java.util.Scanner;

import Model.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.InputMismatchException;
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
	
	public boolean optionSelection(Scanner scan) {
		
		int selection = 0;
		switch (access) {
			case 0:
				System.out.println("0. Salir de la aplicación");
				System.out.println("1. Agregar o cambiar información personal");
				System.out.println("2. Reservar un carro");
				selection = scan.nextInt();
				if (0 <= selection && selection <= 2) runOptions(selection, scan);
				break;
			case 1:
				System.out.println("0. Salir de la aplicación");
				System.out.println("1. Agregar o cambiar información personal");
				System.out.println("2. Reservar un carro");
				System.out.println("3. Confirmar recogida de un carro");
				System.out.println("4. Confirmar devolución de un carro");
				selection = scan.nextInt();
				if (0 <= selection && selection <= 4) runOptions(selection, scan);
				break;
			case 2:
				System.out.println("0. Salir de la aplicación");
				System.out.println("1. Agregar o cambiar información personal");
				System.out.println("2. Reservar un carro");
				System.out.println("3. Confirmar recogida de un carro");
				System.out.println("4. Confirmar devolución de un carro");
				System.out.println("5. Registrar nuevo empleado");
				selection = scan.nextInt();
				if (0 <= selection && selection <= 5) runOptions(selection, scan);
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
				System.out.println("14. Generar historial de alquileres para un carro");
				selection = scan.nextInt();
				if (0 <= selection && selection <= 14) runOptions(selection, scan);
				break;
		}
		if (selection == 0) return false;
		else return true;
		
		
	}

	private void runOptions(int selection, Scanner scan) {

		String clientLogin;
		List<String> usernames = Arrays.asList(Users.getUsernames());
		ArrayList<String> categories = new ArrayList<>(CarRental.getCategories());
		while (selection != 0) {
		abc: switch (selection) {	
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
				scan.nextLine();
				System.out.println("Ingrese su nombre de usuario: ");
				clientLogin = scan.nextLine();
				Client client = CarRental.getClient(clientLogin);
				System.out.println("Ingrese el número de la categoría de vehículo que desea alquilar: ");
				int i = 1;
				for (String elemento : categories){
					
					System.out.println(i + ". " + elemento);
					i += 1;
				}
				int categoriaSelect = scan.nextInt();
				String categoria = categories.get(categoriaSelect -1);
				System.out.println("Ingrese el número de la sede en la que desea recoger su vehículo: ");
				ArrayList<String> tiendas = new ArrayList<>(CarRental.getStores());
				i = 1;
				for (String tienda : tiendas){
					
					System.out.println(i + ". " + tienda);
					i += 1;
				}
				int storeOriginSelect = scan.nextInt();
				String storeOrigin = tiendas.get(storeOriginSelect -1);
				System.out.println("Ingrese el número de la sede en la que desea devolver su vehículo: ");
				i = 1;
				for (String tienda : tiendas){
					
					System.out.println(i + ". " + tienda);
					i += 1;
				}
				int storeDestinySelect = scan.nextInt();
				String storeDestiny = tiendas.get(storeDestinySelect -1);
				Calendar fechaInicio = null;
				Calendar fechaFin = null;
				try {
					scan.nextLine();
					System.out.println("Ingrese la fecha y hora aproximada en la que desea recoger su vehículo en formato yy-MM-dd:HH-mm (ej: 23-10-15:09-30): ");
					String fechaI = scan.nextLine();
					System.out.println("Ingrese la fecha y hora aproximada en la que desea devolver su vehículo en formato yy-MM-dd:HH-mm (ej: 23-10-21:21-30): ");
					String fechaF = scan.nextLine();
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
					System.out.println("Ingrese la cantidad de licencias secundarias que va a registrar: ");
					int n = scan.nextInt();
					try {
						CarRental.reserveCar(client.getLogin(), categoria, storeOrigin, storeDestiny, fechaInicio, 
							fechaFin, n);
					} catch (ParseException pe) {
						System.out.println("Se encontró un error en el formato de las fechas ingresadas! " + pe);
					}
				}
				break;
			case 3:
				scan.nextLine();
				System.out.println("Ingrese el nombre de usuario del cliente: ");
				clientLogin = scan.nextLine();
				try {
					CarRental.confirmPickUp(clientLogin, Users.loadUser(login, password).getWorkplace());
				} catch (ParseException pe) {
					System.out.println("Se ha encontrado un error con el formato de la fecha " + pe);
				}
				break;
			case 4:
				scan.nextLine();
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
				scan.nextLine();
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
				scan.nextLine();
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
				while (CarRental.getStore(storeName) == null) {
					System.out.println("Esta tienda no se ha encontrado. Ingrese el nombre nuevamente: ");
					storeName = scan.nextLine();
				}
				Users.registerNewUser(managerLogin, managerPassword, 2, storeName);
				break;
			case 7:
				scan.nextLine();
				System.out.println("Ingrese la marca del carro que va a registrar: ");
				String brand = scan.nextLine();
				System.out.println("Ingrese la placa del carro: ");
				String plate = scan.nextLine();
				System.out.println("Ingrese el modelo del carro: ");
				String model = scan.nextLine();
				System.out.println("Ingrese el color del carro: ");
				String color = scan.nextLine();
				boolean isAutomatic = false;
				try {
					System.out.println("Ingrese 'true' si el carro es automático o 'false' si es manual: ");
					isAutomatic = scan.nextBoolean();
				} catch (InputMismatchException ime) {
					System.out.println("El valor booleano ingresado no es válido " + ime);
					break;
				}
				System.out.println("Ingrese el número de la categoría de vehículo que desea alquilar: ");
				i = 1;
				for (String elemento : categories){
				
					System.out.println(String.valueOf(i) + ". " + elemento);
					i += 1;
				}
				String category = categories.get(scan.nextInt() - 1);
				ArrayList<String> stores = new ArrayList<>(CarRental.getStores());
				i = 1;
				for (String store : stores){
					System.out.println(String.valueOf(i) + ". " + store);
					i += 1;
				}
				String store = stores.get(scan.nextInt() - 1);
				CarRental.registerCar(brand, plate, model, color, isAutomatic, category, 0, store);
				break;
			case 8:
				scan.nextLine();
				System.out.println("Ingrese el nombre para la nueva tienda: ");
				String name = scan.nextLine();
				while (!CarRental.getStores().contains(name)) {
					System.out.println("Ya existe una tienda por este nombre. Por favor ingrese otro: ");
					name = scan.nextLine();
				}
				System.out.println("Ingrese el nombre de la ubicación de la nueva tienda: ");
				String location = scan.nextLine();
				Calendar openingTime = Calendar.getInstance();
				Calendar closingTime = Calendar.getInstance();
				try {
					System.out.println("Ingrese la hora de aprtura de la nueva tienda (HH:MM en formato 24h): ");
					String openingTimeString = scan.nextLine();
					System.out.println("Ingrese la hora de cierre de la nueva tienda (HH:MM en formato 24h): ");
					String closingTimeString = scan.nextLine();
					SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
					openingTime.setTime(simple.parse(openingTimeString));
					closingTime.setTime(simple.parse(closingTimeString));
				} catch (ParseException pe) {
					System.out.println("Error en el formato de las horas de apertura y cierre " + pe);
					break;
				}
				System.out.println("Ingrese los días de apertura de la tienda, tal que uno (1) representa abierto y cero (0) cerrado.");
				System.out.println("Ejemplo: 1011100 -> Lunes, Miercoles, Jueves, Viernes.");
				byte openingDays = Byte.parseByte(scan.nextLine(), 2);
				CarRental.newStore(name, location, openingTime, closingTime, openingDays);
				break;
			case 9:
				scan.nextLine();
				System.out.println("Ingrese la placa del carro que desea cambiar de sede: ");
				String plate2 = scan.nextLine();
				while (!CarRental.carExists(plate2)) {
					System.out.println("Este carro no está registrado en el sistema. Ingrese la placa de nuevo o 'stop' para salir: ");
					plate2 = scan.nextLine();
					if (plate2.equals("stop")) break abc;
				}
				String originStore = CarRental.getStoreByPlate(plate2);
				System.out.println("Ingrese el nombre de la sede a donde desea mover el vehículo: ");
				String destinationStore = scan.nextLine();
				while (!CarRental.storeExists(destinationStore)) {
					System.out.println("Esta tienda no existe o es la misma en que ya está. Ingrese otra o 'stop' para salir: ");
					destinationStore = scan.nextLine();
					if (destinationStore.equals("stop")) break abc;
				}
				System.out.println("Ingrese la cantidad de días que se demorará el cambio de sedes: ");
				int days2 = scan.nextInt();
				CarRental.reserveCar(plate2, originStore, destinationStore, days2);
				break;
			case 10:
				scan.nextLine();
				System.out.println("Ingrese la placa del carro que desea inhabilitar: ");
				String plate3 = scan.nextLine();
				while (!CarRental.carExists(plate3)) {
					System.out.println("Este vehículo no se ha encontrado. Ingrese otra placa o 'stop' para salir: ");
					plate3 = scan.nextLine();
					if (plate3.equals("stop")) break abc;
				}
				if (CarRental.getCar(plate3).getStatus() == (byte) 3) System.out.println("Este vehículo ya está inactivo.");
				else CarRental.changeVehicleStatus(plate3, (byte) 3);
				break;
			case 11:
				scan.nextLine();
				for (String cat: categories) {
					System.out.println(String.format("Ingrese el nuevo precio para la categoría '%s': ", cat));
					CarRental.setTariff(cat, scan.nextInt());
				}
				break;
			case 12:
				scan.nextLine();
				System.out.println("Ingrese el nombre del seguro que desea añadir: ");
				String insuranceName = scan.nextLine();
				while (CarRental.insuranceExists(insuranceName)) {
					System.out.println("Un seguro por este nombre ya existe. Ingrese otro nombre o 'stop' para salir: ");
					insuranceName = scan.nextLine();
					if (insuranceName.equals("stop")) break abc;
				}
				System.out.println("Ingrese el costo diario de este seguro: ");
				int insuranceCost = scan.nextInt();
				System.out.println("Ingrese las especificaciones para este seguro: ");
				String specs = scan.nextLine();
				CarRental.addInsurance(insuranceName, insuranceCost, specs);
				break;
			case 13:
				scan.nextLine();
				System.out.println("Escoga el nombre del seguro que desea cambiar de estado: ");
				i = 1;
				for (String ins: CarRental.getInsurances()) {
					System.out.println(String.format("%-2d. %s", i, ins));
					i++;
				}
				ArrayList<String> insurances = new ArrayList<String>(CarRental.getInsurances());
				String insuranceNam = insurances.get(scan.nextInt() - 1);
				System.out.println(String.format("¿Está seguro de cambiar el estado del seguro %s? (1 para sí; 2 para no): ", insuranceNam));
				if (scan.nextLine().equals("1")) {
					boolean truth = CarRental.changeInsuranceStatus(insuranceNam);
					if (truth) System.out.println("Cambiado a activo.");
					else System.out.println("Cambiado a inactivo.");
				}
				break;
			case 14:
				scan.nextLine();
				System.out.println("Ingrese la placa del carro del que desea consultar el historial: ");
				String plate4 = scan.nextLine();
				while (!CarRental.carExists(plate4)) {
					System.out.println("Este carro no está registrado en el sistema! Ingrese la placa de neuvo o 'stop' para salir: ");
					plate4 = scan.nextLine();
					if (plate4.equals("stop")) break abc;
				}
				String result = CarRental.getPastRentals(CarRental.getCar(plate4));
				System.out.println(result);
				break;
			default:
				System.out.println("Option not found.");
				break;
		} 
		scan.nextLine();
		System.out,println("Ingrese una opción: ");
		selection = scan.nextInt();

		}
	}

}
