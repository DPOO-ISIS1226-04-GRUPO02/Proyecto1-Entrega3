package Processing;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;

import Model.Car;
import Model.Store;
import Model.Client;
import Model.Insurance;


public class CarRental {

	private static HashMap<String, Client> clients = RentalLoader.loadClients();
	private static HashMap<String, Car> cars = RentalLoader.loadCars();
	private static HashMap<Store, HashMap<String, ArrayList<Car>>> inventory = RentalLoader.loadInventory(cars);
	private static HashMap<String, Store> stores = RentalLoader.loadStores(inventory);
	private static HashMap<String, Integer> categories = RentalLoader.loadCategories();
	private static HashMap<String, Insurance> insurances = RentalLoader.loadInsurances();
	private static HashMap<String,User> users = Users.getUsers();

	public static void registerNewClient(String name, long phone, String email, Calendar dateBirth, String nationality, 
		String idPhotoPath, long cardNumber, Calendar cardExpiration, short cardCode, String cardOwner, String cardAddress, 
		String login) {

		Client person = new Client(name, phone, email, dateBirth, nationality, idPhotoPath, cardNumber, cardExpiration, 
			cardCode, cardOwner, cardAddress, login);
		// TODO: Add Licence to person before adding
		clients.put(login, person);
		RentalWriter.addClient(person);

	}

	//TODO: Create all methods
	
}
