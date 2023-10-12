package Processing;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;

import Model.Car;
import Model.Store;
import Model.Client;
import Model.Insurance;
import Model.Licence;


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
		String login, int licenceNumber, String licenceCountry, Calendar licenceExpiration, String licencePhotoPath) {

		Client person = new Client(name, phone, email, dateBirth, nationality, idPhotoPath, cardNumber, cardExpiration, 
			cardCode, cardOwner, cardAddress, login);
		person.setLicence(new Licence(licenceNumber, licenceCountry, licenceExpiration, licencePhotoPath));
		// TODO: Verify that the licence, nor the card have yet expired
		Calendar expDate = (person.getLicence()).getExpiration();
		Calendar currentDate = Calendar.getInstance();
		if (expDate.after(currentDate) || expDate.equals(currentDate)){
			clients.put(login, person);
			// RentalWriter.addClient(person);

		}

		else{
		
		}
		

	}

	//TODO: Create all methods

	public static void reserveCar(Client renter, Car vehicle, int base, Store origin, Store destination, Calendar pickUpdateTime, Calendar returnDateTime, Licence secondaryLicence){
		byte currentStatus = vehicle.getStatus();
		byte available = 0;
		if (currentStatus == available){
			
		}
	}
	
}
