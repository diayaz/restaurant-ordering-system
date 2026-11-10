package restaurant.strategy;

/**
 * STRATEGY pattern - strategy interface for payment methods.
 * Author: Dias (Student 2)
 */
public interface PaymentStrategy {
    /** @return true if the payment succeeded */
    boolean pay(double amount);

    String getName();
}
