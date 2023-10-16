package Processing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Client;
import Model.Insurance;
import Model.Licence;
import Model.Payment;
import Model.Rental;

public class RentalWriter {


    public static void changeTariffs(HashMap<String, Integer> tariffs) {
        // TODO: Finish implementing the method
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
                Path target2 = Paths.get(folderPath + "/licence.jpg");

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
    public void newRental(Rental rental){
        String plate = rental.getCar().getPlate();
        String folderPath = "data/rentals/" + plate;

        File folder = new File(folderPath);

        if (!folder.exists()) {
            boolean result = folder.mkdirs();
            
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
}

