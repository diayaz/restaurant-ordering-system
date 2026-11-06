package restaurant.bridge;

import restaurant.builder.Order;
import restaurant.factory.Meal;

import java.time.format.DateTimeFormatter;

/**
 * BRIDGE pattern - Refined abstraction: full receipt with every item,
 * discount and payment method.
 *
 * Author: Kuandyk (Student 3)
 */
public class DetailedReceipt extends Receipt {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public DetailedReceipt(ReceiptRenderer renderer) {
        super(renderer);
    }

    @Override
    public String print(Order order) {
        renderer.begin("DESIGN PATTERNS BISTRO");
        renderer.text("Order #" + order.getId() + "  |  " + order.getCreatedAt().format(FMT));
        renderer.text("Customer: " + order.getCustomerName());
        renderer.text(order.getDestination());
        renderer.separator();
        for (Meal meal : order.getItems()) {
            renderer.row(meal.getDescription(), money(meal.getPrice()));
        }
        renderer.separator();
        renderer.row("Subtotal", money(order.getSubtotal()));
        renderer.row("Discount: " + order.getDiscountStrategy().getName(), "-" + money(order.getDiscount()));
        renderer.total("Total", money(order.getFinalTotal()));
        renderer.separator();
        renderer.text("Paid by: " + order.getPaymentMethod());
        renderer.text("Thank you! Come again :)");
        return renderer.end();
    }
}
