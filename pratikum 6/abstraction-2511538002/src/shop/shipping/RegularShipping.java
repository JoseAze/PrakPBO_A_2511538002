package shop.shipping;

public class RegularShipping extends ShippingService {

    public RegularShipping() {
        // Cukup kosongkan atau hapus super("Reguler") karena ShippingService tidak butuh parameter
    }

    @Override
    public int ratePerKg() { 
        return 10_000;
    }
}