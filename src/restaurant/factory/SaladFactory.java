package restaurant.factory;

/** FACTORY METHOD - Concrete creator. Author: Yeskendyr (Student 1) */
public class SaladFactory extends MealFactory {
    @Override
    protected Meal createMeal() {
        return new Salad();
    }
}
