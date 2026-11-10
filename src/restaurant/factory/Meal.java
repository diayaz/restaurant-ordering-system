package restaurant.factory;

/**
 * Product interface of the Factory Method pattern.
 * It is also the Component interface of the Decorator pattern,
 * so toppings can wrap any meal created by a factory.
 *
 * Author: Yeskendyr (Student 1)
 */
public interface Meal {
    String getName();

    String getDescription();

    double getPrice();

    MealCategory getCategory();
}
