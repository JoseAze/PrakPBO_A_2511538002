package shop.shipping;

public abstract class ShippingService {
    public abstract int ratePerKg(); // Pastikan bertipe int

    public final long calculateCost(int weightGrams) {
        long kg = (weightGrams + 999) / 1000;
        return kg * ratePerKg();
    }
}