package Model;

import java.util.Calendar;

public class Client {

    private String fullName;
    private long phoneNumber;
    private String email;
    private Calendar dateBirth;
    private String nationality;
    private String idPhotoPath;
    private Licence licence;
    private Payment payment;
    private String login;
    private Rental active = null;

    public Client(String name, long phone, String email, Calendar dateBirth, String nationality, String idPhotoPath, Payment payment,  String login) {
        
        this.fullName = name;
        this.phoneNumber = phone;
        this.email = email;
        this.dateBirth = dateBirth;
        this. nationality = nationality;
        this.idPhotoPath = idPhotoPath;
        this.payment = payment;
        this.login = login;

    }

    public String getName() {

        return fullName;

    }

    public void setName(String name) {

        this.fullName = name;

    }

    public long getPhone() {

        return phoneNumber;

    }

    public void setPhone(long phone) {

        this.phoneNumber = phone;
        
    }

    public String getEmail() {

        return email;

    }

    public void setEmail(String email) {

        this.email = email;
        
    }

    public Calendar getDateBirth() {

        return dateBirth;

    }

    public String getNationality() {

        return nationality;

    }

    public String getIdPhotoPath() {

        return idPhotoPath;

    }

    public void setIdPhotoPath(String path) {

        this.idPhotoPath = path;
        
    }

    public Payment getPayment() {

        return payment;

    }

    public void setPayment(long cardNumber, Calendar expiration, short code, String cardOwner, String cardAddress) {

        this.payment = new Payment(cardNumber, expiration, code, cardOwner, cardAddress);

    }

    public Licence getLicence() {

        return licence;

    }

    public void setLicence(Licence licence) {

        this.licence = licence;

    }

    public String getLogin() {

        return login;

    }

    public Rental getActiveRental() {

        return active;

    }

    public void setActiveRental(Rental active) {

        this.active = active;

    }

}
