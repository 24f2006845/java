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
