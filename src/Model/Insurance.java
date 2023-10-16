package Model;

public class Insurance {

    private String name;
    private int cost;
    private String specification;
    private boolean active;

    public Insurance(String name, int cost, String specification, boolean active) {

        this.name = name;
        this.cost = cost;
        this.specification = specification;
        this.active = active;

    }

    public String getName() {

        return name;

    }

    public int getCost() {

        return cost;

    }

    public String getSpecification() {

        return specification;

    }

    public void setActive(boolean active) {

        this.active = active;

    }

    public boolean isActive() {

        return active;
        
    }

}
