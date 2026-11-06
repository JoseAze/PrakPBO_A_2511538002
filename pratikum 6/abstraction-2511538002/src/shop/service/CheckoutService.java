package shop.service;
import java.util.List;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.payment.RefundablePayment;
import shop.shipping.Shippable;
import shop.shipping.ShippingService;
public class CheckoutService {

    public long subtotal(List<Product> items) {
        long total =0;
        for (Product product : items) { 
            total += product.getFinalPrice();

        }
        return total;
    } 

    public int totalWeight(List<Product> items) {
        int grams = 0;
        for (Product product : items) {
            if (product instanceof Shippable) {  //a capability, not a class
               Shippable shippable = (Shippable) product;
               grams += shippable.getWeightGrams();
            }    
        }
        return grams;
    }
    public long total(List<Product> items, ShippingService shipping) {
        long itemSubtotal = subtotal(items);
        if (items.isEmpty()){
            return itemSubtotal;
        }
        int weightGrams  = totalWeight(items);
        long shippingCost = shipping.calculateCost(weightGrams);

        return itemSubtotal + shippingCost;
        
    }

    public boolean checkout(List<Product> items, ShippingService shipping, PaymentMethod payment) {
        if (items == null || items.isEmpty() || payment == null) {
            return false;
        }

    long totalAmount = total (items, shipping);
    return payment.pay(totalAmount);
    
   }
public boolean refund(RefundablePayment payment, long amount) {
        if (payment == null || amount <= 0) {
            return false;
        }
        return payment.refund(amount);
    }
}
