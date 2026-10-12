# Java Interface Notes

## 1. What Is an Interface?

An interface is a contract that defines what a class must do, without
deciding how the class does it. It is declared with the `interface` keyword.

```java
interface PaymentGateway {
    boolean pay(String orderId, double amount);
}
```

A class uses `implements` to follow an interface contract:

```java
class UpiPaymentGateway implements PaymentGateway {
    @Override
    public boolean pay(String orderId, double amount) {
        return true;
    }
}
```

The implementing class must provide public implementations for the interface
methods, unless the class is abstract.

## 2. Why Do We Need Interfaces?

Interfaces are useful in real applications because they:

- reduce coupling between classes
- provide abstraction and hide implementation details
- allow different classes to follow the same contract
- support polymorphism
- allow a class to implement multiple interfaces
- make testing easier by allowing fake or mock implementations
- make it possible to replace an implementation without changing client code
- support dependency injection

For example, an order service should depend on a `PaymentGateway` interface,
not directly on a UPI, card, or bank implementation. The payment provider can
then be changed without rewriting the order service.

## 3. Interface Rules

### Methods

- Ordinary interface methods are `public` and `abstract` by default.
- Implementing methods must be `public`.
- An interface can also contain `default`, `static`, and private helper
  methods.
- An interface cannot be instantiated directly.

```java
interface Printer {
    void print(String text); // public abstract by default

    default void printHeader() {
        System.out.println("--- Document ---");
    }

    static void showVersion() {
        System.out.println("Printer API v1");
    }
}
```

### Fields

Variables declared in an interface are `public`, `static`, and `final` by
default. They are constants and must be initialized.

```java
interface OrderRules {
    double TAX_RATE = 0.18;
}
```

The explicit form is:

```java
public static final double TAX_RATE = 0.18;
```

### Implementation

- A class uses `implements`.
- A class can implement more than one interface.
- An interface can extend one or more interfaces.
- An implementing class can also extend a class.
- A class may be abstract if it does not implement every required method.

```java
interface Printable {
    void print();
}

interface Scannable {
    void scan();
}

class MultiFunctionPrinter implements Printable, Scannable {
    @Override
    public void print() {
        System.out.println("Printing");
    }

    @Override
    public void scan() {
        System.out.println("Scanning");
    }
}
```

## 4. Interface Types in Modern Java

### Abstract methods

These define the required behavior and do not have a method body.

```java
interface Account {
    void deposit(double amount);
}
```

### Default methods

A `default` method has a body. It provides reusable behavior to implementing
classes, while still allowing them to override it.

```java
interface Logger {
    default void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
```

### Static methods

Static interface methods belong to the interface and are called using the
interface name.

```java
interface IdValidator {
    static boolean isValid(String id) {
        return id != null && !id.isBlank();
    }
}

boolean valid = IdValidator.isValid("ORD-1001");
```

Static interface methods are not inherited by implementing classes.

### Private methods

Since Java 9, private methods can be used as helpers inside an interface.
They can be called by default or static methods in that interface, but not by
implementing classes.

## 5. Annotations

An annotation is metadata added to Java code. It starts with `@` and gives
information to the compiler, tools, or runtime without changing the main
behavior of the code.

### Common Built-in Annotations

#### `@Override`

`@Override` tells the compiler that a method is replacing a method from a
parent class or interface.

```java
interface PaymentGateway {
    boolean pay(String orderId, double amount);
}

class UpiPaymentGateway implements PaymentGateway {
    @Override
    public boolean pay(String orderId, double amount) {
        return true;
    }
}
```

If the method name or parameter list is incorrect, the compiler reports an
error instead of silently treating it as a new method. This helps catch
spelling and signature mistakes.

#### `@FunctionalInterface`

`@FunctionalInterface` verifies that an interface has exactly one abstract
method, allowing it to be used with a lambda expression.

```java
@FunctionalInterface
interface DiscountPolicy {
    double apply(double amount);
}

DiscountPolicy discount = amount -> amount * 0.90;
System.out.println(discount.apply(1000)); // 900.0
```

Adding another abstract method to `DiscountPolicy` causes a compile-time error.

#### `@Deprecated`

`@Deprecated` marks code that should no longer be used because a newer or
safer alternative exists.

```java
class PaymentUtils {
    @Deprecated
    static void oldPaymentMethod() {
        System.out.println("Use processPayment instead.");
    }

    static void processPayment() {
        System.out.println("Processing payment");
    }
}
```

The compiler or IDE displays a warning when deprecated code is used.

#### `@SuppressWarnings`

`@SuppressWarnings` tells the compiler not to display a specific warning.
Suppress only warnings that are understood and intentional.

```java
@SuppressWarnings("deprecation")
static void runLegacyPayment() {
    PaymentUtils.oldPaymentMethod();
}
```

### Creating a Custom Annotation

You can define your own annotation with `@interface`:

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Audited {
    String value();
}

class OrderService {
    @Audited("order-payment")
    void processPayment() {
        System.out.println("Payment processed");
    }
}
```

- `@Target` specifies where the annotation can be used.
- `@Retention` specifies how long the annotation is available.
- `RetentionPolicy.RUNTIME` makes it available while the program is running,
  so reflection-based tools can read it.

Annotations do not automatically execute code. A framework or application
must read them and decide what they mean.

```java
import java.lang.reflect.Method;

Method method = OrderService.class.getDeclaredMethod("processPayment");
Audited audit = method.getAnnotation(Audited.class);
System.out.println(audit.value()); // order-payment
```

Use annotations to add metadata, validation, or configuration. Use ordinary
methods and fields when the code needs to perform the actual business logic.

## 6. Real-World Example: E-Commerce Order Service

An online store may support UPI, card, wallet, and bank payment providers.
The order service should not be tightly coupled to one provider.

```java
interface PaymentGateway {
    boolean pay(String orderId, double amount);
}

class UpiPaymentGateway implements PaymentGateway {
    @Override
    public boolean pay(String orderId, double amount) {
        System.out.println("Paid Rs." + amount + " for " + orderId + " using UPI");
        return true;
    }
}

interface NotificationService {
    void send(String recipient, String message);
}

class EmailNotificationService implements NotificationService {
    @Override
    public void send(String recipient, String message) {
        System.out.println("Email sent to " + recipient + ": " + message);
    }
}

class OrderService {
    private final PaymentGateway paymentGateway;
    private final NotificationService notificationService;

    OrderService(
            PaymentGateway paymentGateway,
            NotificationService notificationService) {
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
    }

    void placeOrder(String orderId, double amount) {
        boolean successful = paymentGateway.pay(orderId, amount);

        if (successful) {
            notificationService.send(
                    "customer@example.com",
                    "Your order " + orderId + " was confirmed.");
        }
    }
}
```

Usage:

```java
PaymentGateway gateway = new UpiPaymentGateway();
NotificationService notificationService =
        new EmailNotificationService();

OrderService orderService = new OrderService(gateway, notificationService);
orderService.placeOrder("ORD-1001", 1499.0);
```

Output:

```text
Paid Rs.1499.0 for ORD-1001 using UPI
Email sent to customer@example.com: Your order ORD-1001 was confirmed.
```

### What this example demonstrates

- `PaymentGateway` is a contract for every payment provider.
- `UpiPaymentGateway` is one implementation of that contract.
- `NotificationService` allows email, SMS, or push notifications to be
  replaced independently.
- `OrderService` depends on interfaces rather than concrete classes.
- `PaymentGateway gateway = new UpiPaymentGateway()` demonstrates
  polymorphism.
- Passing implementations into the `OrderService` constructor is dependency
  injection.

To add card payments, create another implementation without changing
`OrderService`:

```java
class CardPaymentGateway implements PaymentGateway {
    @Override
    public boolean pay(String orderId, double amount) {
        System.out.println("Paid by card");
        return true;
    }
}
```

## 7. Interface and Lambda Expressions

### Functional Interface

A functional interface is an interface with exactly one abstract method. It
may also contain any number of `default` and `static` methods because those
methods already have an implementation.

`@FunctionalInterface` is optional, but it makes the compiler verify that the
interface has exactly one abstract method.

```java
@FunctionalInterface
interface DiscountPolicy {
    double apply(double amount);
}
```

This interface represents a rule that receives an amount and returns a
calculated amount. It can be implemented using a normal class:

```java
class FestivalDiscount implements DiscountPolicy {
    @Override
    public double apply(double amount) {
        return amount * 0.90;
    }
}
```

Adding another abstract method to `DiscountPolicy` causes a compile-time
error:

```java
// double calculateTax(double amount); // Not allowed with @FunctionalInterface
```

### Lambda Expression

A lambda expression is a short way to provide the implementation of a
functional interface without creating a separate class.

```java
DiscountPolicy festivalDiscount = amount -> amount * 0.90;

System.out.println(festivalDiscount.apply(1000));
```

The general syntax is:

```java
(parameters) -> expression
(parameters) -> { statements; }
```

Examples:

```java
DiscountPolicy noDiscount = amount -> amount;
DiscountPolicy flatDiscount = amount -> {
    if (amount >= 1000) {
        return amount - 100;
    }
    return amount;
};
```

The parameter type and return type are inferred from the functional interface.
The following forms are also valid:

```java
@FunctionalInterface
interface MessageSender {
    void send(String message);
}

MessageSender sender = message -> System.out.println(message);
sender.send("Order confirmed");
```

### Real-Life Example: Order Discount Rules

An online store can use different discount rules during different campaigns.
The order service can receive a `DiscountPolicy` instead of being changed
every time the business creates a new offer.

```java
@FunctionalInterface
interface DiscountPolicy {
    double apply(double amount);
}

class OrderService {
    private final DiscountPolicy discountPolicy;

    OrderService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    double calculateFinalAmount(double amount) {
        return discountPolicy.apply(amount);
    }
}

DiscountPolicy festivalOffer = amount -> amount * 0.90;
OrderService orderService = new OrderService(festivalOffer);

double finalAmount = orderService.calculateFinalAmount(2000);
System.out.println(finalAmount); // 1800.0
```

The same service can use another policy without changing its code:

```java
DiscountPolicy premiumCustomerOffer = amount -> amount >= 1500
        ? amount - 250
        : amount;

OrderService premiumOrder = new OrderService(premiumCustomerOffer);
System.out.println(premiumOrder.calculateFinalAmount(2000)); // 1750.0
```

This is useful because:

- the discount rule is replaceable
- `OrderService` depends on a behavior, not a concrete discount class
- each offer can be tested independently
- new campaigns do not require changes to the order service

### Common Built-in Functional Interfaces

The `java.util.function` package provides common functional interfaces:

| Interface | Abstract method | Purpose |
| --- | --- | --- |
| `Predicate<T>` | `boolean test(T value)` | Checks a condition |
| `Function<T, R>` | `R apply(T value)` | Converts a value |
| `Consumer<T>` | `void accept(T value)` | Uses a value without returning one |
| `Supplier<T>` | `T get()` | Produces a value without input |

Example:

```java
import java.util.function.Function;
import java.util.function.Predicate;

Predicate<Double> expensiveOrder = amount -> amount > 1000;
Function<Double, Double> addTax = amount -> amount * 1.18;

System.out.println(expensiveOrder.test(1500.0)); // true
System.out.println(addTax.apply(1000.0));        // 1180.0
```

Use a lambda when the behavior is short and used locally. Use a named class or
method when the behavior is complex, reused widely, or needs a descriptive
name.

## 8. Interface vs Abstract Class

| Interface | Abstract class |
| --- | --- |
| Defines a contract or capability | Defines a common base and shared state |
| A class can implement multiple interfaces | A class can extend only one class |
| Does not have normal instance constructors | Can have constructors |
| Fields are constants by default | Can have normal instance fields |
| Best for unrelated classes with common behavior | Best for closely related classes |

Use an interface when you want to describe a capability such as
`Payable`, `Runnable`, or `Serializable`. Use an abstract class when related
subclasses need shared fields, constructors, or common implementation.