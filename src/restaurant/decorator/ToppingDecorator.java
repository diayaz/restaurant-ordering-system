package restaurant.decorator;

import restaurant.factory.Meal;
import restaurant.factory.MealCategory;

/**
 * DECORATOR pattern - abstract Decorator.
 *
 * Implements the same Meal interface as the wrapped object and forwards
 * every call to it. Concrete toppings add their own price and description
 * on top. Decorators can be stacked: Bacon(ExtraCheese(Burger)).
 *
 * Author: Yeskendyr (Student 1)
 */
public abstract class ToppingDecorator implements Meal {
    protected final Meal wrapped;

    protected ToppingDecorator(Meal wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getName() {
        return wrapped.getName();
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice();
    }

    @Override
    public MealCategory getCategory() {
        return wrapped.getCategory();
    }

    @Override
    public String toString() {
        return getDescription() + String.format(" - %.0f KZT", getPrice());
    }
}
