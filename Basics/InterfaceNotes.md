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

## 5. Real-World Example: E-Commerce Order Service

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

## 6. Interface and Lambda Expressions

An interface with exactly one abstract method is called a **functional
interface**. It can be implemented with a lambda expression.

```java
@FunctionalInterface
interface DiscountPolicy {
    double apply(double amount);
}

DiscountPolicy festivalDiscount = amount -> amount * 0.90;
System.out.println(festivalDiscount.apply(1000));
```

`@FunctionalInterface` is optional, but it makes the compiler verify that the
interface has only one abstract method.

## 7. Interface vs Abstract Class

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