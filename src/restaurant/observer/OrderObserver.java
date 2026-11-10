package restaurant.observer;

import restaurant.builder.Order;

/**
 * OBSERVER pattern - Observer interface.
 * Every party interested in order changes (kitchen, customer, cashier)
 * implements this interface and is notified automatically.
 *
 * Author: Dias (Student 2)
 */
public interface OrderObserver {
    void update(Order order, OrderStatus oldStatus, OrderStatus newStatus);
}
