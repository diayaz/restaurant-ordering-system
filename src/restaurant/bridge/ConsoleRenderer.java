package restaurant.bridge;

/**
 * BRIDGE pattern - Concrete implementor: plain-text receipt for the console
 * or a thermal printer.
 *
 * Author: Kuandyk (Student 3)
 */
public class ConsoleRenderer implements ReceiptRenderer {
    private static final int WIDTH = 46;
    private StringBuilder out;

    @Override
    public void begin(String title) {
        out = new StringBuilder();
        out.append('+').append("=".repeat(WIDTH)).append("+\n");
        int pad = (WIDTH - title.length()) / 2;
        out.append('|').append(" ".repeat(pad)).append(title)
           .append(" ".repeat(WIDTH - pad - title.length())).append("|\n");
        out.append('+').append("=".repeat(WIDTH)).append("+\n");
    }

    @Override
    public void text(String text) {
        out.append("| ").append(fit(text, WIDTH - 2)).append(" |\n");
    }

    @Override
    public void row(String left, String right) {
        int space = WIDTH - 2 - right.length();
        out.append("| ").append(fit(left, space)).append(right).append(" |\n");
    }

    @Override
    public void separator() {
        out.append('|').append("-".repeat(WIDTH)).append("|\n");
    }

    @Override
    public void total(String label, String value) {
        row(label.toUpperCase(), value);
    }

    @Override
    public String end() {
        out.append('+').append("=".repeat(WIDTH)).append("+\n");
        return out.toString();
    }

    private static String fit(String s, int width) {
        if (s.length() > width) {
            return s.substring(0, width - 3) + "...";
        }
        return s + " ".repeat(width - s.length());
    }
}
