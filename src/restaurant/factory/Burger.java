package restaurant.factory;

/** FACTORY METHOD - Concrete product. Author: Yeskendyr (Student 1) */
class Burger extends BaseMeal {
    Burger() {
        super("Burger", "Beef Burger", 2500, MealCategory.BURGER);
    }
}
