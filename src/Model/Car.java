package Model;

import java.util.Calendar;

public class Car {

	private byte status;
    private String brand;
    private String plate;
    private String model;
    private String color;
    private boolean isAutomatic;
    private String category;
    private int availableIn;


    public Car(String brand, String plate, String model, String color, boolean isAutomatic, String category, 
        int availableIn, byte status) {

        this.brand = brand;
        this.plate = plate;
        this.model = model;
        this.color = color;
        this.isAutomatic = isAutomatic;
        this.category = category;
        this.availableIn = availableIn;
        this.status = status;

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

    public String getModel() {

        return model;

    }

    public String getColor() {

        return color;

    }

    public boolean isAutomatic() {

        return isAutomatic;

    }

    public String getCategory() {

        return category;

    }

    public void setAvailableTime(int days) {

        this.availableIn = days;

    }

    public Calendar getAvailableDate() {

        Calendar current = Calendar.getInstance();
        current.add(Calendar.DAY_OF_MONTH, availableIn);
        return current;

    }
	
}
