package restaurant.factory;

/** FACTORY METHOD - Concrete creator. Author: Yeskendyr (Student 1) */
public class PizzaFactory extends MealFactory {
    @Override
    protected Meal createMeal() {
        return new Pizza();
    }
}
