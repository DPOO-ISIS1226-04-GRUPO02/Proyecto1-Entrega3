package Processing;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

import Model.Client;
import Model.Car;
import Model.Rental;
import Model.Store;
import Model.Insurance;

public class RentalLoader {

    private static String mainFolderPath = "";
    private static String clientsFolderPath = "";
    private static String storesFolderPath = "";
    private static String carsFolderPath = "";
    private static String rentalsFolderPath = "";
    private static String usersFolderPath = "";
    private static String insurancesFolderPath = "";
    // TODO: Initialize all values for the folders

    public static HashMap<String, User> usersInformation() {

        HashMap<String, User> users = new HashMap<String, User>();
        
        // TODO: Get the information for all users

        return users;

    }

    public static HashMap<String, Integer> loadCategories() {

        HashMap<String, Integer> categories = new HashMap<String, Integer>();

        // TODO: Obtain categories' information

        return categories;

    }

    public static HashMap<String, Client> loadClients() {

        HashMap<String, Client> clients = new HashMap<String, Client>();

        // TODO: Get all clients' inforamtion

        return clients;

    }

    public static HashMap<String, Car> loadCars() {

        HashMap<String, Car> cars = new HashMap<String, Car>();

        // TODO: Get all cars' information

        return cars;

    }

    public static HashMap<Store, HashMap<String, ArrayList<Car>>> loadInventory(HashMap<String, Car> cars) {

        HashMap<Store, HashMap<String, ArrayList<Car>>> stores = 
            new HashMap<Store, HashMap<String, ArrayList<Car>>>();

        // TODO: Get all stores' information

        return stores;

    }

    public static HashMap<String, Store> loadStores(HashMap<Store, HashMap<String, ArrayList<Car>>> inventory) {

        HashMap<String, Store> stores = new HashMap<String, Store>();
        Set<Store> storeList = inventory.keySet();
        for (Store store: storeList) {

            String name = store.getName();
            stores.put(name, store);

        }

        return stores;

    }

    public static HashMap<Car, ArrayList<Rental>> loadRentals(HashMap<String, Car> cars) {

        HashMap<Car, ArrayList<Rental>> rentals = new HashMap<Car, ArrayList<Rental>>();

        // TODO: Get all past rentals for all cars

        return rentals;

    }

    public static HashMap<String, Insurance> loadInsurances() {

        HashMap<String, Insurance> insurances = new HashMap<String, Insurance>();

        // TODO: Get all insurances' information

        return insurances;

    }

}
