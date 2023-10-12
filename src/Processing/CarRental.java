package Processing;

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;

import Model.Car;
import Model.Store;
import Model.Client;
import Model.Insurance;
import Model.Licence;
import Model.Rental;

public class CarRental {

	private static HashMap<String, Client> clients = RentalLoader.loadClients();
	private static HashMap<String, Car> cars = RentalLoader.loadCars();
	private static HashMap<Store, HashMap<String, ArrayList<Car>>> inventory = RentalLoader.loadInventory(cars);
	private static HashMap<String, Store> stores = RentalLoader.loadStores(inventory);
	private static HashMap<String, Integer> categories = RentalLoader.loadCategories();
	private static HashMap<String, Insurance> insurances = RentalLoader.loadInsurances();
	private static HashMap<Car, ArrayList<Rental>> rentals = RentalLoader.loadRentals(cars);

	public static void registerNewClient(String name, long phone, String email, Calendar dateBirth, String nationality, 
		String idPhotoPath, long cardNumber, Calendar cardExpiration, short cardCode, String cardOwner, String cardAddress, 
		String login, long licenceNumber, String licenceCountry, Calendar licenceExpiration, String licencePhotoPath) {

		Client person = new Client(name, phone, email, dateBirth, nationality, idPhotoPath, cardNumber, cardExpiration, 
			cardCode, cardOwner, cardAddress, login);
		person.setLicence(newLicence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath, null));
		// TODO: Verify that the licence, nor the card have yet expired
		clients.put(login, person);
		// RentalWriter.addClient(person);

	}

	private static Client getClient(String login) {

		Client found = clients.get(login);
		return found;

	}

	public static boolean clientExists(String login) {

		Client client = CarRental.getClient(login);
		if (client.equals(null)) return false;
		else return true;

	}

	public static void modifyClient(String login) {

		Scanner scan = new Scanner(System.in);
		System.out.println("¿Qué desea modificar de su perfil?");
		System.out.println("1. Nombre");
		System.out.println("2. Teléfono");
		System.out.println("3. Email");
		System.out.println("4. Foto de identificación");
		System.out.println("5. Método de pago");
		System.out.println("6. Licencia");
		byte response = scan.nextByte();
		byte i = 0;
		Integer[] calendarValues = {0, 0, 0};
		Client client = getClient(login);
		switch (response) {

			case 1:
				System.out.println("Ingrese el nuevo valor para 'nombre': ");
				String name = scan.nextLine();
				client.setName(name);
				break;
			case 2:
				System.out.println("Ingrese el nuevo valor para 'teléfono': ");
				long phone = scan.nextLong();
				client.setPhone(phone);
				break;
			case 3:
				System.out.println("Ingrese el nuevo valor para 'email': ");
				String email = scan.nextLine();
				client.setEmail(email);
				break;
			case 4:
				System.out.println("Ingrese el nuevo camino de la foto de identificación: ");
				String newPath = scan.nextLine();
				client.setIdPhotoPath(newPath);
				break;
			case 5:
				System.out.println("Ingrese el número de su tarjeta de crédito: ");
				long cardNumber = scan.nextLong();
				System.out.println("Ingrese la fecha de expiración de su tarjeta (dd/mm/aaaa): ");
				String cardExpiratioString = scan.nextLine();
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
				client.setPayment(cardNumber, cardExpiration, cardCode, cardOwner, cardAddress);
			case 6:
				System.out.println("-- DATOS DE LA LICENCIA DE CONDUCCION --");
				System.out.println("Ingrese el número de la licencia: ");
				long licenceNumber = scan.nextLong();
				System.out.println("Ingrese el país en que se expidió la licencia: ");
				String licenceCountry = scan.nextLine();
				System.out.println("Ingrese la fecha de expiración de su licencia (dd/mm/aaaa): ");
				String licenceExpiratioString = scan.nextLine();
				for (String value: licenceExpiratioString.split("/")) {
									
					calendarValues[i] = Integer.parseInt(value);
					i += 1;
									
				}
				Calendar licenceExpiration = Calendar.getInstance();
				licenceExpiration.set(calendarValues[0], calendarValues[1], calendarValues[2], 0, 0, 0);
				System.out.println("Ingrese la ubicación de la foto de su licencia (en el computador): ");
				String licencePhotoPath = scan.nextLine(); 
				CarRental.newLicence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath, login);
				break;
			default:
				System.out.println("Option not found.");
				break;

		}
		scan.close();

	}

	public static Licence newLicence(long licenceNumber, String licenceCountry, Calendar licenceExpiration, 
		String licencePhotoPath, String login) {
			
		Licence created = new Licence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath);
		if (!login.equals(null)) getClient(login).setLicence(created);
		return created;

	}

	//TODO: Create all methods
	
}
