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
import java.util.Calendar;
import java.util.HashMap;

import Model.Car;
import Model.Client;
import Model.Insurance;
import Model.Licence;
import Model.Payment;
import Model.Rental;

public class RentalWriter {


    /**
     * @param category
     * @param value
     */
    public static void changeTariffs(String category, int value) {
        String filePath = "data/categories.txt";
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
        String filePath = "data/cars.txt";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;

            StringBuilder stringBuilder = new StringBuilder();
            while ((linea = bufferedReader.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts[1].equals(car.getPlate())) {
                    // Se encontró la línea correspondiente a la placa, se modifica la información
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM_dd");
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
        String filePath = "data/cars.txt";
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

    public static void addClient(Client client) {
        String username = client.getLogin();
        String folderPath = "data/clients/" + username;

        File folder = new File(folderPath);

        if (!folder.exists()) {
            boolean result = folder.mkdirs();
            if (result) {
                String filePath = folderPath + "/clientInfo.txt";
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
                Path target = Paths.get(folderPath + "/identification.jpg");

                try {
                    Files.copy(source, target);
                    System.out.println("\nIdentificación cargada con éxito.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String filePath2 = folderPath + "/licenceInfo.txt";
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
                Path target2 = Paths.get(folderPath + "/license.jpg");

                try {
                    Files.copy(source2, target2);
                    System.out.println("\nLicencia cargada con éxito.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String filePath3 = folderPath + "/paymentInfo.txt";
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
        String folderPath = "data/rentals/" + plate;

        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
            
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(rental.getPickUp().getTime());
        folderPath = folderPath + "/" + date;
        File folder2 = new File(folderPath);
        folder2.mkdirs();
        String filePath = folderPath + "/info.txt";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd:HH-mm");
        String datePickString = dateFormat.format(rental.getPickUp().getTime());
        String dateReturnString = dateFormat.format(rental.getReturn().getTime());
        String content = rental.getClient().getLogin() + ',' + rental.getCar().getPlate() + ',' + String.valueOf(rental.getFinalCharge()) + ',' + rental.getOrigin() + ',' + rental.getDestination() + ',' + datePickString + ',' + dateReturnString;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.append(content);
            bufferedWriter.newLine();
            System.out.println("\nInformación del usuario guardada con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath2 = folderPath + "/insurance.txt";
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
        String filePath3 = folderPath + "/insurance.txt";
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
        String filePath = "data/clients/" + username + "/clientInfo.txt";
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
        String id = "data/clients" + username + "/identification.jpg";
        String identification = person.getIdPhotoPath();
        try {
            File fotoId = new File(id);
            fotoId.delete();
        } catch (Exception e){
            e.printStackTrace();
        }
        Path source = Paths.get(identification);
        Path target = Paths.get("data/clients" + username + "/identification.jpg");

        try {
            Files.copy(source, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath2 = "data/clients/" + username + "/licenceInfo.txt";
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

        String li = "data/clients" + username + "/license.jpg";
        String licence = person.getLicence().getPhotoPath();
        try {
            File fotoId = new File(li);
            fotoId.delete();
        } catch (Exception e){
            e.printStackTrace();
        }
        Path source2 = Paths.get(licence);
        Path target2 = Paths.get("data/clients" + username + "/license.jpg");

        try {
            Files.copy(source2, target2);
        } catch (IOException e) {
            e.printStackTrace();
    }

    String filePath3 = "data/clients/" + username + "/paymentInfo.txt";
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
}}



