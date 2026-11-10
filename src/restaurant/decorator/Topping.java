package restaurant.decorator;

import restaurant.factory.Meal;

/**
 * Helper enum used by the menu/facade to apply a decorator by name.
 * Author: Yeskendyr (Student 1)
 */
public enum Topping {
    EXTRA_CHEESE("Extra Cheese", 400),
    BACON("Bacon", 600),
    SPICY_SAUCE("Spicy Sauce", 250),
    EXTRA_ICE("Extra Ice", 100);

    private final String label;
    private final double price;

    Topping(String label, double price) {
        this.label = label;
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public double getPrice() {
        return price;
    }

    /** Wraps the given meal with the matching concrete decorator. */
    public Meal applyTo(Meal meal) {
        switch (this) {
            case EXTRA_CHEESE: return new ExtraCheese(meal);
            case BACON:        return new Bacon(meal);
            case SPICY_SAUCE:  return new SpicySauce(meal);
            case EXTRA_ICE:    return new ExtraIce(meal);
            default: throw new IllegalStateException();
        }
    }
}
