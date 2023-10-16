package Model;

import java.util.Calendar;

public class Licence {

    private long number;
    private String country;
    private Calendar expiration;
    private String photoPath;

    public Licence(long number, String country, Calendar expiration, String photoPath) {

        this.number = number;
        this.country = country;
        this.expiration = expiration;
        this.photoPath = photoPath;

    }

    public long getNumber() {

        return number;

    }

    public String getCountry() {

        return country;

    }

    public Calendar getExpiration() {

        return expiration;

    }

    public String getPhotoPath() {

        return photoPath;
        
    }

}
