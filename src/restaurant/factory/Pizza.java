package restaurant.factory;

/** FACTORY METHOD - Concrete product. Author: Yeskendyr (Student 1) */
class Pizza extends BaseMeal {
    Pizza() {
        super("Pizza", "Margherita Pizza", 3800, MealCategory.PIZZA);
    }
}
