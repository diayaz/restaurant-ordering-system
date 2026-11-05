package restaurant.bridge;

import restaurant.builder.Order;

/**
 * BRIDGE pattern - Refined abstraction: compact receipt (total only).
 * Author: Kuandyk (Student 3)
 */
public class ShortReceipt extends Receipt {

    public ShortReceipt(ReceiptRenderer renderer) {
        super(renderer);
    }

    @Override
    public String print(Order order) {
        renderer.begin("RECEIPT #" + order.getId());
        renderer.row(order.getCustomerName(), order.getItems().size() + " item(s)");
        renderer.total("Total", money(order.getFinalTotal()));
        return renderer.end();
    }
}
