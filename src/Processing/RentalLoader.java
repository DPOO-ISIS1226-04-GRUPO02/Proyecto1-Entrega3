package Processing;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
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
import Model.Payment;
import Processing.CarRental;

public class RentalLoader {

    private static String mainFolderPath = "";
    // TODO: Define folder path

    public static HashMap<String, User> usersInformation() {

        HashMap<String, User> users = new HashMap<String, User>();
        BufferedReader br = new BufferedReader(new FileReader(ruta));
		String linea = br.readLine();
        linea = br.readLine();
		while (linea != null)
        {
           String[] partes = linea.split(",");
           String username = partes[0];
           String password = partes[1];
           int access = Integer.parseInt(partes[2]);
           String workplace = partes[3];

           User newUser= new User(username, password, access, workplace);
           users.put(username, newUser);

           linea = br.readLine();


        }
        



        return users;

    }

    public static HashMap<String, Integer> loadCategories() {

        HashMap<String, Integer> categories = new HashMap<String, Integer>();
        BufferedReader br = new BufferedReader(new FileReader(ruta));
		String linea = br.readLine();
        linea = br.readLine();
        {
             String[] partes = linea.split(",");
             String category = partes[0];
             int price = Integer.parseInt(partes[1]);

             categories.put(category, price);

             linea = br.readLine();

        }

        return categories;

    }

    public static HashMap<String, Client> loadClients() {

        HashMap<String, Client> clients = new HashMap<String, Client>();

        
        String folderPath = "ruta";
        File mainFolder = new File(folderPath);
        File[] clientFolders = mainFolder.listFiles();
    
            for (File clientFolder: clientFolders)
            {
                if (file.isFile())
                {
                
                String logIn = clientFolder.getName();

                File clientInfoFile= new File(clientFolder, "clientInfo.txt");
                File paymentFile = new File(clientFolder, "paymentInfo.txt");
                File licenceFile = new File(clientFolder, "licenceInfo.txt");
                
                //objeto payment
                Payment newPayment= null;

            
                BufferedReader br = new BufferedReader(new FileReader(paymentFile));
		        String linea = br.readLine();
                linea = br.readLine();
		        while (linea != null)
                {
                    String[] partes = linea.split(",");
                    long number = Long.parseLong(partes[0]);
                    String strExpiration = partes[1];
                    short code = Short.parseShort(partes[2]);
                    String owner = partes[3];
                    String address = partes[4];

                    //cambiar fecha string a Calendar
                    DateFormat formatter = new SimpleDateFormat("MM-yy");
                    Calendar expiration = Calendar.getInstance();

                    Date expirationDate = (Date)formatter.parse(strExpiration);
                    expiration.setTime(expirationDate);

                    newPayment= new Payment(number, expiration, code, owner, address);
                    linea = br.readLine();


                }

                

                //license
                long number =0;
                String country = null;
                Calendar expirationLicence = null;
                String photoPath = null;


                BufferedReader br = new BufferedReader(new FileReader(licenceFile));
		        String linea = br.readLine();
                linea = br.readLine();
		        while (linea != null)
                {
                    String[] partes = linea.split(",");
                    number = Long.parseLong(partes[0]);
                    country = partes[1];
                    String strExpirationLicence = partes[2];
                    photoPath= "./data/clients/"+logIn+"/licence.JPG"

                    //cambiar fecha string a Calendar
                    DateFormat formatter = new SimpleDateFormat("yy-MM");
                    expirationLicence = Calendar.getInstance();

                    Date expirationLicenceDate = (Date)formatter.parse(strExpirationLicence);
                    expirationLicence.setTime(expirationLicenceDate);

                    linea = br.readLine();
                }


                //client
                BufferedReader br = new BufferedReader(new FileReader(clientInfoFile));
		        String linea = br.readLine();
                linea = br.readLine();
		        while (linea != null)
                {
                    String[] partes = linea.split(",");
                    String name = partes[0];
                    long phone = Long.parseLong(partes[1]);
                    String email = partes[2];
                    String strDateBirth = partes[3];
                    String nationality = partes[4];
                    String idPhotopath = "./data/clients/"+logIn+"/identification.JPG";
                    String login = partes[5];

                    //cambiar fecha string a Calendar
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar dateBirth = Calendar.getInstance();

                    Date dateBirthDate = (Date)formatter.parse(strDateBirth);
                    dateBirth.setTime(dateBirthDate);

                    Client newClient = new Client(name, phone, email, dateBirth, nationality, idPhotopath, newPayment, login);
                    newClient.setLicence(newLicence(number, country, expirationLicence, photoPath, login));

                    linea = br.readLine();

                }




                }
               


            }
        


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

            long daysBetween = ChronoUnit.DAYS.between(availableIn.toInstant(), Calendar.getInstance().toInstant());

            Car newCar = new Car(brand, plate, model, color, isAutomatic, plate, (int) daysBetween);
            cars.put(plate, newCar);

            linea = br.readLine();


        }
        br.close();
        return cars;

    }

    public static HashMap<String, Store> loadStores() throws IOException, ParseException {

        HashMap<String, Store> stores = new HashMap<String, Store>();
        HashMap<String, Car> cars = loadCars();

        //lectura de la carpeta con los distintos .txt"
        String folderPath = ruta;
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

                
                ArrayList<String> platesCarsInventory = new ArrayList<>();
                for (int i=5; i< partes.length; i++)
                {
                    String car = partes[i];
                    platesCarsInventory.add(car);
                }

                 HashMap<String, ArrayList<String>> inventory = new HashMap<String, ArrayList<String>>();
                 for (String carPlate: platesCarsInventory){
                    
                    Car car= cars.get(carPlate);

                    if (car!= null)
                    {
                        String category = car.getCategory();

                        if (inventory.containsKey(category))
                        {
                            inventory.get(category).add(carPlate);
                        }
                        else
                        {
                            ArrayList<String> categoryCars = new ArrayList<String>();
                            categoryCars.add(carPlate);
                            inventory.put(category, categoryCars);
                    }
                 }
                 
                    
                 




                Store newStore = new Store(name, location, openingTime, closingTime, openingDays, inventory);
                stores.put(name, newStore);


                linea = br.readLine();


                }
                }
            }
        
        }


        return stores;

    }



    public static HashMap<String, Insurance> loadInsurances() {

        HashMap<String, Insurance> insurances = new HashMap<String, Insurance>();
                BufferedReader br = new BufferedReader(new FileReader(ruta));
		String linea = br.readLine();
        linea = br.readLine();
		while (linea != null)
        {
            String[] partes = linea.split(",");
            String name = partes[0];
            int cost = Integer.parseInt(partes[1]);
            String specification = partes[2];
            boolean active = Boolean.parseBoolean(partes[3]);

            Insurance newInsurance= new Insurance(name, cost, specification, active);
            insurances.put(name, newInsurance);

            linea = br.readLine();

        }

        return insurances;

    }


    public static HashMap<Car, ArrayList<Rental>> loadRentals(HashMap<String, Car> cars) {

        HashMap<Car, ArrayList<Rental>> rentals = new HashMap<Car, ArrayList<Rental>>();

        // TODO: Get all past rentals for all cars

        return rentals;

    }

}
