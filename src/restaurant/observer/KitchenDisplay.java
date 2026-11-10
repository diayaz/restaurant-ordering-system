package restaurant.observer;

import restaurant.builder.Order;
import restaurant.factory.Meal;

/**
 * OBSERVER pattern - Concrete observer: screen in the kitchen.
 * Reacts to new and cancelled orders.
 *
 * Author: Dias (Student 2)
 */
public class KitchenDisplay implements OrderObserver {

    @Override
    public void update(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        if (newStatus == OrderStatus.PLACED) {
            System.out.println("  [Kitchen] New order #" + order.getId() + " received:");
            for (Meal meal : order.getItems()) {
                System.out.println("            - " + meal.getDescription());
            }
            if (!order.getComment().isEmpty()) {
                System.out.println("            note: " + order.getComment());
            }
        } else if (newStatus == OrderStatus.COOKING) {
            System.out.println("  [Kitchen] Chef started cooking order #" + order.getId());
        } else if (newStatus == OrderStatus.CANCELLED) {
            System.out.println("  [Kitchen] Order #" + order.getId() + " cancelled - stop cooking!");
        }
    }
}
