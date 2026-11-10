package restaurant.strategy;

/** STRATEGY - Concrete payment strategy: cash with change. Author: Dias (Student 2) */
public class CashPayment implements PaymentStrategy {
    private final double cashGiven;

    public CashPayment(double cashGiven) {
        this.cashGiven = cashGiven;
    }

    @Override
    public boolean pay(double amount) {
        if (cashGiven < amount) {
            System.out.printf("  [Payment] Not enough cash: given %.0f, need %.0f KZT%n", cashGiven, amount);
            return false;
        }
        System.out.printf("  [Payment] Cash %.0f KZT received, change %.0f KZT%n", cashGiven, cashGiven - amount);
        return true;
    }

    @Override
    public String getName() {
        return "Cash";
    }
}
