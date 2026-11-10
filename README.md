# Restaurant Ordering System - Final Project (Software Design Patterns)

A Java console application that integrates **7 design patterns** in one working project.

| Student   | Patterns                   | Packages                                   |
|-----------|----------------------------|--------------------------------------------|
| Yeskendyr | Factory Method, Decorator  | `restaurant.factory`, `restaurant.decorator` |
| Dias      | Observer, Strategy         | `restaurant.observer`, `restaurant.strategy` |
| Kuandyk   | Builder, Bridge, Facade    | `restaurant.builder`, `restaurant.bridge`, `restaurant.facade` |

## Requirements
JDK 11 or newer. No external libraries.

## Build and run
Linux / macOS:
```
./build.sh
./run.sh           # interactive menu
./run.sh --demo    # scripted demo of all patterns
```
Windows:
```
build.bat
run.bat
run.bat --demo
```
Manually:
```
javac -d out $(find src -name "*.java")
java -cp out restaurant.Main --demo
```
IntelliJ IDEA / Eclipse: open the folder, mark `src` as Sources Root, run `restaurant.Main`.

## How the patterns work together
`Main` -> **Facade** (`RestaurantFacade`) -> **Factory Method** creates a meal -> **Decorator** adds toppings ->
**Builder** assembles the `Order` -> **Observer** notifies kitchen / customer / cashier on every status change ->
**Strategy** calculates the discount and performs the payment -> **Bridge** prints a short/detailed receipt
as console text or HTML.

## Project structure
```
src/restaurant/
  Main.java      console client (menu + --demo)
  factory/       Meal, BaseMeal, MealCategory, MealFactory, Burger/Pizza/Salad/DrinkFactory, Burger, Pizza, Salad, Drink
  decorator/     ToppingDecorator, ExtraCheese, Bacon, SpicySauce, ExtraIce, Topping
  observer/      OrderSubject, OrderObserver, OrderStatus, KitchenDisplay, CustomerNotifier, CashierDashboard
  strategy/      DiscountStrategy, NoDiscount, StudentDiscount, HappyHourDiscount,
                 PaymentStrategy, CashPayment, CardPayment, KaspiQrPayment
  builder/       Order (+ nested Order.Builder)
  bridge/        Receipt, ShortReceipt, DetailedReceipt, ReceiptRenderer, ConsoleRenderer, HtmlRenderer
  facade/        RestaurantFacade
uml/             Graphviz sources of the UML class diagrams (render: dot -Tpng file.dot -o file.png)
```
