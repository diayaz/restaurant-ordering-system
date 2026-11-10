package restaurant.observer;

import restaurant.builder.Order;

/**
 * OBSERVER pattern - Concrete observer: cashier dashboard.
 * Keeps live sales statistics; updated only when an order is paid.
 *
 * Author: Dias (Student 2)
 */
public class CashierDashboard implements OrderObserver {
    private int paidOrders;
    private double revenue;

    @Override
    public void update(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        if (newStatus == OrderStatus.PAID) {
            paidOrders++;
            revenue += order.getFinalTotal();
            System.out.printf("  [Cashier] Order #%d paid %.0f KZT via %s. Today: %d order(s), %.0f KZT%n",
                    order.getId(), order.getFinalTotal(), order.getPaymentMethod(), paidOrders, revenue);
        }
    }

    public int getPaidOrders() {
        return paidOrders;
    }

    public double getRevenue() {
        return revenue;
    }
}
