package restaurant.strategy;

/** STRATEGY - Concrete payment strategy: bank card. Author: Dias (Student 2) */
public class CardPayment implements PaymentStrategy {
    private final String cardNumber;

    public CardPayment(String cardNumber) {
        this.cardNumber = cardNumber.replaceAll("\\s", "");
    }

    @Override
    public boolean pay(double amount) {
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            System.out.println("  [Payment] Invalid card number");
            return false;
        }
        System.out.printf("  [Payment] %.0f KZT charged to card **** %s%n", amount, cardNumber.substring(12));
        return true;
    }

    @Override
    public String getName() {
        return "Card";
    }
}
