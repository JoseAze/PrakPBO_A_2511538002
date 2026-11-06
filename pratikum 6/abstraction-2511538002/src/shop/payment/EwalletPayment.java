package shop.payment;

public class EwalletPayment implements RefundablePayment {
    private final String provider;
    private long balance; // Tambahan field untuk saldo

    // Konstruktor dengan 2 parameter (provider dan saldo awal)
    public EwalletPayment(String provider, long initialBalance) {
        if (provider == null || provider.isBlank()) {
            throw new IllegalArgumentException("Provider must not be blank");
        }
        this.provider = provider;
        this.balance = initialBalance;
    }

    @Override
    public String getName() {
        return "E-Wallet " + provider;
    }

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || balance < amount) {
            return false;
        }
        balance -= amount; // Kurangi saldo saat membayar
        return true;
    }

    @Override
    public boolean refund(long amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount; // Tambah saldo saat refund
        return true;
    }

    // Method untuk mengecek sisa saldo
    public long getBalance() {
        return balance;
    }
}