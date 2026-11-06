package shop.app;

import java.util.List;
import shop.model.*;
import shop.payment.*;
import shop.service.*;
import shop.shipping.*;

public class ShopApp {
    public static void main(String[] args) {
        // Katalog Produk (Menggunakan int sesuai constructor asli produkmu)
        Product coffee = new PhysicalProduct("Kopi Arabika Gayo 250g", 85000, 250);
        Product rendang = new PhysicalProduct("Rendang Kemasan 250g", 120000, 450);
        Product ebook = new DigitalProduct("E-book Resep Kue Nusantara", 50000);

        System.out.println("== Katalog ==");
        System.out.println(coffee);
        System.out.println(rendang);
        System.out.println(ebook);

        // Ringkasan Pengiriman & Subtotal
        ShippingService regular = new RegularShipping();
        ShippingService express = new ExpressShipping();
        
        long subtotal = coffee.getFinalPrice() * 2 + rendang.getFinalPrice() + ebook.getFinalPrice();
        int totalWeight = ((PhysicalProduct) coffee).getWeightGrams() + ((PhysicalProduct) rendang).getWeightGrams();
        long regCost = regular.calculateCost(totalWeight);
        long expCost = express.calculateCost(totalWeight);

        System.out.println("== Ringkasan ==");
        System.out.println("Subtotal : Rp" + subtotal);
        System.out.println("Berat kiriman : " + totalWeight + " g");
        System.out.println("Total Reguler : Rp" + (subtotal + regCost));
        System.out.println("Total Ekspres : Rp" + (subtotal + expCost));

        // Simulasi Pembayaran (Gunakan BankTransferPayment atau sesuai nama class transfer di projectmu)
        System.out.println("== Pembayaran ==");
        EwalletPayment ewallet = new EwalletPayment("DompetKu", 90000);
        boolean ewPay = ewallet.pay(50000);
        System.out.println("E-Wallet DompetKu berhasil? " + ewPay);
        System.out.println("Sisa saldo: Rp" + ewallet.getBalance());

        BankTransferPayment transfer = new BankTransferPayment("Bank Nagari");
        boolean trPay = transfer.pay(120000);
        System.out.println("Transfer Bank Nagari berhasil? " + trPay);

        CreditCardPayment cc = new CreditCardPayment("Siti Rahma", "1234-5678-9012-3456", 1000000);
        boolean ccPay = cc.pay(200000);
        System.out.println("Kartu Kredit (Siti Rahma) berhasil? " + ccPay);
        System.out.println("Cicilan 3 bulan: Rp" + cc.monthlyInstallment(268000, 3));

        // Simulasi Refund
        System.out.println("== Refund e-book ==");
        boolean refundOk = ewallet.refund(ebook.getFinalPrice());
        System.out.println("Refund e-wallet berhasil? " + refundOk);
        System.out.println("Saldo setelah refund: Rp" + ewallet.getBalance());

        // Struk Pesanan (Inner Class Order.Receipt)
        Address address = new Address("Jl. Moh Hatta No. 10", "Padang", "25121");
        Order order = new Order("ORD-0001", address);
        order.addLine(coffee, 2);
        order.addLine(ebook, 1);

        Order.Receipt receipt = order.new Receipt();
        System.out.println(receipt.toText());

        // Voucher Promo (Anonymous Class - Tanpa override refund jika method interface PaymentMethod tidak punya refund)
        CheckoutService checkout = new CheckoutService();
        PaymentMethod voucher = new PaymentMethod() {
            private long remaining = 50_000;
            @Override
            public String getName() { return "Voucher Promo Hari Ini"; }
            @Override
            public boolean pay(long amount) {
                if (amount <= 0 || amount > remaining) return false;
                remaining -= amount;
                return true;
            }
        };
        boolean voucherSuccess = checkout.checkout(List.of(ebook), regular, voucher);
        System.out.println(voucher.getName() + " untuk e-book berhasil? " + voucherSuccess);
    }
}