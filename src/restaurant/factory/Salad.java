package restaurant.factory;

/** FACTORY METHOD - Concrete product. Author: Yeskendyr (Student 1) */
class Salad extends BaseMeal {
    Salad() {
        super("Salad", "Caesar Salad", 2200, MealCategory.SALAD);
    }
}
