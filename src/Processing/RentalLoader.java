package Processing;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import Model.Client;
import Model.Car;
import Model.Rental;
import Model.Store;
import Model.User;
import Model.Insurance;

public class RentalLoader {

    private static String mainFolderPath = "";
    // TODO: Define folder path

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
        BufferedReader br = new BufferedReader(new FileReader(ruta));
		String linea = br.readLine();
        linea = br.readLine();
		while (linea != null)
        {
            String[] partes = linea.split(",");
            String brand = partes[0];
            String plate = partes[1];
            Short model = Short.parseShort(partes[2]);
            String color = partes[3];
            boolean isAutomatic = Boolean.parseBoolean(partes[4]);
            String strAvailableIn = partes[5];

            // cambiar fecha string a Calendar
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar availableIn = Calendar.getInstance();

            Date availableInDate = (Date)formatter.parse(strAvailableIn);
            availableIn.setTime(availableInDate);

            Car newCar = new Car(brand, plate, model, color, isAutomatic, plate, availableIn);
            cars.put(plate, newCar);
            linea = br.readLine();


        }
        br.close();
        return cars;

    }

    public static HashMap<String, Store> loadStores() throws IOException, ParseException {

        HashMap<String, Store> stores = new HashMap<String, Store>();

        //lectura de la carpeta con los distintos .txt"
        String folderPath = "ruta";
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        for (File file: listOfFiles)
        {
            if (file.isFile())
            {
                BufferedReader br = new BufferedReader(new FileReader(file));
		        String linea = br.readLine();
                linea = br.readLine();
		        while (linea != null)
            {
                String[] partes = linea.split(",");
                String name= partes[0];
                String location= partes[1] ;
                String  strOpeningTime = partes[2];
                String strClosingTime = partes[3];
                byte openingDays= Byte.parseByte(partes[4]);
                


                //cambiar hora string a calendar
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Calendar openingTime = Calendar.getInstance();
                Calendar closingTime = Calendar.getInstance();

                Date openingDate = (Date)formatter.parse(strOpeningTime);
                Date closingDate = (Date)formatter.parse(strClosingTime);

                openingTime.setTime(openingDate);
                closingTime.setTime(closingDate);

                
                ArrayList<String> inventory = new ArrayList<>();
                for (int i=5; i< partes.length; i++)
                {
                    String car = partes[i];
                    inventory.add(car);
                }

                Store newStore = new Store(name, location, openingTime, closingTime, openingDays, inventory);
                stores.put(name, newStore);


                linea = br.readLine();


            }
        }
        
        }


        return stores;

    }

    public static HashMap<Store, HashMap<String, ArrayList<Car>>> loadInventory(HashMap<String, Car> cars) {

        HashMap<Store, HashMap<String, ArrayList<Car>>> stores = 
            new HashMap<Store, HashMap<String, ArrayList<Car>>>();

        // TODO: Get all stores' information

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
