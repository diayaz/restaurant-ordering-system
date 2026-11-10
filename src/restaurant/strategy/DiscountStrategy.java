package restaurant.strategy;

import restaurant.builder.Order;

/**
 * STRATEGY pattern - strategy interface for discount algorithms.
 * The order total is calculated the same way; only the discount rule changes.
 *
 * Author: Dias (Student 2)
 */
public interface DiscountStrategy {
    /** @return discount amount in KZT (not the final price) */
    double calculateDiscount(Order order);

    String getName();
}
