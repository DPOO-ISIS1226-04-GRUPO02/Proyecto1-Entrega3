package Processing;

import java.util.HashMap;
import java.util.ArrayList;

import Model.Car;
import Model.Store;
import Model.Client;
import Model.Insurance;


public class CarRental {

	private HashMap<String, Client> clients;
	private HashMap<String, Car> cars;
	private HashMap<String, Store> stores;
	private HashMap<Store, HashMap<String, ArrayList<Car>>> inventory;
	private HashMap<String, Integer> categories;
	private HashMap<String, Insurance> insurances;
	private Users users;
	private RentalWriter writer;
	
	//TODO: Create all methods
	
}
