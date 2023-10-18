package Processing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import Model.Car;
import Model.Client;
import Model.Extra;
import Model.Insurance;
import Model.Licence;
import Model.Payment;
import Model.Rental;
import Model.Store;
import Model.User;

public class RentalWriter {

    private static String separator = File.separator;

    /**
     * @param category
     * @param value
     */
    public static void changeTariffs(String category, int value) {
        String filePath = "data" + separator + "categories.txt";
        boolean categoryFound = false;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;

            StringBuilder stringBuilder = new StringBuilder();
            while ((linea = bufferedReader.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts[0].equals(category)) {
                    stringBuilder.append(parts[0]).append(",").append(value).append(System.lineSeparator());
                    categoryFound = true;
                } else {
                    stringBuilder.append(linea).append(System.lineSeparator());
                }
            }

            bufferedReader.close();

            // If category is not found, add it to the end of the file
            if (!categoryFound) {
                stringBuilder.append(category).append(",").append(value).append(System.lineSeparator());
            }

            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    public static void changeCarInformation(Car car){
        String filePath = "data" + separator + "cars.txt";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;

            StringBuilder stringBuilder = new StringBuilder();
            while ((linea = bufferedReader.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts[1].equals(car.getPlate())) {
                    // Se encontró la línea correspondiente a la placa, se modifica la información
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(car.getAvailableDate().getTime());
                    byte aut = 0;
                    if (car.isAutomatic()){
                        aut = (byte) 1;
                    }
                    stringBuilder.append(car.getBrand()).append(",").append(car.getPlate()).append(",").append(car.getModel()).append(",")
                            .append(car.getColor()).append(",").append(formattedDate).append(",").append(String.valueOf(aut)).append(",")
                            .append(car.getCategory()).append(",").append(String.valueOf(car.getStatus())).append(System.lineSeparator());
                } else {
                    // La línea no corresponde a la placa, se agrega sin cambios
                    stringBuilder.append(linea).append(System.lineSeparator());
                }
            }

            bufferedReader.close();

            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

            System.out.println("Información del carro modificada con éxito.");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public static void addCar(Car car) {
        String filePath = "data" + separator + "cars.txt";
        try {
            FileWriter fileWriter = new FileWriter(filePath, true); // Modo adjunto al final del archivo
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM_dd");
            String formattedDate = dateFormat.format(car.getAvailableDate().getTime());
            byte aut = 0;
            if (car.isAutomatic()){
                aut = (byte) 1;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(car.getBrand()).append(",").append(car.getPlate()).append(",").append(car.getModel()).append(",").append(car.getColor()).append(",").append(formattedDate).append(",").append(String.valueOf(aut)).append(",")
                            .append(car.getCategory()).append(",").append(String.valueOf(car.getStatus())).append(System.lineSeparator());

            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void changeStoreInformation(Store store) {
        String filePath = "data" + separator + "stores" + separator + store.getName() + ".txt";
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.write("");
            fw.close();
            FileWriter fileWriter = new FileWriter(filePath, true);

            StringBuilder stringBuilder = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String OpformattedDate = dateFormat.format(store.getOpHour().getTime());
            String ClformattedDate = dateFormat.format(store.getCloseHour().getTime());
            stringBuilder.append(store.getName()).append(",").append(store.getLocation()).append(",").append(OpformattedDate).append(",").append(ClformattedDate).append(",")
                    .append(String.valueOf(store.opDays()));

            ArrayList<String> plates = new ArrayList<>();
            for (ArrayList<String> valueList : store.getInventory().values()) {
                plates.addAll(valueList);
            }
            for (String plate: plates){
                stringBuilder.append(",").append(plate);
            }

            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

            System.out.println("Información de la tienda modificada con éxito.");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void addStore(Store store) {
        // Especifica la ruta de la carpeta stores dentro de la carpeta data
        String folderPath = "data" + separator + "stores" + separator;

        // Crea un directorio si no existe
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Crea un archivo con el nombre de la tienda en la carpeta stores
        String filePath = folderPath + store.getName() + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            StringBuilder stringBuilder = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String OpformattedDate = dateFormat.format(store.getOpHour().getTime());
            String ClformattedDate = dateFormat.format(store.getCloseHour().getTime());
            stringBuilder.append(store.getName()).append(",").append(store.getLocation()).append(",").append(OpformattedDate).append(",").append(ClformattedDate).append(",")
                    .append(String.valueOf(store.opDays()));

            ArrayList<String> plates = new ArrayList<>();
            for (ArrayList<String> valueList : store.getInventory().values()) {
                plates.addAll(valueList);
            }
            for (String plate: plates){
                stringBuilder.append(",").append(plate);
            }

            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

            System.out.println("Información de la tienda modificada con éxito.");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void addClient(Client client) {
        String username = client.getLogin();
        String folderPath = "data" + separator + "clients" + separator + username + separator;

        File folder = new File(folderPath);

        if (!folder.exists()) {
            boolean result = folder.mkdirs();
            if (result) {
                String filePath = folderPath + "clientInfo.txt";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(client.getDateBirth().getTime());
                String content = client.getName() + ',' + client.getPhone() + ',' + client.getEmail() + ',' + dateString + ',' + client.getNationality() + ',' + client.getLogin();
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
                    bufferedWriter.append(content);
                    bufferedWriter.newLine();
                    System.out.println("\nInformación del usuario guardada con éxito.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String sourceImagePath = client.getIdPhotoPath();
                Path source = Paths.get(sourceImagePath);
                Path target = Paths.get(folderPath + "identification.jpg");

                try {
                    Files.copy(source, target);
                    System.out.println("\nIdentificación cargada con éxito.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String filePath2 = folderPath + "licenceInfo.txt";
                Licence licence = client.getLicence();
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
                String dateString2 = dateFormat2.format(licence.getExpiration().getTime());
                String content2 = licence.getNumber() + ',' + licence.getCountry() + ',' + dateString2;
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath2, true))) {
                    bufferedWriter.append(content2);
                    bufferedWriter.newLine();
                    System.out.println("\nInformación de la licencia guardada con éxito.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String sourceImagePath2 = licence.getPhotoPath();
                Path source2 = Paths.get(sourceImagePath2);
                Path target2 = Paths.get(folderPath + "license.jpg");

                try {
                    Files.copy(source2, target2);
                    System.out.println("\nLicencia cargada con éxito.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String filePath3 = folderPath + "paymentInfo.txt";
                Payment payment = client.getPayment();
                SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM");
                String dateString3 = dateFormat3.format(payment.getExpiration().getTime());
                String content3 = payment.getNumber() + ',' + dateString3 + ',' + String.valueOf(payment.getCode()) + ',' + payment.getOwner() + ',' + payment.getAddress();
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath3, true))) {
                    bufferedWriter.append(content3);
                    bufferedWriter.newLine();
                    System.out.println("\nInformación del pago guardada con éxito.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public static void newRental(Rental rental){
        String plate = rental.getCar().getPlate();
        String folderPath = "data" + separator + "rentals" + separator + plate;

        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
            
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(rental.getPickUp().getTime());
        folderPath += separator + date;
        File folder2 = new File(folderPath);
        folder2.mkdirs();
        File folder3 = new File(folderPath + separator + "extra");
        folder3.mkdirs();
        String filePath = folderPath + separator + "info.txt";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd:HH-mm");
        String datePickString = dateFormat.format(rental.getPickUp().getTime());
        String dateReturnString = dateFormat.format(rental.getReturn().getTime());
        String content = rental.getClient().getLogin() + ',' + rental.getCar().getPlate() + ',' + String.valueOf(rental.getFinalCharge()) + ',' + rental.getOrigin().getName() + ',' + rental.getDestination().getName() + ',' + datePickString + ',' + dateReturnString + ',' + rental.getActive();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.append(content);
            bufferedWriter.newLine();
            System.out.println("\nInformación del usuario guardada con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath2 = folderPath + separator + "insurance.txt";
        ArrayList<Insurance> insurances = rental.getInsurances();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath2, true))) {
            for (Insurance insurance : insurances) {
            bufferedWriter.append(insurance.getName());
            bufferedWriter.newLine();
            }
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }
        String filePath3 = folderPath + separator + "secondaryDriver.txt";
        ArrayList<Licence> secondaryLicences = rental.getSecondaryDriver();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath3, true))) {
            for (Licence lic : secondaryLicences) {
            bufferedWriter.append(String.valueOf(lic.getNumber()));
            bufferedWriter.newLine();
            }
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }    
            
        
        

                

    }
    public static void changeClientInformation (Client person){
        String username = person.getLogin();
        String filePath = "data" + separator + "clients" + separator + username + separator + "clientInfo.txt";
        try {
        File file = new File(filePath);
        FileWriter fr = new FileWriter(file, false);
        fr.write("");
        fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(person.getDateBirth().getTime());
            String content = person.getName() + ',' + person.getPhone() + ',' + person.getEmail() + ',' + dateString + ',' + person.getNationality() + ',' + person.getLogin();
            bufferedWriter.write(content);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String id = "data" + separator + "clients" + separator + username + separator + "identification.jpg";
        String identification = person.getIdPhotoPath();
        try {
            File fotoId = new File(id);
            fotoId.delete();
        } catch (Exception e){
            e.printStackTrace();
        }
        Path source = Paths.get(identification);
        Path target = Paths.get("data" + separator + "clients" + separator + username + separator + "identification.jpg");

        try {
            Files.copy(source, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath2 = "data" + separator + "clients" + separator + username + separator + "licenceInfo.txt";
        try {
        File file = new File(filePath);
        FileWriter fr = new FileWriter(file, false);
        fr.write("");
        fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(filePath2, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Licence licence = person.getLicence();
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
            String dateString2 = dateFormat2.format(licence.getExpiration().getTime());
            String content2 = licence.getNumber() + ',' + licence.getCountry() + ',' + dateString2;
            bufferedWriter.write(content2);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String li = "data" + separator + "clients" + separator + username + separator + "license.jpg";
        String licence = person.getLicence().getPhotoPath();
        try {
            File fotoId = new File(li);
            fotoId.delete();
        } catch (Exception e){
            e.printStackTrace();
        }
        Path source2 = Paths.get(licence);
        Path target2 = Paths.get("data" + separator + "clients" + separator + username + separator + "license.jpg");

        try {
            Files.copy(source2, target2);
        } catch (IOException e) {
            e.printStackTrace();
    }

    String filePath3 = "data" + separator + "clients" + separator + username + separator + "paymentInfo.txt";
        try {
        File file = new File(filePath3);
        FileWriter fr = new FileWriter(file, false);
        fr.write("");
        fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Payment payment = person.getPayment();
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM");
            String dateString3 = dateFormat3.format(payment.getExpiration().getTime());
            String content3 = payment.getNumber() + ',' + dateString3 + ',' + String.valueOf(payment.getCode()) + ',' + payment.getOwner() + ',' + payment.getAddress();
            bufferedWriter.write(content3);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public static void changeRentalInformation(Rental rental){
        String plate = rental.getCar().getPlate();
        String folderPath = "data" + separator + "rentals" + separator + plate;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(rental.getPickUp().getTime());

        folderPath = folderPath + separator + date;
        String filePath = folderPath + separator + "info.txt";
        try{
        FileWriter fw = new FileWriter(filePath);
        fw.write("");
        fw.close();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd:HH-mm");
        String datePickString = dateFormat.format(rental.getPickUp().getTime());
        String dateReturnString = dateFormat.format(rental.getReturn().getTime());
        String content = rental.getClient().getLogin() + ',' + rental.getCar().getPlate() + ',' + String.valueOf(rental.getFinalCharge()) + ',' + rental.getOrigin().getName() + ',' + rental.getDestination().getName() + ',' + datePickString + ',' + dateReturnString + ',' + rental.getActive();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.append(content);
            bufferedWriter.newLine();
            System.out.println("\nInformación del usuario actualizada con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        filePath = folderPath + separator + "insurance.txt";
        try{
        FileWriter fw = new FileWriter(filePath);
        fw.write("");
        fw.close();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            ArrayList<Insurance> insurances = rental.getInsurances();
            for (Insurance insurance : insurances) {
            bufferedWriter.append(insurance.getName());
            bufferedWriter.newLine();
            }
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        filePath = folderPath + separator + "secondaryDriver.txt";
        try{
        FileWriter fw = new FileWriter(filePath);
        fw.write("");
        fw.close();
        ArrayList<Licence> secondaryLicences = rental.getSecondaryDriver();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            for (Licence lic : secondaryLicences) {
            bufferedWriter.append(String.valueOf(lic.getNumber()));
            bufferedWriter.newLine();
            }
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        filePath = folderPath + separator + "extra";
        File directory = new File(filePath);
        File[] files = directory.listFiles();
        if (files != null && files.length>0){
            for (File file: files){
                file.delete();
            }
        }
        ArrayList<Extra> extras = rental.getExtras();
        if (extras != null && extras.size() > 0){
        for (int i = 0; i < extras.size() ; i++){
            Extra extra = extras.get(i);
            String txtfilePath = filePath + separator + "extra" + i + ".txt";
            String content = extra.getType() + "," + String.valueOf(extra.getCost()) + "," + extra.getSpecification(); 
            try {
                FileWriter fwtxt = new FileWriter (txtfilePath);
                fwtxt.write(content);
                fwtxt.close();

            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
    }

    public static void newUser(User user) {
        String filePath = "data" + separator + "users.txt";
        try {
            FileWriter fileWriter = new FileWriter(filePath, true); // Modo adjunto al final del archivo
            String userEntry = user.getUsername() + "," + user.getPassword() + "," + String.valueOf(user.getAccess()) + "," + user.getWorkplace() + System.lineSeparator();
            fileWriter.write(userEntry);
            fileWriter.close();
            System.out.println("Nuevo usuario agregado con éxito.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void newInsurance(Insurance insurance) {
        String filePath = "data" +separator + "insurances.txt";
        try {
            FileWriter fileWriter = new FileWriter(filePath, true); // Modo adjunto al final del archivo
            String insuranceEntry = insurance.getName() + "," + String.valueOf(insurance.getCost()) + "," + insurance.getSpecification() + "," + "0" + System.lineSeparator();
            fileWriter.write(insuranceEntry);
            fileWriter.close();
            System.out.println("Nuevo seguro agregado con éxito.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}



