package restaurant.strategy;

import restaurant.builder.Order;

/** STRATEGY - Concrete strategy: regular price. Author: Dias (Student 2) */
public class NoDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(Order order) {
        return 0;
    }

    @Override
    public String getName() {
        return "No discount";
    }
}
