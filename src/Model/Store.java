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

}
