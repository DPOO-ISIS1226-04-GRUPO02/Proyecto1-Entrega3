package Model;

import java.util.Calendar;

public class Store {

    private String name;
    private String location;
    private Calendar openingTime;
    private Calendar closingTime;
    private byte openingDays;

    public Store(String name, String location, Calendar opening, Calendar closing, byte openingDays) {

        this.name = name;
        this.location = location;
        this.openingTime = opening;
        this.closingTime = closing;
        this.openingDays = openingDays;

    }

    public String getName() {

        return name;

    }

    public String getLocation() {

        return location;

    }

    public boolean checkOpeningHours(Calendar current) {

        boolean truthValue = false;
        
        // TODO: Finish method

        return truthValue;

    }

}
