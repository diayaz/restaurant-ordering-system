package restaurant.observer;

/**
 * OBSERVER pattern - Subject interface (implemented by Order).
 * Author: Dias (Student 2)
 */
public interface OrderSubject {
    void attach(OrderObserver observer);

    void detach(OrderObserver observer);

    void notifyObservers(OrderStatus oldStatus, OrderStatus newStatus);
}
