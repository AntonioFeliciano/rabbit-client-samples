package utah.codecamp;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dalinar on 4/9/2016.
 */
public class BasicOrder {
    protected String customer;
    protected String address;
    protected List<Pizza> pizzas;

    public BasicOrder(String customer, String address){
        this.customer = customer;
        this.address = address;
        this.pizzas = new LinkedList<Pizza>();
    }

    public void addPizza(String size, String[] toppings){
        this.pizzas.add(new Pizza(size, toppings));
    }

    public String getCustomer(){
        return customer;
    }

    public String getAddress(){
        return address;
    }

    public List<Pizza> getPizzas(){
        return pizzas;
    }
}
