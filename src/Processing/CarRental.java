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
	private static HashMap<Long, Licence> secondaryLicences;

	public static void loadCarRental() throws IOException, ParseException {

		clients = RentalLoader.loadClients();
		cars = RentalLoader.loadCars();
		stores = RentalLoader.loadStores();
		categories = RentalLoader.loadCategories();
		insurances = RentalLoader.loadInsurances();
		rentals = RentalLoader.loadRentals(clients);
		secondaryLicences = RentalLoader.loadSecondaryLicence();

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

	public static Car getCar(String plate) {

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
		if (store == null) return false;
		else return true;

		}

	public static Set<String> getCategories() {

		return categories.keySet();

	}

	public static Set<String> getStores() {

		return stores.keySet();

	}

	public static boolean insuranceExists(String name) {

		if (insurances.keySet().contains(name)) return true;
		else return false;

	}

	public static Set<String> getInsurances() {

		return insurances.keySet();

	}
 
	public static void addInsurance(String name, int cost, String specs) {
		Insurance insurance = new Insurance(name, cost, specs, true);
		insurances.put(name, insurance);
		RentalWriter.newInsurance(insurance);

	}

	public static boolean changeInsuranceStatus(String name) {

		boolean truth = insurances.get(name).isActive();
		insurances.get(name).setActive(!truth);
		return !truth;
		
	}

	public static void modifyClient(String login, Scanner scan) {

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
				client.setPayment(new Payment(cardNumber, cardExpiration, cardCode, cardOwner, cardAddress));
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
		RentalWriter.changeClientInformation(client);

	}

	public static void reserveCar(String renter, String category, String origin, String destination,
		Calendar pickUpDateTime, Calendar returnDateTime, int n, Scanner scan) throws ParseException {

		Store originStore = getStore(origin);
		Store destinationStore = getStore(destination);
		Client person = getClient(renter);
		ArrayList<String> categoryList = originStore.getInventory().get(category);
		int i = 0;
		boolean found = false;
		Car reservation = null;
		while (person.getActiveRental() == null && categoryList != null && (!found && i < categoryList.size())) {
			String plate = categoryList.get(i);
			byte status = getCar(plate).getStatus();
			Calendar availableIn = getCar(plate).getAvailableDate();
			if (status == (byte) 0 && availableIn.before(pickUpDateTime)) {
				found = true;
				reservation = getCar(plate);
				reservation.setStatus((byte)1);
			}
			i += 1;
		}
		if (reservation == null) {
			if (person.getActiveRental() != null){
				System.out.println("Usted ya tiene una reserva activa en curso");
			}
			else{
			System.out.println(String.format(
				"No se ha encontrado un carro de esta categoría en la tienda %s. Seleccione otra, por favor.", 
				origin));
			}
			return;
		}
		ArrayList<Licence> licences = createLicences(n, scan);
		if (storeExists(origin) && storeExists(destination) && clientExists(renter)) {
			int base = categories.get(category);
			Rental newRental = new Rental(person, reservation, base, new ArrayList<Insurance>(), originStore, 
				destinationStore, pickUpDateTime, returnDateTime, licences, new ArrayList<Extra>(), true);
			person.setActiveRental(newRental);
			RentalWriter.newRental(newRental);
			RentalWriter.changeCarInformation(reservation);
			System.out.println("Reserva creada exitosamente");
		} else {
			System.out.println("No se ha podido iniciar la reserva correctamente. Revise los datos que ha ingresado. ");
		}
		
	}

	public static void reserveCar(String plate, String origin, String destination, int days) {

		Calendar returnCalendar = Calendar.getInstance();
		returnCalendar.add(Calendar.DAY_OF_MONTH, days);
		Rental rental = new Rental(null, getCar(plate), days, null, getStore(origin), getStore(destination), 
			Calendar.getInstance(), returnCalendar, null, null, true);
		rental.getCar().setAvailableTime(days);
		RentalWriter.newRental(rental);

	}

	private static ArrayList<Licence> createLicences(int n, Scanner scan) throws ParseException {

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
			Licence licence = new Licence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath);
			licences.add(licence);
			secondaryLicences.put(licenceNumber, licence);
			// TODO: Add new licences to files with RentalWriter
		}
		return licences;

	}

	public static void confirmPickUp(String login, String workplace, Scanner scan) throws ParseException {

		Client person = getClient(login);
		if (workplace.equals("null")){
			ArrayList<String> tiendas = new ArrayList<>(stores.keySet());
			int i = 1;
			for (String sede : tiendas){
				System.out.println(i + ". " + sede);
				i += 1;
			}
			System.out.println("Ingrese el número de la sede desde la que está haciendo la reserva: ");
			int ref = scan.nextInt();
			workplace = tiendas.get(ref - 1);
		}

		if (person.getActiveRental()== null) {
			System.out.println("Ingrese la categoría del vehículo que desea alquilar: ");
			String category = scan.nextLine();
			while (!categories.containsKey(category) && !category.equals("stop")) {
				System.out.println("Esta categoría no existe en esta tienda. Intente de nuevo o escriba 'stop para salir: ");
				category = scan.nextLine();
			}
			System.out.println("Ingrese el nombre de la tienda al que se va a devolver el carro: ");
			String destination = scan.nextLine();
			while (!storeExists(destination) && !destination.equals("stop")) {
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
			reserveCar(login, category, workplace, destination, Calendar.getInstance(), returnDate, n, scan);
		}

		Rental rental = person.getActiveRental();
		if (workplace.equals(rental.getOrigin().getName())){
			rental.setActive(true);
			ArrayList<String> seguros = new ArrayList<>(insurances.keySet());
			int eleccion = 0;
			for (String seguro : seguros){
				System.out.println("Desea añadir el siguiente seguro: " + seguro +". Marque 1 para aceptarlo o 0 para rechazarlo");
				eleccion = scan.nextInt();
				if (eleccion == 1){
					rental.getInsurances().add(insurances.get(seguro));
				}
			}
			Car car = rental.getCar();
			car.setStatus((byte)2);
			RentalWriter.changeRentalInformation(rental);
			RentalWriter.changeCarInformation(car);
			System.out.println("Vehículo entregado");
		}
		else {
			System.out.println("No puede confirmar entregas de una sede en la que no trabaja a menos que sea General Manager");
		}
	}

	private static ArrayList<Extra> registerExtras(Scanner scan) {

		boolean more = true;
		ArrayList<Extra> extras = new ArrayList<Extra>();
		while (more) {
			scan.nextLine();
			System.out.println("Ingrese el tipo de recargo siendo lo más compacto posible: ");
			String type = scan.nextLine();
			System.out.println("Ingrese el costo de este recargo: ");
			int cost = scan.nextInt();
			System.out.println("Ingrese los comentarios del recargo (no use comas ','): ");
			String specification = scan.nextLine();
			extras.add(new Extra(type, cost, specification));
			System.out.println("¿Existe algún otro recargo que desee ingresar?");
			System.out.println("1. Sí\n2. No\nIngrese el número de su respuesta: ");
			int response = scan.nextInt();
			if (response == 2) more = false;
		}
		return extras;

	}

	public static void confirmReturn(String login, int days, int response, String employeeLogin, 
		String employeePassword, Scanner scan) {

		Client person = getClient(login);
		if (person.getActiveRental() == null) System.out.println("Este cliente no tiene una renta activa. ");
		else {
			Rental rental = person.getActiveRental();
			ArrayList<Extra> extrasList = new ArrayList<Extra>();
			if (response == 1) extrasList = registerExtras(scan);
			rental.setExtras(extrasList);
			rental.setActive(false);
			int total = rental.getFinalCharge();
			Store destination = getStore(person.getActiveRental().getDestination().getName());
			String resultingString = "Alquiler finalizado con éxito.\nDetalles:\n";
			ArrayList<Extra> extras = rental.getExtras();
			for (Extra extra: extras) resultingString += String.format(" + %-20s: %d/día", 
				extra.getType(), extra.getCost());
			ArrayList<Insurance> insurances = rental.getInsurances();
			for (Insurance insurance: insurances) resultingString += String.format(" + %-20s: %d/día", 
				insurance.getName(), insurance.getCost());
			Car car = rental.getCar();
			car.setAvailableTime(days);
			car.setStatus((byte) 0);
			destination.addCar(car);
			resultingString += String.format("El total final del alquiler es de %8d", total);
			System.out.println(resultingString);
			RentalWriter.changeRentalInformation(rental);
			RentalWriter.changeCarInformation(car);
		}

	}

	public static void registerCar(String brand, String plate, String model, String color, 
		boolean isAutomatic, String category, int availableIn, String store) {

		Car carro = new Car(brand, plate, model, color, isAutomatic, category, availableIn, (byte) 0);
		carro.setStatus((byte) 0);
		cars.put(plate, carro);
		Store st = stores.get(store);
		((st.getInventory()).get(category)).add(plate);
		RentalWriter.addCar(carro);

	} 

	public static void newStore(String name, String location, Calendar openingTime, Calendar closingTime, 
		byte OpeningDays) {

		HashMap <String, ArrayList<String>> inventory = new HashMap<String, ArrayList<String>>();
		Store store = new Store(name, location, openingTime, closingTime, OpeningDays, inventory);
		stores.put(name, store);
		RentalWriter.addStore(store);

	}

	public static void changeVehicleStatus(String plate, byte status) {

		Car car = cars.get(plate);
		(car).setStatus(status);
		RentalWriter.changeCarInformation(car);

	}

	public static String getPastRentals(Car car) {

		ArrayList<Rental> listRentals = rentals.get(car);
		String result = "";
		int i = 1;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		for (Rental rental: listRentals) {
			result += String.format("Renta #%d", i);
        	String formattedDateOut = simpleDateFormat.format(rental.getPickUp().getTime());
			result += String.format("\n\tFecha de inicio de la renta: %s", formattedDateOut);
			String formattedDateIn = simpleDateFormat.format(rental.getReturn().getTime());
			result += String.format("\n\tFecha de finalización de la renta: %s", formattedDateIn);
			result += String.format("\n\tCosto total de la renta: %8d$", rental.getFinalCharge());
			result += "\n\n";
		}
		result += "Información completa de las rentas pasadas en ./data/rentals/" + car.getPlate();
		return result;

	}

	public static void setTariff(String category, int amount) {

		categories.put(category, amount);
		RentalWriter.changeTariffs(category, amount);

	}

}
