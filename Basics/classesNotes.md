# Java Classes: Abstract, Inner, and Anonymous Inner Classes

## 1. Abstract Class

An abstract class is an incomplete class that is declared using the
`abstract` keyword. It is used to provide common fields and methods while
leaving some behavior for subclasses to implement.

### Important points

- An abstract class cannot be instantiated directly.
- It can contain abstract methods and concrete methods.
- An abstract method has no body and must be implemented by a concrete subclass.
- An abstract class can have constructors, fields, and static methods.
- A subclass must implement all abstract methods, or it must also be abstract.

### Example

```java
abstract class Animal {
    abstract void sound();

    void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

public class AbstractClassExample {
    public static void main(String[] args) {
        Animal animal = new Dog();
        animal.sound();
        animal.eat();
    }
}
```

Output:

```text
Dog barks
Animal eats
```

## 2. Inner Class

An inner class is a non-static class declared inside another class. It can
access the members of its enclosing class, including private members.

### Example

```java
class Outer {
    private int number = 10;

    class Inner {
        void display() {
            System.out.println("Number: " + number);
        }
    }
}

public class InnerClassExample {
    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.display();
    }
}
```

Output:

```text
Number: 10
```

### Important points

- A regular inner class is associated with an object of the outer class.
- It can directly access all members of the outer class.
- An object is created using `outerObject.new InnerClass()`.
- A regular inner class cannot declare static members, except constant
  variables.

## 3. Anonymous Inner Class

An anonymous inner class is a class without a name. It is declared and
instantiated at the same time, usually for a one-time implementation of a
class or interface.

### Example with an abstract class

```java
abstract class Greeting {
    abstract void message();
}

public class AnonymousClassExample {
    public static void main(String[] args) {
        Greeting greeting = new Greeting() {
            @Override
            void message() {
                System.out.println("Hello from anonymous inner class");
            }
        };

        greeting.message();
    }
}
```

Output:

```text
Hello from anonymous inner class
```

### Example with an interface

```java
interface Calculator {
    int add(int first, int second);
}

public class AnonymousInterfaceExample {
    public static void main(String[] args) {
        Calculator calculator = new Calculator() {
            @Override
            public int add(int first, int second) {
                return first + second;
            }
        };

        System.out.println(calculator.add(5, 3));
    }
}
```

Output:

```text
8
```

### Important points

- An anonymous inner class has no class name.
- It is useful when behavior is needed only once.
- It can extend one class or implement one interface.
- It can access effectively final local variables from the enclosing method.
- For a single abstract method, a lambda expression is often a shorter
  alternative.

```java
Calculator calculator = (first, second) -> first + second;
```

## Difference Between Inner and Anonymous Inner Classes

| Inner class | Anonymous inner class |
| --- | --- |
| Has a class name | Does not have a class name |
| Can be reused | Usually used for one-time behavior |
| Declared separately inside the outer class | Declared and instantiated together |
| Created with `outer.new Inner()` | Created with `new ClassOrInterface() { ... }` |

## 4. Real-World Example: Payment Processing

A payment application may support multiple payment methods. The abstract
`Payment` class defines common behavior, while each payment type implements
its own `pay()` method.

The `Notifier` inner class is a helper that belongs to a payment object. The
anonymous inner class customizes the notifier for a one-time SMS notification.

```java
public class classes {
    public static void main(String[] args) {
        Payment payment = new CardPayment("CUST-101");
        payment.pay(2500);

        payment.printReceipt();
        payment.sendNotification();
    }

    abstract static class Payment {
        private final String customerId;

        Payment(String customerId) {
            this.customerId = customerId;
        }

        abstract void pay(double amount);

        void printReceipt() {
            System.out.println("Receipt generated for " + customerId);
        }

        void sendNotification() {
            Notifier notifier = new Notifier() {
                @Override
                void send(String message) {
                    System.out.println("SMS notification: " + message);
                }
            };
            notifier.send("Payment completed");
        }

        class Notifier {
            void send(String message) {
                System.out.println(message);
            }
        }
    }

    static class CardPayment extends Payment {
        CardPayment(String customerId) {
            super(customerId);
        }

        @Override
        void pay(double amount) {
            System.out.println("Paid Rs." + amount + " using a debit card");
        }
    }
}
```

Output:

```text
Paid Rs.2500.0 using a debit card
Receipt generated for CUST-101
SMS notification: Payment completed
```

In this example:

- `Payment` is an abstract class.
- `CardPayment` is the concrete subclass.
- `Notifier` is a named inner class.
- `new Notifier() { ... }` is an anonymous inner class.
- `Payment payment = new CardPayment(...)` demonstrates runtime
  polymorphism.