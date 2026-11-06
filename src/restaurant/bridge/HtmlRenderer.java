package restaurant.bridge;

/**
 * BRIDGE pattern - Concrete implementor: HTML receipt that can be e-mailed
 * to the customer or opened in a browser.
 *
 * Author: Kuandyk (Student 3)
 */
public class HtmlRenderer implements ReceiptRenderer {
    private StringBuilder out;

    @Override
    public void begin(String title) {
        out = new StringBuilder();
        out.append("<!DOCTYPE html>\n<html><head><meta charset=\"UTF-8\"><title>").append(escape(title))
           .append("</title>\n<style>")
           .append("body{font-family:Arial,sans-serif;background:#f3f1ec;padding:30px}")
           .append(".r{background:#fff;width:380px;margin:auto;padding:22px 26px;border-radius:10px;")
           .append("box-shadow:0 2px 10px rgba(0,0,0,.12)}")
           .append("h2{text-align:center;margin:0 0 12px;color:#b5402a}")
           .append("table{width:100%;border-collapse:collapse}td{padding:4px 0}")
           .append("td.v{text-align:right}tr.t td{font-weight:bold;font-size:18px;border-top:2px solid #333;padding-top:8px}")
           .append("p{margin:4px 0;color:#555}hr{border:0;border-top:1px dashed #aaa}")
           .append("</style></head><body><div class=\"r\">\n<h2>").append(escape(title)).append("</h2>\n<table>\n");
    }

    @Override
    public void text(String text) {
        out.append("<tr><td colspan=\"2\"><p>").append(escape(text)).append("</p></td></tr>\n");
    }

    @Override
    public void row(String left, String right) {
        out.append("<tr><td>").append(escape(left)).append("</td><td class=\"v\">")
           .append(escape(right)).append("</td></tr>\n");
    }

    @Override
    public void separator() {
        out.append("<tr><td colspan=\"2\"><hr></td></tr>\n");
    }

    @Override
    public void total(String label, String value) {
        out.append("<tr class=\"t\"><td>").append(escape(label)).append("</td><td class=\"v\">")
           .append(escape(value)).append("</td></tr>\n");
    }

    @Override
    public String end() {
        out.append("</table>\n</div></body></html>\n");
        return out.toString();
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
