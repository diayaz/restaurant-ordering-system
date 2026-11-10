package restaurant.observer;

import restaurant.builder.Order;

/**
 * OBSERVER pattern - Concrete observer: notifications sent to the customer
 * (in a real system this would be an SMS / push message).
 *
 * Author: Dias (Student 2)
 */
public class CustomerNotifier implements OrderObserver {

    @Override
    public void update(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        String message;
        switch (newStatus) {
            case PLACED:    message = "your order is accepted"; break;
            case COOKING:   message = "your food is being cooked"; break;
            case READY:     message = order.isDelivery()
                                    ? "your order is ready and handed to the courier"
                                    : "your order is ready, it will be served soon"; break;
            case SERVED:    message = order.isDelivery()
                                    ? "your order was delivered to " + order.getAddress()
                                    : "enjoy your meal at table " + order.getTableNumber(); break;
            case PAID:      message = "payment received, thank you!"; break;
            case CANCELLED: message = "your order was cancelled"; break;
            default:        message = newStatus.name();
        }
        System.out.println("  [Customer -> " + order.getCustomerName() + "] " + message);
    }
}
