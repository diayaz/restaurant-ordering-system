package restaurant.factory;

/**
 * Common state for every concrete meal (concrete products).
 *
 * Author: Yeskendyr (Student 1)
 */
public abstract class BaseMeal implements Meal {
    private final String name;
    private final String description;
    private final double price;
    private final MealCategory category;

    protected BaseMeal(String name, String description, double price, MealCategory category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public MealCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return getDescription() + String.format(" - %.0f KZT", getPrice());
    }
}
