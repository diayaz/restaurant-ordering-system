package restaurant.factory;

/**
 * FACTORY METHOD pattern - Creator.
 *
 * createMeal() is the factory method: each subclass decides which concrete
 * Meal to instantiate. prepareMeal() is the common workflow that every
 * kitchen station follows and that relies on the factory method.
 *
 * Author: Yeskendyr (Student 1)
 */
public abstract class MealFactory {

    /** The factory method - implemented by concrete creators. */
    protected abstract Meal createMeal();

    /** Common business logic that uses the product created by the subclass. */
    public Meal prepareMeal() {
        Meal meal = createMeal();
        System.out.println("  [Factory] " + getClass().getSimpleName()
                + " prepared: " + meal.getDescription());
        return meal;
    }

    /** Returns the right concrete creator for the requested category. */
    public static MealFactory forCategory(MealCategory category) {
        switch (category) {
            case BURGER: return new BurgerFactory();
            case PIZZA:  return new PizzaFactory();
            case SALAD:  return new SaladFactory();
            case DRINK:  return new DrinkFactory();
            default: throw new IllegalArgumentException("Unknown meal: " + category);
        }
    }
}
