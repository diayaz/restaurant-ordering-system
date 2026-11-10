package restaurant.strategy;

/** STRATEGY - Concrete payment strategy: Kaspi QR by phone number. Author: Dias (Student 2) */
public class KaspiQrPayment implements PaymentStrategy {
    private final String phone;

    public KaspiQrPayment(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("  [Payment] Kaspi QR: %.0f KZT confirmed from %s%n", amount, phone);
        return true;
    }

    @Override
    public String getName() {
        return "Kaspi QR";
    }
}
