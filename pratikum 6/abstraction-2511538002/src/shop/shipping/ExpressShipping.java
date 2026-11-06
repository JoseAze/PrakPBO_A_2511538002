package shop.shipping;

public class ExpressShipping extends ShippingService {

    public ExpressShipping() {
        
    }

    @Override
    public int ratePerKg() {
        return 12_000; 
        }
}