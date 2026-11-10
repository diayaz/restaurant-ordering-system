package restaurant.decorator;

import restaurant.factory.Meal;

/** DECORATOR - Concrete decorator (+100 KZT, for drinks). Author: Yeskendyr (Student 1) */
public class ExtraIce extends ToppingDecorator {
    public ExtraIce(Meal wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Extra Ice";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + 100;
    }
}
