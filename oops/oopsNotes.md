# OOPs Concepts Basics

Object-Oriented Programming (OOP) is a programming style where code is organized around objects and classes.

Java is an object-oriented language, so everything is based on classes and objects.

## 1. Class
A class is a blueprint or template.
It defines:
- what data an object has
- what actions an object can do

Example:
```java
class Calculator {
    int a;
    int b;

    public int add(int x, int y) {
        return x + y;
    }
}
```

Here, `Calculator` is a class.

## 2. Object
An object is an instance of a class.
It is created from the class blueprint.

Example:
```java
Calculator calc = new Calculator();
```

This means:
- `Calculator` = class name
- `calc` = object reference variable
- `new Calculator()` = creates a new object in memory

## 3. How Java understands a class
Java reads a class as a template and creates objects from it.

When we write:
```java
Calculator calc = new Calculator();
```

Java does this internally:
1. Looks for the class named `Calculator`
2. Allocates memory for a new object
3. Calls the constructor of that class
4. Stores the object in variable `calc`

So, `calc` is not the class itself; it is an object created from the class.

## 4. How methods are called inside a class
Methods belong to a class, and objects call those methods.

Example:
```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}

class Demo {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int result = calc.add(5, 3);
        System.out.println(result); // 8
    }
}
```

Here:
- `calc.add(5, 3)` means: call the `add` method on the object `calc`
- `calc` is the object
- `add` is the method
- `5, 3` are arguments passed to the method

## 5. Class inside class
In Java, a class can contain:
- fields
- methods
- constructors
- nested classes

But usually, we do not define one class inside another in normal programs.
We create an object of one class inside another class, like this:

```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}

class Demo {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println(calc.add(5, 3));
    }
}
```

This is the normal OOP approach:
- one class defines behavior
- another class uses that behavior by creating an object

## 6. Real-world analogy
Think of a class like a blueprint for a car.

```java
class Car {
    void drive() {
        System.out.println("Car is moving");
    }
}
```

Then:
```java
Car myCar = new Car();
myCar.drive();
```

`Car` is the blueprint.
`myCar` is the actual object.
`drive()` is the action performed by the object.

## 7. Why OOP is useful
OOP helps us:
- reuse code
- organize logic
- make programs easier to understand
- create real-world models in software

## 8. Summary
- Class = blueprint
- Object = real instance of class
- `new` keyword = creates an object
- Object calls methods using dot operator `.`
- Example: `calc.add(5, 3)`

## 9. Example with calculator
```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int result = calc.add(10, 5);
        System.out.println("Result: " + result);
    }
}
```

Output:
```java
Result: 15
```

## 10. Very important rule
When we call a method of an object, we use:
```java
objectName.methodName();
```

Example:
```java
calc.add(5, 3);
```

This means: use the object `calc` and call its `add` method.

## 11. Short key points
- Class defines behavior
- Object stores actual data
- We create object with `new`
- We call methods using the dot operator `.`
- Java creates memory for object automatically when `new` is used

This is the basic idea of OOP in Java.

## 12. What is a method in Java?
A method is a block of code that performs a specific task. Methods help us reuse code instead of writing the same logic repeatedly.

Basic syntax:
```java
accessModifier returnType methodName(parameters) {
    // code to execute
    return value;
}
```

Example:
```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```

In this example:
- `public` = access modifier
- `int` = return type
- `add` = method name
- `int a, int b` = parameters
- `return a + b` = value returned by the method

A method can also return nothing. In that case, use `void`:
```java
public void greet() {
    System.out.println("Hello");
}
```

## 13. Method overloading
Method overloading means having multiple methods with the same name in the same class, but with different parameter lists.

The parameters must differ by:
- number of parameters
- type of parameters
- order of parameter types

Example:
```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    double add(double a, double b) {
        return a + b;
    }
}
```

Calling the overloaded methods:
```java
Calculator calc = new Calculator();

System.out.println(calc.add(2, 3));       // 5
System.out.println(calc.add(2, 3, 4));    // 9
System.out.println(calc.add(2.5, 3.5));   // 6.0
```

Complete example:
```java
class Printer {
    void print(String message) {
        System.out.println("Text: " + message);
    }

    void print(int number) {
        System.out.println("Number: " + number);
    }

    void print(String message, int copies) {
        for (int i = 1; i <= copies; i++) {
            System.out.println(message);
        }
    }
}

class Demo {
    public static void main(String[] args) {
        Printer printer = new Printer();

        printer.print("Hello");       // calls print(String)
        printer.print(25);            // calls print(int)
        printer.print("Java", 2);     // calls print(String, int)
    }
}
```

Output:
```text
Text: Hello
Number: 25
Java
Java
```

## 14. Why method overloading helps
Method overloading helps because:
- we can use one meaningful method name for similar tasks
- code becomes easier to read and remember
- users of a class do not need different names such as `printText`, `printNumber`, and `printMany`
- it provides flexibility for different input types or different numbers of inputs

Without overloading, we might write:
```java
void printText(String message) { }
void printNumber(int number) { }
void printMany(String message, int copies) { }
```

With overloading, all related actions can use the name `print`.

## 15. Common mistakes in method overloading

### Mistake 1: Changing only the return type
This is not allowed:
```java
int getValue() {
    return 10;
}

double getValue() {       // Error: same parameters
    return 10.5;
}
```

The parameter list must change. Java does not choose a method using only its return type.

### Mistake 2: Using the same parameter list
These methods are duplicates:
```java
void show(int number) { }
void show(int value) { }   // Error: parameter name does not matter
```

Changing `number` to `value` does not create overloading. The parameter type and number are still the same.

### Mistake 3: Passing arguments that do not match
If no overloaded method can accept the arguments, Java shows a compilation error:
```java
Calculator calc = new Calculator();
calc.add("2", "3");       // Error: no add method accepts two String values
```

### Mistake 4: Making overloads confusing
Overloads should perform related tasks. Avoid using the same method name for completely different behavior because it makes the code difficult to understand.

Important rule:
- Changing only the return type is not method overloading.

For example, these methods are invalid because their parameters are the same:
```java
int calculate(int number) { return number; }
double calculate(int number) { return number; }
```

Method overloading is also called compile-time polymorphism because Java decides which method to call during compilation based on the arguments.

## 16. Encapsulation

Encapsulation means wrapping data and the methods that operate on that data inside a class.

It also means hiding the internal data from direct access and allowing access through controlled methods.

In Java, encapsulation is usually achieved by:
- declaring fields as `private`
- providing `public` getter methods to read the data
- providing `public` setter methods to update the data

### Example of encapsulation
```java
class Human {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class EncapsulationDemo {
    public static void main(String[] args) {
        Human human = new Human();

        human.setName("John");
        human.setAge(30);

        System.out.println("Name: " + human.getName());
        System.out.println("Age: " + human.getAge());
    }
}
```

In this example:
- `name` and `age` are `private`, so they cannot be accessed directly outside the `Human` class
- `getName()` and `getAge()` return the values of the private fields
- `setName()` and `setAge()` update the values of the private fields
- `this.name` refers to the field of the current object
- `name` in `setName(String name)` refers to the method parameter

The following direct access is not allowed:
```java
Human human = new Human();
human.age = 30;          // Error: age has private access
```

Instead, use the setter:
```java
human.setAge(30);
```

### Benefits of encapsulation
Encapsulation helps us:
- protect data from unwanted direct changes
- control how fields are read and updated
- add validation inside setter methods
- hide implementation details
- make code easier to maintain

For example, a setter can validate the value before storing it:
```java
public void setAge(int age) {
    if (age >= 0) {
        this.age = age;
    }
}
```

Now, invalid negative ages are not accepted by the class.

### Important points
- `private` members can be accessed directly only inside their own class
- `public` methods can be called from other classes
- A getter is used to read a value
- A setter is used to update a value
- Encapsulation is also called data hiding
- Encapsulation is different from abstraction: encapsulation hides and controls data, while abstraction hides unnecessary implementation details

## 17. `this` keyword

The `this` keyword refers to the current object.

It is used inside an instance method or constructor to access the fields and methods of the current object.

### Why use `this`?

The `this` keyword is especially useful when a method parameter has the same name as a class field.

Example:
```java
class Human {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

In `setName(String name)`:
- `this.name` refers to the `name` field of the current object
- `name` refers to the method parameter
- `this.name = name` stores the parameter value in the object's field

Without `this`, Java cannot clearly distinguish between the field and the parameter:
```java
public void setName(String name) {
    name = name;       // assigns the parameter to itself
}
```

The field remains unchanged in this case.

### Example with constructor

The `this` keyword can also be used in a constructor to initialize object fields:
```java
class Human {
    private String name;
    private int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void display() {
        System.out.println("Name: " + this.name);
        System.out.println("Age: " + this.age);
    }
}

public class ThisDemo {
    public static void main(String[] args) {
        Human human = new Human("Haider", 21);
        human.display();
    }
}
```

Output:
```text
Name: Haider
Age: 21
```

### Common uses of `this`

The `this` keyword can be used to:
- refer to the current object's fields
- call a method of the current object
- call another constructor in the same class using `this()`
- pass the current object as an argument
- return the current object from a method

Calling another method with `this`:
```java
class Message {
    void show() {
        System.out.println("Hello");
    }

    void display() {
        this.show();
    }
}
```

In most cases, Java allows us to omit `this` when there is no naming conflict:
```java
this.show();    // explicit
show();         // also valid
```

### Important points
- `this` refers to the current object
- `this` cannot be used in a `static` method because static methods do not belong to a specific object
- `this.fieldName` accesses the current object's field
- `this.methodName()` calls a method on the current object
- `this()` calls another constructor in the same class and must be the first statement in that constructor

## 18. Constructor

A constructor is a special member of a class that is used to initialize an object.

A constructor is called automatically when an object is created using the `new` keyword.

### Rules of a constructor

- A constructor must have the same name as the class
- A constructor does not have a return type, not even `void`
- A constructor is called automatically when an object is created
- Constructors are used to initialize object fields
- Constructors can be overloaded

Example:
```java
class Human {
    private String name;
    private int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}

public class ConstructorDemo {
    public static void main(String[] args) {
        Human human = new Human("Haider", 21);
        human.display();
    }
}
```

Output:
```text
Name: Haider
Age: 21
```

In this example:
- `Human(String name, int age)` is a parameterized constructor
- `new Human("Haider", 21)` calls the constructor
- `this.name = name` stores the constructor parameter in the object's field
- `this.age = age` stores the constructor parameter in the object's field

### Default constructor

If a class does not contain any constructor, Java provides a default no-argument constructor automatically.

Example:
```java
class Student {
    String name;
}

Student student = new Student();
```

Here, `new Student()` works because Java provides a default constructor.

However, if we create any constructor ourselves, Java no longer provides the default constructor automatically:
```java
class Student {
    String name;

    Student(String name) {
        this.name = name;
    }
}

Student student = new Student();    // Error: no no-argument constructor
```

To support both forms, define the no-argument constructor explicitly:
```java
class Student {
    String name;

    Student() {
        name = "Unknown";
    }

    Student(String name) {
        this.name = name;
    }
}
```

### Constructor overloading

Constructor overloading means having multiple constructors in the same class with different parameter lists.

```java
class Box {
    int length;
    int width;

    Box() {
        length = 1;
        width = 1;
    }

    Box(int length, int width) {
        this.length = length;
        this.width = width;
    }
}

Box smallBox = new Box();
Box largeBox = new Box(10, 5);
```

Java chooses the constructor based on the arguments passed while creating the object.

### Calling one constructor from another

The `this()` keyword can call another constructor in the same class. It must be the first statement of the constructor.

```java
class Employee {
    String name;
    int age;

    Employee() {
        this("Unknown", 0);
    }

    Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

The no-argument constructor calls the parameterized constructor, which avoids repeating initialization code.

### Constructor vs method

| Constructor | Method |
|---|---|
| Initializes an object | Performs an operation |
| Must have the class name | Can have any valid name |
| Has no return type | Has a return type or `void` |
| Called automatically when an object is created | Called explicitly |
| Cannot be inherited | Methods can be inherited |

### Important points

- Constructors initialize newly created objects
- A constructor is called with the `new` keyword
- A class can have more than one constructor
- Constructors can have parameters
- If no constructor is declared, Java supplies a default no-argument constructor
- Once a constructor is declared, define a no-argument constructor explicitly if it is needed

## 19. Inheritance

Inheritance allows one class to acquire the fields and methods of another class.
The existing class is called the **parent class**, **superclass**, or **base class**.
The new class is called the **child class**, **subclass**, or **derived class**.

The child class uses the `extends` keyword:

```java
class Child extends Parent {
    // additional fields and methods
}
```

### Why is inheritance useful?

Inheritance is useful when there is a genuine **is-a relationship** and multiple
classes share common behavior. It is not required for every Java program, but
it helps in these situations:

- **Code reuse:** Write shared behavior once in the parent instead of repeating it
  in every child class.
- **Specialization:** A child can add behavior that is specific to it.
- **Method overriding:** A child can provide its own implementation of a parent
  method.
- **Polymorphism:** A parent reference can refer to a child object, allowing
  Java to call the appropriate overridden method at runtime.

For example, a dog **is an** animal, so `Dog` can inherit common behavior from
`Animal`. A `Car` should not inherit from `Animal` because there is no valid
is-a relationship.

### Parent class and child class example

```java
class Animal {
    void eat() {
        System.out.println("Animal eats");
    }

    void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Dog barks");
    }

    void fetch() {
        System.out.println("Dog fetches the ball");
    }
}

public class inheritance {
    public static void main(String[] args) {
        Dog dog = new Dog();

        dog.eat();        // Inherited from Animal
        dog.makeSound();  // Dog's overridden version
        dog.fetch();      // Dog's own method

        Animal animal = new Dog();
        animal.makeSound(); // Runtime polymorphism: Dog barks
    }
}
```

Output:

```text
Animal eats
Dog barks
Dog fetches the ball
Dog barks
```

In this example:

- `Animal` is the parent class.
- `Dog` is the child class because it extends `Animal`.
- `Dog` reuses the `eat()` method without defining it again.
- `Dog` overrides `makeSound()` with behavior appropriate for a dog.
- `fetch()` belongs only to `Dog`.
- `Animal animal = new Dog()` shows polymorphism. Although the reference type
  is `Animal`, Java runs `Dog`'s overridden `makeSound()` method.

Inheritance should be used for a clear parent-child relationship. If classes
only share a few unrelated utility methods, a separate helper class or
composition is usually a better choice.

### Important inheritance points

- Use `extends` to inherit from a class.
- Java allows a class to extend only one class.
- A child class inherits accessible methods and fields from its parent.
- Constructors are not inherited, but a child constructor can call a parent
  constructor using `super()`.
- Use `@Override` when replacing a parent method so the compiler can verify it.
- Private members of the parent are not directly accessible in the child.
