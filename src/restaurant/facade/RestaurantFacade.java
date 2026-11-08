package restaurant.facade;

import restaurant.bridge.Receipt;
import restaurant.builder.Order;
import restaurant.decorator.Topping;
import restaurant.factory.Meal;
import restaurant.factory.MealCategory;
import restaurant.factory.MealFactory;
import restaurant.observer.CashierDashboard;
import restaurant.observer.CustomerNotifier;
import restaurant.observer.KitchenDisplay;
import restaurant.observer.OrderStatus;
import restaurant.strategy.DiscountStrategy;
import restaurant.strategy.PaymentStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FACADE pattern.
 *
 * One simple entry point to the whole restaurant subsystem. The client
 * (console UI) does not need to know about factories, decorators, builders,
 * observers, strategies or receipt renderers - it just calls
 * orderMeal(), placeOrder(), nextStatus(), checkout() and printReceipt().
 *
 * Author: Kuandyk (Student 3)
 */
public class RestaurantFacade {
    private final KitchenDisplay kitchen = new KitchenDisplay();
    private final CustomerNotifier customerNotifier = new CustomerNotifier();
    private final CashierDashboard cashier = new CashierDashboard();
    private final List<Order> orders = new ArrayList<>();

    /** Factory Method + Decorator: creates a meal and wraps it with toppings. */
    public Meal orderMeal(MealCategory category, Topping... toppings) {
        Meal meal = MealFactory.forCategory(category).prepareMeal();
        for (Topping topping : toppings) {
            meal = topping.applyTo(meal);
        }
        return meal;
    }

    /** Builder (built by the caller) + Observer: subscribes all observers and places the order. */
    public Order placeOrder(Order.Builder builder) {
        Order order = builder.build();
        order.attach(kitchen);
        order.attach(customerNotifier);
        order.attach(cashier);
        orders.add(order);
        System.out.println("  [Facade] Order #" + order.getId() + " placed for " + order.getCustomerName());
        order.setStatus(OrderStatus.PLACED);
        return order;
    }

    /** Moves the order to the next kitchen status; observers are notified. */
    public void nextStatus(Order order) {
        OrderStatus current = order.getStatus();
        if (current == OrderStatus.SERVED || current == OrderStatus.PAID || current == OrderStatus.CANCELLED) {
            System.out.println("  [Facade] Order #" + order.getId() + " is already " + current);
            return;
        }
        order.setStatus(current.next());
    }

    /** Runs the full kitchen cycle: PLACED -> COOKING -> READY -> SERVED. */
    public void cookAndServe(Order order) {
        while (order.getStatus() != OrderStatus.SERVED
                && order.getStatus() != OrderStatus.PAID
                && order.getStatus() != OrderStatus.CANCELLED) {
            nextStatus(order);
        }
    }

    public void cancel(Order order) {
        if (order.getStatus() == OrderStatus.PAID) {
            System.out.println("  [Facade] Paid order cannot be cancelled");
            return;
        }
        order.setStatus(OrderStatus.CANCELLED);
    }

    /** Strategy: applies the chosen discount and pays with the chosen method. */
    public boolean checkout(Order order, DiscountStrategy discount, PaymentStrategy payment) {
        if (order.getStatus() == OrderStatus.PAID) {
            System.out.println("  [Facade] Order #" + order.getId() + " is already paid");
            return false;
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            System.out.println("  [Facade] Order #" + order.getId() + " is cancelled");
            return false;
        }
        order.setDiscountStrategy(discount);
        System.out.printf("  [Facade] Subtotal %.0f, discount \"%s\" -%.0f, to pay %.0f KZT%n",
                order.getSubtotal(), discount.getName(), order.getDiscount(), order.getFinalTotal());
        if (!payment.pay(order.getFinalTotal())) {
            return false;
        }
        order.setPaymentMethod(payment.getName());
        order.setStatus(OrderStatus.PAID);
        return true;
    }

    /** Bridge: any receipt type with any renderer. */
    public String printReceipt(Order order, Receipt receipt) {
        return receipt.print(order);
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public Order findOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public CashierDashboard getCashier() {
        return cashier;
    }
}
