package Model;

import java.util.Calendar;

public class Payment {

    private long number;
    private Calendar expiration;
    private short code;
    private String owner;
    private String address;

    public Payment(long number, Calendar expiration, short code, String owner, String address) {

        this.number = number;
        this.expiration = expiration;
        this.code = code;
        this.owner = owner;
        this.address = address;

    }

    public long getNumber() {

        return number;

    }

    public Calendar getExpiration() {

        return expiration;

    }

    public short getCode() {

        return code;
        
    }

    public String getOwner() {

        return owner;

    }   
    
    public String getAddress() {

        return address;

    }

}
