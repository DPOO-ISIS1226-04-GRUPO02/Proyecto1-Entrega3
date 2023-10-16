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
	private static HashMap<Car, ArrayList<Rental>> rentals;

	public static void loadCarRental() throws IOException, ParseException {

		clients = RentalLoader.loadClients();
		cars = RentalLoader.loadCars();
		stores = RentalLoader.loadStores();
		categories = RentalLoader.loadCategories();
		insurances = RentalLoader.loadInsurances();
		rentals = RentalLoader.loadRentals();

	}

	public static void registerNewClient(String name, long phone, String email, Calendar dateBirth, String nationality, 
		String idPhotoPath, long cardNumber, Calendar cardExpiration, short cardCode, String cardOwner, String cardAddress, 
		String login, long licenceNumber, String licenceCountry, Calendar licenceExpiration, String licencePhotoPath) {

		Payment payment = new Payment(cardNumber, cardExpiration, cardCode, cardOwner, cardAddress);
		Licence licence = new Licence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath);
		Client person = new Client(name, phone, email, dateBirth, nationality, idPhotoPath, licence, payment, login);
		
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

	public static boolean carExists(String plate) {

		Car car = getCar(plate);
		if (car.equals(null)) return false;
		else return true;

	}

	public static Store getStore(String storeName) {

		return stores.get(storeName);

	}

	public static String getStoreByPlate(String plate) {

		for (String storeString: stores.keySet()) {
			Store store = getStore(storeString);
			for (String category: categories.keySet()) {
				if (store.getInventory().get(category).contains(plate)) return storeString;
			}
		}
		return null;

	}

	public static boolean storeExists(String name) {

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
				Licence licence = new Licence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath);
				if (clientExists(login)) getClient(login).setLicence(licence);
				break;
			default:
				System.out.println("Option not found.");
				break;

		}
		scan.close();

	}

	public static void reserveCar(String renter, String category, String origin, String destination,
		Calendar pickUpdateTime, Calendar returnDateTime, int n) throws ParseException {

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
		ArrayList<Licence> licences = createLicences(n);
		if (storeExists(origin) && storeExists(destination) && clientExists(renter)) {
			int base = categories.get(category);
			Rental newRental = new Rental(person, reservation, base, new ArrayList<Insurance>(), originStore, 
				destinationStore, pickUpdateTime, returnDateTime, licences, new ArrayList<Extra>());
			person.setActiveRental(newRental);
			System.out.println("Reserva creada exitosamente!");
		} else {
			System.out.println("No se ha podido iniciar la reserva correctamente. Revise los datos que ha ingresado. ");
		}
		
	}

	public static void reserveCar(String plate, String origin, String destination, int days) {

		Calendar returnCalendar = Calendar.getInstance();
		returnCalendar.add(Calendar.DAY_OF_MONTH, days);
		Rental rental = new Rental(null, getCar(plate), days, null, getStore(origin), getStore(destination), 
			Calendar.getInstance(), returnCalendar, null, null);
		rental.getCar().setAvailableTime(days);

	}

	public static ArrayList<Licence> createLicences(int n) throws ParseException {

		Scanner scan = new Scanner(System.in);
		ArrayList<Licence> licences = new ArrayList<Licence>();
		for (int j = 0; j < n; j++) {
			System.out.println(String.format("Ingrese los datos de la licencia del conductor #%d.", j+2));
			System.out.println("Ingrese el número de la licencia: ");
			long licenceNumber = scan.nextLong();
			System.out.println("Ingrese el país en que se expidió esta licencia: ");
			String licenceCountry = scan.nextLine();
			System.out.println("Ingrese la fecha de expiración de la licencia (AAAA-MM-DD): ");
			String licenceExpString = scan.nextLine();
			Calendar licenceExpiration = Calendar.getInstance();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date licenceExpDate = (Date) formatter.parse(licenceExpString);
			licenceExpiration.setTime(licenceExpDate);
			System.out.println("Ingrese la ubicación de la licencia en el computador (.png únicamente): ");
			String licencePhotoPath = scan.nextLine();
			licences.add(new Licence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath));
		}
		scan.close();
		return licences;

	}

	public static void confirmPickUp(String login, String workplace) throws ParseException {

		Client person = getClient(login);
		Scanner scan = new Scanner(System.in);

		if (person.getActiveRental().equals(null)) {
			System.out.println("Ingrese la categoría del vehículo que desea alquilar: ");
			String category = scan.nextLine();
			while (!categories.containsKey(category)) {
				System.out.println("Esta categoría no existe en esta tienda. Intente de nuevo o escriba 'stop para salir: ");
				category = scan.nextLine();
			}
			System.out.println("Ingrese el nombre de la tienda al que se va a devolver el carro: ");
			String destination = scan.nextLine();
			while (!storeExists(destination)) {
				System.out.println("Tienda no encontrada. Ingrese el nombre de nuevo o 'stop' para salir: ");
				destination = scan.nextLine();
			}
			System.out.println("Ingrese la fecha en que se planea devolver el vehículo (AAAA-MM-DD:HH-MM): ");
			String returnDateString = scan.nextLine();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd:HH-mm");
        	Calendar returnDate = Calendar.getInstance();
            Date returnDate2 = (Date)formatter.parse(returnDateString);
			returnDate.setTime(returnDate2);
			System.out.println("Ingrese el número de segundos conductores que desea registrar: ");
			int n = scan.nextInt();
			reserveCar(login, category, workplace, destination, Calendar.getInstance(), returnDate, n);
		}
		scan.close();

		Rental rental = person.getActiveRental();
		rental.setActive(true);
		Car car = rental.getCar();
		car.setStatus((byte)2);
		Store origin = getStore(workplace);
		rental.setPickUp(Calendar.getInstance());
		rental.setOrigin(origin);
		
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

	public static void registerCar(String brand, String plate, String model, String color, 
		boolean isAutomatic, String category, int availableIn, String store) {

		Car carro = new Car(brand, plate, model, color, isAutomatic, category, availableIn, (byte) 0);
		carro.setStatus((byte) 0);
		cars.put(plate, carro);
		Store st = stores.get(store);
		((st.getInventory()).get(category)).add(plate);

	} 

	public static void newStore(String name, String location, Calendar openingTime, Calendar closingTime, 
		byte OpeningDays) {

		HashMap <String, ArrayList<String>> inventory = new HashMap<String, ArrayList<String>>();
		Store store = new Store(name, location, openingTime, closingTime, OpeningDays, inventory);
		stores.put(name, store);

	}

	public static void changeVehicleStatus(String plate, byte status) {

		(cars.get(plate)).setStatus(status);

	}

	public static ArrayList<Rental> getPastRentals(Car car) {

		return rentals.get(car);

	}

}
