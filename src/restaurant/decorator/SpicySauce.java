package restaurant.decorator;

import restaurant.factory.Meal;

/** DECORATOR - Concrete decorator (+250 KZT). Author: Yeskendyr (Student 1) */
public class SpicySauce extends ToppingDecorator {
    public SpicySauce(Meal wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Spicy Sauce";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + 250;
    }
}
