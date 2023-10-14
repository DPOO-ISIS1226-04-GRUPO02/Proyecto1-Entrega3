package Model;

public class Extra {

    private String type;
    private int cost;
    private String specification;

    public Extra(String type, int cost, String specification) {

        this.type = type;
        this.cost = cost;
        this.specification = specification;

    }

    public String getType() {

        return type;

    }

    public int getCost() {

        return cost;

    }

    public String getSpecification() {

        return specification;

    }

}
