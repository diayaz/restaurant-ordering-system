package restaurant.decorator;

import restaurant.factory.Meal;

/** DECORATOR - Concrete decorator (+600 KZT). Author: Yeskendyr (Student 1) */
public class Bacon extends ToppingDecorator {
    public Bacon(Meal wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Bacon";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + 600;
    }
}
