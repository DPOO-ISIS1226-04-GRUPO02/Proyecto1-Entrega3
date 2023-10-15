package Model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

public class Rental {

    private boolean active;
    private Client renter;
    private ArrayList<Licence> secondaryDriver;
    private Car car;
    private int baseCharge;
    private ArrayList<Insurance> insurances;
    private Store rentedFrom;
    private Store returnTo;
    private Calendar pickUpDateTime;
    private Calendar returnDateTime;
    private ArrayList<Extra> extras;

    public Rental(Client renter, Car car, int base, ArrayList<Insurance> insurances, Store origin, Store destination, Calendar pickUpDateTime,
        Calendar returnDateTime, Licence secondaryDriver, ArrayList<Extra> extras) {

        this.active = false;
        this.renter = renter;
        this.secondaryDriver = new ArrayList<Licence>();
        this.car = car;
        this.baseCharge = base;
        this.insurances = insurances;
        this.rentedFrom = origin;
        this.returnTo = destination;
        this.pickUpDateTime = pickUpDateTime;
        this.returnDateTime = returnDateTime;
        this.extras = extras;

    }

    public void setActive(boolean active) {

        this.active = active;

    }

    public boolean getActive() {

        return active;
        
    }

    public Client getClient() {

        return renter;

    }

    public ArrayList<Licence> getSecondaryDriver() {

        return secondaryDriver;

    }

    public Car getCar() {

        return car;

    }

    public int getFinalCharge() {

        int total = baseCharge;
        for (Extra extra: extras) {
            total += extra.getCost();
        }
        for (Insurance insurance: insurances) {
            total += insurance.getCost();
        }
        int daysBetween = (int) ChronoUnit.DAYS.between(pickUpDateTime.toInstant(), returnDateTime.toInstant());
        return total * daysBetween;

    }

    public Store getOrigin() {

        return rentedFrom;

    }

    public Store getDestination() {

        return returnTo;

    }

    public void setDestination(Store destination) {

        this.returnTo = destination;

    }

    public Calendar getPickUp() {

        return pickUpDateTime;

    }

    public void setPickUp(Calendar pickUp) {

        this.pickUpDateTime = pickUp;

    }

    public Calendar getReturn() {

        return returnDateTime;

    }

    public void setReturn(Calendar returnDateTime) {

        this.returnDateTime = returnDateTime;

    }

    public ArrayList<Insurance> getInsurances() {

        return insurances;

    }

    public void addExtra(String type, int cost, String specs) {

        extras.add(extras.size(), new Extra(type, cost, specs));

    }

    public ArrayList<Extra> getExtras() {

        return extras;

    }

}
