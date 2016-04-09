package utah.codecamp;

/**
 * Created by Dalinar on 4/9/2016.
 */
public class Pizza {
    private String size;
    private String[] toppings;

    public Pizza(String size, String[] toppings) {
        this.size = size;
        this.toppings = toppings;
    }

    public String getSize(){
        return size;
    }

    public String[] getToppings(){
        return toppings;
    }
}
