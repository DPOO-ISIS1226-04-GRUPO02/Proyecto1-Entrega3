package Model;

import java.util.Calendar;

public class Car {

	private byte status;
    private String brand;
    private String plate;
    private short model;
    private String color;
    private boolean isAutomatic;
    private String category;
    private Calendar availableIn;


    public Car(String brand, String plate, short model, String color, boolean isAutomatic, String category, 
        Calendar availableIn) {

        this.brand = brand;
        this.plate = plate;
        this.model = model;
        this.color = color;
        this.isAutomatic = isAutomatic;
        this.category = category;
        this.availableIn = availableIn;

    }

    public byte getStatus() {

        return status;

    }

    public void setStatus(byte status) {

        this.status = status;

    }

    public String getBrand() {

        return brand;

    }

    public String getPlate() {

        return plate;

    }

    public short getModel() {

        return model;

    }

    public String getColor() {

        return color;

    }

    public boolean isAutomatic() {

        return isAutomatic;

    }

    public void setAvailableTime(int days) {

        this.availableIn = days;

    }

    public Calendar getAvailableDate() {

        // TODO: Finish the method

        return Calendar.getInstance();

    }
	
}
