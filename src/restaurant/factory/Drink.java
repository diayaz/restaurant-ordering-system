package restaurant.factory;

/** FACTORY METHOD - Concrete product. Author: Yeskendyr (Student 1) */
class Drink extends BaseMeal {
    Drink() {
        super("Drink", "Lemonade", 900, MealCategory.DRINK);
    }
}
