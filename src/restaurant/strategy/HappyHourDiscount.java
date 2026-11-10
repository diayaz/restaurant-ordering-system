package restaurant.strategy;

import restaurant.builder.Order;
import restaurant.factory.Meal;
import restaurant.factory.MealCategory;

/**
 * STRATEGY - Concrete strategy: 50% off all drinks.
 * Shows that a strategy can use details of the order, not just the total.
 *
 * Author: Dias (Student 2)
 */
public class HappyHourDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(Order order) {
        double discount = 0;
        for (Meal meal : order.getItems()) {
            if (meal.getCategory() == MealCategory.DRINK) {
                discount += meal.getPrice() * 0.5;
            }
        }
        return discount;
    }

    @Override
    public String getName() {
        return "Happy Hour (drinks -50%)";
    }
}
