package Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Store {

    private String name;
    private String location;
    private Calendar openingTime;
    private Calendar closingTime;
    private byte openingDays;
    private HashMap<String, ArrayList<String>> inventory;

    public Store(String name, String location, Calendar opening, Calendar closing, byte openingDays, 
        HashMap<String, ArrayList<String>> inventory) {

        this.name = name;
        this.location = location;
        this.openingTime = opening;
        this.closingTime = closing;
        this.openingDays = openingDays;
        this.inventory = inventory;

    }

    public String getName() {

        return name;

    }

    public String getLocation() {

        return location;

    }
    public Calendar getOpHour(){
        return openingTime;
    }
    public Calendar getCloseHour(){
        return closingTime;
    }
    public byte opDays (){
        return openingDays;
    }

    public HashMap<String, ArrayList<String>> getInventory() {

        return inventory;

    }

    public boolean checkOpeningHours() {

        Calendar current = Calendar.getInstance();
        boolean truthValue = true;
        
        int opHour = openingTime.get(Calendar.HOUR);
        int opMin = openingTime.get(Calendar.MINUTE) + opHour * 60;
        int clHour = closingTime.get(Calendar.HOUR);
        int clMin = closingTime.get(Calendar.MINUTE) + clHour * 60;
        int hour = current.get(Calendar.HOUR);
        int min = current.get(Calendar.MINUTE) + hour * 60;
        if (opMin > min || min > clMin) truthValue = false;

        int week = current.get(Calendar.DAY_OF_WEEK);
        switch (week) {

            case Calendar.MONDAY:
                if ((openingDays & 1) != 1) truthValue = false;
                break;
            case Calendar.TUESDAY:
                if ((openingDays & 2) != 2) truthValue = false;
                break;
            case Calendar.WEDNESDAY:
                if ((openingDays & 4) != 4) truthValue = false;
                break;
            case Calendar.THURSDAY:
                if ((openingDays & 8) != 8) truthValue = false;
                break;
            case Calendar.FRIDAY:
                if ((openingDays & 16) != 16) truthValue = false;
                break;
            case Calendar.SATURDAY:
                if ((openingDays & 32) != 32) truthValue = false;
                break;
            case Calendar.SUNDAY:
                if ((openingDays & 64) != 64) truthValue = false;
                break;

        }

        return truthValue;

    }

    public void addCar(Car car) {

        String category = car.getCategory();
        inventory.get(category).add(car.getPlate());

    }

    public void removeCar(Car car) {

        String category = car.getCategory();
        inventory.get(category).remove(car.getPlate());
        
    }

}
