package restaurant.builder;

import restaurant.factory.Meal;
import restaurant.observer.OrderObserver;
import restaurant.observer.OrderStatus;
import restaurant.observer.OrderSubject;
import restaurant.strategy.DiscountStrategy;
import restaurant.strategy.NoDiscount;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BUILDER pattern - Product built by Order.Builder (author: Kuandyk, Student 3).
 *
 * An order has many optional parts (table OR address, comment, several meals),
 * so instead of a constructor with 8 parameters it is assembled step by step
 * by the nested static Builder, which also validates the result.
 *
 * The Order is also the SUBJECT of the OBSERVER pattern (author: Dias, Student 2)
 * and the CONTEXT of the STRATEGY pattern (discount) - this is where the
 * patterns of different students work together.
 */
public class Order implements OrderSubject {

    public enum Type { DINE_IN, DELIVERY }

    private static int nextId = 1;

    private final int id;
    private final String customerName;
    private final Type type;
    private final int tableNumber;
    private final String address;
    private final String comment;
    private final List<Meal> items;
    private final LocalDateTime createdAt;

    private OrderStatus status;
    private final List<OrderObserver> observers = new ArrayList<>();

    private DiscountStrategy discountStrategy = new NoDiscount();
    private String paymentMethod = "-";

    /** Private: an Order can be created only through the Builder. */
    private Order(Builder builder) {
        this.id = nextId++;
        this.customerName = builder.customerName;
        this.type = builder.type;
        this.tableNumber = builder.tableNumber;
        this.address = builder.address;
        this.comment = builder.comment;
        this.items = Collections.unmodifiableList(new ArrayList<>(builder.items));
        this.createdAt = LocalDateTime.now();
    }

    // ------------------------------------------------------------ BUILDER
    public static class Builder {
        private String customerName;
        private Type type = Type.DINE_IN;
        private int tableNumber;
        private String address = "";
        private String comment = "";
        private final List<Meal> items = new ArrayList<>();

        public Builder customer(String name) {
            this.customerName = name;
            return this;
        }

        public Builder dineIn(int tableNumber) {
            this.type = Type.DINE_IN;
            this.tableNumber = tableNumber;
            return this;
        }

        public Builder delivery(String address) {
            this.type = Type.DELIVERY;
            this.address = address;
            return this;
        }

        public Builder addItem(Meal meal) {
            this.items.add(meal);
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        /** Validates the collected data and creates the immutable order. */
        public Order build() {
            if (customerName == null || customerName.trim().isEmpty()) {
                throw new IllegalStateException("Customer name is required");
            }
            if (items.isEmpty()) {
                throw new IllegalStateException("Order must contain at least one meal");
            }
            if (type == Type.DINE_IN && tableNumber <= 0) {
                throw new IllegalStateException("Dine-in order needs a table number");
            }
            if (type == Type.DELIVERY && address.trim().isEmpty()) {
                throw new IllegalStateException("Delivery order needs an address");
            }
            return new Order(this);
        }
    }

    // ------------------------------------------------------------ OBSERVER (subject)
    @Override
    public void attach(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(OrderStatus oldStatus, OrderStatus newStatus) {
        for (OrderObserver observer : observers) {
            observer.update(this, oldStatus, newStatus);
        }
    }

    /** Every status change automatically notifies all subscribed observers. */
    public void setStatus(OrderStatus newStatus) {
        OrderStatus old = this.status;
        this.status = newStatus;
        notifyObservers(old, newStatus);
    }

    // ------------------------------------------------------------ STRATEGY (context)
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public double getSubtotal() {
        double sum = 0;
        for (Meal meal : items) {
            sum += meal.getPrice();
        }
        return sum;
    }

    public double getDiscount() {
        return discountStrategy.calculateDiscount(this);
    }

    public double getFinalTotal() {
        return getSubtotal() - getDiscount();
    }

    // ------------------------------------------------------------ getters
    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public Type getType() { return type; }
    public boolean isDelivery() { return type == Type.DELIVERY; }
    public int getTableNumber() { return tableNumber; }
    public String getAddress() { return address; }
    public String getComment() { return comment; }
    public List<Meal> getItems() { return items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public OrderStatus getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getDestination() {
        return isDelivery() ? "Delivery: " + address : "Table " + tableNumber;
    }

    @Override
    public String toString() {
        return String.format("#%d %-10s %-34s %-9s %d item(s)  %.0f KZT",
                id, customerName, getDestination(), status, items.size(), getSubtotal());
    }
}
