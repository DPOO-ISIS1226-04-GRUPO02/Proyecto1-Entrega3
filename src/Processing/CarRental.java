package Processing;

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import Model.Car;
import Model.Store;
import Model.Client;
import Model.Insurance;
import Model.Licence;
import Model.Rental;
import Model.Extra;

public class CarRental {

	private static HashMap<String, Client> clients = RentalLoader.loadClients();
	private static HashMap<String, Car> cars = RentalLoader.loadCars();
	private static HashMap<String, Store> stores = RentalLoader.loadStores();
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
		Calendar expDate = (person.getLicence()).getExpiration();
		Calendar currentDate = Calendar.getInstance();
		if (expDate.after(currentDate) || expDate.equals(currentDate)){
			clients.put(login, person);
			// RentalWriter.addClient(person);

		} else {
		
		}
		

	}

	private static Client getClient(String login) {

		return clients.get(login);

	}

	public static boolean clientExists(String login) {

		Client client = getClient(login);
		if (client.equals(null)) return false;
		else return true;

	}

	private static Car getCar(String plate) {

		return cars.get(plate);

	}

	private static boolean carExists(String plate) {

		Car car = getCar(plate);
		if (car.equals(null)) return false;
		else return true;

	}

	public static Store getStore(String storeName) {

		return stores.get(storeName);

	}

	private static boolean storeExists(String name) {

		Store store = getStore(name);
		if (store.equals(null)) return false;
		else return true;

	}

	private static int getPriceCategory(String category) {

		return categories.get(category);

	}

	public static Set<String> getCategories() {

		return categories.keySet();

	}

	public static Set<Insurance> getInsurances() {

		return (Set<Insurance>) insurances.values();

	}

	public static void addInsurance(String name, int cost, String specs) {

		insurances.put(name, new Insurance(name, cost, specs, true));

	}

	public static void changeInsuranceStatus(String name, boolean status) {

		insurances.get(name).setActive(status);
		
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
				newLicence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath, login);
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

	public static void reserveCar(String renter, String category, int base, String origin, String destination,
		Calendar pickUpdateTime, Calendar returnDateTime, Licence secondaryLicence){
		
		Store originStore = getStore(origin);
		Store destinationStore = getStore(destination);
		Client person = getClient(renter);
		ArrayList<String> categoryList = originStore.getInventory().get(category);
		int i = 0;
		boolean found = false;
		Car reservation = null;
		while (!found && i < categoryList.size()) {
			String plate = categoryList.get(i);
			byte status = getCar(plate).getStatus();
			if (status == 0) {
				found = true;
				reservation = getCar(plate);
				reservation.setStatus((byte)1);
			}
		}
		if (reservation.equals(null)) {
			System.out.println(String.format(
				"No se ha encontrado un carro de esta categoría en la tienda %s. Seleccione otra, por favor.", 
				origin));
			return;
		}
		if (storeExists(origin) && storeExists(destination) && clientExists(renter)) {
			Rental newRental = new Rental(person, reservation, base, originStore, destinationStore, pickUpdateTime, 
				returnDateTime, secondaryLicence);
			person.setActiveRental(newRental);
		} else {
			System.out.println("No se ha podido iniciar la reserva correctamente. Revise los datos que ha ingresado. ");
		}
		
	}

	public static void confirmPickUp(String login) {

		Scanner scan = new Scanner(System.in);
		Client person = getClient(login);
		if (person.getActiveRental().equals(null)) {
			System.out.println("Por favor ingrese los siguientes datos para poder formalizar la reserva.");
			System.out.println("Ingrese el nombre de usuario del cliente que va a realizar la reserva: ");
			String clientLogin = scan.nextLine();
			while (!clientExists(clientLogin)) {
				System.out.println("Nombre de usuario no encontrado!"); 
				System.out.println("Ingrese de nuevo el nombre de usuario o 'stop' si quiere salir: ");
				clientLogin = scan.nextLine();
				if (clientLogin.equals("stop")) return;
			}
			System.out.println("Ingrese la categoría de la cual desea alquilar el carro: ");
			String category = scan.nextLine();
			while (!categories.containsKey(category)) {
				System.out.println("Esta categoría no existe en nuestro sistema. Ingrese una nuevamente o 'stop' para salir: ");
				category = scan.nextLine();
				if (category.equals("stop")) return;
			}
			System.out.println("¿A qué tienda desea devolver el carro luego del alquiler?: ");
			String destination = scan.nextLine();
			while (!storeExists(destination)) {
				System.out.println("Esta tienda no está registrada en nuestro sistema. Ingrese otra o 'stop' para salir: ");
				destination = scan.nextLine();
				if (destination.equals("stop")) return;
			}
			// TODO: Finish Rental formalization
		} 
		Rental rental = person.getActiveRental();
		Car car = rental.getCar();
		rental.setActive(true);
		car.setStatus((byte) 2);
		rental.setPickUp(Calendar.getInstance());
		rental.getOrigin().removeCar(car);

	}

	public static void confirmReturn(String login, int days, String comments, int extraCharges, String employeeLogin, 
		String employeePassword) {

		Client person = getClient(login);
		if (!person.getActiveRental().equals(null)) System.out.println("Este cliente no tiene una renta activa. ");
		else {
			Rental rental = person.getActiveRental();
			rental.setActive(false);
			rental.setReturn(Calendar.getInstance());
			int total = rental.getFinalCharge();
			Store destination = getStore(Users.loadUser(employeeLogin, employeePassword).getWorkplace());
			if (!destination.equals(rental.getDestination())) rental.addExtra("Devuelto a tienda distinta", 
				10000, "El carro fue devuelto a una tienda distinta de la que fue inicialmente planeada.");
			String resultingString = "Alquiler finalizado con éxito.\nDetalles:\n";
			ArrayList<Extra> extras = rental.getExtras();
			for (Extra extra: extras) resultingString += String.format(" + %-20s %d/día", 
				extra.getType() + ":", extra.getCost());
			ArrayList<Insurance> insurances = rental.getInsurances();
			for (Insurance insurance: insurances) resultingString += String.format(" + %-20s %d/día", 
				insurance.getName() + ":", insurance.getCost());
			Car car = rental.getCar();
			car.setAvailableTime(days);
			car.setStatus((byte) 0);
			destination.addCar(car);
			resultingString += String.format("El total final del alquiler es de %8d", total);
		}

	} 
	
}
