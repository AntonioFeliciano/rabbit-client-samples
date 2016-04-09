package utah.codecamp;

import java.util.List;

/**
 * Created by Dalinar on 4/9/2016.
 */
public class Delivery {
    private String customer;
    private String address;
    private String couponCode;
    private List<Pizza> pizzas;

    public String getCustomer(){
        return customer;
    }

    public String getAddress(){
        return address;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }
}
