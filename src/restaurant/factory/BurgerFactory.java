package restaurant.factory;

/** FACTORY METHOD - Concrete creator. Author: Yeskendyr (Student 1) */
public class BurgerFactory extends MealFactory {
    @Override
    protected Meal createMeal() {
        return new Burger();
    }
}
