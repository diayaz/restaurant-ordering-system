package restaurant.strategy;

import restaurant.builder.Order;

/** STRATEGY - Concrete strategy: 10% off the whole order. Author: Dias (Student 2) */
public class StudentDiscount implements DiscountStrategy {
    private static final double RATE = 0.10;

    @Override
    public double calculateDiscount(Order order) {
        return order.getSubtotal() * RATE;
    }

    @Override
    public String getName() {
        return "Student -10%";
    }
}
