package restaurant.decorator;

import restaurant.factory.Meal;

/** DECORATOR - Concrete decorator (+400 KZT). Author: Yeskendyr (Student 1) */
public class ExtraCheese extends ToppingDecorator {
    public ExtraCheese(Meal wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Extra Cheese";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + 400;
    }
}
