package restaurant;

import restaurant.bridge.ConsoleRenderer;
import restaurant.bridge.DetailedReceipt;
import restaurant.bridge.HtmlRenderer;
import restaurant.bridge.Receipt;
import restaurant.bridge.ReceiptRenderer;
import restaurant.bridge.ShortReceipt;
import restaurant.builder.Order;
import restaurant.decorator.Topping;
import restaurant.facade.RestaurantFacade;
import restaurant.factory.Meal;
import restaurant.factory.MealCategory;
import restaurant.strategy.CardPayment;
import restaurant.strategy.CashPayment;
import restaurant.strategy.DiscountStrategy;
import restaurant.strategy.HappyHourDiscount;
import restaurant.strategy.KaspiQrPayment;
import restaurant.strategy.NoDiscount;
import restaurant.strategy.PaymentStrategy;
import restaurant.strategy.StudentDiscount;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Restaurant Ordering System - console client.
 *
 * Talks to the system ONLY through RestaurantFacade.
 * Run with "--demo" to execute a scripted scenario that shows all 7 patterns.
 *
 * Team: Yeskendyr (Factory Method, Decorator),
 *       Dias (Observer, Strategy),
 *       Kuandyk (Builder, Bridge, Facade)
 */
public class Main {
    private static final RestaurantFacade restaurant = new RestaurantFacade();
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) {
            runDemo();
            return;
        }
        banner();
        while (true) {
            System.out.println();
            System.out.println("================ MAIN MENU ================");
            System.out.println(" 1. Show menu");
            System.out.println(" 2. Create new order");
            System.out.println(" 3. List orders");
            System.out.println(" 4. Move order to next status");
            System.out.println(" 5. Checkout (discount + payment + receipt)");
            System.out.println(" 6. Cancel order");
            System.out.println(" 7. Run full demo scenario");
            System.out.println(" 0. Exit");
            int choice = readInt("Choose: ", 0, 7);
            try {
                switch (choice) {
                    case 1: showMenu(); break;
                    case 2: createOrder(); break;
                    case 3: listOrders(); break;
                    case 4: nextStatus(); break;
                    case 5: checkout(); break;
                    case 6: cancelOrder(); break;
                    case 7: runDemo(); break;
                    case 0:
                        System.out.println("Bye!");
                        return;
                    default: break;
                }
            } catch (IllegalStateException e) {
                System.out.println("  [Error] " + e.getMessage());
            }
        }
    }

    // ================================================================ MENU ACTIONS
    private static void banner() {
        System.out.println("+------------------------------------------------+");
        System.out.println("|      DESIGN PATTERNS BISTRO - Ordering System  |");
        System.out.println("|  Factory | Decorator | Observer | Strategy     |");
        System.out.println("|  Builder | Bridge    | Facade                  |");
        System.out.println("+------------------------------------------------+");
    }

    private static void showMenu() {
        System.out.println("---------------- MENU ----------------");
        System.out.println(" Meals:");
        System.out.println("   1. Beef Burger ........ 2500 KZT");
        System.out.println("   2. Margherita Pizza ... 3800 KZT");
        System.out.println("   3. Caesar Salad ....... 2200 KZT");
        System.out.println("   4. Lemonade ........... 900 KZT");
        System.out.println(" Toppings:");
        Topping[] toppings = Topping.values();
        for (int i = 0; i < toppings.length; i++) {
            System.out.printf("   %d. %-18s +%.0f KZT%n", i + 1, toppings[i].getLabel(), toppings[i].getPrice());
        }
    }

    private static void createOrder() {
        Order.Builder builder = new Order.Builder();
        builder.customer(readLine("Customer name: "));
        int type = readInt("1 - Dine-in, 2 - Delivery: ", 1, 2);
        if (type == 1) {
            builder.dineIn(readInt("Table number: ", 1, 50));
        } else {
            builder.delivery(readLine("Delivery address: "));
        }
        showMenu();
        MealCategory[] categories = MealCategory.values();
        while (true) {
            int m = readInt("Add meal (1-4, 0 = finish): ", 0, 4);
            if (m == 0) {
                break;
            }
            List<Topping> chosen = new ArrayList<>();
            while (true) {
                int t = readInt("  Add topping (1-4, 0 = none/finish): ", 0, Topping.values().length);
                if (t == 0) {
                    break;
                }
                chosen.add(Topping.values()[t - 1]);
            }
            Meal meal = restaurant.orderMeal(categories[m - 1], chosen.toArray(new Topping[0]));
            builder.addItem(meal);
            System.out.println("  Added: " + meal);
        }
        String comment = readLine("Comment (Enter to skip): ");
        if (!comment.isEmpty()) {
            builder.comment(comment);
        }
        restaurant.placeOrder(builder);
    }

    private static void listOrders() {
        if (restaurant.getOrders().isEmpty()) {
            System.out.println("  No orders yet.");
            return;
        }
        for (Order order : restaurant.getOrders()) {
            System.out.println("  " + order);
        }
    }

    private static Order pickOrder() {
        listOrders();
        if (restaurant.getOrders().isEmpty()) {
            return null;
        }
        Order order = restaurant.findOrder(readInt("Order id: ", 1, Integer.MAX_VALUE));
        if (order == null) {
            System.out.println("  Order not found");
        }
        return order;
    }

    private static void nextStatus() {
        Order order = pickOrder();
        if (order != null) {
            restaurant.nextStatus(order);
        }
    }

    private static void cancelOrder() {
        Order order = pickOrder();
        if (order != null) {
            restaurant.cancel(order);
        }
    }

    private static void checkout() {
        Order order = pickOrder();
        if (order == null) {
            return;
        }
        System.out.println("Discount: 1 - none, 2 - student -10%, 3 - happy hour (drinks -50%)");
        DiscountStrategy discount;
        switch (readInt("Choose discount: ", 1, 3)) {
            case 2: discount = new StudentDiscount(); break;
            case 3: discount = new HappyHourDiscount(); break;
            default: discount = new NoDiscount();
        }
        order.setDiscountStrategy(discount);
        System.out.printf("  To pay: %.0f KZT%n", order.getFinalTotal());
        System.out.println("Payment: 1 - cash, 2 - card, 3 - Kaspi QR");
        PaymentStrategy payment;
        switch (readInt("Choose payment: ", 1, 3)) {
            case 1: payment = new CashPayment(readInt("Cash given: ", 0, Integer.MAX_VALUE)); break;
            case 2: payment = new CardPayment(readLine("Card number (16 digits): ")); break;
            default: payment = new KaspiQrPayment(readLine("Phone number: "));
        }
        if (!restaurant.checkout(order, discount, payment)) {
            return;
        }
        int kind = readInt("Receipt: 1 - short, 2 - detailed: ", 1, 2);
        int format = readInt("Format: 1 - console, 2 - HTML file: ", 1, 2);
        ReceiptRenderer renderer = format == 1 ? new ConsoleRenderer() : new HtmlRenderer();
        Receipt receipt = kind == 1 ? new ShortReceipt(renderer) : new DetailedReceipt(renderer);
        output(order, restaurant.printReceipt(order, receipt), format == 2);
    }

    private static void output(Order order, String text, boolean html) {
        if (!html) {
            System.out.print(text);
            return;
        }
        Path file = Paths.get("receipt_" + order.getId() + ".html");
        try {
            Files.write(file, text.getBytes(StandardCharsets.UTF_8));
            System.out.println("  HTML receipt saved to " + file.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("  Could not save receipt: " + e.getMessage());
        }
    }

    // ================================================================ DEMO
    private static void section(String title) {
        System.out.println();
        System.out.println("==== " + title + " " + "=".repeat(Math.max(0, 60 - title.length())));
    }

    private static void runDemo() {
        banner();

        section("1. FACTORY METHOD + DECORATOR (Yeskendyr)");
        Meal burger = restaurant.orderMeal(MealCategory.BURGER, Topping.EXTRA_CHEESE, Topping.BACON);
        Meal pizza = restaurant.orderMeal(MealCategory.PIZZA, Topping.SPICY_SAUCE);
        Meal lemonade = restaurant.orderMeal(MealCategory.DRINK, Topping.EXTRA_ICE);
        Meal salad = restaurant.orderMeal(MealCategory.SALAD);
        System.out.println("  Decorated results:");
        System.out.println("    " + burger);
        System.out.println("    " + pizza);
        System.out.println("    " + lemonade);
        System.out.println("    " + salad);

        section("2. BUILDER + FACADE + OBSERVER (Kuandyk, Dias)");
        Order dineIn = restaurant.placeOrder(new Order.Builder()
                .customer("Aruzhan")
                .dineIn(7)
                .addItem(burger)
                .addItem(lemonade)
                .comment("no onions please"));
        System.out.println();
        Order delivery = restaurant.placeOrder(new Order.Builder()
                .customer("Timur")
                .delivery("Mangilik El 55, Astana")
                .addItem(pizza)
                .addItem(salad)
                .addItem(restaurant.orderMeal(MealCategory.DRINK)));

        System.out.println();
        System.out.println("  Builder validation - order without meals:");
        try {
            new Order.Builder().customer("Empty").dineIn(1).build();
        } catch (IllegalStateException e) {
            System.out.println("  [Builder] rejected: " + e.getMessage());
        }

        section("3. OBSERVER - status changes notify kitchen/customer");
        restaurant.cookAndServe(dineIn);
        System.out.println();
        restaurant.cookAndServe(delivery);

        section("4. STRATEGY - discounts and payments (Dias)");
        DiscountStrategy[] discounts = {new NoDiscount(), new StudentDiscount(), new HappyHourDiscount()};
        System.out.printf("  Order #%d (subtotal %.0f KZT) with different discount strategies:%n",
                dineIn.getId(), dineIn.getSubtotal());
        for (DiscountStrategy d : discounts) {
            dineIn.setDiscountStrategy(d);
            System.out.printf("    %-26s discount %5.0f -> total %5.0f KZT%n", d.getName(), dineIn.getDiscount(), dineIn.getFinalTotal());
        }
        System.out.println();
        restaurant.checkout(dineIn, new HappyHourDiscount(), new CashPayment(5000));
        System.out.println();
        System.out.println("  Card payment attempt with a wrong number:");
        restaurant.checkout(delivery, new StudentDiscount(), new CardPayment("1234"));
        System.out.println();
        restaurant.checkout(delivery, new StudentDiscount(), new KaspiQrPayment("+7 701 123 45 67"));

        section("5. BRIDGE - receipt type x output format (Kuandyk)");
        System.out.println("  ShortReceipt + ConsoleRenderer:");
        System.out.print(restaurant.printReceipt(dineIn, new ShortReceipt(new ConsoleRenderer())));
        System.out.println("  DetailedReceipt + ConsoleRenderer:");
        System.out.print(restaurant.printReceipt(dineIn, new DetailedReceipt(new ConsoleRenderer())));
        System.out.println("  DetailedReceipt + HtmlRenderer:");
        output(delivery, restaurant.printReceipt(delivery, new DetailedReceipt(new HtmlRenderer())), true);

        section("SUMMARY");
        for (Order order : restaurant.getOrders()) {
            System.out.println("  " + order);
        }
        System.out.printf("  Cashier dashboard: %d paid orders, revenue %.0f KZT%n",
                restaurant.getCashier().getPaidOrders(), restaurant.getCashier().getRevenue());
    }

    // ================================================================ INPUT HELPERS
    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!in.hasNextLine()) {
            System.exit(0);
        }
        return in.nextLine().trim();
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            String line = readLine(prompt);
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through
            }
            System.out.println("  Please enter a number from " + min + " to " + max);
        }
    }
}
