package restaurant.bridge;

/**
 * BRIDGE pattern - Implementor.
 *
 * Low-level formatting operations. The receipt (abstraction) only says
 * WHAT to print; the renderer decides HOW it looks (plain text, HTML, ...).
 *
 * Author: Kuandyk (Student 3)
 */
public interface ReceiptRenderer {
    void begin(String title);

    void text(String text);

    void row(String left, String right);

    void separator();

    void total(String label, String value);

    String end();
}
