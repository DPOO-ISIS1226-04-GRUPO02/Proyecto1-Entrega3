package Processing;

import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import Model.Car;
import Model.Store;
import Model.User;
import Model.Client;
import Model.Insurance;
import Model.Licence;
import Model.Payment;
import Model.Rental;
import Model.Extra;

public class CarRental {

	private static HashMap<String, Client> clients;
	private static HashMap<String, Car> cars;
	private static HashMap<String, Store> stores;
	private static HashMap<String, Integer> categories;
	private static HashMap<String, Insurance> insurances;
	private static HashMap<Long, Licence> secondaryLicences;
	private static HashMap<Car, ArrayList<Rental>> rentals;

	public static void loadCarRental() throws IOException, ParseException {

		clients = RentalLoader.loadClients();
		cars = RentalLoader.loadCars();
		stores = RentalLoader.loadStores();
		categories = RentalLoader.loadCategories();
		insurances = RentalLoader.loadInsurances();
		secondaryLicences = RentalLoader.loadSecondaryLicence();
		rentals = RentalLoader.loadRentals();

	}

	public static void registerNewClient(String name, long phone, String email, Calendar dateBirth, String nationality, 
		String idPhotoPath, long cardNumber, Calendar cardExpiration, short cardCode, String cardOwner, String cardAddress, 
		String login, long licenceNumber, String licenceCountry, Calendar licenceExpiration, String licencePhotoPath) {

		Payment payment = new Payment(licenceNumber, licenceExpiration, cardCode, cardOwner, cardAddress);
		Client person = new Client(name, phone, email, dateBirth, nationality, idPhotoPath, null, payment, login);
		person.setLicence(newLicence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath, null));
		
		clients.put(login, person);
		RentalWriter.addClient(person);	
	}

	public static Client getClient(String login) {

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

	public static Store getStore(String storeName) {

		return stores.get(storeName);

	}

	private static boolean storeExists(String name) {

		Store store = getStore(name);
		if (store.equals(null)) return false;
		else return true;

		}

	public static Set<String> getCategories() {

		return categories.keySet();

	}
	public static Set<String> getStores() {
		return stores.keySet();
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
		if (clientExists(login)) getClient(login).setLicence(created);
		return created;

	}

	public static void reserveCar(String renter, String category, String origin, String destination,
		Calendar pickUpdateTime, Calendar returnDateTime){
		Store originStore = getStore(origin);
		Store destinationStore = getStore(destination);
		Client person = getClient(renter);
		ArrayList<String> categoryList = originStore.getInventory().get(category);
		int i = 0;
		int base = 0;
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
			base = categories.get(category);
			Rental newRental = new Rental(person, reservation, base, new ArrayList<Insurance>(), originStore, 
				destinationStore, pickUpdateTime, returnDateTime, new ArrayList<Licence>(), new ArrayList<Extra>());
			person.setActiveRental(newRental);
			System.out.println("Reserva creada exitosamente");
		} else {
			System.out.println("No se ha podido iniciar la reserva correctamente. Revise los datos que ha ingresado. ");
		}
		
	}

	public static void confirmPickUp(String login, ArrayList<Licence> secondaryDriver, String employeeLogin, 
		String employeePassword) {
		Client person = getClient(login);
		Rental rental = person.getActiveRental();

		rental.setActive(true);
		Car car = rental.getCar();
		car.setStatus((byte)2);
		if ( secondaryDriver.size() > 0){
			for (Licence licence : secondaryDriver) {
				rental.getSecondaryDriver().add(licence);
			}
		}
		
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
			if (!destination.equals(rental.getDestination())) 
				rental.addExtra("Devuelto a tienda distinta", 
				0000 /* TODO: Change value */,
				"El carro fue devuelto a una tienda distinta de la que fue inicialmente planeada.");
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
			System.out.println(resultingString);
		}

	}
	public static void registerCar(Byte status, String brand, String plate, String model, String color, boolean isAutomatic, String category, int availableIn, String store){
		Car carro = new Car(brand, plate, model, color, isAutomatic, category, availableIn, (byte) 0);
		carro.setStatus(status);
		cars.put(carro.getPlate(), carro);
		Store st = stores.get(store);
		((st.getInventory()).get(category)).add(plate);
	} 
	public static void newStore(String name, String location, Calendar openingTime, Calendar closingTime, byte OpeningDays){
		HashMap <String, ArrayList<String>> inventory = new HashMap<String, ArrayList<String>>();
		Store store = new Store(name, location, openingTime, closingTime, OpeningDays, inventory);
		stores.put(name, store);
	}
	public static void changeVehicleStatus(String plate, byte status){
		(cars.get(plate)).setStatus(status);
	}
	public static ArrayList<Rental> getPastRentals(Car car){
		return rentals.get(car);
	}
}
