package Processing;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
import Model.Licence;
import Model.Payment;
import Processing.CarRental;

public class RentalLoader {



    public static HashMap<String, User> usersInformation() {

        HashMap<String, User> users = new HashMap<String, User>();
        BufferedReader br = new BufferedReader(new FileReader("./data/users.txt"));
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
        


        br.close();
        return users;

    }

    public static HashMap<String, Integer> loadCategories() throws IOException {

        HashMap<String, Integer> categories = new HashMap<String, Integer>();
        BufferedReader br = new BufferedReader(new FileReader("./data/categories.txt"));
		String linea = br.readLine();
        linea = br.readLine();
        {
             String[] partes = linea.split(",");
             String category = partes[0];
             int price = Integer.parseInt(partes[1]);

             categories.put(category, price);

             linea = br.readLine();

        }
        br.close();
        return categories;

    }

    public static HashMap<String, Client> loadClients() {

        HashMap<String, Client> clients = new HashMap<String, Client>();

        
        String folderPath = ".data/clients";
        File mainFolder = new File(folderPath);
        File[] clientFolders = mainFolder.listFiles();
    
            for (File clientFolder: clientFolders)
            {
                if (clientFolder.isFile())
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

                br.close();

                //license
                long number =0;
                String country = null;
                Calendar expirationLicence = null;
                String photoPath = null;


                BufferedReader br2 = new BufferedReader(new FileReader(licenceFile));
		        String linea2 = br2.readLine();
                linea2 = br2.readLine();
		        while (linea2 != null)
                {
                    String[] partes = linea2.split(",");
                    number = Long.parseLong(partes[0]);
                    country = partes[1];
                    String strExpirationLicence = partes[2];
                    photoPath= "./data/clients/"+logIn+"/licence.JPG";

                    //cambiar fecha string a Calendar
                    DateFormat formatter = new SimpleDateFormat("yy-MM");
                    expirationLicence = Calendar.getInstance();

                    Date expirationLicenceDate = (Date)formatter.parse(strExpirationLicence);
                    expirationLicence.setTime(expirationLicenceDate);

                    linea = br.readLine();
                }
                br2.close();

                //client
                BufferedReader br1 = new BufferedReader(new FileReader(clientInfoFile));
		        String linea1 = br1.readLine();
                linea1 = br1.readLine();
		        while (linea1 != null)
                {
                    String[] partes = linea1.split(",");
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
                    clients.put(login, newClient);

                    linea = br.readLine();

                }

                br1.close();


                }
               


            }
        

        
        return clients;

    }

    public static HashMap<String, Car> loadCars() throws IOException, ParseException {

        HashMap<String, Car> cars = new HashMap<String, Car>();

        // TODO: Get all cars' information
        BufferedReader br = new BufferedReader(new FileReader("./data/cars"));
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
        String folderPath = "./data/stores";
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
                br.close();
            }
        
        }

        
        return stores;

    }



    public static HashMap<String, Insurance> loadInsurances() throws IOException {

        HashMap<String, Insurance> insurances = new HashMap<String, Insurance>();
        BufferedReader br = new BufferedReader(new FileReader(".data/insurances.txt"));
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
        br.close();
        return insurances;

    }

    public static HashMap<Long, Licence> loadSecondaryLicence() throws IOException
    {
        HashMap<Long, Licence> secondaryLicence = new HashMap<Long, Licence>();
        BufferedReader br = new BufferedReader(new FileReader(".data/secondaryLicence.txt"));
		String linea = br.readLine();
        linea = br.readLine();
		while (linea != null)
        {
            String[] partes = linea.split(",");
            long number = Long.parseLong(partes[0]);
            String country = partes[1];
            String strExpirationLicence = partes[2];
           // String photoPath= "./data/clients/"+logIn+"/licence.JPG";

            //cambiar fecha string a Calendar
            DateFormat formatter = new SimpleDateFormat("yy-MM");
            Calendar expirationLicence = Calendar.getInstance();

            Date expirationLicenceDate = (Date)formatter.parse(strExpirationLicence);
            expirationLicence.setTime(expirationLicenceDate);

            Licence newLicence = new Licence(number, country, expirationLicence, photoPath);
            secondaryLicence.put(number, newLicence);

        }
        


        return secondaryLicence;
    }

    public static HashMap<Car, ArrayList<Rental>> loadRentals() throws IOException, ParseException {

        //HashMaps información
        HashMap<String, Client> clients = loadClients();
        HashMap<Long, Licence> secondaryLicence = loadSecondaryLicence();
        HashMap<String, Car> cars = loadCars();
        HashMap<String, Insurance> insurances = loadInsurances();
         HashMap<String, Store> stores = loadStores();

        //carga de datos
        HashMap<Car, ArrayList<Rental>> rentals = new HashMap<Car, ArrayList<Rental>>();
        String folderPath = "./data/rentals";
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
                    String login = partes[0];
                    long secondaryLicenceNum = Long.parseLong(partes[1]);
                    String plate= partes[2];
                    int baseCharge = Integer.parseInt(partes[3]);
                    String insuranceStr = partes[4];
                    String rentedFromStr = partes[5];
                    String returnToStr = partes[6];
                    String strPickUpDateTime = partes[7];
                    String strReturnDateTime = partes[8];

                    //Manejo de extra
                    //String extraType = (partes.length>9)? partes[9]: null;
                    //int cost = (partes.length>10)? Integer.parseInt(partes[10]):null;
                    //String specification = (partes.length>11)? partes[11]: null;

                    //cambiar String a Calendar
                    DateFormat formatter= new SimpleDateFormat("yy-MM-dd:HH-mm");
                    Calendar pickUpDateTime = Calendar.getInstance();
                    Calendar returnDateTime = Calendar.getInstance();

                    Date pickUpDateTimeDate = (Date)formatter.parse(strPickUpDateTime);
                    Date returnDateTimeDate = (Date)formatter.parse(strReturnDateTime);

                    pickUpDateTime.setTime(pickUpDateTimeDate);
                    returnDateTime.setTime(returnDateTimeDate);

                    //encuentra la información y la convierte en objeto
                    Client renter = clients.get(login);
                    Licence secondaryDriver= secondaryLicence.get(secondaryLicenceNum);
                    Car car = cars.get(plate);
                    Insurance insurance = insurances.get(insuranceStr);
                    Store rentedFrom= stores.get(rentedFromStr);
                    Store returnTo = stores.get(returnToStr);

                    Retal newRental = new Rental(renter, secondaryDriver, car, baseCharge, insurance,rentedFrom, returnTo, pickUpDateTime, returnDateTime, extras);
                    rentals.put(car, newRental);

                    



                    


                    linea = br.readLine();  
                }
                
            br.close();   
            }
            
        }

        return rentals;

    }

}
