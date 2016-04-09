package utah.codecamp;

/**
 * Created by Dalinar on 4/9/2016.
 */
public class CouponOrder extends BasicOrder{
    private String coupon;

    public CouponOrder(String customer, String address, String coupon){
        super(customer, address);
        this.coupon = coupon;
    }
}
