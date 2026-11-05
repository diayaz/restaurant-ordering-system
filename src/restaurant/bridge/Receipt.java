package restaurant.bridge;

import restaurant.builder.Order;

/**
 * BRIDGE pattern - Abstraction.
 *
 * Holds a reference to a ReceiptRenderer (the "bridge"). Receipt types and
 * output formats vary independently: 2 receipt types x 2 renderers = 4
 * combinations without creating 4 subclasses.
 *
 * Author: Kuandyk (Student 3)
 */
public abstract class Receipt {
    protected final ReceiptRenderer renderer;

    protected Receipt(ReceiptRenderer renderer) {
        this.renderer = renderer;
    }

    /** Builds the receipt for the given order and returns the rendered text. */
    public abstract String print(Order order);

    protected static String money(double value) {
        return String.format("%.0f KZT", value);
    }
}
